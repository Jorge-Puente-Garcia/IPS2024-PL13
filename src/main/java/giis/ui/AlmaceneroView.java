package giis.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.Color;

public class AlmaceneroView {

	private JFrame frameTerminalPortatil;
	private JPanel pnPaginaPrincipal;
	private JButton btnVerOrdenesDeTrabajo;
	private JPanel pnOrdenesDeTrabajo;
	private JButton btnVolverAPaginaPrincipal;
	private JScrollPane scrollPane;
	private JPanel pnVisualizaciónOrdenesDeTrabajo;

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
		frameTerminalPortatil = new JFrame();
		frameTerminalPortatil.setBounds(100, 100, 698, 528);
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
			pnOrdenesDeTrabajo.add(getScrollPane());
		}
		return pnOrdenesDeTrabajo;
	}
	private JButton getBtnVolverAPaginaPrincipal() {
		if (btnVolverAPaginaPrincipal == null) {
			btnVolverAPaginaPrincipal = new JButton("Volver");
			btnVolverAPaginaPrincipal.setEnabled(true);
			btnVolverAPaginaPrincipal.setBounds(10, 10, 110, 34);
		}
		return btnVolverAPaginaPrincipal;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 54, 664, 427);
			scrollPane.setViewportView(getPanel_2());
		}
		return scrollPane;
	}
	private JPanel getPanel_2() {
		if (pnVisualizaciónOrdenesDeTrabajo == null) {
			pnVisualizaciónOrdenesDeTrabajo = new JPanel();
			pnVisualizaciónOrdenesDeTrabajo.setBackground(Color.LIGHT_GRAY);
			FlowLayout fl_pnVisualizaciónOrdenesDeTrabajo = new FlowLayout(FlowLayout.CENTER, 5, 5);
			fl_pnVisualizaciónOrdenesDeTrabajo.setAlignOnBaseline(true);
			pnVisualizaciónOrdenesDeTrabajo.setLayout(fl_pnVisualizaciónOrdenesDeTrabajo);
		}
		return pnVisualizaciónOrdenesDeTrabajo;
	}
}
