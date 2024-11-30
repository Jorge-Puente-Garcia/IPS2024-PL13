package giis.model.Tienda;

public class Cliente {
    private String dni;
    private String nombre;
    private String apellidos;
    private String direccion;
    private int numeroTelefono;
    private boolean empresa;
    
    public Cliente(String dni, String nombre, String apellidos, String direccion, int numeroTelefono, boolean empresa) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.numeroTelefono = numeroTelefono;
		this.empresa = empresa;
	}
    
    public Cliente() {
    	
    }

	public Cliente (String dni) {
    	this(dni, "no-name","no-surname","no-direction", 0, false);
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(int numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public boolean isEmpresa() {
		return empresa;
	}

	public void setEmpresa(boolean empresa) {
		this.empresa = empresa;
	}
    
    
}