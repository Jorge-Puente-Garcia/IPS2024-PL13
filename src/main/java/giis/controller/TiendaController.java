package giis.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import giis.model.Cliente;
import giis.model.Tienda.CarritoModel;
import giis.model.Tienda.CarritoProductos;
import giis.model.Tienda.Categorias;
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

    public TiendaController(Database db, String[] clienteData) {
        this.db = new Database();

        this.model = new TiendaModel(db);
        this.carrito = new CarritoModel(db,
            new Cliente(clienteData[0]));
        this.tienda = new Tienda(this);
        initView();
    }

    private void initView() {
        try {
            tienda.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductosDto> getProductos(String CategoriaName) {
        return model.getProductos(CategoriaName);
    }

    public void agregarAlCarrito(String referencia, int cantidad) {
        carrito.agregarAlCarrito(referencia, cantidad);
    }

    public List<CarritoProductos> devolverCarrito() {
        return carrito.getCarrito();
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
        PedidoCarrito pc = new PedidoCarrito(carrito.getCliente(),
            obtenerFecha(), roundToTwoDecimals(carrito.getTotal()),
            devolverCarrito(), db);
        pc.cargarPedidoDb();
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

    public Cliente getCliente() {
        return carrito.getCliente();
    }

    public List<Categorias> getCategoriasIniciales() {
        return model.getCategoriasPrincipales();
    }

    public List<Categorias> getSubCategorias(String nombre) {
        return model.getSubcategorias(nombre);
    }

    public List<Categorias> mostrarCategoriasReferencia(String referencia) {
        return model.CategoriasAnteriorReferencia(referencia);
    }

    public List<Categorias> mostrarCategoriasPadre(String categoria) {
        return model.CategoriasAnteriorCategoria(categoria);
    }
}
