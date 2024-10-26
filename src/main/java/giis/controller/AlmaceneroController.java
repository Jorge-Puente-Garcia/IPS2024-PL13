package giis.controller;

import java.util.ArrayList;
import java.util.List;

import giis.model.Estado;
import giis.model.Almacenero.AlmaceneroModel;
import giis.model.Almacenero.OrdenTrabajoDto;
import giis.model.Almacenero.OrdenTrabajoRecord;
import giis.model.Almacenero.PedidoARecogerDto;
import giis.model.Almacenero.PedidoARecogerRecord;

public class AlmaceneroController {
	private AlmaceneroModel model;
	
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
			d.setId(r.getId());
			if(r.getEstado().equals("Pendiente de empaquetado")) {
				d.setEstado(Estado.PendienteDeEmpaquetado);
			}else if(r.getEstado().equals("Empaquetado")) {
				d.setEstado(Estado.Empaquetado);
			}else if(r.getEstado().equals("En recogida")) {
				d.setEstado(Estado.EnRecogida);
			}
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
			if(r.getEstado().equals("Pendiente de recogida")) {
				d.setEstado(Estado.PendienteDeRecogida);
			}else if(r.getEstado().equals("Recogido")) {
				d.setEstado(Estado.Recogido);
			}else if(r.getEstado().equals("En recogida")) {
				d.setEstado(Estado.EnRecogida);
			}
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
	public String getEtiquetaEnvio(OrdenTrabajoDto otd) {
		OrdenTrabajoRecord or= new OrdenTrabajoRecord();
		or.setCodigoBarras(otd.getCodigoBarras());
		or.setId(otd.getId());
		String r=model.creaEtiqueta(or);
		return r;
		
	}
	public String getAlbaran(OrdenTrabajoDto otd) {
		OrdenTrabajoRecord or= new OrdenTrabajoRecord();
		or.setCodigoBarras(otd.getCodigoBarras());
		or.setId(otd.getId());
		String r=model.creaAlbaran(or);
		return r;
		
	}
	
}
