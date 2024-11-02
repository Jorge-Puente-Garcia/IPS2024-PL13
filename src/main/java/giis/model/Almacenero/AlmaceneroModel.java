package giis.model.Almacenero;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import giis.util.Database;

public class AlmaceneroModel {

	private Database db;
	
	
	public AlmaceneroModel() {
		this.db = new Database();
		db.createDatabase(false);
		db.loadDatabase();

	}

	public List<PedidoARecogerRecord> getPedidosPendientesRecogida() {
		String sql = "SELECT p.id ,p.fecha, SUM(pp.cantidad) as Tamano, p.estado FROM Pedido p LEFT JOIN ProductosPedido pp "
				+ "ON pp.pedido_id = p.id WHERE p.estado = 'Pendiente de recogida' GROUP BY p.id, p.fecha, p.estado ORDER BY p.fecha;";

		List<PedidoARecogerRecord> li=db.executeQueryPojo(PedidoARecogerRecord.class, sql);
		return li;
	}

	public void ponEnRecogidaElPedido(PedidoARecogerRecord par) {
		String sqlUpdate = "UPDATE PEDIDO SET estado='Recogido' WHERE id=?;";
		db.executeUpdate(sqlUpdate,par.getId());
		
	}

	public boolean isOkIdAlmacenero(String id) {
		String sql = "SELECT nombre from ALMACENERO WHERE id=?;";
		List<Object[]> lista = db.executeQueryArray(sql,id);//CAMBIO
		if (lista.isEmpty()) {
			return false;
		}
		return true;
	}

	public void creaOrdenDeTrabajo(int almaceneroId, List<PedidoARecogerRecord> pars) {
	    String sqlInsert = "INSERT INTO OrdenTrabajo (fecha_creacion, estado, almacenero_id, incidencia) " 
	                    + "VALUES (?, 'Pendiente de recogida', ?, '');";
	    db.executeUpdate(sqlInsert, LocalDate.now().toString(), almaceneroId); // Inserta la Orden de Trabajo inicial

	    // Obtener el ID de la Orden de Trabajo actual
	    String sacaIdOrdenTrabajo = "SELECT MAX(id) AS id FROM OrdenTrabajo;";
	    List<Object[]> idWoList = db.executeQueryArray(sacaIdOrdenTrabajo);
	    String ordenTrabajoId = idWoList.get(0)[0].toString();
	    String sacaProductosPedido = "SELECT pp.producto_id, pp.cantidad FROM Pedido ped JOIN ProductosPedido pp ON ped.id = pp.pedido_id WHERE ped.id = ?;";
	    String insertaOrdenTrabajoProducto = "INSERT INTO OrdenTrabajoProducto (orden_trabajo_id, producto_id, cantidad) VALUES (?, ?, ?);";

	    int cargaActual = 0; // Carga actual de la orden de trabajo

	    for (PedidoARecogerRecord par : pars) {
	        // Consulta la lista de productos y cantidades asociadas al pedido
	        List<Object[]> productosIdCantidad = db.executeQueryArray(sacaProductosPedido, par.getId());

	        for (Object[] s : productosIdCantidad) {
	            int productoId = Integer.parseInt(s[0].toString());
	            int cantidad = Integer.parseInt(s[1].toString());

	            // Divide la cantidad en lotes de tamaño máximo 5
	            while (cantidad > 0) {
	                int lote = Math.min(cantidad, 5 - cargaActual); // Tamaño del lote restante para completar 5
	                db.executeUpdate(insertaOrdenTrabajoProducto, ordenTrabajoId, productoId, lote);

	                // Actualiza el contador de carga actual y la cantidad restante
	                cargaActual += lote;
	                cantidad -= lote;

	                // Si la carga actual llega a 5, crear nueva orden y restablecer el contador
	                if (cargaActual == 5) {
	                    db.executeUpdate(sqlInsert, LocalDate.now().toString(), almaceneroId);
	                    idWoList = db.executeQueryArray(sacaIdOrdenTrabajo);
	                    ordenTrabajoId = idWoList.get(0)[0].toString();
	                    cargaActual = 0; // Reinicia el contador de carga para la nueva orden de trabajo
	                }
	            }
	        }
	        // Actualiza el estado del pedido a "En recogida"
	        ponEnRecogidaElPedido(par);
	    }
	}

