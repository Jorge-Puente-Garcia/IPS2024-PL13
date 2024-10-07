package giis.model;

import java.util.ArrayList;
import java.util.List;

import giis.util.Database;

public class LectorBd {

	private static Database db = new Database();

	public static List<PedidoDto> getPedidosPendientesRecogida() {
		db.createDatabase(false);
		db.loadDatabase();
		String sql = "SELECT p.fecha, SUM(pp.cantidad), p.estado FROM Pedido p LEFT JOIN PedidoProducto pp "
				+ "ON pp.pedido_id = p.id WHERE p.estado = 'Pendiente de recogida' GROUP BY p.id, p.fecha, p.estado ORDER BY p.fecha;";

		List<Object[]> lista = db.executeQueryArray(sql, new Object[0]);
		List<PedidoDto> listaPedidosNoRecogidos = new ArrayList<PedidoDto>();
		PedidoDto dto;

		for (Object[] d : lista) {
			dto = new PedidoDto();
			dto.setFecha(d[0].toString());
			dto.setTamano(Integer.parseInt(d[1].toString()));
			dto.setEstado(d[2].toString());
			listaPedidosNoRecogidos.add(dto);
		}
		for (PedidoDto d : listaPedidosNoRecogidos) {
			System.out.println(d);
		}
		return listaPedidosNoRecogidos;
	}

	public static List<LocalizacionDto> getLocalizacionBd() {
		db.createDatabase(false);
		db.loadDatabase();
		String sql="SELECT * FROM Localizacion";

		List<Object[]> lista=db.executeQueryArray(sql,new Object[0]);
		List<LocalizacionDto> listaLocalizaciones=new ArrayList<LocalizacionDto>();
		LocalizacionDto loc;
		for(Object[] d: lista) {
			loc=new LocalizacionDto(Integer.parseInt(d[0].toString()),
					Integer.parseInt(d[1].toString()),
					Integer.parseInt(d[2].toString()),
					d[3].toString().charAt(0));
			
			listaLocalizaciones.add(loc);
		}
		
		return listaLocalizaciones;
	
		
	
	
	}

	public static List<ProductoDto> getProductoBd() {
		db.createDatabase(false);
		db.loadDatabase();
		String sql = "SELECT p.referencia, p.datosBasicos, p.unidades, l.fila, l.columna, l.estanteria, l.cara " +
                "FROM Producto p " +
                "JOIN Localizacion l ON p.localizacion_id = l.id";;

		List<Object[]> lista=db.executeQueryArray(sql,new Object[0]);
		List<ProductoDto> listaProducto=new ArrayList<ProductoDto>();
		for(Object[] d: lista) {
			LocalizacionDto localizacion = new LocalizacionDto(
		            Integer.parseInt(d[3].toString()), // fila
		            Integer.parseInt(d[4].toString()), // columna
		            Integer.parseInt(d[5].toString()), // estanteria
		            d[6].toString().charAt(0)          // cara
		        );
		        
		        // Crear una instancia de ProductoDto
		        ProductoDto pto = new ProductoDto(
		            d[0].toString(),  // referencia
		            d[1].toString(),  // datosBasicos
		            Integer.parseInt(d[2].toString()), // unidades
		            localizacion // localización
		        );
		        
		        listaProducto.add(pto);
		    }
		
		return listaProducto;
	
		
	
	
	}
	
	
}
