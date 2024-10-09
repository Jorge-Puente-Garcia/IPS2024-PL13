package giis.model.Tienda;

import java.util.List;

import giis.util.Database;

public class ProductosPedido {

	private String referencia;
	private int cantidad;
	
	public ProductosPedido(String referencia, int cantidad) {
		
		this.referencia = referencia;
		this.cantidad = cantidad;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public void añadirProducto(int idPedido, Database db) {
		String sql = "INSERT INTO ProductosPedido (pedido_id, producto_id, cantidad) VALUES (" 
	             + idPedido + ", " 
	             + obtenerIdProducto(db) + ", " 
	             + cantidad +")"; 		
		db.executeUpdate(sql);
	}
	
	private int obtenerIdProducto(Database db) {
		
		String sql = "SELECT id FROM Producto where referencia='"+referencia+"';";
		List<Object[]> lista=db.executeQueryArray(sql,new Object[0]);
		int rs = 0;
		for(Object[] d: lista) {		
			rs = Integer.parseInt(d[0].toString());		
		}
		return rs;
	}
	
}
