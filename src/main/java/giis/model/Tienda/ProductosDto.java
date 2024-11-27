package giis.model.Tienda;

public class ProductosDto {

    private String referencia;
    private String datosbasicos;
    private String precio;
    private int unidades;
    private int unidadesMinimas;

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDatosbasicos() {
        return datosbasicos;
    }

    public void setDatosbasicos(String datosbasicos) {
        this.datosbasicos = datosbasicos;
    }

    @Override
    public String toString() {
        return "ProductosDto [referencia=" + referencia + ", datosbasicos="
            + datosbasicos + "]";
    }

    public String getPrecioUnitario() {
        return precio + " €";
    }

    public void setPrecioUnitario(String string) {
        this.precio = string;
    }
    
	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public int getUnidadesMinimas() {
		return unidadesMinimas;
	}

	public void setUnidadesMinimas(int unidadesMinimas) {
		this.unidadesMinimas = unidadesMinimas;
	}

	public String hayPocasUnidades() {
		if(unidades <= unidadesMinimas){
			return "Quedan Pocas Unidades!";
		}
		return "";
	}

}
