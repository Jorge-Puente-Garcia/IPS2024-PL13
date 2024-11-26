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

import giis.model.Almacenero.AlmaceneroModel;
import giis.model.Almacenero.ElementoARecogerDto;
import giis.model.Almacenero.Estado;
import giis.model.Almacenero.FilaInformeVentasUsuarioDia;
import giis.model.Almacenero.OrdenTrabajoDto;
import giis.model.Almacenero.OrdenTrabajoRecord;
import giis.model.Almacenero.PaqueteAExpedirDto;
import giis.model.Almacenero.PedidoARecogerDto;
import giis.model.Almacenero.PedidoARecogerRecord;
import giis.ui.AlmaceneroView;
import giis.util.Database;
import giis.util.SwingUtil;

public class AlmaceneroController {
	
	private static final int TAMAÑO_WORKORDER = 5;
	
	private AlmaceneroModel model;
	private AlmaceneroView vista;
	private List<PedidoARecogerRecord> pedidosSinRecoger;
	private List<PedidoARecogerDto> pedidosSinRecogerParaImprimir;
	private List<OrdenTrabajoRecord> pedidosAsignados;
	private List<OrdenTrabajoRecord> pedidosAPendientesEmpaquetado;
	private List<OrdenTrabajoDto> pedidosAsignadosParaImprimir;
	private List<OrdenTrabajoDto> pedidosPendientesEmpaquetadoParaImprimir;
	private List<ElementoARecogerDto> elementosARecoger;
	private List<ElementoARecogerDto> elementosAEmpaquetar;
	private OrdenTrabajoRecord ordenTrabajoEnRecogida;
	private OrdenTrabajoRecord ordenTrabajoEnEmpaquetado;
	private int codigoBarrasCaja;
	private int almaceneroId;
	private String tipoVehiculo="";
	
	
	public AlmaceneroController(AlmaceneroView almaceneroView, Database db) {
		this.vista=almaceneroView;
		this.model = new AlmaceneroModel(db);
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
	private List<ElementoARecogerDto> getElementosAEmpaquetarDeLaWorkorderSeleccionada() {
		ordenTrabajoEnEmpaquetado=pedidosAPendientesEmpaquetado.get(vista.getTablaOrdenesTrabajoPendientesEmpaquetado().getSelectedRow());
		this.elementosAEmpaquetar =model.getElementosAEmpaquetarDeLaOrdenDeTrabajo(ordenTrabajoEnEmpaquetado);
		return elementosAEmpaquetar;
	}
	private List<FilaInformeVentasUsuarioDia> getImportesVestasPorTipoUsuarioYDía() {
		return model.getInformeVentasPorUsuarioYDia();
	}
	private List<PaqueteAExpedirDto> getPaquetesSegunTipo(String tipo) {
		return model.getPaquetesSegunElTipo(tipo);
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
		List<PedidoARecogerRecord> pars = new ArrayList<>();
		PedidoARecogerRecord par=pedidosSinRecoger.get(selectedRow);		
		pars.add(par);
		pedidosSinRecoger.remove(par);
		
		int tamaño = par.getTamaño();
	
		if(tamaño < TAMAÑO_WORKORDER) {
			for (PedidoARecogerRecord parr : pedidosSinRecoger) {
				int tamañoTot = tamaño + parr.getTamaño();
				if(tamañoTot <= 5) {
					pars.add(parr);
					tamaño = tamañoTot;
				}
			}
		}
		
		model.creaOrdenDeTrabajo(almaceneroId,pars);
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
	
	public TableModel getTableModelElemetosParaRecoger() {
		TableModel tmodel =SwingUtil.getTableModelFromPojos(getElementosARecogerDeLaWorkorderSeleccionada(),
				new String[] { "nombre", "cantidad","pasillo","posicion","estanteria","altura"});
		return tmodel;
	}
	
	public TableModel getTableModelElementosAEmpaquetarDeUnaOt() {
		TableModel tmodel =SwingUtil.getTableModelFromPojos(getElementosAEmpaquetarDeLaWorkorderSeleccionada(),
				new String[] { "nombre", "cantidad"});
		return tmodel;
	}
	
	protected TableModel getTableModelInformeVentasUsuarioDia() {
		TableModel tmodel =SwingUtil.getTableModelFromPojos(getImportesVestasPorTipoUsuarioYDía(),
				new String[] { "dia, particular", "empresa, total de los dias"});
		return null;
	}
	
	protected TableModel getTableModelPaquetesSegunTipoDeVehiculo(String tipo) {
		TableModel tmodel =SwingUtil.getTableModelFromPojos(getPaquetesSegunTipo(tipo),
				new String[] { "tipo", "destinatario","codigoBarras"});
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
			model.updateToPendienteDeEmpaquetadoElProducto(ordenTrabajoEnRecogida);
			
			}
		};
	}
	public ActionListener getActionListenerEmpezarARecoger() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//int fila=vista.getTablaOrdenesTrabajoSeleccionadas().getSelectedRow();
				//Lo comento porque no quiero que desaparezca de la ventaa de OTs para recoger(funcionalida de poner en pausa)
				//model.updateWorkOrderParaQuePaseAEnProcesoDeRecogida(pedidosAsignados.get(fila));
				
