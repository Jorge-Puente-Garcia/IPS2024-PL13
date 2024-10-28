package giis.model.Tienda;

public class CarritoProductos {

    private String referencia;
    private int cantidad;
    private double precio;

    public CarritoProductos(String referencia, int cantidad, double precio) {
        this.referencia = referencia;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public CarritoProductos() {
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
