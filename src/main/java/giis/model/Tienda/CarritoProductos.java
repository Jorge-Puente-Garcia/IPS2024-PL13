package giis.model.Tienda;

public class CarritoProductos {

    private String referencia;
    private int cantidad;
    private double precioBase;
    private String iva;
    private double precio;
    
    
    public CarritoProductos(String referencia, int cantidad,double precioBase, String iva, double precio) {
        this.referencia = referencia;
        this.cantidad = cantidad;
        this.precio = precio;
        this.precioBase = precioBase;
        this.iva = iva;
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

	public double getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(double precioBase) {
		this.precioBase = precioBase;
	}

	public String getIva() {
		return iva +"%";
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

}
