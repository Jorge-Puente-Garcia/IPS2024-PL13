package giis.controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import giis.model.Estado;
import giis.model.Almacenero.AlmaceneroModel;
import giis.model.Almacenero.ElementoARecogerDto;
import giis.model.Almacenero.OrdenTrabajoDto;
import giis.model.Almacenero.OrdenTrabajoRecord;
import giis.model.Almacenero.PedidoARecogerDto;
import giis.model.Almacenero.PedidoARecogerRecord;
import giis.ui.AlmaceneroView;
import giis.util.SwingUtil;

public class AlmaceneroController {
	private AlmaceneroModel model;
	private AlmaceneroView vista;
	private List<PedidoARecogerRecord> pedidosSinRecoger;
	private List<PedidoARecogerDto> pedidosSinRecogerParaImprimir;
	private List<OrdenTrabajoRecord> pedidosAsignados;
	private List<OrdenTrabajoRecord> pedidosAPendientesEmpaquetado;
	private List<OrdenTrabajoDto> pedidosAsignadosParaImprimir;
	private List<OrdenTrabajoDto> pedidosPendientesEmpaquetadoParaImprimir;
	private List<ElementoARecogerDto> elementosARecoger;
	private OrdenTrabajoRecord ordenTrabajoEnRecogida;
	private int almaceneroId;
	
	
	public AlmaceneroController(AlmaceneroView almaceneroView) {
		this.vista=almaceneroView;
		this.model = new AlmaceneroModel();
	}
	public List<PedidoARecogerDto> getPedidosPendientesDeEntrarEnUnaOT(){
		pedidosSinRecoger=model.getPedidosPendientesRecogida();
		pedidosSinRecogerParaImprimir=pedidoRecordToDtoList();
		return pedidosSinRecogerParaImprimir;
	}
	
	public List<OrdenTrabajoDto> getOrdenesDeTrabajoSeleccionadas() {
		pedidosAsignados=model.getOrdenesDeTrabajoDelAlmaceneroPorId(almaceneroId);
		pedidosAsignadosParaImprimir=workorderRecordToDtoList(pedidosAsignados);
		return pedidosAsignadosParaImprimir;
	}
	
