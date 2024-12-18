package giis.model.Tienda;

import java.util.ArrayList;
import java.util.List;

import giis.model.Almacenero.Estado;
import giis.util.Database;

public class PedidoCarrito {

    // cliente_id, fecha, total, estado
    private Database db;
    private Cliente cliente;
    private String fecha;
    private Double total;
    private Estado estado;

    private List<ProductosPedido> productosPedido;

    public PedidoCarrito(Cliente cliente2, String fecha, Double total,
        List<CarritoProductos> list, Database db) {
        this.cliente = cliente2;
        this.fecha = fecha;
        this.total = total;
        this.estado = Estado.PendienteDeRecogida;
        this.productosPedido = añadirLosProductos(list);
        this.db = db;
    }

    public PedidoCarrito(String fecha, Double total,
        List<CarritoProductos> productosCarrito, Database db) {
        this.fecha = fecha;
        this.total = total;
        this.estado = Estado.PendienteDeRecogida;
        this.productosPedido = añadirLosProductos(productosCarrito);
        this.db = db;
    }

    public PedidoCarrito() {
    }

    private List<ProductosPedido> añadirLosProductos(
        List<CarritoProductos> list) {
        List<ProductosPedido> aux = new ArrayList<ProductosPedido>();
        for (CarritoProductos cp : list) {
            aux.add(new ProductosPedido(cp.getReferencia(), cp.getCantidad()));
        }
        return aux;
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
            + id + "', '" + getClienteId(cliente.getDni())	 + "', '" + fecha + "', " + total + ", '"
            + (estado == Estado.PendienteDeRecogida ? "Pendiente de recogida"
                : "Recogido")
            + "');";
        db.executeUpdate(sql);

        for (ProductosPedido pd : productosPedido) {
            pd.añadirProducto(id, db, cliente.getDni());
            restarStockProducto(pd.getReferencia(), pd.getCantidad());
        }
        
        String sqlD = "delete from carrito where dni = ?";
		db.executeUpdate(sqlD, cliente.getDni());
		
        ConfirmacionConsola(id);
    }

    private String getClienteId(String dni) {
    	String sql = "SELECT id FROM cliente WHERE dni = ?";
    	List<Object[]> rs = db.executeQueryArray(sql, dni);
    	Object[] ob = rs.get(0);
    	return ob[0].toString();
    }

    private int obtenerSiguienteIdPedido() {
        String sql = "SELECT count(id) FROM Pedido";
        List<Object[]> lista = db.executeQueryArray(sql, new Object[0]);
        int rs = 0;
        for (Object[] d : lista) {
            rs = Integer.parseInt(d[0].toString());
        }
        return rs;
    }

    private void ConfirmacionConsola(int id) {
        System.out.print("Se ha cargado a la base datos el Pedido de id: ");
        System.out.print(id);
        // String.format("Nombre: %s, Ciudad: %s, País: %s", nombre, ciudad,
        // pais);
        System.out.print(String.format(
            "\nCliente: %s, Fecha: %s, Total: %.2f\n", cliente, fecha, total));
        System.out.print("Con los siguientes productos:\n");
        for (ProductosPedido pc : productosPedido) {
            System.out.print(String.format("->Referencia: %s, Unidades: %d\n",
                pc.getReferencia(), pc.getCantidad()));
        }
    }
    
    private void restarStockProducto(String referencia, int unidades) {
    	String sql = "UPDATE Producto "
    	           + "SET unidades = unidades - ? "
    	           + "WHERE referencia = ?;";
    	db.executeUpdate(sql, unidades, referencia);    	
    	verUnidades(referencia);
    }
    
    private void verUnidades(String referencia) {
    	String sql = "select unidades  FROM Producto "
                + "WHERE referencia = ?;";
    	List<Object[]> a = db.executeQueryArray(sql, referencia);
    	System.out.print(a.get(0).toString());
    }
}
