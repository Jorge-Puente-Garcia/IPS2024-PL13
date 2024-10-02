package giis.model;

public class ProductosPedido {

	private String referencia;
	private int cantidad;
	
	public ProductosPedido(String referencia, int cantidad) {
		
		this.referencia = referencia;
		this.cantidad = cantidad;
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
	
	
}
