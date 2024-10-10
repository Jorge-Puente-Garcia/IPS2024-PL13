package giis.controller;

import java.util.ArrayList;
import java.util.List;

import giis.model.Almacenero.AlmaceneroModel;
import giis.model.Almacenero.*;
import giis.ui.AlmaceneroView;

public class AlmaceneroController {
	private AlmaceneroModel model;
	private AlmaceneroView view;
	
	private List<PedidoARecogerRecord> pedidosSinRecoger;
	private List<PedidoARecogerDto> pedidosSinRecogerParaImprimir;
	private List<OrdenTrabajoRecord> pedidosAsignados;
	private List<OrdenTrabajoDto> pedidosAsignadosParaImprimir;
	private int almaceneroId;
	
	
	public AlmaceneroController() {
		this.model = new AlmaceneroModel();
	}
	public List<PedidoARecogerDto> getPedidosPendientesRecogida(){
		pedidosSinRecoger=model.getPedidosPendientesRecogida();
		pedidosSinRecogerParaImprimir=pedidoRecordToDtoList();
		return pedidosSinRecogerParaImprimir;
	}
	
	public List<OrdenTrabajoDto> getOrdenesDeTrabajoSeleccionadas() {
		pedidosAsignados=model.getOrdenesDeTrabajoDelAlmaceneroPorId(almaceneroId);
		pedidosAsignadosParaImprimir=workorderRecordToDtoList();
		return pedidosAsignadosParaImprimir;
	}
	
	
	private List<OrdenTrabajoDto> workorderRecordToDtoList() {
		List<OrdenTrabajoDto> l=new ArrayList<OrdenTrabajoDto>();
		for(OrdenTrabajoRecord r: pedidosAsignados) {
			OrdenTrabajoDto d=new OrdenTrabajoDto();
			d.setEstado(r.getEstado());
			d.setFechaCreacion(r.getFechaCreacion());
			d.setIncidencias(r.getIncidencias());
			d.setAlmaceneroId(r.getAlmaceneroId());
			l.add(d);
		}
		return l;
	}
	public void ponEnRecogidaElPedido(int selectedRow) {
		PedidoARecogerRecord par=pedidosSinRecoger.get(selectedRow);
		model.creaOrdenDeTrabajo(almaceneroId);
		model.ponEnRecogidaElPedido(par);
	}
	
	private List<PedidoARecogerDto> pedidoRecordToDtoList() {
		List<PedidoARecogerDto> l=new ArrayList<PedidoARecogerDto>();
		for(PedidoARecogerRecord r: pedidosSinRecoger) {
			PedidoARecogerDto d=new PedidoARecogerDto();
			d.setEstado(r.getEstado());
			d.setFecha(r.getFecha());
			d.setTamano(r.getTamaño());
			l.add(d);
		}
		return l;
	}
	public boolean isOkIdAlmacenero(String id) {
		if(model.isOkIdAlmacenero(id)) {
			almaceneroId=Integer.parseInt(id);
			return true;
		}
		return false;
	}
	
}
