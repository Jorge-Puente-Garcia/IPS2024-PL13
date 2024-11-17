package giis.model.Almacenero;

public class OrdenTrabajoDto {

	private String fechaCreacion;
	private Estado estado;
	private String incidencias;
	private String almaceneroId;
	private String codigoBarras;
	private String id;
	
	public void setId(String id) {
		this.id = id;
	}


	public OrdenTrabajoDto() {
		
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
	public void setEstado(Estado estado2) {
		this.estado = estado2;
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


	public String getCodigoBarras() {
		return codigoBarras;
	}


	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}


	public String getId() {
		return id;
	}
	
	
	
}
