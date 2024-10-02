package giis.model;

public class Producto {
    private String referencia;
    private String datosBasicos;
    private int unidades;
    private Localizacion localizacion;
 

    public Producto(String referencia, String datosBasicos,int unidades,Localizacion localizacion) {
    	 this.referencia = referencia;
         this.datosBasicos = datosBasicos;
         this.unidades = unidades;
         this.setLocalizacion(localizacion);
    }

    // Getters y Setters
    public String getReferencia() {
        return referencia;
    }
   

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDatosBasicos() {
        return datosBasicos;
    }

    public void setDatosBasicos(String datosBasicos) {
        this.datosBasicos = datosBasicos;
    }

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}

	
}