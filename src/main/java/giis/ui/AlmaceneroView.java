package giis.ui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import giis.controller.AlmaceneroController;
import giis.util.SwingUtil;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;

public class AlmaceneroView {

	private JFrame frameTerminalPortatil;
	private JPanel pnPaginaPrincipal;
	private JButton btnSeleccionarOrdenesDeTrabajo;
	private JPanel pnOrdenesDeTrabajoDisponibles;
	private JButton btnVolverAPaginaPrincipal;
	private JScrollPane scrollPane;
	private JTable tablaOrdenesTrabajoDisponibles;
	private AlmaceneroController controller;
	private JButton btnCrearOT;
	private JButton btnCancelar;
	private JButton btnVerOrdenesDeTrabajo;
	private JPanel pnIdentificación;
	private JButton btnIdentificarse;
	private JPanel pnOrdenesDeTrabajoSeleccionadas;
	private JScrollPane scrpOrdenesTrabajoSeleccionadas;
	private JTable tablaOrdenesTrabajoSeleccionadas;
	private JButton btnVolverAtrasVerOrdenesTrabajo;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlmaceneroView window = new AlmaceneroView();
					window.frameTerminalPortatil.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AlmaceneroView() {
		initialize();
		
	}
	public JFrame getFrameTerminalPortatil() {
		return frameTerminalPortatil;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		controller=new AlmaceneroController();
		frameTerminalPortatil = new JFrame();
		frameTerminalPortatil.setBounds(100, 100, 444, 453);
		frameTerminalPortatil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameTerminalPortatil.getContentPane().setLayout(new CardLayout(0, 0));
		frameTerminalPortatil.getContentPane().add(getPnIdentificación(), "pnIdentificación");
		frameTerminalPortatil.getContentPane().add(getPnPaginaPrincipal(), "pnPaginaPrincipal");
		frameTerminalPortatil.getContentPane().add(getPnOrdenesDeTrabajoDisponibles(), "pnOrdenesDeTrabajo");
		frameTerminalPortatil.getContentPane().add(getPnOrdenesDeTrabajoSeleccionadas(), "pnOrdenesDeTrabajoSeleccionadas");
	}

