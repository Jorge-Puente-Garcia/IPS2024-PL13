package giis.ui;

import java.awt.CardLayout;
import java.awt.Component;
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
import giis.util.Database;
import giis.util.SwingUtil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

public class AlmaceneroView {

	private JFrame frameTerminalPortatil;
	private JPanel pnPaginaPrincipal;
	private JButton btnSeleccionarOrdenesDeTrabajo;
	private JPanel pnPedidosDisponiblesParaCrearOts;
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
	private JButton btnVisualizarAlbaran;
	private JLabel lblNewLabel;
	private JLabel lblTítuloPanelOpcionesAlmacenero;
	private JLabel lblPedidosDisponiblesParaCrearOts;
	private JLabel lblVerOTsSeleccionadas;
	private JLabel lblOTsPendientesDeEmpaquetado;
	private JButton btnVisualizarInformesDisponibles;
	private JPanel pnInformesDisponibles;
	private JButton btnVisualizarInformeVentasPorUsuarioYDia;
	private JButton btnInformePorEmpresaYDia;
	private JButton btnInformeNumeroOTsEmpleadoYDia;
	private JButton btnInformeCantidaProductosEmpleadoYDia;
	private JPanel pnInformacionInformes;
	private JButton btnVolverALaPantallaDeInformes;
	private JScrollPane scrpInfoInformes;
	private JLabel lblTituloPanelMostrarInfoInformes;
	private JTable tbInfoInformes;
	private JButton btnRecepcionVehiculo;
	private JPanel pnRecepcionVehiculo;
	private JButton btnVolverPaginaPrincipalDesdeRecepcionVehiculo;
	private JLabel lblRecepcionVehiculo;
	private JLabel lblMatriculaVehículo;
	private JLabel lblTipoDelVehiculo;
	private JRadioButton rdbtnRegional;
	private JRadioButton rdbtnNacional;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField tfMatículaVehiculo;
	private JButton btnRecepcionarElVehiculo;
	private JPanel pnExpediciónPaquetes;
	private JLabel lblTituloExpedicionPaquetes;
	private JScrollPane scrpPaquetesParaExpedir;
	private JButton btnVolverPaginaPrincipalDesdeExpedicionPaquetes;
	private JTable tbPaquetesParaExpedicion;
	private JButton btnEscanearPaqueteAExpedir;
	private JLabel lblTipoVehiculoRecepcionado;
	private JLabel lblMatriculaVehiculo;
	private JLabel lblParaElTipoDeVehiculo;
	private JLabel lblParaLaMatriculaDelVehiculo;
	private JButton btnFinalizarExpedicion;
	private JButton btnVolverPantallaPrincipalDesdeInformes;
	private JButton btnVerPantallaExpedicionDePaquetes;
	private JLabel lblTituloTiposInformes;

	/**
	 * Create the application.
	 * @param db 
	 */
	public AlmaceneroView(Database db) {
		initialize(db);

	}

	public JFrame getFrameTerminalPortatil() {
		return frameTerminalPortatil;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Database db) {
		controller = new AlmaceneroController(this,db);
		frameTerminalPortatil = new JFrame();
		frameTerminalPortatil.setBounds(100, 100, 429, 463);
		frameTerminalPortatil.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameTerminalPortatil.getContentPane().setLayout(new CardLayout(0, 0));
		frameTerminalPortatil.getContentPane().add(getPnIdentificación(), "pnIdentificación");
		frameTerminalPortatil.getContentPane().add(getPnPaginaPrincipal(), "pnPaginaPrincipal");
		frameTerminalPortatil.getContentPane().add(getPnPedidosDisponiblesParaCrearOts(), "pnOrdenesDeTrabajo");
		frameTerminalPortatil.getContentPane().add(getPnOrdenesDeTrabajoSeleccionadas(),
				"pnOrdenesDeTrabajoSeleccionadas");
		frameTerminalPortatil.getContentPane().add(getPnRecogida(), "pnRecogida");
		frameTerminalPortatil.getContentPane().add(getPnVisualizarOrdenesPendientesEmpaquetado(), "pnEmpaquetado");
		frameTerminalPortatil.getContentPane().add(getPnEmpaquetadoOrden(), "pnEmpaquetadoProductos");
		frameTerminalPortatil.getContentPane().add(getPnInformesDisponibles(), "pnInformesDisponibles");
		frameTerminalPortatil.getContentPane().add(getPnInformacionInformes(), "pnInfoInformes");
		frameTerminalPortatil.getContentPane().add(getPnRecepcionVehiculo(), "pnRecepcionVehiculo");
		frameTerminalPortatil.getContentPane().add(getPnExpediciónPaquetes(), "pnExpedicionPaquetes");
		frameTerminalPortatil.setLocationRelativeTo(null);
	}

