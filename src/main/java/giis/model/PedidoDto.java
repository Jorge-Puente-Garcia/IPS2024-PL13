package giis.model;

public class PedidoDto {

	/*
	 * fecha DATE NOT NULL,
	 *  total DECIMAL(10, 2) NOT NULL,
	 *  estado TEXT NOT NULL, orden_trabajo_id INTEGER,
	 */
	public String fecha;
	public int tamaño;
	public String estado;
	
	public PedidoDto(String fecha, int tamano, String estado) {
		this.fecha = fecha;
		this.tamaño = tamano;
		this.estado = estado;
	}
	public PedidoDto() {}

	public String getFecha() {
		return fecha;
	}

	public int getTamaño() {
		return tamaño;
	}

	@Override
	public String toString() {
		return "Pedido [fecha=" + fecha + ", Tamano=" + tamaño + ", estado=" + estado + "]";
	}

	public String getEstado() {
		return estado;
	}
	
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setTamano(int precio) {
		this.tamaño = precio;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	

}
