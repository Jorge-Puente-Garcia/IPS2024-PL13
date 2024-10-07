package giis.model.Almacenero;

import giis.model.Estado;

public class PedidoARecogerRecord {

	private String id;
	private String fecha;
	private int tamaño;
	private Estado estado;
	
	public PedidoARecogerRecord(String id,String fecha, int tamano, Estado estado) {
		super();
		this.id=id;
		this.fecha = fecha;
		this.tamaño = tamano;
		this.estado = estado;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getFecha() {
		return fecha;
	}

	public int getTamaño() {
		return tamaño;
	}

	public Estado getEstado() {
		return estado;
	}

	public PedidoARecogerRecord() {
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setTamano(int tamano) {
		this.tamaño = tamano;
		
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "PedidoARecogerRecord [id=" + id + ", fecha=" + fecha + ", tamaño=" + tamaño + ", estado=" + estado
				+ "]";
	}
}
