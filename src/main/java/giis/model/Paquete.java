package giis.model;

import java.util.ArrayList;

import giis.model.Almacenero.OrdenTrabajo;
import giis.model.Almacenero.Producto;

public class Paquete {
	OrdenTrabajo orden;
	ArrayList<Producto> productos;
	
	public Paquete(OrdenTrabajo orden) {
		setOrden(orden);
	}
	private void setOrden(OrdenTrabajo o) {
		this.orden = o;
	}
	public OrdenTrabajo getOrden() {
		return this.orden;
	}

	/**
	 * Método que añade el Produto a la lista de Productos del Paquete
	 * @param producto
	 */
	public void anadirProducto(Producto producto) {
		productos.add(producto);
	}
	
	
	
	
	
	
	
	
}
