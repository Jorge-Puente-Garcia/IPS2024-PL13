package giis.model.Almacenero;

import giis.model.Estado;

public class PedidoARecoger {

	private String fecha;
	private int tamano;
	private Estado estado;
	
	public PedidoARecoger(String fecha, int tamano, Estado estado) {
		super();
		this.fecha = fecha;
		this.tamano = tamano;
		this.estado = estado;
	}
	
	public PedidoARecoger() {
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setTamano(int tamano) {
		this.tamano = tamano;
		
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "PedidoARecoger [fecha=" + fecha + ", tamano=" + tamano + ", estado=" + estado + "]";
	}
}
