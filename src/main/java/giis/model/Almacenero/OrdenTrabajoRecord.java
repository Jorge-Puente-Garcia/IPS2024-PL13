package giis.model.Almacenero;

import giis.model.Estado;

public class OrdenTrabajoRecord {
	
	private String id;
	private String fechaCreacion;
	private Estado estado;
	private String incidencias;
	private String almaceneroId;
	private String codigoBarras;
	

	public OrdenTrabajoRecord() {
	
	}
		public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado recogido) {
		this.estado = recogido;
	}
	public String getIncidencias() {
		return incidencias;
	}
	public void setIncidencias(String incidencias) {
		this.incidencias = incidencias;
	}
	public String getAlmaceneroId() {
		return almaceneroId;
	}
	public void setAlmaceneroId(String almaceneroId) {
		this.almaceneroId = almaceneroId;
	}

	@Override
	public String toString() {
		return "OrdenTrabajoRecord [id=" + id + ", fechaCreacion=" + fechaCreacion + ", estado=" + estado
				+ ", incidencias=" + incidencias + ", almaceneroId=" + almaceneroId + "]";
	}
	

}
