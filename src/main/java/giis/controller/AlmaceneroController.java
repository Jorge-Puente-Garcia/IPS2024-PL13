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
	private List<OrdenTrabajoDto> pedidosAsignadosParaImprimir;
	private int almaceneroId;
	
	
	public AlmaceneroController(AlmaceneroView almaceneroView) {
		this.vista=almaceneroView;
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
	public ActionListener getActionListenerParaSeleccionarOrdenesTrabajo() {
		// TODO Auto-generated method stub
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
				if (fila >= 0) {
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
	public ActionListener getActionListenerVolverAtrasHaciaOrdenesTrabajo() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
	public ActionListener getActionListenerFinalizarEmpaquetado() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String worOrdId = JOptionPane
						.showInputDialog("Por favor, dame el id de la workorder con el empaquetado finalizado: ");
				String codigoBarras = JOptionPane.showInputDialog("Código de barras que tenrá el paquete: : ");
				OrdenTrabajoDto dto = new OrdenTrabajoDto();
				dto.setId(worOrdId);
				dto.setCodigoBarras(codigoBarras);
				JOptionPane.showMessageDialog(null, getEtiquetaEnvio(dto), "Etiqueta de envio: ",
						JOptionPane.INFORMATION_MESSAGE);
				String infoAlvaran = getAlbaran(dto);
				vista.getTaAlbaran().setText(infoAlvaran);
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnAlbaran");
			}
		};
	}
	public ActionListener getActionListenerEmpaquetar() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnEmpaquetado");
			}
		};
	}
	public ActionListener getActionListenerEmpezarEmpezarProcesoEmpaquetado() {
	return new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
			cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnEmpaquetado");
		}
	};
	}
	public ActionListener getActionListenerCrearOrdenTrabajo() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vista.getTablaOrdenesTrabajoDisponibles().setEnabled(false);
				vista.getBtnCrearOT().setEnabled(false);
				ponEnRecogidaElPedido(vista.getTablaOrdenesTrabajoDisponibles().getSelectedRow());
				TableModel tmodel = SwingUtil.getTableModelFromPojos(getPedidosPendientesRecogida(),
						new String[] { "fecha", "tamaño", "estado" });
				vista.getTablaOrdenesTrabajoDisponibles().setModel(tmodel);
				SwingUtil.autoAdjustColumns(vista.getTablaOrdenesTrabajoDisponibles());
				CardLayout cl = (CardLayout) (vista.getFrameTerminalPortatil().getContentPane().getLayout());
				cl.show(vista.getFrameTerminalPortatil().getContentPane(), "pnOrdenesDeTrabajoSeleccionadas");
				TableModel tmodel2 = SwingUtil.getTableModelFromPojos(getOrdenesDeTrabajoSeleccionadas(),
						new String[] { "fechaCreacion", "estado", "incidencias", "almaceneroId" });
				vista.getTablaOrdenesTrabajoSeleccionadas().setModel(tmodel2);
				SwingUtil.autoAdjustColumns(vista.getTablaOrdenesTrabajoSeleccionadas());
			}
		};
	}
	
}
