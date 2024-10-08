package giis.model;

public class ProductoDto {
    private String referencia;
    private String datosBasicos;
    private int unidades;
    private LocalizacionDto localizacion;
    private int precio;

    public ProductoDto(String referencia, String datosBasicos,int unidades,int precio,LocalizacionDto localizacion2) {
    	 this.referencia = referencia;
         this.datosBasicos = datosBasicos;
         this.unidades = unidades;
         this.precio=precio;
         this.setLocalizacionDto(localizacion2);
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

	public LocalizacionDto getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacionDto(LocalizacionDto localizacion2) {
		this.localizacion = localizacion2;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	
}