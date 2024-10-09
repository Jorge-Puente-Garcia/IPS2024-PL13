package giis.model.Almacenero;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import giis.model.Estado;
import giis.model.OrdenTrabajo;
import giis.model.Producto;
import giis.model.ProductoDto;

public class PedidoARecogerDto {

	private String fecha;
	private int tamaño;
	private Estado estado;
	
	public PedidoARecogerDto(String fecha, int tamano, Estado estado) {
		super();
		this.fecha = fecha;
		this.tamaño = tamano;
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public int getTamaño() {
		return tamaño;
	}

	public Estado getEstado() {
		return estado;
	}

	public PedidoARecogerDto() {
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setTamano(int tamano) {
		this.tamaño = tamano;
		
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "PedidoARecoger [fecha=" + fecha + ", tamano=" + tamaño + ", estado=" + estado + "]";
	}



	
}