	public List<OrdenTrabajoRecord> getOrdenesDeTrabajoDelAlmaceneroPorId(int almaceneroId) {
		String sql = "SELECT id, fecha_creacion, estado, incidencia, almacenero_id FROM OrdenTrabajo WHERE almacenero_id=? AND estado='Pendiente de recogida';";
		List<OrdenTrabajoRecord> li=db.executeQueryPojo(OrdenTrabajoRecord.class, sql,almaceneroId);
		return li;
	}

	public String creaEtiqueta(OrdenTrabajoRecord otr) {
		String sacarInfoEtiquetaEnvio = "SELECT Cliente.nombre, Cliente.apellidos, Cliente.direccion, Cliente.numeroTelefono "
				+ "FROM OrdenTrabajo JOIN Pedido ON Pedido.orden_trabajo_id = OrdenTrabajo.id JOIN Cliente "
				+ "ON Pedido.cliente_id = Cliente.id WHERE OrdenTrabajo.id = ?;";
		String actualizarCodigoBarras="UPDATE Paquete SET codigoBarrasPaquete = ? WHERE id = ? ;";
		String codigoBarras=UUID.randomUUID().toString();
		db.executeUpdate(actualizarCodigoBarras,codigoBarras,otr.getId());
		List<EtiquetaRecord> li=db.executeQueryPojo(EtiquetaRecord.class, sacarInfoEtiquetaEnvio,otr.getId());
		EtiquetaRecord edto = li.get(0);

		String paqueteId = otr.getId();
		edto.setPaqueteId(paqueteId);

		String etiqueta = "Etiqueta de envio:                                \n" + "Codigo de barras: "
				+ codigoBarras + "\n" + "Identificador del paquete: " + edto.getPaqueteId() + "\n"
				+ "Nombre cliente: " + edto.getNombre() + "\n" + "Apellido cliente: " + edto.getApellidos() + "\n"
				+ "Dirección cliente: " + edto.getDireccion() + "                   \n" + "Telefono cliente: "
				+ edto.getNumeroTelefono();

		return etiqueta;

	}

	public String creaAlbaran(OrdenTrabajoRecord otr) {
		db = new Database();
		db.createDatabase(false);
		db.loadDatabase();
		String sacarInfoParaElAlbaran = "SELECT Producto.datosBasicos AS nombre, Producto.referencia, OrdenTrabajoProducto.cantidad "
				+ "FROM OrdenTrabajo JOIN OrdenTrabajoProducto ON OrdenTrabajo.id = OrdenTrabajoProducto.orden_trabajo_id "
				+ "JOIN Producto ON OrdenTrabajoProducto.producto_id = Producto.id WHERE OrdenTrabajo.id =?;";

		List<Object[]> lista = db.executeQueryArray(sacarInfoParaElAlbaran,otr.getId()); //CAMBIO
		
		// Cálculo del ancho máximo de las columnas
		int maxNombreLength = 0;
		int maxReferenciaLength = 0;
		int maxCantidadLength = 0;
		for (Object[] d : lista) {
			String nombre = d[0].toString();
			String referencia = d[1].toString();
			String cantidad = d[2].toString();

			maxNombreLength = Math.max(maxNombreLength, nombre.length());
			maxReferenciaLength = Math.max(maxReferenciaLength, referencia.length());
			maxCantidadLength = Math.max(maxCantidadLength, cantidad.length());
		}

		// Se mantiene un mínimo de ancho para mantener el formato de 1 hoja
		maxNombreLength = Math.max(maxNombreLength, 31); // Mínimo 31
		maxReferenciaLength = Math.max(maxReferenciaLength, 31); // Mínimo 31
		maxCantidadLength = Math.max(maxCantidadLength, 20); // Mínimo 20
		
		String albaran = "Datos del cliente y del paquete:"+creaEtiqueta(otr).substring(20)+"\n";
		albaran += " Albarán:\n"
	                + String.format("|%-" + maxNombreLength + "s |%-" + maxReferenciaLength + "s  |%-" + maxCantidadLength + "s  |\n",
	                " Nombre producto ", " Referencia producto ", " Cantidad ")
	                + String.format("|%-" + maxNombreLength + "s |%-" + maxReferenciaLength + "s  |%-" + maxCantidadLength + "s  |\n",
	                "-".repeat(maxNombreLength), "-".repeat(maxReferenciaLength), "-".repeat(maxCantidadLength));
		 
		// Ahora, crear el albarán con el formato correcto
		for (Object[] d : lista) {
			String nombre = d[0].toString();
			String referencia = d[1].toString();
			String cantidad = d[2].toString();
			albaran += String.format(
					"|%-" + maxNombreLength + "s | %-" + maxReferenciaLength + "s | %-" + maxCantidadLength + "s |\n",
					nombre, referencia, cantidad)+ String.format("|%-" + maxNombreLength + "s |%-" + maxReferenciaLength + "s  |%-" + maxCantidadLength + "s  |\n",
			                "-".repeat(maxNombreLength), "-".repeat(maxReferenciaLength), "-".repeat(maxCantidadLength));;
		}
		
		String.format("|%-" + maxNombreLength + "s|%-" + maxReferenciaLength + "s|%-" + maxCantidadLength + "s|\n",
                " Nombre producto ", " Referencia producto ", " Cantidad ");
		

		return albaran;

	}
	
