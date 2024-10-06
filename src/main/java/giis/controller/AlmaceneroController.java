package giis.controller;

import java.util.List;

import giis.model.AlmaceneroModel;
import giis.model.PedidoDto;
import giis.ui.AlmaceneroView;

public class AlmaceneroController {
	private AlmaceneroModel model;
	private AlmaceneroView view;
	
	public AlmaceneroController() {
		this.model = new AlmaceneroModel();
	}

	public List<PedidoDto> getPedidosPendientesRecogida(){
		return model.getPedidosPendientesRecogida();
	}
}
