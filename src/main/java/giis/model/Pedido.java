package giis.model;

import java.util.List;

public class Pedido {
    private int clienteId;
    private String fecha;
    private double total;
    private String estado;
    private int ordenTrabajoId;
    private List<Producto> productos;

    public Pedido(int clienteId, String fecha, double total, String estado, int ordenTrabajoId,List<Producto> productos) {
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.ordenTrabajoId = ordenTrabajoId;
        this.setProductos(productos);
    }

    // Getters y Setters
    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getOrdenTrabajoId() {
        return ordenTrabajoId;
    }

    public void setOrdenTrabajoId(int ordenTrabajoId) {
        this.ordenTrabajoId = ordenTrabajoId;
    }

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
}