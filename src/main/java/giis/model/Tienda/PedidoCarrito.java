package giis.model.Tienda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import giis.model.Estado;

public class PedidoCarrito {
	
	// cliente_id, fecha, total, estado
	
	private String cliente;
	private String fecha;
	private int total;
	private Estado estado;
	
	private List<ProductosPedido> productosPedido;
	
	public PedidoCarrito(String Cliente, String fecha, int total, HashMap<String, Object[]> productosCarrito) {
		this.fecha = fecha;
		this.total = total;
		this.estado = Estado.PendienteDeRecogida;
		this.productosPedido = añadirLosProductos(productosCarrito);
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
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

}
