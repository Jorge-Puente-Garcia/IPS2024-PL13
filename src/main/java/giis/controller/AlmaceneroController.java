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
		this.view = new AlmaceneroView();
		//no hay inicializacion especifica del modelo, solo de la vista
		this.initView();
	}

	private void initView() {
		//Abre la ventana (sustituye al main generado por WindowBuilder)
		try {
			view.getFrameTerminalPortatil().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<PedidoDto> getPedidosPendientesRecogida(){
		return model.getPedidosPendientesRecogida();
	}
}
