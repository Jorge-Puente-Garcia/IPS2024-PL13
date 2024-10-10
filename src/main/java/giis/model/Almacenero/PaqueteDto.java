package giis.model.Almacenero;

public class PaqueteDto {

	public String pedidoId;
	public String nombreCliente;
	public String apellidosCliente ;
	public String dniCliente;
	public String fecha;
	
	
	public PaqueteDto(String pedidoId, String nombreCliente, String apellidosCliente, String dniCliente, String fecha) {
		this.pedidoId = pedidoId;
		this.nombreCliente = nombreCliente;
		this.apellidosCliente = apellidosCliente;
		this.dniCliente = dniCliente;
		this.fecha = fecha;
	}
	
	public String getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(String pedidoId) {
		this.pedidoId = pedidoId;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getApellidosCliente() {
		return apellidosCliente;
	}
	public void setApellidosCliente(String apellidosCliente) {
		this.apellidosCliente = apellidosCliente;
	}
	public String getDniCliente() {
		return dniCliente;
	}
	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	
}