	private JPanel getPnPaginaPrincipal() {
		if (pnPaginaPrincipal == null) {
			pnPaginaPrincipal = new JPanel();
			pnPaginaPrincipal.setLayout(new GridLayout(0, 1, 0, 0));
			pnPaginaPrincipal.add(getLblTítuloPanelOpcionesAlmacenero());
			pnPaginaPrincipal.add(getBtnSeleccionarOrdenesDeTrabajo());
			pnPaginaPrincipal.add(getBtnVerOrdenesDeTrabajo());
			pnPaginaPrincipal.add(getBtnEnProcesoRecogida());
			pnPaginaPrincipal.add(getBtnVerOrdenesParaEmpaquetar());
			pnPaginaPrincipal.add(getBtnNewButton_1());
			pnPaginaPrincipal.add(getBtnRecepcionVehiculo());
			pnPaginaPrincipal.add(getBtnVerPantallaExpedicionDePaquetes());
			pnPaginaPrincipal.add(getBtnVisualizarInformesDisponibles());
		}
		return pnPaginaPrincipal;
	}

	private JButton getBtnSeleccionarOrdenesDeTrabajo() {
		if (btnSeleccionarOrdenesDeTrabajo == null) {
			btnSeleccionarOrdenesDeTrabajo = new JButton("Seleccionar ordenes de trabajo");
			btnSeleccionarOrdenesDeTrabajo.addActionListener(controller.getActionListenerParaSeleccionarOrdenesTrabajo());
		}
		return btnSeleccionarOrdenesDeTrabajo;
	}

	private JPanel getPnPedidosDisponiblesParaCrearOts() {
		if (pnPedidosDisponiblesParaCrearOts == null) {
			pnPedidosDisponiblesParaCrearOts = new JPanel();
			pnPedidosDisponiblesParaCrearOts.setLayout(null);
			pnPedidosDisponiblesParaCrearOts.add(getBtnVolverAPaginaPrincipal());
			pnPedidosDisponiblesParaCrearOts.add(getScrollPane(), "cell 10 3,grow");
			pnPedidosDisponiblesParaCrearOts.add(getBtnCrearOT());
			pnPedidosDisponiblesParaCrearOts.add(getBtnCancelar());
			pnPedidosDisponiblesParaCrearOts.add(getLblPedidosDisponiblesParaCrearOts());
		}
		return pnPedidosDisponiblesParaCrearOts;
	}