				//No quiero que se impida que se puedan seleccionar otras 
				//vista.getTablaOrdenesTrabajoSeleccionadas().setEnabled(false);
				TableModel tmodel = getTableModelElemetosParaRecoger();
				vista.getTablaOrdenesElementParaRecoger().setModel(tmodel);
				vista.getTablaOrdenesElementParaRecoger().revalidate();
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				SwingUtil.autoAdjustColumns(vista.getTablaOrdenesElementParaRecoger());
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
				//vista.getTablaOrdenesTrabajoDisponibles().setEnabled(false);
				vista.getBtnCrearOT().setEnabled(false);
				
				
				ponEnRecogidaElPedido(vista.getTablaOrdenesTrabajoDisponibles().getSelectedRow());
				
				TableModel tmodel = SwingUtil.getTableModelFromPojos(getPedidosPendientesDeEntrarEnUnaOT(),
						new String[] { "fecha", "tamaño", "estado" });
				
				vista.getTablaOrdenesTrabajoDisponibles().setModel(tmodel);				
				SwingUtil.autoAdjustColumns(vista.getTablaOrdenesTrabajoDisponibles());
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnOrdenesDeTrabajoSeleccionadas");
				TableModel tmodel2 = SwingUtil.getTableModelFromPojos(getOrdenesDeTrabajoSeleccionadas(),
						new String[] { "fechaCreacion", "estado", "incidencias", "almaceneroId" });
				vista.getTablaOrdenesTrabajoSeleccionadas().setModel(tmodel2);
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
				}else if(!isAValidCodeOfWO(codigoBarras,elementosARecoger)) {
					JOptionPane.showMessageDialog(null, "Ese código de barras no pertenece a esta OT");
				}else if(!isValidUnits((int)vista.getSpinner().getValue(),codigoBarras,elementosARecoger)) {
					JOptionPane.showMessageDialog(null, "La cantidad a recoger es incorrecta");
				}else {
					List<ElementoARecogerDto> elementosNoEliminados=new ArrayList<ElementoARecogerDto>();
					for(ElementoARecogerDto elemento:elementosARecoger) {
						if(!String.valueOf(elemento.getCodigoBarras()).equals(codigoBarras)) {
							elementosNoEliminados.add(elemento);
						}else  {
							if(elemento.cantidad>=(int)vista.getSpinner().getValue()) {
							//Se le resta la cantidad que ya se ha recogido
							int recogido=(int)vista.getSpinner().getValue();
							if(elemento.cantidad==recogido) {
								model.eliminaElOrdenTrabajoProductoYActualizaOrdenTrabajoProductoRecogido(ordenTrabajoEnRecogida,elemento,recogido);
							}else{
								elemento.cantidad-=recogido;
								model.actualizaCantidadYaRecogidaDeUnProducto(ordenTrabajoEnRecogida,elemento,recogido);
								elementosNoEliminados.add(elemento);
							}
							}
						}
					}
					elementosARecoger=elementosNoEliminados;
					TableModel tmodel =SwingUtil.getTableModelFromPojos(getElementosARecogerDeLaWorkorderSeleccionada(),
							new String[] { "nombre", "cantidad","pasillo","posicion","estanteria","altura"});
					vista.getTablaOrdenesElementParaRecoger().setModel(tmodel);
					SwingUtil.autoAdjustColumns(vista.getTablaOrdenesElementParaRecoger());
					if(elementosARecoger.size()==0){
					vista.getBtnFinalizarRecogida().setEnabled(true); ;
					}
				}
				
			}			
		};
	}
	
	public ActionListener getActionPerformedEscanearUnProductoAEmpaquetar() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigoBarras = JOptionPane
						.showInputDialog("Escanea el codigo de barras: ");
				if(codigoBarras.isBlank()) {
					JOptionPane.showMessageDialog(null, "No has escaneado un código correcto");
				}else if(!isAValidCodeOfWO(codigoBarras,elementosAEmpaquetar)) {
					JOptionPane.showMessageDialog(null, "Ese código de barras no pertenece a esta OT");
				}else if(!isValidUnits((int)vista.getSpnCantidadElementosAEmpaquetar().getValue(),codigoBarras,elementosAEmpaquetar)) {
					JOptionPane.showMessageDialog(null, "La cantidad a recoger es incorrecta");
				}else {
					List<ElementoARecogerDto> elementosNoEliminados=new ArrayList<ElementoARecogerDto>();
					for(ElementoARecogerDto elemento:elementosAEmpaquetar) {
						if(!String.valueOf(elemento.getCodigoBarras()).equals(codigoBarras)) {
							elementosNoEliminados.add(elemento);
						}else {
							if(elemento.cantidad>=(int)vista.getSpinner().getValue()) {
								//Se le resta la cantidad que ya se ha recogido
								int recogido=(int)vista.getSpnCantidadElementosAEmpaquetar().getValue();
								if(elemento.cantidad==recogido) {
									if(elementosAEmpaquetar.size()==1) {
										model.empaquetaProducto(ordenTrabajoEnEmpaquetado,codigoBarrasCaja);
									}
									model.eliminaElOrdenTrabajoProductoYActualizaPaqueteProducto(ordenTrabajoEnEmpaquetado,elemento,recogido);
								}else{
									elemento.cantidad-=recogido;
									model.actualizaCantidadYaEmpaquetadaDeUnProducto(ordenTrabajoEnEmpaquetado,elemento,recogido);
									elementosNoEliminados.add(elemento);
								}
								}
							
							
						}
					}
					elementosAEmpaquetar=elementosNoEliminados;
					TableModel tmodel =SwingUtil.getTableModelFromPojos(model.getElementosAEmpaquetarDeLaOrdenDeTrabajo(ordenTrabajoEnEmpaquetado),
							new String[] { "nombre", "cantidad"});
					vista.getTablaElementosProcesoEmpaquetadoDeUnaOt().setModel(tmodel);
					SwingUtil.autoAdjustColumns(vista.getTablaElementosProcesoEmpaquetadoDeUnaOt());
					if(elementosAEmpaquetar.size()==0){
					vista.getBtnFinalizarEmpaquetadoDeLaOt().setEnabled(true); ;
					}
				}
			}
		};
	}
	
	public ActionListener getActionPerformedEscanearUnPaqueteAExpedir() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigoBarras ="";
				codigoBarras+=JOptionPane
						.showInputDialog("Escanea el codigo de barras: ");
				
				if(codigoBarras.isBlank()) {
					JOptionPane.showMessageDialog(null, "No has escaneado un código correcto");
				}else if(!isValidParaElTipoExpedicion(codigoBarras)) {
					JOptionPane.showMessageDialog(null, "Ese código de barras pertenece a un paquete que no es del tipo adecuado para el vehículo");
				}else {
					model.eliminaPaqueteYaEmpaquetado(codigoBarras);
					TableModel tmodel = getTableModelPaquetesSegunTipoDeVehiculo(tipoVehiculo);
					vista.getTbPaquetesParaExpedicion().setModel(tmodel);
					SwingUtil.autoAdjustColumns(vista.getTbPaquetesParaExpedicion());
				}
			}
		};
	}
	
	protected boolean isValidParaElTipoExpedicion(String codigoBarras) {
		return model.esUnTipoDePaqueteValido(codigoBarras);
	}
	private boolean isAValidCodeOfWO(String codigoBarras,List<ElementoARecogerDto> elementosARecoger) {
		for(ElementoARecogerDto elemento:elementosARecoger) {
			if(String.valueOf(elemento.getCodigoBarras()).equals(codigoBarras)) {
				return true;
			}
		}
		return false;
	}
	private boolean isValidUnits(int unidades,String codigoBarras,List<ElementoARecogerDto> elementosARecoger) {
		for(ElementoARecogerDto elemento:elementosARecoger) {
			if(elemento.cantidad>=unidades && String.valueOf(elemento.getCodigoBarras()).equals(codigoBarras)) {
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
				TableModel tmodel = getTableModelPrdenesTrabajoPendientesEmpaquetado();
				vista.getTablaOrdenesTrabajoPendientesEmpaquetado().setModel(tmodel);
				SwingUtil.autoAdjustColumns(vista.getTablaOrdenesTrabajoPendientesEmpaquetado());
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnEmpaquetado");
			}
		};
	}
	public ActionListener getActionPerformedMuestraPanelEmpaquetadoProductos() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnEmpaquetadoProductos");
			}
		};
	}
	public ActionListener getActionPerformedIniciarProcesoEmpaquetado() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableModel tmodel = getTableModelElementosAEmpaquetarDeUnaOt();
				vista.getTablaElementosProcesoEmpaquetadoDeUnaOt().setModel(tmodel);
				SwingUtil.autoAdjustColumns(vista.getTablaElementosProcesoEmpaquetadoDeUnaOt());
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnEmpaquetadoProductos");
				vista.getBtnIniciarEmpaquetado().setEnabled(false);
				codigoBarrasCaja= model.creaPaqueteParaElProcesoEmpaquetado();
				//model.PonEnProcesoEmpaquetadoLaOt(ordenTrabajoEnEmpaquetado);
				TableModel tmodel2 = getTableModelPrdenesTrabajoPendientesEmpaquetado();
				vista.getTablaOrdenesTrabajoPendientesEmpaquetado().setModel(tmodel2);
				SwingUtil.autoAdjustColumns(vista.getTablaOrdenesTrabajoPendientesEmpaquetado());
			}
		};
	}
	public ActionListener getActionPerformedFinalizarProcesoEmpaquetado() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vista.getBtnFinalizarEmpaquetadoDeLaOt().setEnabled(false);
				model.ponLaWorkOrderAEmpaquetada(ordenTrabajoEnEmpaquetado);
				vista.getTablaOrdenesTrabajoPendientesEmpaquetado().setEnabled(true);
				JOptionPane.showMessageDialog(null, model.creaEtiqueta(ordenTrabajoEnEmpaquetado));
			}
		};
	}
	public ActionListener getActionListenerMostrarAlbaran() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vista.getTaAlbaran().setText(model.creaAlbaran(ordenTrabajoEnEmpaquetado));
				JOptionPane.showMessageDialog(null, vista.getTaAlbaran(),"Albaran", JOptionPane.INFORMATION_MESSAGE);
			}
		};
	}
	public ActionListener getActionListenerVisualizarPantallaInformes() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnInformesDisponibles");
			}
		};
	}
	public ActionListener getActionListnerMostrarInformeVentasUsuariDia() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableModel tmodel = getTableModelInformeVentasUsuarioDia();
				vista.getTbInfoInformes().setModel(tmodel);
				SwingUtil.autoAdjustColumns(vista.getTbInfoInformes());
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnInfoInformes");
			}
		};
	}
	public ActionListener getActionListenerEntrarVentanaDeRecivirVehiculo() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnRecepcionVehiculo");
			}
		};
	}
	public ActionListener getActionListenerRecepcionarVehiculo() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matricula= vista.getTfMatículaVehiculo().getText();
				if(matricula.isBlank()) {
					JOptionPane.showMessageDialog(null, "La matrícula no puede estar vacía");
				}else {
					String tipo="";
					if(vista.getRdbtnRegional().isSelected()) {
						tipo="Regional";
					}else if(vista.getRdbtnNacional().isSelected()) {
						tipo="Nacional";
					}
					model.reciveVehiculo(matricula,tipo);
					//Tiene que marcar las etiquetas del tipo de vehículo y de la matrícula
					vista.getLblParaElTipoDeVehiculo().setText(tipo);
					vista.getLblParaLaMatriculaDelVehiculo().setText(matricula);
					//Se pondra el modelo de tabla correspondiente
					tipoVehiculo=tipo;
					TableModel tmodel = getTableModelPaquetesSegunTipoDeVehiculo(tipo);
					vista.getTbPaquetesParaExpedicion().setModel(tmodel);
					SwingUtil.autoAdjustColumns(vista.getTbPaquetesParaExpedicion());
					//Se lleva automaticamente a la pantalla de lanzar los paquetes
					CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
					cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnExpedicionPaquetes");
					//Se bloquea la recepción de otros vehiculos por parte de este almacenero hasta que no acabe con el otro
					vista.getBtnRecepcionarElVehiculo().setEnabled(false);
					vista.getBtnFinalizarExpedicion().setEnabled(true);
					vista.getBtnEscanearPaqueteAExpedir().setEnabled(true);
				}
				
			}
		};
	}
	public ActionListener getActionListenerVerVentanaExpedicion() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnExpedicionPaquetes");
			}
		};
	}
	public ActionListener getActionListenerFinalizarExpedicionPaquetes() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Hay que quitar el vehículo y vaciar la tabla.
				model.eliminaElVehiculo(tipoVehiculo);
				tipoVehiculo="";
				TableModel tmodel = getTableModelPaquetesSegunTipoDeVehiculo(tipoVehiculo);
				vista.getTbPaquetesParaExpedicion().setModel(tmodel);
				SwingUtil.autoAdjustColumns(vista.getTbPaquetesParaExpedicion());
				//Hay que poner enabled el boton de recepcionar
				vista.getBtnRecepcionarElVehiculo().setEnabled(true);
				vista.getBtnFinalizarExpedicion().setEnabled(false);
				vista.getBtnEscanearPaqueteAExpedir().setEnabled(false);
				//Tiene que marcar las etiquetas como vacias
				vista.getLblParaElTipoDeVehiculo().setText("");
				vista.getLblParaLaMatriculaDelVehiculo().setText("");
			}
		};
	}
	
	
	
	
	
	
	
}
