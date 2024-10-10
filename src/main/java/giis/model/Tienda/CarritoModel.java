package giis.model.Tienda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import giis.util.Database;

public class CarritoModel {

	private HashMap<String, Object[]> carrito = new HashMap<>();
	private Database db;
	private Double total = 0.0;
	private String dni;
	public CarritoModel(Database db) {
		this.db = db;
	}

	public HashMap<String, Object[]> devolverCarrito(){
		return carrito;
	}
	
	// Método para agregar un producto al carrito
	public void agregarAlCarrito(String referencia, int cantidad) {
	    if (carrito.containsKey(referencia)) {
	        // Si el producto ya está en el carrito, incrementamos la cantidad
	        Object[] producto = carrito.get(referencia);	        
	        int cantidadActual = (int) producto[0]; // Obtener la cantidad actual
	        producto[0] = cantidadActual + cantidad; // Actualizar la cantidad
	        carrito.put(referencia, producto); // Actualizar el carrito con la nueva cantidad
	        
	    } else {
	        // Si el producto no está en el carrito, lo agregamos con los datos
	        carrito.put(referencia, new Object[]{cantidad,  getPrecioProducto(referencia)});
	    }
	    calcularTotal();
	}

	private double getPrecioProducto(String referencia) {
		String sql= "SELECT precio from Producto where referencia ='" + referencia + "'";
		
		List<Object[]> lista = db.executeQueryArray(sql,new Object[0]);
		Double rs = 0.0;
		for (Object[] object : lista) {
			rs = Double.parseDouble(object[0].toString());
		}
		return rs;
	}
	
	public void cambiarCantidad(String referencia, int cantidad) {
		Object[] producto = carrito.get(referencia);
		producto[0] = cantidad; // Actualizar la cantidad
        carrito.put(referencia, producto);
        calcularTotal();
	}

	public void eliminarDelCarrito(String referencia) {
		carrito.remove(referencia);
		calcularTotal();
	}
	
	private void calcularTotal() {
		this.total = 0.0;
		for (Map.Entry<String, Object[]> entry : devolverCarrito().entrySet()) {
			int cantidad = Integer.parseInt(entry.getValue()[0].toString());
			double precio = Double.parseDouble(entry.getValue()[1].toString());
			this.total += cantidad * precio;
		}
	}
	
	public Double getTotal() {
		return this.total;
	}

	public void setCliente(String dni) {
		this.dni = dni;
	}

	public String getCliente() {
		return dni;
	}
}
