package giis.model.Almacenero;

public class EtiquetaRecord {

	private String nombre;
	private String apellidos;
	private String numeroTelefono;
	private String paqueteId;
	private String direccion;
	
	
	public  EtiquetaRecord() {
		
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNumeroTelefono() {
		return numeroTelefono;
	}
	public void setNumeroTelefono(String numeroTeléfon) {
		this.numeroTelefono = numeroTeléfon;
	}

	public String getPaqueteId() {
		return paqueteId;
	}
	public void setPaqueteId(String paqueteId) {
		this.paqueteId = paqueteId;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
}
