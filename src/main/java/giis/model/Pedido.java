package giis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pedido {

	protected enum Estado{
		PendienteDeRecogida, Recogido
	}
	
	/*
	 * fecha DATE NOT NULL,
	 *  total DECIMAL(10, 2) NOT NULL,
	 *  estado TEXT NOT NULL, orden_trabajo_id INTEGER,
	 */
	private String fecha;
	private int total;
	private String estado;
	private List<ProductosPedido> productosPedido;
	
	public Pedido(String fecha, int total, String estado, HashMap<String, Object[]> productosCarrito) {
		this.fecha = fecha;
		this.total = total;
		this.estado = estado;
		this.productosPedido = añadirLosProductos(productosCarrito);
	}
	
	public Pedido() {}

	private List<ProductosPedido> añadirLosProductos(HashMap<String, Object[]> productosCarrito){
		List<ProductosPedido> aux = new ArrayList<ProductosPedido>();
		for (Map.Entry<String, Object[]> entry : productosCarrito.entrySet()) {
			String referencia = entry.getKey().toString();
			int cantidad = Integer.parseInt(entry.getValue()[0].toString());
			
			aux.add(new ProductosPedido(referencia, cantidad));
		}
		return aux;
	}
	
	public String getFecha() {
		return fecha;
	}

	public int getTotal() {
		return total;
	}

	@Override
	public String toString() {
		return "Pedido [fecha=" + fecha + ", Tamano=" + total + ", estado=" + estado + "]";
	}

	public String getEstado() {
		return estado;
	}
	
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setTamano(int precio) {
		this.total = precio;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

}