	private List<OrdenTrabajoDto> getOrdenesDeTrabajoPendiestesEmpaquetado() {
		pedidosAPendientesEmpaquetado=model.getOrdenesDeTrabajoPendientesEmpaquetado();
		pedidosPendientesEmpaquetadoParaImprimir=workorderRecordToDtoList(pedidosAPendientesEmpaquetado);
		return pedidosPendientesEmpaquetadoParaImprimir;
	}
	private List<ElementoARecogerDto> getElementosARecogerDeLaWorkorderSeleccionada() {		
		ordenTrabajoEnRecogida=pedidosAsignados.get(vista.getTablaOrdenesTrabajoSeleccionadas().getSelectedRow());
		this.elementosARecoger =model.getElementosARecogerDeLaOrdenDeTrabajo(ordenTrabajoEnRecogida);
		return elementosARecoger;
	}
	
	
	private List<OrdenTrabajoDto> workorderRecordToDtoList(List<OrdenTrabajoRecord> pedidosAsignados) {
		List<OrdenTrabajoDto> l=new ArrayList<OrdenTrabajoDto>();
		for(OrdenTrabajoRecord r: pedidosAsignados) {
			OrdenTrabajoDto d=new OrdenTrabajoDto();
			d.setId(r.getId());
			if(r.getEstado().equals("Pendiente de empaquetado")) {
				d.setEstado(Estado.PendienteDeEmpaquetado);
			}else if(r.getEstado().equals("Pendiente de recogida")) {
				d.setEstado(Estado.PendienteDeRecogida);
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
		model.creaOrdenDeTrabajo(almaceneroId,par);
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
	public ActionListener getActionListenerParaSeleccionarOrdenesTrabajo() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnOrdenesDeTrabajo");
			}
		};
	}
	public ActionListener getActionListenerVolverPaginaPrincipal() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vista.getBtnIniciarEmpaquetado().setEnabled(false);
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnPaginaPrincipal");
			}
		};
	}
	public MouseListener getMouseListenerSeleccionarEnLaTablaOrdenesTrabajoDisponibles() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = vista.getTablaOrdenesTrabajoDisponibles().getSelectedRow();
				if(fila<0) {
					JOptionPane.showConfirmDialog(null, "Tienes que seleccionar un pedido","Error de selección",2);
				}else{
					vista.getBtnCrearOT().setEnabled(true);
				}
			}
		};
	}
	public ActionListener getActionListenerCancelar() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vista.getBtnCrearOT().setEnabled(false);
			}
		};
	}
	public ActionListener getActionPerformedVerOrdenesTrabajo() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnOrdenesDeTrabajoSeleccionadas");
				TableModel tmodel = SwingUtil.getTableModelFromPojos(getOrdenesDeTrabajoSeleccionadas(),
						new String[] { "fechaCreacion", "estado", "incidencias", "almaceneroId" });
				vista.getTablaOrdenesTrabajoSeleccionadas().setModel(tmodel);
				SwingUtil.autoAdjustColumns(vista.getTablaOrdenesTrabajoSeleccionadas());
			}
		};
	}
	public ActionListener getActionPerformedIdentificarseAlmacenero() {
	return new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String id = JOptionPane.showInputDialog("Por favor, identifícate con tu 'id':");
			if (id != null && !id.trim().isEmpty()) {
				if (!isOkIdAlmacenero(id)) {
					JOptionPane.showMessageDialog(null, "No has ingresado un id de un almacenero.");
				} else {
					CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
					cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnPaginaPrincipal");
				}

			} else {
				JOptionPane.showMessageDialog(null, "No has ingresado un id correcto");
			}
		}
	};
	}
	public TableModel getTableModelSeleccionOrdenesTrabajo() {
		TableModel tmodel =SwingUtil.getTableModelFromPojos(getOrdenesDeTrabajoSeleccionadas(),
				new String[] { "fechaCreacion", "estado", "incidencias", "almaceneroId" });
		return tmodel;
		
	}
	public TableModel getTableModelPrdenesTrabajoPendientesEmpaquetado() {
		TableModel tmodel =SwingUtil.getTableModelFromPojos(getOrdenesDeTrabajoPendiestesEmpaquetado(),
				new String[] { "fechaCreacion", "estado", "incidencias", "almaceneroId" });
		return tmodel;
	}
	

	public TableModel getTableModerElemetosARecoger() {
		TableModel tmodel =SwingUtil.getTableModelFromPojos(getElementosARecogerDeLaWorkorderSeleccionada(),
				new String[] { "codigoBarras", "cantidad"});
		pedidosAsignados=model.getOrdenesDeTrabajoDelAlmaceneroPorId(almaceneroId);
		return tmodel;
	}
	
	public TableModel getTableModerElemetosRecogidos() {
		TableModel tmodel =SwingUtil.getTableModelFromPojos(getElementosARecogerDeLaWorkorderSeleccionada(),
				new String[] { "nombre", "cantidad","pasillo","posicion","estanteria","altura"});
		return tmodel;
	}
	
	
	public ActionListener getActionListenerVolverAtrasHaciaOrdenesTrabajo() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vista.getBtnRecoger().setEnabled(false);
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnPaginaPrincipal");
			}
		};
	}
	public ActionListener getActionListenerVolverAlPnEmpaquetado() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnPaginaPrincipal");
			}
		};
	}
	public ActionListener getActionListenerFinalizarRecogida() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			vista.getTablaOrdenesTrabajoDisponibles().setEnabled(true);
			vista.getTablaOrdenesTrabajoSeleccionadas().setEnabled(true);
				model.updateToPendienteDeEmpaquetadoElProducto(ordenTrabajoEnRecogida);
			}
		};
	}
	public ActionListener getActionListenerEmpezarARecoger() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila=vista.getTablaOrdenesTrabajoSeleccionadas().getSelectedRow();
				model.updateWorkOrderParaQuePaseAEnProcesoDeRecogida(pedidosAsignados.get(fila));
				vista.getTablaOrdenesTrabajoSeleccionadas().setEnabled(false);
				TableModel tmodel = getTableModerElemetosRecogidos();
				vista.getTablaOrdenesElementosRecogidos().setModel(tmodel);
				vista.getTablaOrdenesElementosRecogidos().revalidate();
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				SwingUtil.autoAdjustColumns(vista.getTablaOrdenesElementosRecogidos());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnRecogida");
				pedidosAsignados=model.getOrdenesDeTrabajoDelAlmaceneroPorId(almaceneroId);
				vista.getLblOTid().setText("OT id: "+ordenTrabajoEnRecogida.getId());
			}
		};
	}
	public ActionListener getActionListenerEmpezarEmpezarProcesoEmpaquetado() {
	return new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
			cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnRecogida");
		}
	};
	}
	public ActionListener getActionListenerCrearOrdenTrabajo() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vista.getTablaOrdenesTrabajoDisponibles().setEnabled(false);
				vista.getBtnCrearOT().setEnabled(false);
				ponEnRecogidaElPedido(vista.getTablaOrdenesTrabajoDisponibles().getSelectedRow());
				TableModel tmodel = SwingUtil.getTableModelFromPojos(getPedidosPendientesDeEntrarEnUnaOT(),
						new String[] { "fecha", "tamaño", "estado" });
				vista.getTablaOrdenesTrabajoDisponibles().setModel(tmodel);
				vista.getTablaOrdenesTrabajoDisponibles().revalidate();
				vista.getTablaOrdenesTrabajoDisponibles().repaint();
				SwingUtil.autoAdjustColumns(vista.getTablaOrdenesTrabajoDisponibles());
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnOrdenesDeTrabajoSeleccionadas");
				TableModel tmodel2 = SwingUtil.getTableModelFromPojos(getOrdenesDeTrabajoSeleccionadas(),
						new String[] { "fechaCreacion", "estado", "incidencias", "almaceneroId" });
				vista.getTablaOrdenesTrabajoSeleccionadas().setModel(tmodel2);
				vista.getTablaOrdenesTrabajoSeleccionadas().revalidate();
				vista.getTablaOrdenesTrabajoSeleccionadas().repaint();
				SwingUtil.autoAdjustColumns(vista.getTablaOrdenesTrabajoSeleccionadas());
				getPedidosPendientesDeEntrarEnUnaOT();
			}
		};
	}
	public MouseListener getMouseListenerSeleccionarUnaOtParaEmpaquetar() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(vista.getTablaOrdenesTrabajoSeleccionadas().getSelectedRow()<0) {
					JOptionPane.showConfirmDialog(null, "Tienes que seleccionar una OT","Error de selección",2);
				}else {
					vista.getBtnRecoger().setEnabled(true); 
				}
				
			}
		};
	}
	
	public MouseListener getMouseListenerSeleccionarEnLaTablaOrdenesPendientesEmpaquetado() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(vista.getTablaOrdenesTrabajoPendientesEmpaquetado().getSelectedRow()<0) {
					JOptionPane.showConfirmDialog(null, "Tienes que seleccionar una OT","Error de selección",2);
				}else {
					vista.getBtnIniciarEmpaquetado().setEnabled(true); 
				}
			}
		};
	}
	
	public ActionListener getActionPerformedEscanearUnProducto() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigoBarras = JOptionPane
						.showInputDialog("Escanea el codigo de barras: ");
				if(codigoBarras.isBlank()) {
					JOptionPane.showMessageDialog(null, "No has escaneado un código correcto");
				}else if(!isAValidCodeOfWO(codigoBarras)) {
					JOptionPane.showMessageDialog(null, "Ese código de barras no pertenece a esta OT");
				}else if(!isValidUnits((int)vista.getSpinner().getValue(),codigoBarras)) {
					JOptionPane.showMessageDialog(null, "La cantidad a recoger es incorrecta");
				}else {
					List<ElementoARecogerDto> elementosNoEliminados=new ArrayList<ElementoARecogerDto>();
					for(ElementoARecogerDto elemento:elementosARecoger) {
						if(!String.valueOf(elemento.getCodigoBarras()).equals(codigoBarras)) {
							elementosNoEliminados.add(elemento);
						}
					}
					elementosARecoger=elementosNoEliminados;
					TableModel tmodel =SwingUtil.getTableModelFromPojos(elementosARecoger,
							new String[] { "nombre", "cantidad","pasillo","posicion","estanteria","altura"});
					vista.getTablaOrdenesElementosRecogidos().setModel(tmodel);
					SwingUtil.autoAdjustColumns(vista.getTablaOrdenesElementosRecogidos());
					if(elementosARecoger.size()==0){
					vista.getBtnFinalizarRecogida().setEnabled(true); ;
					}
				}
				
			}			
		};
	}
	
	private boolean isAValidCodeOfWO(String codigoBarras) {
		for(ElementoARecogerDto elemento:elementosARecoger) {
			if(String.valueOf(elemento.getCodigoBarras()).equals(codigoBarras)) {
				return true;
			}
		}
		return false;
	}
	private boolean isValidUnits(int unidades,String codigoBarras) {
		for(ElementoARecogerDto elemento:elementosARecoger) {
			if(elemento.cantidad==unidades && String.valueOf(elemento.getCodigoBarras()).equals(codigoBarras)) {
				return true;
			}
		}
		return false;
	}
	public ActionListener getActionPerformedNotificaIncidencia() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String incidencia = JOptionPane
						.showInputDialog("Escribe la incidencia: ");
				if(!incidencia.isBlank()) {
					int decision=JOptionPane.showConfirmDialog(null, "Texto incidencia:\n"+incidencia,"Notificación de incidencia:",2);
					if(decision==JOptionPane.OK_OPTION) {
						model.actualizaIncidenciaOT(incidencia,ordenTrabajoEnRecogida);
						vista.getBtnNotificarIncidencia().setEnabled(false);
					}
				}
				
			}
		};
	}
	public ActionListener getActionPerformedMuestraPanelEmpaquetado() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnEmpaquetado");
			}
		};
	}
	
	
}
