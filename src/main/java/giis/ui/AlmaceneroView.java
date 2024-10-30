package giis.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableModel;

import giis.controller.AlmaceneroController;
import giis.util.SwingUtil;

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
	private JPanel pnEmpaquetado;
	private JButton btnVolverPnEmpaquetado;
	private JButton btnFinalizarEmpaquetado;
	private JButton btnEmpaquetar;
	private JButton btnEnProcesoEmpaquetado;
	private JPanel pnVisualizarAlbaran;
	private JScrollPane scrpAlbaran;
	private JTextArea taAlbaran;
	protected String infoAlvaran;
	private JScrollPane scrpVisualizarElementosYaEmpaquetados;
	private JLabel lblCantidad;
	private JSpinner spinner;
	private JButton btnEscanearYAñadir;
	private JButton btnNotificarIncidencia;
	private JTable tablaOrdenesElemetosRecogidos;
	private JLabel lblArecoger;

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
		controller = new AlmaceneroController(this);
		frameTerminalPortatil = new JFrame();
		frameTerminalPortatil.setBounds(100, 100, 412, 463);
		frameTerminalPortatil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameTerminalPortatil.getContentPane().setLayout(new CardLayout(0, 0));
		frameTerminalPortatil.getContentPane().add(getPnIdentificación(), "pnIdentificación");
		frameTerminalPortatil.getContentPane().add(getPnPaginaPrincipal(), "pnPaginaPrincipal");
		frameTerminalPortatil.getContentPane().add(getPnOrdenesDeTrabajoDisponibles(), "pnOrdenesDeTrabajo");
		frameTerminalPortatil.getContentPane().add(getPnOrdenesDeTrabajoSeleccionadas(),
				"pnOrdenesDeTrabajoSeleccionadas");
		frameTerminalPortatil.getContentPane().add(getPnEmpaquetado(), "pnEmpaquetado");
		frameTerminalPortatil.getContentPane().add(getPnVisualizarAlbaran(), "pnAlbaran");
		frameTerminalPortatil.setLocationRelativeTo(null);
	}

	private JPanel getPnPaginaPrincipal() {
		if (pnPaginaPrincipal == null) {
			pnPaginaPrincipal = new JPanel();
			pnPaginaPrincipal.setLayout(new GridLayout(3, 1, 0, 0));
			pnPaginaPrincipal.add(getBtnSeleccionarOrdenesDeTrabajo());
			pnPaginaPrincipal.add(getBtnVerOrdenesDeTrabajo());
			pnPaginaPrincipal.add(getBtnEnProcesoEmpaquetado());
		}
		return pnPaginaPrincipal;
	}

	private JButton getBtnSeleccionarOrdenesDeTrabajo() {
		if (btnSeleccionarOrdenesDeTrabajo == null) {
			btnSeleccionarOrdenesDeTrabajo = new JButton("Seleccionar ordenes de trbajo");
			btnSeleccionarOrdenesDeTrabajo.addActionListener(controller.getActionListenerParaSeleccionarOrdenesTrabajo());
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
			btnVolverAPaginaPrincipal.addActionListener(controller.getActionListenerVolverPaginaPrincipal());
			btnVolverAPaginaPrincipal.setEnabled(true);
			btnVolverAPaginaPrincipal.setBounds(10, 10, 110, 34);
		}
		return btnVolverAPaginaPrincipal;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTablaOrdenesTrabajoDisponibles());
			scrollPane.setBounds(67, 72, 404, 252);
			scrollPane.setViewportView(getTablaOrdenesTrabajoDisponibles());
		}
		return scrollPane;
	}

	public JTable getTablaOrdenesTrabajoDisponibles() {
		if (tablaOrdenesTrabajoDisponibles == null) {
			tablaOrdenesTrabajoDisponibles = new JTable();
			tablaOrdenesTrabajoDisponibles.setFillsViewportHeight(true);
			tablaOrdenesTrabajoDisponibles.setShowHorizontalLines(false);
			tablaOrdenesTrabajoDisponibles.setName("tabPedidosNoRecogidos");
			tablaOrdenesTrabajoDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaOrdenesTrabajoDisponibles.setDefaultEditor(Object.class, null); // readonly
			TableModel tmodel = SwingUtil.getTableModelFromPojos(controller.getPedidosPendientesRecogida(),
					new String[] { "fecha", "tamaño", "estado" });
			
			tablaOrdenesTrabajoDisponibles.setModel(tmodel);
			tablaOrdenesTrabajoDisponibles.revalidate();
			tablaOrdenesTrabajoDisponibles.repaint();
			//SwingUtil.autoAdjustColumns(tablaOrdenesTrabajoDisponibles);
			tablaOrdenesTrabajoDisponibles.addMouseListener(controller.getMouseListenerSeleccionarEnLaTablaOrdenesTrabajoDisponibles());
		}
		return tablaOrdenesTrabajoDisponibles;
	}

	public JButton getBtnCrearOT() {
		if (btnCrearOT == null) {
			btnCrearOT = new JButton("Crear OT");
			btnCrearOT.addActionListener(controller.getActionListenerCrearOrdenTrabajo());
			btnCrearOT.setEnabled(false);
			btnCrearOT.setBounds(361, 372, 110, 34);
		}
		return btnCrearOT;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(controller.getActionListenerCancelar());
			btnCancelar.setBounds(241, 372, 110, 34);
		}
		return btnCancelar;
	}

	private JButton getBtnVerOrdenesDeTrabajo() {
		if (btnVerOrdenesDeTrabajo == null) {
			btnVerOrdenesDeTrabajo = new JButton("Ver mis ordenes de trabajo");
			btnVerOrdenesDeTrabajo.addActionListener(controller.getActionPerformedVerOrdenesTrabajo());
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
			btnIdentificarse.addActionListener(controller.getActionPerformedIdentificarseAlmacenero());
			btnIdentificarse.setBounds(137, 177, 222, 45);
		}
		return btnIdentificarse;
	}

	private JPanel getPnOrdenesDeTrabajoSeleccionadas() {
		if (pnOrdenesDeTrabajoSeleccionadas == null) {
			pnOrdenesDeTrabajoSeleccionadas = new JPanel();
			pnOrdenesDeTrabajoSeleccionadas.setLayout(null);
			pnOrdenesDeTrabajoSeleccionadas.add(getScrpOrdenesTrabajoSeleccionadas());
			pnOrdenesDeTrabajoSeleccionadas.add(getBtnVolverAtrasVerOrdenesTrabajo());
			pnOrdenesDeTrabajoSeleccionadas.add(getBtnEmpaquetar());
		}
		return pnOrdenesDeTrabajoSeleccionadas;
	}

	private JScrollPane getScrpOrdenesTrabajoSeleccionadas() {
		if (scrpOrdenesTrabajoSeleccionadas == null) {
			scrpOrdenesTrabajoSeleccionadas = new JScrollPane((Component) null);
			scrpOrdenesTrabajoSeleccionadas.setBounds(34, 56, 452, 252);
			scrpOrdenesTrabajoSeleccionadas.setViewportView(getTablaOrdenesTrabajoSeleccionadas());
		}
		return scrpOrdenesTrabajoSeleccionadas;
	}

	public JTable getTablaOrdenesTrabajoSeleccionadas() {
		if (tablaOrdenesTrabajoSeleccionadas == null) {
			tablaOrdenesTrabajoSeleccionadas = new JTable();
			tablaOrdenesTrabajoSeleccionadas.setShowHorizontalLines(false);
			tablaOrdenesTrabajoSeleccionadas.setFillsViewportHeight(true);
			tablaOrdenesTrabajoSeleccionadas.setName("tabPedidosNoEmpaquetados");
			tablaOrdenesTrabajoSeleccionadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaOrdenesTrabajoSeleccionadas.setDefaultEditor(Object.class, null); // readonly
			TableModel tmodel = controller.getTableModelSeleccionOrdenesTrabajo();
			tablaOrdenesTrabajoSeleccionadas.setModel(tmodel);
			tablaOrdenesTrabajoDisponibles.revalidate();
			tablaOrdenesTrabajoDisponibles.repaint();
			SwingUtil.autoAdjustColumns(tablaOrdenesTrabajoSeleccionadas);
		}
		return tablaOrdenesTrabajoSeleccionadas;
	}

	private JButton getBtnVolverAtrasVerOrdenesTrabajo() {
		if (btnVolverAtrasVerOrdenesTrabajo == null) {
			btnVolverAtrasVerOrdenesTrabajo = new JButton("Volver");
			btnVolverAtrasVerOrdenesTrabajo.addActionListener(controller.getActionListenerVolverAtrasHaciaOrdenesTrabajo());
			btnVolverAtrasVerOrdenesTrabajo.setBounds(10, 10, 108, 36);
		}
		return btnVolverAtrasVerOrdenesTrabajo;
	}

	private JPanel getPnEmpaquetado() {
		if (pnEmpaquetado == null) {
			pnEmpaquetado = new JPanel();
			pnEmpaquetado.setLayout(null);
			pnEmpaquetado.add(getBtnVolverPnEmpaquetado());
			pnEmpaquetado.add(getBtnFinalizarEmpaquetado());
			pnEmpaquetado.add(getScrpVisualizarElementosYaEmpaquetados());
			pnEmpaquetado.add(getLblCantidad());
			pnEmpaquetado.add(getSpinner());
			pnEmpaquetado.add(getBtnEscanearYAñadir());
			pnEmpaquetado.add(getBtnNotificarIncidencia());
			pnEmpaquetado.add(getLblArecoger());
		}
		return pnEmpaquetado;
	}

	private JButton getBtnVolverPnEmpaquetado() {
		if (btnVolverPnEmpaquetado == null) {
			btnVolverPnEmpaquetado = new JButton("Volver");
			btnVolverPnEmpaquetado.addActionListener(controller.getActionListenerVolverAlPnEmpaquetado());
			btnVolverPnEmpaquetado.setBounds(10, 10, 96, 33);
		}
		return btnVolverPnEmpaquetado;
	}
	
	public JTable getTablaOrdenesElementosRecogidos() {
		if (tablaOrdenesElemetosRecogidos == null) {
			tablaOrdenesElemetosRecogidos = new JTable();
			tablaOrdenesElemetosRecogidos.setShowHorizontalLines(false);
			tablaOrdenesElemetosRecogidos.setFillsViewportHeight(true);
			tablaOrdenesElemetosRecogidos.setName("tabElementosARecoger");
			tablaOrdenesElemetosRecogidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaOrdenesElemetosRecogidos.setDefaultEditor(Object.class, null); // readonly
			SwingUtil.autoAdjustColumns(tablaOrdenesElemetosRecogidos);
		}
		return tablaOrdenesElemetosRecogidos;
	}

	private JButton getBtnFinalizarEmpaquetado() {
		if (btnFinalizarEmpaquetado == null) {
			btnFinalizarEmpaquetado = new JButton("Finalizar empaquetado");
			btnFinalizarEmpaquetado.addActionListener(controller.getActionListenerFinalizarEmpaquetado());
			btnFinalizarEmpaquetado.setBounds(192, 380, 191, 31);
		}
		return btnFinalizarEmpaquetado;
	}

	private JButton getBtnEmpaquetar() {
		if (btnEmpaquetar == null) {
			btnEmpaquetar = new JButton("Empaquetar");
			btnEmpaquetar.addActionListener(controller.getActionListenerEmpaquetar());
			btnEmpaquetar.setBounds(368, 318, 118, 31);
		}
		return btnEmpaquetar;
	}

	private JButton getBtnEnProcesoEmpaquetado() {
		if (btnEnProcesoEmpaquetado == null) {
			btnEnProcesoEmpaquetado = new JButton("En proceso de empaquetado");
			btnEnProcesoEmpaquetado.addActionListener(controller.getActionListenerEmpezarEmpezarProcesoEmpaquetado());
		}
		return btnEnProcesoEmpaquetado;
	}

	private JPanel getPnVisualizarAlbaran() {
		if (pnVisualizarAlbaran == null) {
			pnVisualizarAlbaran = new JPanel();
			pnVisualizarAlbaran.setLayout(new BorderLayout(0, 0));
			pnVisualizarAlbaran.add(getScrpAlbaran());
		}
		return pnVisualizarAlbaran;
	}

	private JScrollPane getScrpAlbaran() {
		if (scrpAlbaran == null) {
			scrpAlbaran = new JScrollPane();
			scrpAlbaran.setViewportView(getTaAlbaran());
		}
		return scrpAlbaran;
	}

	public JTextArea getTaAlbaran() {
		if (taAlbaran == null) {
			taAlbaran = new JTextArea();
			taAlbaran.setFont(new Font("Monospaced", Font.PLAIN, 12));
		}
		return taAlbaran;
	}
	private JScrollPane getScrpVisualizarElementosYaEmpaquetados() {
		if (scrpVisualizarElementosYaEmpaquetados == null) {
			scrpVisualizarElementosYaEmpaquetados = new JScrollPane();
			scrpVisualizarElementosYaEmpaquetados.setBounds(10, 85, 377, 160);
			scrpVisualizarElementosYaEmpaquetados.setViewportView(getTablaOrdenesElementosRecogidos());
		}
		return scrpVisualizarElementosYaEmpaquetados;
	}
	private JLabel getLblCantidad() {
		if (lblCantidad == null) {
			lblCantidad = new JLabel("Canrtiad a recoger:");
			lblCantidad.setBounds(10, 300, 135, 21);
		}
		return lblCantidad;
	}
	private JSpinner getSpinner() {
		if (spinner == null) {
			spinner = new JSpinner();
			spinner.setFont(new Font("Tahoma", Font.PLAIN, 12));
			spinner.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
			spinner.setBounds(123, 300, 47, 31);
		}
		return spinner;
	}
	private JButton getBtnEscanearYAñadir() {
		if (btnEscanearYAñadir == null) {
			btnEscanearYAñadir = new JButton("Escanear y añadir");
			btnEscanearYAñadir.setBounds(180, 300, 172, 32);
		}
		return btnEscanearYAñadir;
	}
	private JButton getBtnNotificarIncidencia() {
		if (btnNotificarIncidencia == null) {
			btnNotificarIncidencia = new JButton("Notificar incidencia");
			btnNotificarIncidencia.setBounds(10, 380, 172, 31);
		}
		return btnNotificarIncidencia;
	}
	private JLabel getLblArecoger() {
		if (lblArecoger == null) {
			lblArecoger = new JLabel("A recoger:");
			lblArecoger.setBounds(10, 53, 154, 21);
		}
		return lblArecoger;
	}
}
