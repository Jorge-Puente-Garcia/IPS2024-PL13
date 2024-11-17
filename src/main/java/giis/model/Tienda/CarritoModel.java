package giis.model.Tienda;

import java.util.ArrayList;
import java.util.List;

import giis.util.Database;

public class CarritoModel {

    private Database db;
    private Double total = 0.0;
    private Cliente cliente;
    private List<CarritoProductos> carrito = new ArrayList<>();

    public CarritoModel(Database db, Cliente cliente) {
        this.db = db;
        this.cliente = cliente;
        crearCarrito();

    }

    private void actualizarCarrito() {
        String sql = "SELECT referencia, cantidad, precio from Carrito where dni = ?;";
        carrito = db.executeQueryPojo(CarritoProductos.class, sql,
            cliente.getDni());
    }

    private void actualizarTotal() {
        total = carrito.stream().mapToDouble(CarritoProductos::getPrecio).sum();
    }

    public void crearCarrito() {
        actualizarCarrito();
        actualizarTotal();
    }

    public void eliminarDelCarrito(String referencia) {
        String sql = "delete from Carrito where referencia = ? and dni = ?;";
        db.executeUpdate(sql, referencia, cliente.getDni());
        actualizarCarrito();
        actualizarTotal();
    }

    public void agregarAlCarrito(String referencia, int cantidad) {
        String checkSql = "SELECT referencia, cantidad, precio FROM Carrito WHERE referencia = ? and dni = ?;";
        List<CarritoProductos> cr = db.executeQueryPojo(CarritoProductos.class,
            checkSql, referencia, cliente.getDni());

        if (cr.size() == 0) {
            String sql = "INSERT INTO Carrito (dni, referencia, cantidad, precio) "
                + "VALUES (?, ?, ?, (SELECT precio FROM producto WHERE referencia = ?) * ?);";
            db.executeUpdate(sql, cliente.getDni(), referencia, cantidad,
                referencia, cantidad);

        } else {
            String sql = "UPDATE Carrito SET cantidad = cantidad + ?, "
                + "precio = ROUND(precio + (SELECT precio FROM producto WHERE referencia = ?) * ?, 2) "
                + "WHERE referencia = ? and dni = ?;";
            db.executeUpdate(sql, cantidad, referencia, cantidad, referencia,
                cliente.getDni());
        }
        actualizarCarrito();
        actualizarTotal();
    }

    public void cambiarCantidad(String referencia, int cantidad) {
        String sql = "update Carrito set cantidad = ?, "
            + "precio = (SELECT precio FROM producto WHERE referencia = ?) * ?"
            + "where referencia = ? and dni = ?";
        db.executeUpdate(sql, cantidad, referencia, cantidad, referencia,
            cliente.getDni());
        actualizarCarrito();
        actualizarTotal();
    }

    public Database getDb() {
        return db;
    }

    public void setDb(Database db) {
        this.db = db;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<CarritoProductos> getCarrito() {
        return carrito;
    }

}
