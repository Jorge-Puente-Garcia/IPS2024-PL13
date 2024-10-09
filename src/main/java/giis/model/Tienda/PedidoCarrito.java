package giis.model.Tienda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import giis.model.Estado;
import giis.util.Database;

public class PedidoCarrito {
	
	// cliente_id, fecha, total, estado
	private Database db;
	private String cliente;
	private String fecha;
	private Double total;
	private Estado estado;
	
	private List<ProductosPedido> productosPedido;
	
	public PedidoCarrito(String cliente, String fecha, Double total, HashMap<String, Object[]> productosCarrito, Database db) {
		this.cliente = cliente;
		this.fecha = fecha;
		this.total = total;
		this.estado = Estado.PendienteDeRecogida;
		this.productosPedido = añadirLosProductos(productosCarrito);
		this.db = db;
	}	
	public PedidoCarrito(String fecha, Double total, HashMap<String, Object[]> productosCarrito, Database db) {
		this.fecha = fecha;
		this.total = total;
		this.estado = Estado.PendienteDeRecogida;
		this.productosPedido = añadirLosProductos(productosCarrito);
		this.db = db;
	}
	
	public PedidoCarrito() {}

	private List<ProductosPedido> añadirLosProductos(HashMap<String, Object[]> productosCarrito){
		List<ProductosPedido> aux = new ArrayList<ProductosPedido>();
		for (Map.Entry<String, Object[]> entry : productosCarrito.entrySet()) {
			String referencia = entry.getKey().toString();
			int cantidad = Integer.parseInt(entry.getValue()[0].toString());
			
			aux.add(new ProductosPedido(referencia, cantidad));
		}
		return aux;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<ProductosPedido> getProductosPedido() {
		return productosPedido;
	}

	public void setProductosPedido(List<ProductosPedido> productosPedido) {
		this.productosPedido = productosPedido;
	}

	public void cargarPedidoDb() {
		int id = obtenerSiguienteIdPedido() + 1;
		String sql = "INSERT INTO pedido (id, cliente_id, fecha, total, estado) VALUES ('"
				 + id + "', '" 
	             + cliente + "', '" 
	             + fecha + "', " 
	             + total + ", '" 
	             + (estado == Estado.PendienteDeRecogida ? "Pendiente de recogida" : "Recogido") + "');";
		db.executeUpdate(sql);
		
		for (ProductosPedido pd : productosPedido) {
			pd.añadirProducto(id, db);
		}		
		ConfirmacionConsola(id);	
	}
	
	private int obtenerSiguienteIdPedido() {
		String sql = "SELECT count(id) FROM Pedido";		
		List<Object[]> lista=db.executeQueryArray(sql,new Object[0]);
		int rs = 0;
		for(Object[] d: lista) {		
			rs = Integer.parseInt(d[0].toString());		
		}
		return rs;
	}
	
	private void ConfirmacionConsola(int id) {
		System.out.print("Se ha cargado a la base datos el Pedido de id: ");
		System.out.print(id);
		//String.format("Nombre: %s, Ciudad: %s, País: %s", nombre, ciudad, pais);
		System.out.print(String.format("\nCliente: %s, Fecha: %s, Total: %.2f\n", cliente,fecha,total));
		System.out.print("Con los siguientes productos:\n");
		for(ProductosPedido pc: productosPedido) {
			System.out.print(String.format("->Referencia: %s, Unidades: %d\n",pc.getReferencia(), pc.getCantidad()));
		}
	}
}