	private JButton getBtnVolverAPaginaPrincipal() {
		if (btnVolverAPaginaPrincipal == null) {
			btnVolverAPaginaPrincipal = new JButton("Volver");
			btnVolverAPaginaPrincipal.addActionListener(controller.getActionListenerVolverPaginaPrincipal());
			btnVolverAPaginaPrincipal.setEnabled(true);
			btnVolverAPaginaPrincipal.setBounds(10, 66, 110, 34);
		}
		return btnVolverAPaginaPrincipal;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getTablaOrdenesTrabajoDisponibles());
			scrollPane.setBounds(10, 110, 378, 252);
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
			pnIdentificación.add(getLblNewLabel());
		}
		return pnIdentificación;
	}

	private JButton getBtnIdentificarse() {
		if (btnIdentificarse == null) {
			btnIdentificarse = new JButton("Identifícate");
			btnIdentificarse.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
			pnOrdenesDeTrabajoSeleccionadas.add(getLblVerOTsSeleccionadas());
		}
		return pnOrdenesDeTrabajoSeleccionadas;
	}

	private JScrollPane getScrpOrdenesTrabajoSeleccionadas() {
		if (scrpOrdenesTrabajoSeleccionadas == null) {
			scrpOrdenesTrabajoSeleccionadas = new JScrollPane((Component) null);
			scrpOrdenesTrabajoSeleccionadas.setBounds(20, 123, 368, 252);
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
	
	public JTable getTbPaquetesParaExpedicion() {
		if (tbPaquetesParaExpedicion == null) {
			tbPaquetesParaExpedicion = new JTable();
			tbPaquetesParaExpedicion.setShowHorizontalLines(false);
			tbPaquetesParaExpedicion.setFillsViewportHeight(true);
			tbPaquetesParaExpedicion.setName("tabPaquetesAExpedir");
			tbPaquetesParaExpedicion.setDefaultEditor(Object.class, null); // readonly
		}
		return tbPaquetesParaExpedicion;
	}
	
	private JButton getBtnVolverAtrasVerOrdenesTrabajo() {
		if (btnVolverAtrasVerOrdenesTrabajo == null) {
			btnVolverAtrasVerOrdenesTrabajo = new JButton("Volver");
			btnVolverAtrasVerOrdenesTrabajo.addActionListener(controller.getActionListenerVolverAtrasHaciaOrdenesTrabajo());
			btnVolverAtrasVerOrdenesTrabajo.setBounds(20, 77, 108, 36);
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
	
	public JTable getTablaOrdenesElementParaRecoger() {
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
			btnRecoger.setBounds(236, 385, 152, 31);
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
			scrpVisualizarElementosYaEmpaquetados.setViewportView(getTablaOrdenesElementParaRecoger());
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
			pnVisualizarOrdenesPendientesEmpaquetado.add(getLblOTsPendientesDeEmpaquetado());
		}
		return pnVisualizarOrdenesPendientesEmpaquetado;
	}
	private JButton getBtnVolverDesdeEmpaquetado() {
		if (btnVolverDesdeEmpaquetado == null) {
			btnVolverDesdeEmpaquetado = new JButton("Volver");
			btnVolverDesdeEmpaquetado.addActionListener(controller.getActionListenerVolverPaginaPrincipal());
			btnVolverDesdeEmpaquetado.setBounds(10, 72, 105, 32);
		}
		return btnVolverDesdeEmpaquetado;
	}
	private JScrollPane getScrpOrdenesPendientesEmpaquetado() {
		if (scrpOrdenesPendientesEmpaquetado == null) {
			scrpOrdenesPendientesEmpaquetado = new JScrollPane((Component) null);
			scrpOrdenesPendientesEmpaquetado.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrpOrdenesPendientesEmpaquetado.setBounds(10, 114, 395, 252);
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
			btnIniciarEmpaquetado.setBounds(225, 376, 180, 40);
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
			pnEmpaquetadoOrden.add(getBtnVisualizarAlbaran());
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
	public JButton getBtnVisualizarAlbaran() {
		if (btnVisualizarAlbaran == null) {
			btnVisualizarAlbaran = new JButton("Visualizar albaran");
			btnVisualizarAlbaran.setEnabled(false);
			btnVisualizarAlbaran.addActionListener(controller.getActionListenerMostrarAlbaran());
			btnVisualizarAlbaran.setBounds(29, 375, 188, 41);
		}
		return btnVisualizarAlbaran;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Identificación del almacenero");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblNewLabel.setBounds(10, 123, 395, 45);
		}
		return lblNewLabel;
	}
	private JLabel getLblTítuloPanelOpcionesAlmacenero() {
		if (lblTítuloPanelOpcionesAlmacenero == null) {
			lblTítuloPanelOpcionesAlmacenero = new JLabel("Panel de opciones del almacenero");
			lblTítuloPanelOpcionesAlmacenero.setHorizontalAlignment(SwingConstants.CENTER);
			lblTítuloPanelOpcionesAlmacenero.setFont(new Font("Arial Black", Font.PLAIN, 20));
		}
		return lblTítuloPanelOpcionesAlmacenero;
	}
	private JLabel getLblPedidosDisponiblesParaCrearOts() {
		if (lblPedidosDisponiblesParaCrearOts == null) {
			lblPedidosDisponiblesParaCrearOts = new JLabel("Creación de OT's");
			lblPedidosDisponiblesParaCrearOts.setHorizontalAlignment(SwingConstants.CENTER);
			lblPedidosDisponiblesParaCrearOts.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblPedidosDisponiblesParaCrearOts.setBounds(10, 10, 395, 43);
		}
		return lblPedidosDisponiblesParaCrearOts;
	}
	private JLabel getLblVerOTsSeleccionadas() {
		if (lblVerOTsSeleccionadas == null) {
			lblVerOTsSeleccionadas = new JLabel("OT's asignadas");
			lblVerOTsSeleccionadas.setHorizontalAlignment(SwingConstants.CENTER);
			lblVerOTsSeleccionadas.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblVerOTsSeleccionadas.setBounds(20, 10, 368, 46);
		}
		return lblVerOTsSeleccionadas;
	}
	private JLabel getLblOTsPendientesDeEmpaquetado() {
		if (lblOTsPendientesDeEmpaquetado == null) {
			lblOTsPendientesDeEmpaquetado = new JLabel("OT's pendientes de empaquetado");
			lblOTsPendientesDeEmpaquetado.setHorizontalAlignment(SwingConstants.CENTER);
			lblOTsPendientesDeEmpaquetado.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblOTsPendientesDeEmpaquetado.setBounds(10, 10, 395, 52);
		}
		return lblOTsPendientesDeEmpaquetado;
	}
	private JButton getBtnVisualizarInformesDisponibles() {
		if (btnVisualizarInformesDisponibles == null) {
			btnVisualizarInformesDisponibles = new JButton("Informes ");
			btnVisualizarInformesDisponibles.addActionListener(controller.getActionListenerVisualizarPantallaInformes());
		}
		return btnVisualizarInformesDisponibles;
	}
	private JPanel getPnInformesDisponibles() {
		if (pnInformesDisponibles == null) {
			pnInformesDisponibles = new JPanel();
			pnInformesDisponibles.setLayout(new GridLayout(0, 1, 0, 0));
			pnInformesDisponibles.add(getLblTituloTiposInformes());
			pnInformesDisponibles.add(getBtnVolverPantallaPrincipalDesdeInformes());
			pnInformesDisponibles.add(getBtnVisualizarInformeVentasPorUsuarioYDia());
			pnInformesDisponibles.add(getBtnInformePorEmpresaYDia());
			pnInformesDisponibles.add(getBtnInformeNumeroOTsEmpleadoYDia());
			pnInformesDisponibles.add(getBtnInformeCantidaProductosEmpleadoYDia());
		}
		return pnInformesDisponibles;
	}
	private JButton getBtnVisualizarInformeVentasPorUsuarioYDia() {
		if (btnVisualizarInformeVentasPorUsuarioYDia == null) {
			btnVisualizarInformeVentasPorUsuarioYDia = new JButton("Informe de ventas porusuario y dia");
			btnVisualizarInformeVentasPorUsuarioYDia.addActionListener(controller.getActionListnerMostrarInformeVentasUsuariDia());
		}
		return btnVisualizarInformeVentasPorUsuarioYDia;
	}
	private JButton getBtnInformePorEmpresaYDia() {
		if (btnInformePorEmpresaYDia == null) {
			btnInformePorEmpresaYDia = new JButton("Informe de ventas por empresa y día");
			btnInformePorEmpresaYDia.addActionListener(controller.getActionListenerMostrarInformeVentasPorEmpresas());
		}
		return btnInformePorEmpresaYDia;
	}
	private JButton getBtnInformeNumeroOTsEmpleadoYDia() {
		if (btnInformeNumeroOTsEmpleadoYDia == null) {
			btnInformeNumeroOTsEmpleadoYDia = new JButton("Informe de OT's recogidas por empleado y día");
			btnInformeNumeroOTsEmpleadoYDia.addActionListener(controller.getActionListenerMostrarInformeOTRecogidasEmpleado());
		}
		return btnInformeNumeroOTsEmpleadoYDia;
	}
	private JButton getBtnInformeCantidaProductosEmpleadoYDia() {
		if (btnInformeCantidaProductosEmpleadoYDia == null) {
			btnInformeCantidaProductosEmpleadoYDia = new JButton("Informe de productos recogidos por empleado y día");
			btnInformeCantidaProductosEmpleadoYDia.addActionListener(controller.getActionListenerInformeProductosEmpleadoDia());
		}
		return btnInformeCantidaProductosEmpleadoYDia;
	}
	private JPanel getPnInformacionInformes() {
		if (pnInformacionInformes == null) {
			pnInformacionInformes = new JPanel();
			pnInformacionInformes.setLayout(null);
			pnInformacionInformes.add(getBtnVolverALaPantallaDeInformes());
			pnInformacionInformes.add(getScrpInfoInformes());
			pnInformacionInformes.add(getLblTituloPanelMostrarInfoInformes());
		}
		return pnInformacionInformes;
	}
	private JButton getBtnVolverALaPantallaDeInformes() {
		if (btnVolverALaPantallaDeInformes == null) {
			btnVolverALaPantallaDeInformes = new JButton("Volver");
			btnVolverALaPantallaDeInformes.addActionListener(controller.getActionListenerVolverALosTiposDeInformes());
			btnVolverALaPantallaDeInformes.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnVolverALaPantallaDeInformes.setBounds(86, 370, 241, 46);
		}
		return btnVolverALaPantallaDeInformes;
	}
	private JScrollPane getScrpInfoInformes() {
		if (scrpInfoInformes == null) {
			scrpInfoInformes = new JScrollPane();
			scrpInfoInformes.setBounds(0, 86, 415, 274);
			scrpInfoInformes.setViewportView(getTbInfoInformes());
		}
		return scrpInfoInformes;
	}
	public JLabel getLblTituloPanelMostrarInfoInformes() {
		if (lblTituloPanelMostrarInfoInformes == null) {
			lblTituloPanelMostrarInfoInformes = new JLabel("");
			lblTituloPanelMostrarInfoInformes.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloPanelMostrarInfoInformes.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblTituloPanelMostrarInfoInformes.setBounds(10, 10, 395, 61);
		}
		return lblTituloPanelMostrarInfoInformes;
	}
	public JTable getTbInfoInformes() {
		if (tbInfoInformes == null) {
			tbInfoInformes = new JTable();
			tbInfoInformes.setShowHorizontalLines(false);
			tbInfoInformes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tbInfoInformes.setName("tabElementosEnProcesoEmpaquetado");
			tbInfoInformes.setFillsViewportHeight(true);
		}
		return tbInfoInformes;
	}
	private JButton getBtnRecepcionVehiculo() {
		if (btnRecepcionVehiculo == null) {
			btnRecepcionVehiculo = new JButton("Recepción de vehículo");
			btnRecepcionVehiculo.addActionListener(controller.getActionListenerEntrarVentanaDeRecivirVehiculo());
		}
		return btnRecepcionVehiculo;
	}
	private JPanel getPnRecepcionVehiculo() {
		if (pnRecepcionVehiculo == null) {
			pnRecepcionVehiculo = new JPanel();
			pnRecepcionVehiculo.setLayout(null);
			pnRecepcionVehiculo.add(getBtnVolverPaginaPrincipalDesdeRecepcionVehiculo());
			pnRecepcionVehiculo.add(getLblRecepcionVehiculo());
			pnRecepcionVehiculo.add(getLblMatriculaVehículo());
			pnRecepcionVehiculo.add(getLblTipoDelVehiculo());
			pnRecepcionVehiculo.add(getRdbtnRegional());
			pnRecepcionVehiculo.add(getRdbtnNacional());
			pnRecepcionVehiculo.add(getTfMatículaVehiculo());
			pnRecepcionVehiculo.add(getBtnRecepcionarElVehiculo());
		}
		return pnRecepcionVehiculo;
	}
	private JButton getBtnVolverPaginaPrincipalDesdeRecepcionVehiculo() {
		if (btnVolverPaginaPrincipalDesdeRecepcionVehiculo == null) {
			btnVolverPaginaPrincipalDesdeRecepcionVehiculo = new JButton("Volver");
			btnVolverPaginaPrincipalDesdeRecepcionVehiculo.addActionListener(controller.getActionListenerVolverPaginaPrincipal());
			btnVolverPaginaPrincipalDesdeRecepcionVehiculo.setBounds(10, 76, 116, 34);
		}
		return btnVolverPaginaPrincipalDesdeRecepcionVehiculo;
	}
	private JLabel getLblRecepcionVehiculo() {
		if (lblRecepcionVehiculo == null) {
			lblRecepcionVehiculo = new JLabel("Recepción del vehículo");
			lblRecepcionVehiculo.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblRecepcionVehiculo.setHorizontalAlignment(SwingConstants.CENTER);
			lblRecepcionVehiculo.setBounds(10, 10, 395, 51);
		}
		return lblRecepcionVehiculo;
	}
	private JLabel getLblMatriculaVehículo() {
		if (lblMatriculaVehículo == null) {
			lblMatriculaVehículo = new JLabel("Matrícula del vehículo:");
			lblMatriculaVehículo.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblMatriculaVehículo.setBounds(10, 190, 125, 34);
		}
		return lblMatriculaVehículo;
	}
	private JLabel getLblTipoDelVehiculo() {
		if (lblTipoDelVehiculo == null) {
			lblTipoDelVehiculo = new JLabel("Tipo del vehículo:");
			lblTipoDelVehiculo.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblTipoDelVehiculo.setBounds(10, 257, 125, 34);
		}
		return lblTipoDelVehiculo;
	}
	public JRadioButton getRdbtnRegional() {
		if (rdbtnRegional == null) {
			rdbtnRegional = new JRadioButton("Regional");
			rdbtnRegional.setFont(new Font("Tahoma", Font.PLAIN, 12));
			buttonGroup.add(rdbtnRegional);
			rdbtnRegional.setSelected(true);
			rdbtnRegional.setBounds(139, 261, 132, 27);
		}
		return rdbtnRegional;
	}
	public JRadioButton getRdbtnNacional() {
		if (rdbtnNacional == null) {
			rdbtnNacional = new JRadioButton("Nacional");
			rdbtnNacional.setFont(new Font("Tahoma", Font.PLAIN, 12));
			buttonGroup.add(rdbtnNacional);
			rdbtnNacional.setBounds(273, 257, 132, 34);
		}
		return rdbtnNacional;
	}
	public JTextField getTfMatículaVehiculo() {
		if (tfMatículaVehiculo == null) {
			tfMatículaVehiculo = new JTextField();
			tfMatículaVehiculo.setBounds(145, 190, 260, 34);
			tfMatículaVehiculo.setColumns(10);
		}
		return tfMatículaVehiculo;
	}
	public JButton getBtnRecepcionarElVehiculo() {
		if (btnRecepcionarElVehiculo == null) {
			btnRecepcionarElVehiculo = new JButton("Recepcionar");
			btnRecepcionarElVehiculo.addActionListener(controller.getActionListenerRecepcionarVehiculo());
			btnRecepcionarElVehiculo.setBounds(88, 316, 233, 51);
		}
		return btnRecepcionarElVehiculo;
	}
	private JPanel getPnExpediciónPaquetes() {
		if (pnExpediciónPaquetes == null) {
			pnExpediciónPaquetes = new JPanel();
			pnExpediciónPaquetes.setLayout(null);
			pnExpediciónPaquetes.add(getLblTituloExpedicionPaquetes());
			pnExpediciónPaquetes.add(getScrpPaquetesParaExpedir());
			pnExpediciónPaquetes.add(getBtnVolverPaginaPrincipalDesdeExpedicionPaquetes());
			pnExpediciónPaquetes.add(getBtnEscanearPaqueteAExpedir());
			pnExpediciónPaquetes.add(getLblTipoVehiculoRecepcionado());
			pnExpediciónPaquetes.add(getLblMatriculaVehiculo());
			pnExpediciónPaquetes.add(getLblParaElTipoDeVehiculo());
			pnExpediciónPaquetes.add(getLblParaLaMatriculaDelVehiculo());
			pnExpediciónPaquetes.add(getBtnFinalizarExpedicion());
		}
		return pnExpediciónPaquetes;
	}
	private JLabel getLblTituloExpedicionPaquetes() {
		if (lblTituloExpedicionPaquetes == null) {
			lblTituloExpedicionPaquetes = new JLabel("Expedición de paquetes");
			lblTituloExpedicionPaquetes.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblTituloExpedicionPaquetes.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloExpedicionPaquetes.setBounds(10, 10, 395, 47);
		}
		return lblTituloExpedicionPaquetes;
	}
	private JScrollPane getScrpPaquetesParaExpedir() {
		if (scrpPaquetesParaExpedir == null) {
			scrpPaquetesParaExpedir = new JScrollPane();
			scrpPaquetesParaExpedir.setBounds(10, 143, 395, 182);
			scrpPaquetesParaExpedir.setViewportView(getTbPaquetesParaExpedicion());
		}
		return scrpPaquetesParaExpedir;
	}
	private JButton getBtnVolverPaginaPrincipalDesdeExpedicionPaquetes() {
		if (btnVolverPaginaPrincipalDesdeExpedicionPaquetes == null) {
			btnVolverPaginaPrincipalDesdeExpedicionPaquetes = new JButton("Volver");
			btnVolverPaginaPrincipalDesdeExpedicionPaquetes.addActionListener(controller.getActionListenerVolverPaginaPrincipal());
			btnVolverPaginaPrincipalDesdeExpedicionPaquetes.setBounds(10, 67, 122, 36);
		}
		return btnVolverPaginaPrincipalDesdeExpedicionPaquetes;
	}
	
	public JButton getBtnEscanearPaqueteAExpedir() {
		if (btnEscanearPaqueteAExpedir == null) {
			btnEscanearPaqueteAExpedir = new JButton("Escanear y cargar ");
			btnEscanearPaqueteAExpedir.setEnabled(false);
			btnEscanearPaqueteAExpedir.addActionListener(controller.getActionPerformedEscanearUnPaqueteAExpedir());
			btnEscanearPaqueteAExpedir.setBounds(181, 335, 224, 36);
		}
		return btnEscanearPaqueteAExpedir;
	}
	private JLabel getLblTipoVehiculoRecepcionado() {
		if (lblTipoVehiculoRecepcionado == null) {
			lblTipoVehiculoRecepcionado = new JLabel("Tipo de vehículo:");
			lblTipoVehiculoRecepcionado.setBounds(180, 67, 107, 29);
		}
		return lblTipoVehiculoRecepcionado;
	}
	private JLabel getLblMatriculaVehiculo() {
		if (lblMatriculaVehiculo == null) {
			lblMatriculaVehiculo = new JLabel("Matrícula:");
			lblMatriculaVehiculo.setBounds(180, 106, 107, 29);
		}
		return lblMatriculaVehiculo;
	}
	public JLabel getLblParaElTipoDeVehiculo() {
		if (lblParaElTipoDeVehiculo == null) {
			lblParaElTipoDeVehiculo = new JLabel("");
			lblParaElTipoDeVehiculo.setBounds(297, 67, 108, 29);
		}
		return lblParaElTipoDeVehiculo;
	}
	public JLabel getLblParaLaMatriculaDelVehiculo() {
		if (lblParaLaMatriculaDelVehiculo == null) {
			lblParaLaMatriculaDelVehiculo = new JLabel("");
			lblParaLaMatriculaDelVehiculo.setBounds(297, 106, 108, 29);
		}
		return lblParaLaMatriculaDelVehiculo;
	}
	public JButton getBtnFinalizarExpedicion() {
		if (btnFinalizarExpedicion == null) {
			btnFinalizarExpedicion = new JButton("Finalizar");
			btnFinalizarExpedicion.setEnabled(false);
			btnFinalizarExpedicion.addActionListener(controller.getActionListenerFinalizarExpedicionPaquetes());
			btnFinalizarExpedicion.setBounds(181, 381, 224, 35);
		}
		return btnFinalizarExpedicion;
	}
	private JButton getBtnVolverPantallaPrincipalDesdeInformes() {
		if (btnVolverPantallaPrincipalDesdeInformes == null) {
			btnVolverPantallaPrincipalDesdeInformes = new JButton("Volver");
			btnVolverPantallaPrincipalDesdeInformes.addActionListener(controller.getActionListenerVolverPaginaPrincipal());
		}
		return btnVolverPantallaPrincipalDesdeInformes;
	}
	private JButton getBtnVerPantallaExpedicionDePaquetes() {
		if (btnVerPantallaExpedicionDePaquetes == null) {
			btnVerPantallaExpedicionDePaquetes = new JButton("Expedición de paquetes");
			btnVerPantallaExpedicionDePaquetes.addActionListener(controller.getActionListenerVerVentanaExpedicion());
		}
		return btnVerPantallaExpedicionDePaquetes;
	}
	private JLabel getLblTituloTiposInformes() {
		if (lblTituloTiposInformes == null) {
			lblTituloTiposInformes = new JLabel("Tipos de informes");
			lblTituloTiposInformes.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblTituloTiposInformes.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblTituloTiposInformes;
	}
}
