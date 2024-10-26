package giis.model.Almacenero;

public class OrdenTrabajoRecord {
	
	private String id;
	private String fecha_creacion;
	private String estado;
	private String incidencia;
	private String almacenero_id;
	private String codigoBarras;
	

	
		public OrdenTrabajoRecord(String id, String fecha_creacion, String estado, String incidencia, String almacenero_id) {
		this.id = id;
		this.fecha_creacion = fecha_creacion;
		this.estado = estado;
		this.incidencia = incidencia;
		this.almacenero_id = almacenero_id;
	}

		public OrdenTrabajoRecord() {
			// TODO Auto-generated constructor stub
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
		return fecha_creacion;
	}
	public void setFecha_creacion(String fechaCreacion) {
		this.fecha_creacion = fechaCreacion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String recogido) {
		this.estado = recogido;
	}
	public String getIncidencias() {
		return incidencia;
	}
	public void setIncidencia(String incidencias) {
		this.incidencia = incidencias;
	}
	public String getAlmaceneroId() {
		return almacenero_id;
	}
	public void setAlmacenero_id(String almaceneroId) {
		this.almacenero_id = almaceneroId;
	}

	@Override
	public String toString() {
		return "OrdenTrabajoRecord [id=" + id + ", fechaCreacion=" + fecha_creacion + ", estado=" + estado
				+ ", incidencias=" + incidencia + ", almaceneroId=" + almacenero_id + "]";
	}
	

}
