package giis.controller;

import java.util.HashMap;
import java.util.List;

import giis.model.Tienda.Carrito;
import giis.model.Tienda.ProductosDto;
import giis.model.Tienda.TiendaModel;
import giis.ui.TiendaView;
import giis.util.Database;

public class TiendaController {
	private Database db = new Database();
	
	private TiendaModel model;
	private Carrito carrito;
	private TiendaView view;
	
	public static void main(String[] args) {
		TiendaController c = new TiendaController();
		c.initView();
		}
	
	public TiendaController() {
		db.createDatabase(false);
		db.loadDatabase();
		
		this.model = new TiendaModel(db);		
		this.carrito = new Carrito(db);		
		this.view = new TiendaView(this);	
	}

	private void initView() {
		//Abre la ventana (sustituye al main generado por WindowBuilder)
		try {
			view.setVisible(true);
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
		return String.format("%.2f", carrito.getTotal());
	}
}
