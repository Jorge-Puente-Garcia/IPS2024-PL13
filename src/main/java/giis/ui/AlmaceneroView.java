package giis.ui;

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
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
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
	private JPanel pnRecogida;
	private JButton btnVolverPnEmpaquetado;
	private JButton btnFinalizarEmpaquetado;
	private JButton btnRecoger;
	private JButton btnEnProcesoRecogida;
	protected String infoAlvaran;
	private JScrollPane scrpVisualizarElementosYaEmpaquetados;
	private JLabel lblCantidad;
	private JSpinner spinner;
	private JButton btnEscanearYAñadir;
	private JButton btnNotificarIncidencia;
	private JTable tablaOrdenesElemetosRecogidos;
	private JLabel lblArecoger;
	private JLabel lblRecogidaOT;
	private JLabel lblOTid;
	private JButton btnVerOrdenesParaEmpaquetar;
	private JButton btnNewButton_1;
	private JPanel pnVisualizarOrdenesPendientesEmpaquetado;
	private JButton btnVolverDesdeEmpaquetado;
	private JTextArea taAlbaran;
	private JScrollPane scrpOrdenesPendientesEmpaquetado;
	private JTable tablaOrdenesTrabajoPendeientesEmpaquetado;
	private JButton btnIniciarEmpaquetado;
	private JPanel pnEmpaquetadoOrden;
	private JLabel lblEmpaquetadoProductos;
	private JButton btnVolverDesdeEmpaquetadoDeproductosDeOt;
	private JScrollPane scrpProductosAEmpaquetar;
	private JTable tablaElementosProcesoEmpaquetadoDeUnaOt;
	private JLabel lblCantidadAEmpaquetar;
	private JSpinner spnCantidadElementosAEmpaquetar;
	private JButton btnEscanearYeEmpaquetar;
	private JButton btnFinalizarEmpaquetadoDeLaOt;

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
		frameTerminalPortatil.setBounds(100, 100, 429, 463);
		frameTerminalPortatil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameTerminalPortatil.getContentPane().setLayout(new CardLayout(0, 0));
		frameTerminalPortatil.getContentPane().add(getPnIdentificación(), "pnIdentificación");
		frameTerminalPortatil.getContentPane().add(getPnPaginaPrincipal(), "pnPaginaPrincipal");
		frameTerminalPortatil.getContentPane().add(getPnOrdenesDeTrabajoDisponibles(), "pnOrdenesDeTrabajo");
		frameTerminalPortatil.getContentPane().add(getPnOrdenesDeTrabajoSeleccionadas(),
				"pnOrdenesDeTrabajoSeleccionadas");
		frameTerminalPortatil.getContentPane().add(getPnRecogida(), "pnRecogida");
		frameTerminalPortatil.getContentPane().add(getPnVisualizarOrdenesPendientesEmpaquetado(), "pnEmpaquetado");
		frameTerminalPortatil.getContentPane().add(getPnEmpaquetadoOrden(), "pnEmpaquetadoProductos");
		frameTerminalPortatil.setLocationRelativeTo(null);
	}

	private JPanel getPnPaginaPrincipal() {
		if (pnPaginaPrincipal == null) {
			pnPaginaPrincipal = new JPanel();
			pnPaginaPrincipal.setLayout(new GridLayout(5, 1, 0, 0));
			pnPaginaPrincipal.add(getBtnSeleccionarOrdenesDeTrabajo());
			pnPaginaPrincipal.add(getBtnVerOrdenesDeTrabajo());
			pnPaginaPrincipal.add(getBtnEnProcesoRecogida());
			pnPaginaPrincipal.add(getBtnVerOrdenesParaEmpaquetar());
			pnPaginaPrincipal.add(getBtnNewButton_1());
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
			scrollPane.setBounds(10, 72, 378, 252);
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
			TableModel tmodel = SwingUtil.getTableModelFromPojos(controller.getPedidosPendientesDeEntrarEnUnaOT(),
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
			btnCrearOT.setBounds(278, 372, 110, 34);
		}
		return btnCrearOT;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(controller.getActionListenerCancelar());
			btnCancelar.setBounds(158, 372, 110, 34);
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
			btnIdentificarse.setBounds(85, 178, 222, 45);
		}
		return btnIdentificarse;
	}

	private JPanel getPnOrdenesDeTrabajoSeleccionadas() {
		if (pnOrdenesDeTrabajoSeleccionadas == null) {
			pnOrdenesDeTrabajoSeleccionadas = new JPanel();
			pnOrdenesDeTrabajoSeleccionadas.setLayout(null);
			pnOrdenesDeTrabajoSeleccionadas.add(getScrpOrdenesTrabajoSeleccionadas());
			pnOrdenesDeTrabajoSeleccionadas.add(getBtnVolverAtrasVerOrdenesTrabajo());
			pnOrdenesDeTrabajoSeleccionadas.add(getBtnRecoger());
		}
		return pnOrdenesDeTrabajoSeleccionadas;
	}

	private JScrollPane getScrpOrdenesTrabajoSeleccionadas() {
		if (scrpOrdenesTrabajoSeleccionadas == null) {
			scrpOrdenesTrabajoSeleccionadas = new JScrollPane((Component) null);
			scrpOrdenesTrabajoSeleccionadas.setBounds(20, 56, 368, 252);
			scrpOrdenesTrabajoSeleccionadas.setViewportView(getTablaOrdenesTrabajoSeleccionadas());
		}
		return scrpOrdenesTrabajoSeleccionadas;
	}

	public JTable getTablaOrdenesTrabajoSeleccionadas() {
		if (tablaOrdenesTrabajoSeleccionadas == null) {
			tablaOrdenesTrabajoSeleccionadas = new JTable();
			tablaOrdenesTrabajoSeleccionadas.addMouseListener(controller.getMouseListenerSeleccionarUnaOtParaEmpaquetar());
			tablaOrdenesTrabajoSeleccionadas.setShowHorizontalLines(false);
			tablaOrdenesTrabajoSeleccionadas.setFillsViewportHeight(true);
			tablaOrdenesTrabajoSeleccionadas.setName("tabPedidosNoEmpaquetados");
			tablaOrdenesTrabajoSeleccionadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaOrdenesTrabajoSeleccionadas.setDefaultEditor(Object.class, null); // readonly
			TableModel tmodel = controller.getTableModelSeleccionOrdenesTrabajo();
			tablaOrdenesTrabajoSeleccionadas.setModel(tmodel);
			SwingUtil.autoAdjustColumns(tablaOrdenesTrabajoSeleccionadas);
		}
		return tablaOrdenesTrabajoSeleccionadas;
	}
	
	public JTable getTablaOrdenesTrabajoPendientesEmpaquetado() {
		if (tablaOrdenesTrabajoPendeientesEmpaquetado == null) {
			tablaOrdenesTrabajoPendeientesEmpaquetado = new JTable();
			tablaOrdenesTrabajoPendeientesEmpaquetado.addMouseListener(controller.getMouseListenerSeleccionarEnLaTablaOrdenesPendientesEmpaquetado());
			tablaOrdenesTrabajoPendeientesEmpaquetado.setShowHorizontalLines(false);
			tablaOrdenesTrabajoPendeientesEmpaquetado.setFillsViewportHeight(true);
			tablaOrdenesTrabajoPendeientesEmpaquetado.setName("tabPedidosNoEmpaquetados");
			tablaOrdenesTrabajoPendeientesEmpaquetado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaOrdenesTrabajoPendeientesEmpaquetado.setDefaultEditor(Object.class, null); // readonly
			TableModel tmodel = controller.getTableModelPrdenesTrabajoPendientesEmpaquetado();
			tablaOrdenesTrabajoPendeientesEmpaquetado.setModel(tmodel);
			SwingUtil.autoAdjustColumns(tablaOrdenesTrabajoPendeientesEmpaquetado);
		}
		return tablaOrdenesTrabajoPendeientesEmpaquetado;
	}

	public JTable getTablaElementosProcesoEmpaquetadoDeUnaOt() {
		if (tablaElementosProcesoEmpaquetadoDeUnaOt == null) {
			tablaElementosProcesoEmpaquetadoDeUnaOt = new JTable();
			tablaElementosProcesoEmpaquetadoDeUnaOt.setShowHorizontalLines(false);
			tablaElementosProcesoEmpaquetadoDeUnaOt.setFillsViewportHeight(true);
			tablaElementosProcesoEmpaquetadoDeUnaOt.setName("tabElementosEnProcesoEmpaquetado");
			tablaElementosProcesoEmpaquetadoDeUnaOt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tablaElementosProcesoEmpaquetadoDeUnaOt.setDefaultEditor(Object.class, null); // readonly
		}
		return tablaElementosProcesoEmpaquetadoDeUnaOt;
	}
	
	private JButton getBtnVolverAtrasVerOrdenesTrabajo() {
		if (btnVolverAtrasVerOrdenesTrabajo == null) {
			btnVolverAtrasVerOrdenesTrabajo = new JButton("Volver");
			btnVolverAtrasVerOrdenesTrabajo.addActionListener(controller.getActionListenerVolverAtrasHaciaOrdenesTrabajo());
			btnVolverAtrasVerOrdenesTrabajo.setBounds(10, 10, 108, 36);
		}
		return btnVolverAtrasVerOrdenesTrabajo;
	}

	private JPanel getPnRecogida() {
		if (pnRecogida == null) {
			pnRecogida = new JPanel();
			pnRecogida.setLayout(null);
			pnRecogida.add(getBtnVolverPnEmpaquetado());
			pnRecogida.add(getBtnFinalizarRecogida());
			pnRecogida.add(getScrpVisualizarElementosYaEmpaquetados());
			pnRecogida.add(getLblCantidad());
			pnRecogida.add(getSpinner());
			pnRecogida.add(getBtnEscanearYAñadir());
			pnRecogida.add(getBtnNotificarIncidencia());
			pnRecogida.add(getLblArecoger());
			pnRecogida.add(getLblRecogidaOT());
			pnRecogida.add(getLblOTid());
		}
		return pnRecogida;
	}

	private JButton getBtnVolverPnEmpaquetado() {
		if (btnVolverPnEmpaquetado == null) {
			btnVolverPnEmpaquetado = new JButton("Volver");
			btnVolverPnEmpaquetado.addActionListener(controller.getActionListenerVolverAlPnEmpaquetado());
			btnVolverPnEmpaquetado.setBounds(10, 62, 96, 33);
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

	public JButton getBtnFinalizarRecogida() {
		if (btnFinalizarEmpaquetado == null) {
			btnFinalizarEmpaquetado = new JButton("Finalizar recogida");
			btnFinalizarEmpaquetado.setEnabled(false);
			btnFinalizarEmpaquetado.addActionListener(controller.getActionListenerFinalizarRecogida());
			btnFinalizarEmpaquetado.setBounds(214, 380, 191, 31);
		}
		return btnFinalizarEmpaquetado;
	}

	public JButton getBtnRecoger() {
		if (btnRecoger == null) {
			btnRecoger = new JButton("Iniciar recogida");
			btnRecoger.setEnabled(false);
			btnRecoger.addActionListener(controller.getActionListenerEmpezarARecoger());
			btnRecoger.setBounds(222, 318, 152, 31);
		}
		return btnRecoger;
	}

	private JButton getBtnEnProcesoRecogida() {
		if (btnEnProcesoRecogida == null) {
			btnEnProcesoRecogida = new JButton("En proceso de recogida");
			btnEnProcesoRecogida.addActionListener(controller.getActionListenerEmpezarEmpezarProcesoEmpaquetado());
		}
		return btnEnProcesoRecogida;
	}
	private JScrollPane getScrpVisualizarElementosYaEmpaquetados() {
		if (scrpVisualizarElementosYaEmpaquetados == null) {
			scrpVisualizarElementosYaEmpaquetados = new JScrollPane();
			scrpVisualizarElementosYaEmpaquetados.setBounds(0, 136, 415, 160);
			scrpVisualizarElementosYaEmpaquetados.setViewportView(getTablaOrdenesElementosRecogidos());
		}
		return scrpVisualizarElementosYaEmpaquetados;
	}
	private JLabel getLblCantidad() {
		if (lblCantidad == null) {
			lblCantidad = new JLabel("Canrtiad a recoger:");
			lblCantidad.setBounds(31, 306, 135, 21);
		}
		return lblCantidad;
	}
	public JSpinner getSpinner() {
		if (spinner == null) {
			spinner = new JSpinner();
			spinner.setFont(new Font("Tahoma", Font.PLAIN, 12));
			spinner.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
			spinner.setBounds(176, 300, 47, 31);
		}
		return spinner;
	}
	private JButton getBtnEscanearYAñadir() {
		if (btnEscanearYAñadir == null) {
			btnEscanearYAñadir = new JButton("Escanear y añadir");
			btnEscanearYAñadir.addActionListener(controller.getActionPerformedEscanearUnProducto());
			btnEscanearYAñadir.setBounds(233, 300, 172, 32);
		}
		return btnEscanearYAñadir;
	}
	public JButton getBtnNotificarIncidencia() {
		if (btnNotificarIncidencia == null) {
			btnNotificarIncidencia = new JButton("Notificar incidencia");
			btnNotificarIncidencia.addActionListener(controller.getActionPerformedNotificaIncidencia());
			btnNotificarIncidencia.setBounds(32, 380, 172, 31);
		}
		return btnNotificarIncidencia;
	}
	private JLabel getLblArecoger() {
		if (lblArecoger == null) {
			lblArecoger = new JLabel("A recoger:");
			lblArecoger.setBounds(10, 105, 154, 21);
		}
		return lblArecoger;
	}
	private JLabel getLblRecogidaOT() {
		if (lblRecogidaOT == null) {
			lblRecogidaOT = new JLabel("Recogida OT");
			lblRecogidaOT.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblRecogidaOT.setHorizontalAlignment(SwingConstants.CENTER);
			lblRecogidaOT.setBounds(10, 10, 395, 39);
		}
		return lblRecogidaOT;
	}
	public JLabel getLblOTid() {
		if (lblOTid == null) {
			lblOTid = new JLabel("");
			lblOTid.setFont(new Font("Arial Black", Font.PLAIN, 14));
			lblOTid.setBounds(176, 62, 154, 33);
		}
		return lblOTid;
	}
	private JButton getBtnVerOrdenesParaEmpaquetar() {
		if (btnVerOrdenesParaEmpaquetar == null) {
			btnVerOrdenesParaEmpaquetar = new JButton("Ver ordenes para empaquetar");
			btnVerOrdenesParaEmpaquetar.addActionListener(controller.getActionPerformedMuestraPanelEmpaquetado());
		}
		return btnVerOrdenesParaEmpaquetar;
	}
	private JButton getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JButton("En proceso de empaquetado");
			btnNewButton_1.addActionListener(controller.getActionPerformedMuestraPanelEmpaquetadoProductos());
		}
		return btnNewButton_1;
	}
	
	public JTextArea getTaAlbaran() {
		if (taAlbaran == null) {
			taAlbaran = new JTextArea();
			taAlbaran.setFont(new Font("Monospaced", Font.PLAIN, 12));
		}
		return taAlbaran;
	}
	private JPanel getPnVisualizarOrdenesPendientesEmpaquetado() {
		if (pnVisualizarOrdenesPendientesEmpaquetado == null) {
			pnVisualizarOrdenesPendientesEmpaquetado = new JPanel();
			pnVisualizarOrdenesPendientesEmpaquetado.setLayout(null);
			pnVisualizarOrdenesPendientesEmpaquetado.add(getBtnVolverDesdeEmpaquetado());
			pnVisualizarOrdenesPendientesEmpaquetado.add(getScrpOrdenesPendientesEmpaquetado());
			pnVisualizarOrdenesPendientesEmpaquetado.add(getBtnIniciarEmpaquetado());
		}
		return pnVisualizarOrdenesPendientesEmpaquetado;
	}
	private JButton getBtnVolverDesdeEmpaquetado() {
		if (btnVolverDesdeEmpaquetado == null) {
			btnVolverDesdeEmpaquetado = new JButton("Volver");
			btnVolverDesdeEmpaquetado.addActionListener(controller.getActionListenerVolverPaginaPrincipal());
			btnVolverDesdeEmpaquetado.setBounds(10, 10, 105, 32);
		}
		return btnVolverDesdeEmpaquetado;
	}
	private JScrollPane getScrpOrdenesPendientesEmpaquetado() {
		if (scrpOrdenesPendientesEmpaquetado == null) {
			scrpOrdenesPendientesEmpaquetado = new JScrollPane((Component) null);
			scrpOrdenesPendientesEmpaquetado.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrpOrdenesPendientesEmpaquetado.setBounds(10, 72, 395, 252);
			scrpOrdenesPendientesEmpaquetado.setViewportView(getTablaOrdenesTrabajoPendientesEmpaquetado());
		}
		return scrpOrdenesPendientesEmpaquetado;
	}
	
	private JScrollPane getScrpProductosAEmpaquetar() {
		if (scrpProductosAEmpaquetar == null) {
			scrpProductosAEmpaquetar = new JScrollPane();
			scrpProductosAEmpaquetar.setBounds(10, 106, 395, 176);
			scrpProductosAEmpaquetar.setViewportView(getTablaElementosProcesoEmpaquetadoDeUnaOt());
		}
		return scrpProductosAEmpaquetar;
	}
	
	public JButton getBtnIniciarEmpaquetado() {
		if (btnIniciarEmpaquetado == null) {
			btnIniciarEmpaquetado = new JButton("Iniciar empaquetado");
			btnIniciarEmpaquetado.addActionListener(controller.getActionPerformedIniciarProcesoEmpaquetado());
			btnIniciarEmpaquetado.setEnabled(false);
			btnIniciarEmpaquetado.setBounds(225, 334, 180, 40);
		}
		return btnIniciarEmpaquetado;
	}
	private JPanel getPnEmpaquetadoOrden() {
		if (pnEmpaquetadoOrden == null) {
			pnEmpaquetadoOrden = new JPanel();
			pnEmpaquetadoOrden.setLayout(null);
			pnEmpaquetadoOrden.add(getLblEmpaquetadoProductos());
			pnEmpaquetadoOrden.add(getBtnVolverDesdeEmpaquetadoDeproductosDeOt());
			pnEmpaquetadoOrden.add(getScrpProductosAEmpaquetar());
			pnEmpaquetadoOrden.add(getLblCantidadAEmpaquetar());
			pnEmpaquetadoOrden.add(getSpnCantidadElementosAEmpaquetar());
			pnEmpaquetadoOrden.add(getBtnEscanearYeEmpaquetar());
			pnEmpaquetadoOrden.add(getBtnFinalizarEmpaquetadoDeLaOt());
		}
		return pnEmpaquetadoOrden;
	}
	private JLabel getLblEmpaquetadoProductos() {
		if (lblEmpaquetadoProductos == null) {
			lblEmpaquetadoProductos = new JLabel("Empaquetado OT");
			lblEmpaquetadoProductos.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblEmpaquetadoProductos.setHorizontalAlignment(SwingConstants.CENTER);
			lblEmpaquetadoProductos.setBounds(10, 10, 395, 46);
		}
		return lblEmpaquetadoProductos;
	}
	private JButton getBtnVolverDesdeEmpaquetadoDeproductosDeOt() {
		if (btnVolverDesdeEmpaquetadoDeproductosDeOt == null) {
			btnVolverDesdeEmpaquetadoDeproductosDeOt = new JButton("Volver");
			btnVolverDesdeEmpaquetadoDeproductosDeOt.addActionListener(controller.getActionListenerVolverPaginaPrincipal());
			btnVolverDesdeEmpaquetadoDeproductosDeOt.setBounds(10, 66, 105, 30);
		}
		return btnVolverDesdeEmpaquetadoDeproductosDeOt;
	}
	
	private JLabel getLblCantidadAEmpaquetar() {
		if (lblCantidadAEmpaquetar == null) {
			lblCantidadAEmpaquetar = new JLabel("Cantidad a empaquetar:");
			lblCantidadAEmpaquetar.setBounds(20, 292, 130, 24);
		}
		return lblCantidadAEmpaquetar;
	}
	public JSpinner getSpnCantidadElementosAEmpaquetar() {
		if (spnCantidadElementosAEmpaquetar == null) {
			spnCantidadElementosAEmpaquetar = new JSpinner();
			spnCantidadElementosAEmpaquetar.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
			spnCantidadElementosAEmpaquetar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			spnCantidadElementosAEmpaquetar.setBounds(160, 292, 47, 31);
		}
		return spnCantidadElementosAEmpaquetar;
	}
	private JButton getBtnEscanearYeEmpaquetar() {
		if (btnEscanearYeEmpaquetar == null) {
			btnEscanearYeEmpaquetar = new JButton("Escanear y empaquetar");
			btnEscanearYeEmpaquetar.addActionListener(controller.getActionPerformedEscanearUnProductoAEmpaquetar());
			btnEscanearYeEmpaquetar.setBounds(217, 292, 188, 41);
		}
		return btnEscanearYeEmpaquetar;
	}
	public JButton getBtnFinalizarEmpaquetadoDeLaOt() {
		if (btnFinalizarEmpaquetadoDeLaOt == null) {
			btnFinalizarEmpaquetadoDeLaOt = new JButton("Finalizar empaquetado");
			btnFinalizarEmpaquetadoDeLaOt.addActionListener(controller.getActionPerformedFinalizarProcesoEmpaquetado());
			btnFinalizarEmpaquetadoDeLaOt.setEnabled(false);
			btnFinalizarEmpaquetadoDeLaOt.setBounds(227, 375, 178, 41);
		}
		return btnFinalizarEmpaquetadoDeLaOt;
	}
}
