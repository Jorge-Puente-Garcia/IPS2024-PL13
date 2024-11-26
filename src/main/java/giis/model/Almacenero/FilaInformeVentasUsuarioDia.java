package giis.model.Almacenero;

public class FilaInformeVentasUsuarioDia {
	
	public String dia;
	public int particular;//El dinero pagado por un particular en un dia
	public int empresa;//El dinero pagado por una empresa en un dia
	
	public FilaInformeVentasUsuarioDia() {
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public int getParticular() {
		return particular;
	}

	public void setParticular(int particular) {
		this.particular = particular;
	}

	public int getEmpresa() {
		return empresa;
	}

	public void setEmpresa(int empresa) {
		this.empresa = empresa;
	}
	
	
}
