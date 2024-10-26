package giis.model.Almacenero;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import giis.model.Estado;
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
		db.executeUpdate(sqlUpdate,par.getId());//CAMBIO
		
	}

	public boolean isOkIdAlmacenero(String id) {
		String sql = "SELECT nombre from ALMACENERO WHERE id=?;";
		List<Object[]> lista = db.executeQueryArray(sql,id);//CAMBIO
		if (lista.isEmpty()) {
			return false;
		}
		return true;
	}

	public void creaOrdenDeTrabajo(int almaceneroId) {
		String sqlInsert = "INSERT INTO OrdenTrabajo (fecha_creacion,estado,almacenero_id,incidencia) " 
						+ "VALUES (? , 'En recogida' , ? ,'Ninguna');";
		//String sqlInsert = "INSERT INTO OrdenTrabajo (fecha_creacion,estado,almacenero_id,incidencia) " + "VALUES ('"
		//+ LocalDate.now().toString() + "' , 'En recogida' , '" + almaceneroId + "' ,'Ninguna');";
		db.executeUpdate(sqlInsert,LocalDate.now().toString(),almaceneroId); //CAMBIO

	}

	public List<OrdenTrabajoRecord> getOrdenesDeTrabajoDelAlmaceneroPorId(int almaceneroId) {
		String sql = "SELECT id, fecha_creacion, estado, incidencia, almacenero_id FROM OrdenTrabajo WHERE almacenero_id=?;";

		//List<Object[]> lista = db.executeQueryArray(sql, new Object[0]);
		//	List<OrdenTrabajoRecord> listaPedidosNoRecogidos = new ArrayList<OrdenTrabajoRecord>();
		List<OrdenTrabajoRecord> li=db.executeQueryPojo(OrdenTrabajoRecord.class, sql,almaceneroId);
//		OrdenTrabajoRecord dto;
//		for (Object[] d : lista) {
//			dto = new OrdenTrabajoRecord();
//			dto.setId(d[0].toString());
//			dto.setFechaCreacion(d[1].toString());
//			String estado = d[2].toString();
//			switch (estado) {
//			case "En recogida" -> dto.setEstado(Estado.EnRecogida);
//			case "Pendiente de empaquetado" -> dto.setEstado(Estado.PendienteDeEmpaquetado);
//			case "Empaquetado" -> dto.setEstado(Estado.Empaquetado);
//			default -> throw new IllegalArgumentException();
//			}
//			if (d[3] == null) {
//				dto.setIncidencias("Sin incidencias");
//			} else {
//				dto.setIncidencias(d[3].toString());
//			}
//
//			dto.setAlmaceneroId(d[4].toString());

			//listaPedidosNoRecogidos.add(dto);
		//}

		return li;
	}

	public String creaEtiqueta(OrdenTrabajoRecord otr) {
		String sacarInfoEtiquetaEnvio = "SELECT Cliente.nombre, Cliente.apellidos, Cliente.direccion, Cliente.numeroTelefono "
				+ "FROM OrdenTrabajo JOIN Pedido ON Pedido.orden_trabajo_id = OrdenTrabajo.id JOIN Cliente "
				+ "ON Pedido.cliente_id = Cliente.id WHERE OrdenTrabajo.id = ?;";
		String actualizarCodigoBarras="UPDATE OrdenTrabajo SET codigoBarrasPaquete = ? WHERE id = ? ;";
		
		db.executeUpdate(actualizarCodigoBarras,otr.getCodigoBarras(),otr.getId());
		List<Object[]> lista = db.executeQueryArray(sacarInfoEtiquetaEnvio, new Object[0]);
		
		List<EtiquetaRecord> li=db.executeQueryPojo(EtiquetaRecord.class, sacarInfoEtiquetaEnvio,otr.getId());
		EtiquetaRecord edto = li.get(0);
		String direccion = "";
//		for (Object[] d : lista) {
//			String nombre = d[0].toString();
//			String apellidos = d[1].toString();
//			direccion = d[2].toString();
//			String numeroTeléfono = d[3].toString();
//			edto.setNombre(nombre);
//			edto.setApellidos(apellidos);
//			edto.setNumeroTeléfon(numeroTeléfono);
//			edto.setCodigoBarra(otr.getCodigoBarras());
//		}

		String paqueteId = otr.getId();
		edto.setPaqueteId(paqueteId);

		String etiqueta = "Etiqueta de envio:                                \n" + "Codigo de barras: "
				+ edto.getCodigoBarra() + "\n" + "Identificador del paquete: " + edto.getPaqueteId() + "\n"
				+ "Nombre cliente: " + edto.getNombre() + "\n" + "Apellido cliente: " + edto.getApellidos() + "\n"
				+ "Dirección cliente: " + direccion + "                   \n" + "Telefono cliente: "
				+ edto.getNumeroTeléfon();

		// System.out.println(etiqueta);

		return etiqueta;

	}

	public String creaAlbaran(OrdenTrabajoRecord otr) {
		db = new Database();
		db.createDatabase(false);
		db.loadDatabase();
		String sacarInfoAlbaran = "SELECT Producto.datosBasicos AS nombre, Producto.referencia, OrdenTrabajoProducto.cantidad "
				+ "FROM OrdenTrabajo JOIN OrdenTrabajoProducto ON OrdenTrabajo.id = OrdenTrabajoProducto.orden_trabajo_id "
				+ "JOIN Producto ON OrdenTrabajoProducto.producto_id = Producto.id WHERE OrdenTrabajo.id =?;";

		List<Object[]> lista = db.executeQueryArray(sacarInfoAlbaran,otr.getId()); //CAMBIO
		
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
		
		String albaran = "Datos del cliente y del paquete:"+creaEtiqueta(otr).substring(20);
		albaran += " Albarán:                                                                                  \n"
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
		

		albaran += "Datos del cliente y del paquete:"+creaEtiqueta(otr).substring(20);

		System.out.println(albaran);

		return albaran;

	}

	public static void main(String[] args) {
//		OrdenTrabajoRecord otr = new OrdenTrabajoRecord();
//		db=new Database();
//		db.createDatabase(false);
//		db.loadDatabase();
//		List<PedidoARecogerRecord> l=getPedidosPendientesRecogida();
//		for(PedidoARecogerRecord p:l) {
//			System.out.println(p.getId());
//		}
	}

}
