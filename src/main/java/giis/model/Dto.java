package giis.model;

public class Dto{
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Dto(String nombre) {
		super();
		this.nombre = nombre;
	}
	public Dto() {}
}
