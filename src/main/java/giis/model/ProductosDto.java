package giis.model;

public class ProductosDto {

	private String referencia;
	private String datosbasicos;
	
	
	
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
		return "ProductosDto [referencia=" + referencia + ", datosbasicos=" + datosbasicos + "]";
	}
	
	
	
	
}
