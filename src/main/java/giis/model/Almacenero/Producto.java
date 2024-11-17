package giis.model.Almacenero;

import giis.model.Localizacion;

public class Producto {
    private String referencia;
    private String datosBasicos;
    private int unidades;
    private Localizacion localizacion;
    private double precio;

    public Producto(String referencia, String datosBasicos,int unidades,double precio,Localizacion localizacion) {
    	 this.referencia = referencia;
         this.datosBasicos = datosBasicos;
         this.unidades = unidades;
         this.setPrecio(precio);
         this.setLocalizacion(localizacion);
    }

    // Getters y Setters
    public String getReferencia() {
        return referencia;
    }
   

    @Override
	public String toString() {
		return "Producto [referencia=" + referencia + ", datosBasicos=" + datosBasicos + ", unidades=" + unidades
				+ ", localizacion=" + localizacion + ", precio=" + precio + "]";
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	
}