	private JPanel getPnPaginaPrincipal() {
		if (pnPaginaPrincipal == null) {
			pnPaginaPrincipal = new JPanel();
			pnPaginaPrincipal.setLayout(new GridLayout(3, 1, 0, 0));
			pnPaginaPrincipal.add(getBtnSeleccionarOrdenesDeTrabajo());
			pnPaginaPrincipal.add(getBtnVerOrdenesDeTrabajo());
		}
		return pnPaginaPrincipal;
	}
	private JButton getBtnSeleccionarOrdenesDeTrabajo() {
		if (btnSeleccionarOrdenesDeTrabajo == null) {
			btnSeleccionarOrdenesDeTrabajo = new JButton("Seleccionar ordenes de trbajo");
			btnSeleccionarOrdenesDeTrabajo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)(frameTerminalPortatil.getContentPane().getLayout());
					cl.show(frameTerminalPortatil.getContentPane(), "pnOrdenesDeTrabajo");
				}
			});
		}
		return btnSeleccionarOrdenesDeTrabajo;
	}
	private JPanel getPnOrdenesDeTrabajoDisponibles() {
		if (pnOrdenesDeTrabajoDisponibles == null) {
			pnOrdenesDeTrabajoDisponibles = new JPanel();
			pnOrdenesDeTrabajoDisponibles.setLayout(null);
			pnOrdenesDeTrabajoDisponibles.add(getBtnVolverAPaginaPrincipal());
			pnOrdenesDeTrabajoDisponibles.add(getScrollPane(), "cell 10 3,grow");
			pnOrdenesDeTrabajoDisponibles.add(getBtnCrearOT());
			pnOrdenesDeTrabajoDisponibles.add(getBtnCancelar());
		}
		return pnOrdenesDeTrabajoDisponibles;
	}
	private JButton getBtnVolverAPaginaPrincipal() {
		if (btnVolverAPaginaPrincipal == null) {
			btnVolverAPaginaPrincipal = new JButton("Volver");
			btnVolverAPaginaPrincipal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)(frameTerminalPortatil.getContentPane().getLayout());
					cl.show(frameTerminalPortatil.getContentPane(), "pnPaginaPrincipal");
				}
			});
			btnVolverAPaginaPrincipal.setEnabled(true);
			btnVolverAPaginaPrincipal.setBounds(10, 10, 110, 34);
		}
		return btnVolverAPaginaPrincipal;
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTablaOrdenesTrabajoDisponibles());
			scrollPane.setBounds(85, 72, 259, 252);
			scrollPane.setViewportView(getTablaOrdenesTrabajoDisponibles());
		}
		return scrollPane;
	}
	public JTable getTablaOrdenesTrabajoDisponibles() {
		if (tablaOrdenesTrabajoDisponibles == null) {
			tablaOrdenesTrabajoDisponibles = new JTable();
			tablaOrdenesTrabajoDisponibles.setFillsViewportHeight(true);
			tablaOrdenesTrabajoDisponibles.setName("tabPedidosNoRecogidos");
			tablaOrdenesTrabajoDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaOrdenesTrabajoDisponibles.setDefaultEditor(Object.class, null); //readonly
			TableModel tmodel=SwingUtil.getTableModelFromPojos(controller.getPedidosPendientesRecogida(), new String[] {"fecha", "tamaño", "estado"});
			tablaOrdenesTrabajoDisponibles.setModel(tmodel);
			SwingUtil.autoAdjustColumns(tablaOrdenesTrabajoDisponibles);tablaOrdenesTrabajoDisponibles.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int fila = tablaOrdenesTrabajoDisponibles.getSelectedRow();
					if(fila >= 0) {
						getBtnCrearOT().setEnabled(true);
					}
				}
			});
		}
		return tablaOrdenesTrabajoDisponibles;
	}
	private JButton getBtnCrearOT() {
		if (btnCrearOT == null) {
			btnCrearOT = new JButton("Crear OT");
			btnCrearOT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getTablaOrdenesTrabajoDisponibles().setEnabled(false);
					getBtnCrearOT().setEnabled(false);
					controller.ponEnRecogidaElPedido(getTablaOrdenesTrabajoDisponibles().getSelectedRow());
					TableModel tmodel=SwingUtil.getTableModelFromPojos(controller.getPedidosPendientesRecogida(), new String[] {"fecha", "tamaño", "estado"});
					getTablaOrdenesTrabajoDisponibles().setModel(tmodel);
					SwingUtil.autoAdjustColumns(tablaOrdenesTrabajoDisponibles);
					CardLayout cl = (CardLayout)(frameTerminalPortatil.getContentPane().getLayout());
					cl.show(frameTerminalPortatil.getContentPane(), "pnOrdenesDeTrabajoSeleccionadas");
					TableModel tmodel2=SwingUtil.getTableModelFromPojos(controller.getOrdenesDeTrabajoSeleccionadas(), new String[] {"fechaCreacion", "estado", "incidencias","almaceneroId"});
					getTablaOrdenesTrabajoSeleccionadas().setModel(tmodel2);
					SwingUtil.autoAdjustColumns(getTablaOrdenesTrabajoSeleccionadas());
				}
			});
			btnCrearOT.setEnabled(false);
			btnCrearOT.setBounds(310, 372, 110, 34);
		}
		return btnCrearOT;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getBtnCrearOT().setEnabled(false);
				}
			});
			btnCancelar.setBounds(190, 372, 110, 34);
		}
		return btnCancelar;
	}
	private JButton getBtnVerOrdenesDeTrabajo() {
		if (btnVerOrdenesDeTrabajo == null) {
			btnVerOrdenesDeTrabajo = new JButton("Ver mis ordenes de trabajo");
			btnVerOrdenesDeTrabajo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)(frameTerminalPortatil.getContentPane().getLayout());
					cl.show(frameTerminalPortatil.getContentPane(), "pnOrdenesDeTrabajoSeleccionadas");
					TableModel tmodel=SwingUtil.getTableModelFromPojos(controller.getOrdenesDeTrabajoSeleccionadas(), new String[] {"fechaCreacion", "estado", "incidencias","almaceneroId"});
					getTablaOrdenesTrabajoSeleccionadas().setModel(tmodel);
					SwingUtil.autoAdjustColumns(getTablaOrdenesTrabajoSeleccionadas());
				}
			});
		}
		return btnVerOrdenesDeTrabajo;
	}
	private JPanel getPnIdentificación() {
		if (pnIdentificación == null) {
			pnIdentificación = new JPanel();
			pnIdentificación.setLayout(null);
			pnIdentificación.add(getBtnIdentificarse());
		}
		return pnIdentificación;
	}
	private JButton getBtnIdentificarse() {
		if (btnIdentificarse == null) {
			btnIdentificarse = new JButton("Identifícate");
			btnIdentificarse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String id = JOptionPane.showInputDialog("Por favor, identifícate con tu 'id':");
					if (id != null && !id.trim().isEmpty()) {
	                   if(!controller.isOkIdAlmacenero(id)) {
	                	    JOptionPane.showMessageDialog(null, "No has ingresado un id de un almacenero.");
	                   }else {
	                	   CardLayout cl = (CardLayout)(frameTerminalPortatil.getContentPane().getLayout());
	   						cl.show(frameTerminalPortatil.getContentPane(), "pnPaginaPrincipal");
	                   }
	                   
	                } else {
	                    JOptionPane.showMessageDialog(null, "No has ingresado un id correcto");
	                }
				}
			});
			btnIdentificarse.setBounds(92, 159, 222, 45);
		}
		return btnIdentificarse;
	}
	private JPanel getPnOrdenesDeTrabajoSeleccionadas() {
		if (pnOrdenesDeTrabajoSeleccionadas == null) {
			pnOrdenesDeTrabajoSeleccionadas = new JPanel();
			pnOrdenesDeTrabajoSeleccionadas.setLayout(null);
			pnOrdenesDeTrabajoSeleccionadas.add(getScrpOrdenesTrabajoSeleccionadas());
			pnOrdenesDeTrabajoSeleccionadas.add(getBtnVolverAtrasVerOrdenesTrabajo());
		}
		return pnOrdenesDeTrabajoSeleccionadas;
	}
	private JScrollPane getScrpOrdenesTrabajoSeleccionadas() {
		if (scrpOrdenesTrabajoSeleccionadas == null) {
			scrpOrdenesTrabajoSeleccionadas = new JScrollPane((Component) null);
			scrpOrdenesTrabajoSeleccionadas.setBounds(34, 56, 355, 252);
			scrpOrdenesTrabajoSeleccionadas.setViewportView(getTablaOrdenesTrabajoSeleccionadas());
		}
		return scrpOrdenesTrabajoSeleccionadas;
	}
	
	public JTable getTablaOrdenesTrabajoSeleccionadas() {
		if (tablaOrdenesTrabajoSeleccionadas == null) {
			tablaOrdenesTrabajoSeleccionadas = new JTable();
			tablaOrdenesTrabajoSeleccionadas.setFillsViewportHeight(true);
			tablaOrdenesTrabajoSeleccionadas.setName("tabPedidosNoEmpaquetados");
			tablaOrdenesTrabajoSeleccionadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaOrdenesTrabajoSeleccionadas.setDefaultEditor(Object.class, null); //readonly
			TableModel tmodel=SwingUtil.getTableModelFromPojos(controller.getOrdenesDeTrabajoSeleccionadas(), new String[] {"fechaCreacion", "estado", "incidencias","almaceneroId"});
			tablaOrdenesTrabajoSeleccionadas.setModel(tmodel);
			SwingUtil.autoAdjustColumns(tablaOrdenesTrabajoSeleccionadas);
				
		}
		return tablaOrdenesTrabajoSeleccionadas;
	}
	private JButton getBtnVolverAtrasVerOrdenesTrabajo() {
		if (btnVolverAtrasVerOrdenesTrabajo == null) {
			btnVolverAtrasVerOrdenesTrabajo = new JButton("Volver");
			btnVolverAtrasVerOrdenesTrabajo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)(frameTerminalPortatil.getContentPane().getLayout());
					cl.show(frameTerminalPortatil.getContentPane(), "pnPaginaPrincipal");
				}
			});
			btnVolverAtrasVerOrdenesTrabajo.setBounds(10, 10, 108, 36);
		}
		return btnVolverAtrasVerOrdenesTrabajo;
	}
}
