package giis.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTextField;

import giis.model.Tienda.CarritoModel;
import giis.model.Tienda.PedidoCarrito;
import giis.model.Tienda.ProductosDto;
import giis.model.Tienda.TiendaModel;
import giis.ui.Tienda;
import giis.util.Database;

public class TiendaController {
	
	private Database db;
	
	private Tienda tienda;
	
	private TiendaModel model;
	private CarritoModel carrito;
	
	public TiendaController(Database db) {
		this.db = new Database();
			
		this.model = new TiendaModel(db);
		this.carrito = new CarritoModel(db);
		
		this.tienda = new Tienda(this);	
		
		initView();
	}

	private void initView() {
		//Abre la ventana (sustituye al main generado por WindowBuilder)
		try {
			tienda.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ProductosDto> getProductos(){
		return model.getProductos();
	}
	
	public void agregarAlCarrito(String referencia, int cantidad) {
		carrito.agregarAlCarrito(referencia, cantidad);
	}
	
	public HashMap<String, Object[]> devolverCarrito(){
		return carrito.devolverCarrito();
	}
	
	public void cambiarCantidad(String referencia, int cantidad) {
		carrito.cambiarCantidad(referencia, cantidad);
	}

	public void eliminarDelCarrito(String referencia) {
		carrito.eliminarDelCarrito(referencia);
	}

	public String getTotal() {
		Double db = roundToTwoDecimals(carrito.getTotal());
		return db.toString();
	}	
	
	public void crearPedido() {
		PedidoCarrito pc = new PedidoCarrito(carrito.getCliente(), obtenerFecha(), 
											roundToTwoDecimals(carrito.getTotal()), 
											devolverCarrito(), db);
		pc.cargarPedidoDb();
		this.carrito = new CarritoModel(db);
	}
	
	private static double roundToTwoDecimals(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP); // Redondear a 2 decimales
        return bd.doubleValue(); // Retornar como double
    }
	
	private static String obtenerFecha() {
		LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaActual.format(formato);
	}

	public void setCliente(String dni) {
		carrito.setCliente(dni);
	}

	public boolean getCliente() {
		String dni = carrito.getCliente();
		return dni==null || dni.isBlank();
	}
}
