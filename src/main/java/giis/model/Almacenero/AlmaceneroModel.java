package giis.model.Almacenero;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import giis.util.Database;

public class AlmaceneroModel {

	private Database db;
	
	
	public AlmaceneroModel(Database db2) {
		this.db = db2;

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
	    String insertaOrdenTrabajoProductoRecogidos = "INSERT INTO OrdenTrabajoProductoRecogido (orden_trabajo_id, producto_id, cantidad) VALUES (?, ?, ?);";
	    String insertaPaqueteProducto = "INSERT INTO PaqueteProducto (orden_trabajo_id, producto_id, cantidad) VALUES (?, ?, ?);";
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
	                db.executeUpdate(insertaOrdenTrabajoProductoRecogidos, ordenTrabajoId, productoId, 0);	         
	                db.executeUpdate(insertaPaqueteProducto, ordenTrabajoId, productoId, 0);	

	                cargaActual += lote;
	                cantidad -= lote;

	                // Si la carga actual llega a 5, restablecer el contador y evaluar si hay más productos pendientes
	                if (cargaActual == 5) {
	                    if (cantidad > 0 || existenMasElementosPendientes(productosIdCantidad, pars, par)) {
	                        db.executeUpdate(sqlInsert, LocalDate.now().toString(), almaceneroId);
	                        idWoList = db.executeQueryArray(sacaIdOrdenTrabajo);
	                        ordenTrabajoId = idWoList.get(0)[0].toString();
	                    }
	                    cargaActual = 0; // Reinicia el contador de carga para la nueva orden de trabajo
	                }
	            }
	        }
	        // Actualiza el estado del pedido a "En recogida"
	        ponEnRecogidaElPedido(par);
	    }
	}

	/**
	 * Verifica si hay más elementos pendientes de procesar en la lista actual y en las órdenes restantes.
	 */
	private boolean existenMasElementosPendientes(List<Object[]> productosIdCantidad, List<PedidoARecogerRecord> pars, PedidoARecogerRecord pedidoActual) {
	    int indexActual = pars.indexOf(pedidoActual);
	    if (indexActual < pars.size() - 1) {
	        return true; // Hay más pedidos en la lista
	    }
	    return false; // No quedan elementos pendientes
	}



	public List<OrdenTrabajoRecord> getOrdenesDeTrabajoDelAlmaceneroPorId(int almaceneroId) {
		String sql = "SELECT id, fecha_creacion, estado, incidencia, almacenero_id FROM OrdenTrabajo WHERE almacenero_id=? AND estado='Pendiente de recogida';";
		List<OrdenTrabajoRecord> li=db.executeQueryPojo(OrdenTrabajoRecord.class, sql,almaceneroId);
		return li;
	}

	public String creaEtiqueta(OrdenTrabajoRecord otr) {
		String sacarInfoEtiquetaEnvio = "SELECT Cliente.nombre, Cliente.apellidos, Cliente.direccion, Cliente.numeroTelefono "
				+ "FROM PaqueteProducto JOIN Pedido ON Pedido.orden_trabajo_id = PaqueteProducto.orden_trabajo_id JOIN Cliente "
				+ "ON Pedido.cliente_id = Cliente.id WHERE PaqueteProducto.orden_trabajo_id = ?;";
		String sacaCodigoBarras="SELECT MAX(id) FROM Paquete";
		List<Object[]> codigoBarrasl=db.executeQueryArray(sacaCodigoBarras);
		String codigoBarras=codigoBarrasl.get(0)[0].toString();
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
		String sacarInfoParaElAlbaran = "SELECT Producto.datosBasicos AS nombre, Producto.referencia, ProductosPedido.cantidad, "
		        + "Cliente.nombre AS cliente_nombre, Cliente.apellidos, Cliente.direccion, Cliente.numeroTelefono "
		        + "FROM Pedido "
		        + "JOIN ProductosPedido ON Pedido.id = ProductosPedido.pedido_id "
		        + "JOIN Producto ON ProductosPedido.producto_id = Producto.id "
		        + "JOIN Cliente ON Pedido.cliente_id = Cliente.id "
		        + "WHERE Pedido.id = ?;";

		// Obtener todos los IDs de los pedidos
		List<Object[]> pedidosIds = db.executeQueryArray("SELECT id FROM Pedido");

		// Crear un acumulador para el texto del albarán
		StringBuilder albaranes = new StringBuilder();

		// Recorrer todos los pedidos
		for (Object[] pedidoId : pedidosIds) {
		    // Obtener los productos de cada pedido
		    List<Object[]> lista = db.executeQueryArray(sacarInfoParaElAlbaran, (int)pedidoId[0]);

		    if (!lista.isEmpty()) {
		        // Obtener la información del cliente desde el primer resultado (la misma para todos los productos)
		        String clienteNombre = lista.get(0)[3].toString();
		        String clienteApellidos = lista.get(0)[4].toString();
		        String clienteDireccion = lista.get(0)[5].toString();
		        String clienteTelefono = lista.get(0)[6].toString();

		        // Cálculo del ancho máximo de las columnas para productos
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

		        // Añadir los datos del cliente al principio del albarán
		        albaranes.append("Nombre del cliente: ").append(clienteNombre).append("\n")
		        		.append("Apellidos del cliente").append(clienteApellidos).append("\n")
		                .append("Dirección cliente: ").append(clienteDireccion).append("\n")
		                .append("Teléfono cliente: ").append(clienteTelefono).append("\n");

		        // Añadir el título de los productos y los productos del pedido
		        albaranes.append("Albarán para el pedido ID: ").append((int)pedidoId[0]).append("\n")
		                .append(String.format("|%-" + maxNombreLength + "s |%-" + maxReferenciaLength + "s  |%-" + maxCantidadLength + "s  |\n",
		                        "Nombre producto", "Referencia producto", "Cantidad"))
		                .append(String.format("|%-" + maxNombreLength + "s |%-" + maxReferenciaLength + "s  |%-" + maxCantidadLength + "s  |\n",
		                        "-".repeat(maxNombreLength), "-".repeat(maxReferenciaLength), "-".repeat(maxCantidadLength)));

		        // Agregar los productos del pedido
		        for (Object[] d : lista) {
		            String nombre = d[0].toString();
		            String referencia = d[1].toString();
		            String cantidad = d[2].toString();
		            albaranes.append(String.format("|%-" + maxNombreLength + "s | %-" + maxReferenciaLength + "s | %-" + maxCantidadLength + "s |\n",
		                    nombre, referencia, cantidad));
		        }

		    // Separar los albaranes por tres líneas
		    albaranes.append("\n");
		}
		}
		// Retornar todos los albaranes generados
		return albaranes.toString();


	}
	
	public List<ElementoARecogerDto> getElementosARecogerDeLaOrdenDeTrabajo(OrdenTrabajoRecord ordenTrabajoRecord) {
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

	public void actualizaIncidenciaOT(String incidencia, OrdenTrabajoRecord ordenTrabajoEnRecogida) {
		String actualizarIncidenciaWO="UPDATE OrdenTrabajo SET incidencia=? WHERE id=?;";
		db.executeUpdate(actualizarIncidenciaWO,incidencia,ordenTrabajoEnRecogida.getId());
	}

	public List<OrdenTrabajoRecord> getOrdenesDeTrabajoPendientesEmpaquetado() {
		String pedidosPendientesEmpaquetado="SELECT id, fecha_creacion, estado, incidencia, almacenero_id FROM OrdenTrabajo WHERE estado='Pendiente de empaquetado' AND (incidencia='' or incidencia is null);";
		List<OrdenTrabajoRecord> li=db.executeQueryPojo(OrdenTrabajoRecord.class, pedidosPendientesEmpaquetado);
		return li;
	}

	public List<ElementoARecogerDto> getElementosAEmpaquetarDeLaOrdenDeTrabajo(
			OrdenTrabajoRecord ordenTrabajoRecord) {
		
		String sql = "SELECT Producto.id AS codigoBarras,Producto.nombre, OrdenTrabajoProductoRecogido.cantidad AS cantidad, Localizacion.pasillo, Localizacion.posicion, "
				+ "Localizacion.estanteria , Localizacion.altura FROM OrdenTrabajoProductoRecogido JOIN Producto ON OrdenTrabajoProductoRecogido.producto_id = Producto.id JOIN Localizacion"
				+ " ON Producto.localizacion_id = Localizacion.id WHERE OrdenTrabajoProductoRecogido.orden_trabajo_id = ?  ORDER BY Localizacion.pasillo ASC, "
				+ "Localizacion.posicion ASC, CASE WHEN Localizacion.estanteria = 'Izquierda' THEN 0 ELSE 1 END;";
		List<ElementoARecogerDto> li=db.executeQueryPojo(ElementoARecogerDto.class, sql,ordenTrabajoRecord.getId());
		return li;
	}

	public int creaPaqueteParaElProcesoEmpaquetado() {
		String creacionPaquete="Insert into Caja DEFAULT VALUES;";
		db.executeUpdate(creacionPaquete);
		String sacaNumeroPaquete="SELECT MAX(id) FROM Caja";
		List<Object[]>idCaja=db.executeQueryArray(sacaNumeroPaquete);
		
		return Integer.parseInt(idCaja.get(0)[0].toString());
	}

	public void empaquetaProducto( OrdenTrabajoRecord ordenTrabajoEnEmpaquetado, int idCaja) {
		 String addAlPaquete = "INSERT INTO Paquete (caja_id, ordentrabajo_id, tipo) "
		            + "SELECT ?, ?, CASE WHEN Cliente.empresa = 1 THEN 'Nacional' ELSE 'Regional' END "
		            + "FROM Pedido "
		            + "JOIN Cliente ON Pedido.cliente_id = Cliente.id "
		            + "WHERE Pedido.orden_trabajo_id = ?;";
		db.executeUpdate(addAlPaquete, idCaja,ordenTrabajoEnEmpaquetado.getId(),ordenTrabajoEnEmpaquetado.getId());
		
	}

	public void ponLaWorkOrderAEmpaquetada(OrdenTrabajoRecord ordenTrabajoEnEmpaquetado) {
		String marcaComoEmpaquetada="UPDATE OrdenTrabajo SET estado='Empaquetado' WHERE id=?;";
		db.executeUpdate(marcaComoEmpaquetada, ordenTrabajoEnEmpaquetado.getId());
	}

	public void PonEnProcesoEmpaquetadoLaOt(OrdenTrabajoRecord ordenTrabajoEnEmpaquetado) {
		String marcaComoEmpaquetada="UPDATE OrdenTrabajo SET estado='En empaquetado' WHERE id=?;";
		db.executeUpdate(marcaComoEmpaquetada, ordenTrabajoEnEmpaquetado.getId());
	}

	public void actualizaCantidadYaRecogidaDeUnProducto(OrdenTrabajoRecord ordenTrabajoEnRecogida,
			ElementoARecogerDto elemento, int recogido) {
		String cambiarCantidadARecoger="UPDATE OrdenTrabajoProducto SET cantidad=cantidad-? WHERE orden_trabajo_id=? AND producto_id=?;";
		db.executeUpdate(cambiarCantidadARecoger,recogido, ordenTrabajoEnRecogida.getId(), elemento.getCodigoBarras());
		String cambiarCantidadRecogidos="UPDATE OrdenTrabajoProductoRecogido SET cantidad=cantidad+? WHERE orden_trabajo_id=? AND producto_id=?;";
		db.executeUpdate(cambiarCantidadRecogidos,recogido, ordenTrabajoEnRecogida.getId(), elemento.getCodigoBarras());
		
	}
	public void actualizaCantidadYaEmpaquetadaDeUnProducto(OrdenTrabajoRecord ordenTrabajoEnEmpaquetado,
			ElementoARecogerDto elemento, int recogido) {
		String cambiarCantidadARecoger="UPDATE OrdenTrabajoProductoRecogido SET cantidad=cantidad-? WHERE orden_trabajo_id=? AND producto_id=?;";
		db.executeUpdate(cambiarCantidadARecoger,recogido, ordenTrabajoEnEmpaquetado.getId(), elemento.getCodigoBarras());
		String cambiarCantidadRecogidos="UPDATE PaqueteProducto SET cantidad=cantidad+? WHERE orden_trabajo_id=? AND producto_id=?;";
		db.executeUpdate(cambiarCantidadRecogidos,recogido, ordenTrabajoEnEmpaquetado.getId(), elemento.getCodigoBarras());
		
	}

	public void eliminaElOrdenTrabajoProductoYActualizaOrdenTrabajoProductoRecogido(
			OrdenTrabajoRecord ordenTrabajoEnRecogida, ElementoARecogerDto elemento, int recogido) {
		String eliminaDeOTProducto="DELETE FROM OrdenTrabajoProducto WHERE orden_trabajo_id = ? AND producto_id = ?;";
		db.executeUpdate(eliminaDeOTProducto, ordenTrabajoEnRecogida.getId(), elemento.getCodigoBarras());
		String cambiarCantidadRecogidos="UPDATE OrdenTrabajoProductoRecogido SET cantidad=cantidad+? WHERE orden_trabajo_id=? AND producto_id=?;";
		db.executeUpdate(cambiarCantidadRecogidos,recogido, ordenTrabajoEnRecogida.getId(), elemento.getCodigoBarras());
		
	}

	public void eliminaElOrdenTrabajoProductoYActualizaPaqueteProducto(OrdenTrabajoRecord ordenTrabajoEnEmpaquetado,
			ElementoARecogerDto elemento, int recogido) {
		String eliminaDeOTProducto="DELETE FROM OrdenTrabajoProductoRecogido WHERE orden_trabajo_id = ? AND producto_id = ?;";
		db.executeUpdate(eliminaDeOTProducto, ordenTrabajoEnEmpaquetado.getId(), elemento.getCodigoBarras());
		String cambiarCantidadRecogidos="UPDATE PaqueteProducto SET cantidad=cantidad+? WHERE orden_trabajo_id=? AND producto_id=?;";
		db.executeUpdate(cambiarCantidadRecogidos,recogido, ordenTrabajoEnEmpaquetado.getId(), elemento.getCodigoBarras());
		
	}

	public void reciveVehiculo(String matricula, String tipo) {
		String creaVehiculo="INSERT INTO Vehiculo (matricula, tipo) VALUES (?, ?);";
		db.executeUpdate(creaVehiculo, matricula,tipo);
	}

	public List<PaqueteAExpedirDto> getPaquetesSegunElTipo(String tipo) {
		String sacaPaquetes="SELECT Paquete.id AS codigoBarras, Paquete.tipo, "
				+ "Cliente.nombre || ' ' || Cliente.apellidos AS destinatario "
				+ "FROM Paquete JOIN OrdenTrabajo ON Paquete.ordentrabajo_id = OrdenTrabajo.id "
				+ "JOIN Pedido ON OrdenTrabajo.id = Pedido.orden_trabajo_id JOIN Cliente ON Pedido.cliente_id = Cliente.id"
				+ " WHERE Paquete.tipo=?;";
		return db.executeQueryPojo(PaqueteAExpedirDto.class, sacaPaquetes, tipo);
	}

	public boolean esUnTipoDePaqueteValido(String codigoBarras) {
		String sacaSiEsta="SELECT "
				+ "Paquete.id AS paquete_id, "
				+ "Paquete.tipo AS tipo_paquete, "
				+ "Vehiculo.tipo AS tipo_vehiculo "
				+ "FROM Paquete "
				+ "JOIN Vehiculo ON Paquete.tipo = Vehiculo.tipo "
				+ "WHERE Paquete.id = ?;";
		List<Object[]> res=db.executeQueryArray(sacaSiEsta, codigoBarras);
		return !(res.size()==0 || res.get(0).length==0);
	}

	public void eliminaPaqueteYaEmpaquetado(String codigo) {
		String elimina="DELETE FROM Paquete WHERE id = ?;";
		db.executeUpdate(elimina,codigo);
	}

	public void eliminaElVehiculo(String tipoVehiculo) {
		String eliminaVehiculo="Delete from Vehiculo where tipo=?";
		db.executeUpdate(eliminaVehiculo, tipoVehiculo);
	}

	public List<FilaInformeVentasUsuarioDia> getInformeVentasPorUsuarioYDia() {
		String infoInformeVentas="SELECT p.fecha AS dia, SUM(CASE WHEN c.empresa = 0 "
				+ "THEN p.total ELSE 0 END) AS particular, SUM(CASE WHEN c.empresa = 1 "
				+ "THEN p.total ELSE 0 END) AS empresa, SUM(p.total) AS total FROM Pedido p "
				+ "JOIN Cliente c ON p.cliente_id = c.id GROUP BY p.fecha Order by p.fecha;";
		
		List<FilaInformeVentasUsuarioDia> tabla=db.executeQueryPojo(FilaInformeVentasUsuarioDia.class, infoInformeVentas);
		double totalParticular = 0;
		double totalEmpresa = 0;
		double totalTotal = 0;
		for (FilaInformeVentasUsuarioDia fila : tabla) {
		    totalParticular += fila.getParticular();
		    totalEmpresa += fila.getEmpresa();
		    totalTotal +=fila.getTotal();
		}
		FilaInformeVentasUsuarioDia totalesColumnas=new FilaInformeVentasUsuarioDia();
		totalesColumnas.setDia("Total");
		totalesColumnas.setParticular(totalParticular);
		totalesColumnas.setEmpresa(totalEmpresa);
		totalesColumnas.setTotal(totalTotal);
		tabla.add(totalesColumnas);
		
		return tabla;
	}

	public List<String> getTodasEmpresasYTotalPalModel() {
		String sacaNombresTodasEmpresas=" SELECT DISTINCT c.nombre AS empresa FROM Pedido p"
				+ " JOIN Cliente c ON p.cliente_id = c.id"
				+ " WHERE c.empresa = 1"
				+ " ORDER BY c.nombre;";
		List<String> columnNames = new ArrayList<>();
		List<Object[]> nombres=db.executeQueryArray(sacaNombresTodasEmpresas);
		for(Object[] o: nombres) {
			columnNames.add(o[0].toString());
		}
		return columnNames;
	}
	
	public List<String> getIdsEmpresas() {
		String sacaNombresTodasEmpresas=" SELECT c.id AS empresa FROM Pedido p"
				+ " JOIN Cliente c ON p.cliente_id = c.id"
				+ " WHERE c.empresa = 1"
				+ " ORDER BY c.nombre;";
		List<String> columnNames = new ArrayList<>();
		List<Object[]> nombres=db.executeQueryArray(sacaNombresTodasEmpresas);
		for(Object[] o: nombres) {
			columnNames.add(o[0].toString());
		}
		return columnNames;
	}
	
	public String sacaNombreEmpresaPorId(String id) {
		String sacaNombreEmpresa="SELECT nombre From Cliente Where id=?";
		return db.executeQueryArray(sacaNombreEmpresa, id).get(0)[0].toString() ;
	}

	public DefaultTableModel getInformeVentasSegunEmpresas(List<String> ids) {
		String infoPorDiaEmpresaEspecifica ="SELECT DATE(p.fecha) AS dia,"
				+ " COALESCE(SUM(p.total), 0) AS total_gastado, c.nombre"
				+ "	FROM Pedido p"
				+ "	JOIN Cliente c ON p.cliente_id = c.id"
				+ "	WHERE c.id = ?"
				+ "	GROUP BY DATE(p.fecha)"
				+ "	ORDER BY dia;";
		
		
		// Mapa para los resultados
		Map<String, Map<String, Double>> data = new LinkedHashMap<>();

		// Inicializar un mapa para los totales por empresa
	    Map<String, Double> totalPorEmpresa = new HashMap<>();
	    for (String empresa : ids) {
	        totalPorEmpresa.put(sacaNombreEmpresaPorId(empresa), 0.0); // Inicializamos el total para cada empresa
	    }
		for (String empresa : ids) { // empresas: lista de nombres de empresas
			List<Object[]> infoPorDiaEmpresa=db.executeQueryArray(infoPorDiaEmpresaEspecifica, empresa);
			if(!infoPorDiaEmpresa.isEmpty()) {
			    double totalColumnaEmpresa = 0;
				double total=0;
				for (Object[] row : infoPorDiaEmpresa) {
				    String dia = row[0].toString();
				    total = Double.parseDouble(row[1].toString());

				    // Inicializar el mapa para el día si no existe
				    data.putIfAbsent(dia, new HashMap<>());

				    // Agregar el total de la empresa al día correspondiente
				    data.get(dia).put(sacaNombreEmpresaPorId(empresa), total);
				    totalColumnaEmpresa += total;
				}
				totalColumnaEmpresa/=infoPorDiaEmpresa.size();
				// Acumular el total de cada empresa
				totalPorEmpresa.put(
				        sacaNombreEmpresaPorId(empresa),
				        totalPorEmpresa.get(sacaNombreEmpresaPorId(empresa)) + totalColumnaEmpresa
				    );
			}
		}
		
		 // Obtener las columnas (empresas + total)
		// Usar TreeSet para asegurar el orden
	    Set<String> empresasOrdenadas = new TreeSet<>();
	    for (Map<String, Double> diaData : data.values()) {
	    	empresasOrdenadas.addAll(diaData.keySet());
	    }
	    
	    // Asegurarse de agregar la columna Total y Día
	    List<String> columnNames = new ArrayList<>();
	    columnNames.add("Dia");
	    columnNames.addAll(empresasOrdenadas);  
	    columnNames.add("Total");
	    // Paso 2: Crear las filas
	    List<Object[]> rows = new ArrayList<>();
	    for (String dia : data.keySet()) {
	        Map<String, Double> diaData = data.get(dia);
	        Object[] row = new Object[columnNames.size()];

	        // Primer valor es el día
	        row[0] = dia;

	        // Agregar los valores de cada empresa
	        double totalDia = 0.0;
	        for (int i = 1; i <= empresasOrdenadas.size(); i++) {
	            String empresa = columnNames.get(i);
	            double total = diaData.getOrDefault(empresa, 0.0);
	            row[i] = total;
	            totalDia += total;
	        }

	        // Colocar el total acumulado en la última columna
	        row[columnNames.size() - 1] = totalDia;

	        // Añadir la fila a la lista de filas
	        rows.add(row);
	    }

	 // Fila con los totales por empresa (total por columna)
	    Object[] totalRow = new Object[columnNames.size()];
	    totalRow[0] = "Total"; // La primera columna en vez de la fecha será "Total"

	    // Agregar los totales por empresa a las columnas correspondientes
	    double totalGlobal = 0.0;
	    for (int i = 1; i <= empresasOrdenadas.size(); i++) {
	        String empresa = columnNames.get(i);
	        double totalEmpresa = totalPorEmpresa.get(empresa);
	        totalRow[i] = totalEmpresa;
	        totalGlobal += totalEmpresa;
	    }

	    // Colocar el total global al final
	    totalRow[columnNames.size() - 1] = totalGlobal;

	    // Añadir la fila de totales por columna
	    rows.add(totalRow);

	    
	    // Crear el DefaultTableModel con los datos
	    return new DefaultTableModel(rows.toArray(new Object[0][0]), columnNames.toArray());
		
	}
	
	public List<String> getEmpleados() {
		String sacaEmpleados="Select id from Almacenero";
		List<String> columnNames = new ArrayList<>();
		List<Object[]> nombres=db.executeQueryArray(sacaEmpleados);
		for(Object[] o: nombres) {
			columnNames.add(o[0].toString());
		}
		return columnNames;
	}

	public TableModel getInformeOTEmpleadoDia(List<String> empleados) {
		String infoPorDiaEmpleadoEspecifico = "SELECT DATE(O.dia) AS dia, "
	            + "A.id AS empleado, "
	            + "COUNT(DISTINCT OT.id) AS ordenes_recogidas "
	            + "FROM OrdenTrabajoRecogidaEmpleadoDia O "
	            + "JOIN Almacenero A ON O.almacenero_id = A.id "
	            + "JOIN OrdenTrabajo OT ON O.ordenTrabajo_id = OT.id "
	            + "WHERE A.id IN (?) "  
	            + "GROUP BY O.dia "
	            + "ORDER BY O.dia desc;";

		// Mapa para los resultados
		Map<String, Map<String, Integer>> data = new LinkedHashMap<>();
		// Inicializar un mapa para los totales por empleado
		Map<String, Integer> totalPorEmpleado = new HashMap<>();
		for (String empleado : empleados) {
		    totalPorEmpleado.put(empleado, 0);
		}

		for (String empleado : empleados) {
		    List<Object[]> infoPorDiaEmpleado = db.executeQueryArray(infoPorDiaEmpleadoEspecifico, empleado);
		    if (!infoPorDiaEmpleado.isEmpty()) {
		        // Para cada día y empleado, contamos el número de OTs
		        for (Object[] row : infoPorDiaEmpleado) {
		            String dia = row[0].toString();
		            String nombreEmpleado = row[1].toString();
		            int ordenesRecogidas = Integer.parseInt(row[2].toString());

		            // Inicializar el mapa para el día si no existe
		            data.putIfAbsent(dia, new HashMap<>());
		            // Agregar el número de OTs recogidas por ese empleado en ese día
		            data.get(dia).put(nombreEmpleado, ordenesRecogidas);

		            // Acumular el total de OTs recogidas por el empleado
		            totalPorEmpleado.put(nombreEmpleado, totalPorEmpleado.get(nombreEmpleado) + ordenesRecogidas);
		        }
		    }
		}

		// Obtener las columnas (empleados + total)
		Set<String> empleadosOrdenados = new TreeSet<>();
		for (Map<String, Integer> diaData : data.values()) {
		    empleadosOrdenados.addAll(diaData.keySet());
		}

		// Asegurarse de agregar la columna Total y Día
		List<String> columnNames = new ArrayList<>();
		columnNames.add("Dia");
		columnNames.addAll(empleadosOrdenados);  
		columnNames.add("Total");

		// Paso 2: Crear las filas
		List<Object[]> rows = new ArrayList<>();
		for (String dia : data.keySet()) {
		    Map<String, Integer> diaData = data.get(dia);
		    Object[] row = new Object[columnNames.size()];

		    // Primer valor es el día
		    row[0] = dia;

		    // Agregar los valores de cada empleado
		    int totalDia = 0;
		    for (int i = 1; i <= empleadosOrdenados.size(); i++) {
		        String empleado = columnNames.get(i);
		        int ordenes = diaData.getOrDefault(empleado, 0);
		        row[i] = ordenes;
		        totalDia += ordenes;
		    }

		    // Colocar el total acumulado en la última columna
		    row[columnNames.size() - 1] = totalDia;

		    // Añadir la fila a la lista de filas
		    rows.add(row);
		}

		// Fila con los totales por empleado (total por columna)
		Object[] totalRow = new Object[columnNames.size()];
		totalRow[0] = "Total";

		// Agregar los totales por empleado a las columnas correspondientes
		int totalGlobal = 0;
		for (int i = 1; i <= empleadosOrdenados.size(); i++) {
		    String empleado = columnNames.get(i);
		    int totalEmpleado = totalPorEmpleado.get(empleado);
		    totalRow[i] = totalEmpleado;
		    totalGlobal += totalEmpleado;
		}

		// Colocar el total global al final
		totalRow[columnNames.size() - 1] = totalGlobal;

		// Añadir la fila de totales por columna
		rows.add(totalRow);

		// Crear el DefaultTableModel con los datos
		return new DefaultTableModel(rows.toArray(new Object[0][0]), columnNames.toArray());

	}

	public void guardaOTRecogidaConFechaAlmacenero(int almaceneroId, OrdenTrabajoRecord ordenTrabajoEnRecogida) {
		String guardaOrdenTrabajoRecogida="INSERT INTO OrdenTrabajoRecogidaEmpleadoDia (almacenero_id, dia, ordenTrabajo_id) VALUES (?, ?, ?); ";
		db.executeUpdate(guardaOrdenTrabajoRecogida, almaceneroId,LocalDate.now().toString(),ordenTrabajoEnRecogida.getId());
	}
	
	public void guardaProductoRecogidoConFechaAlmacenero(int almaceneroId, ElementoARecogerDto elemento, int recogido) {
		String guardaOrdenTrabajoRecogida="INSERT INTO ProductoRecogidoEmpleadoDia (almacenero_id, dia, producto_id, cantidad) VALUES (?, ?, ?, ?);";
		db.executeUpdate(guardaOrdenTrabajoRecogida, almaceneroId,LocalDate.now().toString(),elemento.getCodigoBarras(),recogido);
	}

	public TableModel getInformeProductosEmpleadoDia(List<String> empleados) {
		String infoPorDiaEmpleadoEspecifico = "SELECT DATE(PR.dia) AS dia, "
		        + "A.id AS empleado, "
		        + "SUM(PR.cantidad) AS cantidad_recogida "
		        + "FROM ProductoRecogidoEmpleadoDia PR "
		        + "JOIN Almacenero A ON PR.almacenero_id = A.id "
		        + "WHERE A.id IN (?) "  // Filtrando por empleados específicos
		        + "GROUP BY PR.dia, A.id "
		        + "ORDER BY PR.dia DESC;";

		// Mapa para los resultados
		Map<String, Map<String, Integer>> data = new LinkedHashMap<>();
		// Inicializar un mapa para los totales por empleado
		Map<String, Integer> totalPorEmpleado = new HashMap<>();
		for (String empleado : empleados) {
		    totalPorEmpleado.put(empleado, 0);  // Inicializamos el total para cada empleado
		}

		// Recoger la información por empleado y día
		for (String empleado : empleados) {
		    List<Object[]> infoPorDiaEmpleado = db.executeQueryArray(infoPorDiaEmpleadoEspecifico, empleado);
		    if (!infoPorDiaEmpleado.isEmpty()) {
		        // Para cada día y empleado, sumamos la cantidad total de productos recogidos
		        for (Object[] row : infoPorDiaEmpleado) {
		            String dia = row[0].toString();
		            String nombreEmpleado = row[1].toString();
		            if(row[2]!=null) {
		            	int cantidadRecogida = Integer.parseInt(row[2].toString());

			            // Inicializar el mapa para el día si no existe
			            data.putIfAbsent(dia, new HashMap<>());
			            // Agregar la cantidad total de productos recogidos por ese empleado en ese día
			            data.get(dia).put(nombreEmpleado, cantidadRecogida);

			            // Acumular el total de productos recogidos por el empleado
			            totalPorEmpleado.put(nombreEmpleado, totalPorEmpleado.get(nombreEmpleado) + cantidadRecogida);
		            }
		            
		        }
		    }
		}

		// Obtener las columnas (empleados + total)
		Set<String> empleadosOrdenados = new TreeSet<>();
		for (Map<String, Integer> diaData : data.values()) {
		    empleadosOrdenados.addAll(diaData.keySet());
		}

		// Asegurarse de agregar la columna Total y Día
		List<String> columnNames = new ArrayList<>();
		columnNames.add("Dia");
		columnNames.addAll(empleadosOrdenados);  
		columnNames.add("Total");

		// Crear las filas
		List<Object[]> rows = new ArrayList<>();
		for (String dia : data.keySet()) {
		    Map<String, Integer> diaData = data.get(dia);
		    Object[] row = new Object[columnNames.size()];

		    // Primer valor es el día
		    row[0] = dia;

		    // Agregar los valores de cada empleado
		    int totalDia = 0;
		    for (int i = 1; i <= empleadosOrdenados.size(); i++) {
		        String empleado = columnNames.get(i);
		        int cantidad = diaData.getOrDefault(empleado, 0);
		        row[i] = cantidad;
		        totalDia += cantidad;
		    }

		    // Colocar el total acumulado en la última columna
		    row[columnNames.size() - 1] = totalDia;

		    // Añadir la fila a la lista de filas
		    rows.add(row);
		}

		// Fila con los totales por empleado (total por columna)
		Object[] totalRow = new Object[columnNames.size()];
		totalRow[0] = "Total"; // La primera columna en vez de la fecha será "Total"

		// Agregar los totales por empleado a las columnas correspondientes
		int totalGlobal = 0;
		for (int i = 1; i <= empleadosOrdenados.size(); i++) {
		    String empleado = columnNames.get(i);
		    int totalEmpleado = totalPorEmpleado.get(empleado);
		    totalRow[i] = totalEmpleado;
		    totalGlobal += totalEmpleado;
		}

		// Colocar el total global al final
		totalRow[columnNames.size() - 1] = totalGlobal;

		// Añadir la fila de totales por columna
		rows.add(totalRow);

		// Crear el DefaultTableModel con los datos
		return new DefaultTableModel(rows.toArray(new Object[0][0]), columnNames.toArray());
	}

	

	
	

	

	

	
}
