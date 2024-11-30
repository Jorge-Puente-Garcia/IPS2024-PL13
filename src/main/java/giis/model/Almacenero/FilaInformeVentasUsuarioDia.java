package giis.model.Almacenero;

public class FilaInformeVentasUsuarioDia {
	
	public String dia;
	public double particular;//El dinero pagado por un particular en un dia
	public double empresa;//El dinero pagado por una empresa en un dia
	public double total;
	
	public FilaInformeVentasUsuarioDia() {
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public double getParticular() {
		return particular;
	}

	public void setParticular(double particular) {
		this.particular = particular;
	}

	public double getEmpresa() {
		return empresa;
	}

	public void setEmpresa(double empresa) {
		this.empresa = empresa;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
