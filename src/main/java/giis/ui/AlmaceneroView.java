package giis.ui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import giis.controller.AlmaceneroController;
import giis.util.SwingUtil;

public class AlmaceneroView {

	private JFrame frameTerminalPortatil;
	private JPanel pnPaginaPrincipal;
	private JButton btnVerOrdenesDeTrabajo;
	private JPanel pnOrdenesDeTrabajo;
	private JButton btnVolverAPaginaPrincipal;
	private JScrollPane scrollPane;
	private JTable table;
	private AlmaceneroController controller;
	private JButton btnSeleccionar;
	private JButton btnCancelar;
	
	
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
		frameTerminalPortatil.getContentPane().add(getPnPaginaPrincipal(), "pnPaginaPrincipal");
		frameTerminalPortatil.getContentPane().add(getPnOrdenesDeTrabajo(), "pnOrdenesDeTrabajo");
	}

	private JPanel getPnPaginaPrincipal() {
		if (pnPaginaPrincipal == null) {
			pnPaginaPrincipal = new JPanel();
			pnPaginaPrincipal.setLayout(new GridLayout(3, 1, 0, 0));
			pnPaginaPrincipal.add(getBtnVerOrdenesDeTrabajo());
		}
		return pnPaginaPrincipal;
	}
	private JButton getBtnVerOrdenesDeTrabajo() {
		if (btnVerOrdenesDeTrabajo == null) {
			btnVerOrdenesDeTrabajo = new JButton("Ordenes de trbajo");
			btnVerOrdenesDeTrabajo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)(frameTerminalPortatil.getContentPane().getLayout());
					cl.show(frameTerminalPortatil.getContentPane(), "pnOrdenesDeTrabajo");
				}
			});
		}
		return btnVerOrdenesDeTrabajo;
	}
	private JPanel getPnOrdenesDeTrabajo() {
		if (pnOrdenesDeTrabajo == null) {
			pnOrdenesDeTrabajo = new JPanel();
			pnOrdenesDeTrabajo.setLayout(null);
			pnOrdenesDeTrabajo.add(getBtnVolverAPaginaPrincipal());
			pnOrdenesDeTrabajo.add(getScrollPane(), "cell 10 3,grow");
			pnOrdenesDeTrabajo.add(getBtnSeleccionar());
			pnOrdenesDeTrabajo.add(getBtnCancelar());
		}
		return pnOrdenesDeTrabajo;
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
			scrollPane = new JScrollPane(getTable());
			scrollPane.setBounds(85, 72, 259, 252);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	public JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setFillsViewportHeight(true);
			table.setName("tabPedidosNoRecogidos");
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setDefaultEditor(Object.class, null); //readonly
			TableModel tmodel=SwingUtil.getTableModelFromPojos(controller.getPedidosPendientesRecogida(), new String[] {"fecha", "tamaño", "estado"});
			table.setModel(tmodel);
			SwingUtil.autoAdjustColumns(table);
		}
		return table;
	}
	private JButton getBtnSeleccionar() {
		if (btnSeleccionar == null) {
			btnSeleccionar = new JButton("Continuar");
			btnSeleccionar.setBounds(310, 372, 110, 34);
		}
		return btnSeleccionar;
	}
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setBounds(190, 372, 110, 34);
		}
		return btnCancelar;
	}
}