	public List<ElementoARecogerDto> getElementosARecogerDeLaOrdenDeTrabajo(OrdenTrabajoRecord ordenTrabajoRecord) {
		String sacaTodo="Select * from ordenTrabajoProducto where OrdenTrabajoProducto.orden_trabajo_id ="+ordenTrabajoRecord.getId()+";";
		List<Object[]> todo=db.executeQueryArray(sacaTodo);
		System.out.println(ordenTrabajoRecord.getId());
		String sql = "SELECT Producto.id AS codigoBarras,Producto.nombre, OrdenTrabajoProducto.cantidad AS cantidad, Localizacion.pasillo, Localizacion.posicion, "
				+ "Localizacion.estanteria , Localizacion.altura FROM OrdenTrabajoProducto JOIN Producto ON OrdenTrabajoProducto.producto_id = Producto.id JOIN Localizacion"
				+ " ON Producto.localizacion_id = Localizacion.id WHERE OrdenTrabajoProducto.orden_trabajo_id = ? ORDER BY Localizacion.pasillo ASC, "
				+ "Localizacion.posicion ASC, CASE WHEN Localizacion.estanteria = 'Izquierda' THEN 0 ELSE 1 END;";
		List<ElementoARecogerDto> li=db.executeQueryPojo(ElementoARecogerDto.class, sql,ordenTrabajoRecord.getId());
		return li;
	}
	
	public void updateWorkOrderParaQuePaseAEnProcesoDeRecogida(OrdenTrabajoRecord ordenTrabajoRecord) {
		String actualizaLasOt= "UPDATE OrdenTrabajo SET estado='En recogida' WHERE id=?;";
		db.executeUpdate(actualizaLasOt, ordenTrabajoRecord.getId());
	}
	
	public void updateToPendienteDeEmpaquetadoElProducto(OrdenTrabajoRecord ordenTrabajoEnRecogida) {
		String updateAPendienteEmpaquetado="UPDATE OrdenTrabajo SET estado='Pendiente de empaquetado' WHERE id=?;";
		db.executeUpdate(updateAPendienteEmpaquetado, ordenTrabajoEnRecogida.getId());
		
	}
	
	
//	public static void main(String[] args) {
//		OrdenTrabajoRecord otr = new OrdenTrabajoRecord();
//		db=new Database();
//		db.createDatabase(false);
//		db.loadDatabase();
//		List<PedidoARecogerRecord> l=getPedidosPendientesRecogida();
//		for(PedidoARecogerRecord p:l) {
//			System.out.println(p.getId());
//		}
//	}

	public void actualizaIncidenciaOT(String incidencia, OrdenTrabajoRecord ordenTrabajoEnRecogida) {
		String actualizarIncidenciaWO="UPDATE OrdenTrabajo SET incidencia=? WHERE id=?;";
		db.executeUpdate(actualizarIncidenciaWO,incidencia,ordenTrabajoEnRecogida.getId());
	}

	public List<OrdenTrabajoRecord> getOrdenesDeTrabajoPendientesEmpaquetado() {
		String pedidosPendientesEmpaquetado="SELECT id, fecha_creacion, estado, incidencia, almacenero_id FROM OrdenTrabajo WHERE estado='Pendiente de empaquetado' AND incidencia='' or incidencia is null;";
		List<OrdenTrabajoRecord> li=db.executeQueryPojo(OrdenTrabajoRecord.class, pedidosPendientesEmpaquetado);
		return li;
	}

	

	

	
}
