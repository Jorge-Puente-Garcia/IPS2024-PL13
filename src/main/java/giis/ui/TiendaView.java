package giis.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import giis.controller.TiendaController;
import giis.model.Tienda.ProductosDto;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JSpinner;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.ComponentOrientation;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TiendaView extends JFrame {

	private static final long serialVersionUID = 1L;
	private TiendaController tdc;
	
	
	private JPanel contentPane;
	private JScrollPane scpProductos;
	private JTable tbProductos;
	private JPanel panel;
	private JButton btAñadirCarrito;
	private JSpinner spCantidad;
	private JPanel pnTituloyCarrito;
	private JLabel LabelTienda;
	private JButton btCarrito;

	/**
	 * Create the frame.
	 * @param carrito 
	 */
	    public TiendaView(TiendaController tiendaController) {
	    	this.tdc = tiendaController;
	    	
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(100, 100, 450, 300);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

	        setContentPane(contentPane);
	        contentPane.setLayout(new BorderLayout(0, 0));
	        contentPane.add(getPnTituloyCarrito(), BorderLayout.NORTH);
	        contentPane.add(getScpProductos(), BorderLayout.CENTER);
	        contentPane.add(getPanel(), BorderLayout.SOUTH);
	   
	        setMinimumSize(new Dimension(850, 450));
	        setLocationRelativeTo(null);
	    }
	private JScrollPane getScpProductos() {
		if (scpProductos == null) {
			scpProductos = new JScrollPane();
			scpProductos.setViewportView(getTbProductos());
		}
		return scpProductos;
	}
	private JTable getTbProductos() {
		if (tbProductos == null) {
			DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Referencia", "Datos Básicos"}, 0) {                
				private static final long serialVersionUID = 1L;
				@Override
                public boolean isCellEditable(int row, int column) {
                    return false;  // Deshabilita la edición de celdas
                }
            };
            
            // Rellenar la tabla con los productos
            for (ProductosDto producto : tdc.getProductos()) {
                String referencia = producto.getReferencia();
                String datosbasicos = producto.getDatosbasicos();
                tableModel.addRow(new Object[]{referencia, datosbasicos});
            }

            // Asignar el modelo al JTable
            tbProductos = new JTable(tableModel);
            tbProductos.setFont(new Font("Arial", Font.PLAIN, 14));
            tbProductos.getColumnModel().getColumn(0).setPreferredWidth(80);
            tbProductos.getColumnModel().getColumn(0).setMaxWidth(80);
            tbProductos.getColumnModel().getColumn(1).setPreferredWidth(300);
            tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return tbProductos;
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
			panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			panel.add(getBtAñadirCarrito());
			panel.add(getSpCantidad());
		}
		return panel;
	}
	private JButton getBtAñadirCarrito() {
		if (btAñadirCarrito == null) {
			btAñadirCarrito = new JButton("Añadir al carrito");
			btAñadirCarrito.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int fila = tbProductos.getSelectedRow();
					if(fila >= 0) {
						tdc.agregarAlCarrito(tbProductos.getValueAt(fila, 0).toString(), Integer.parseInt(spCantidad.getValue().toString()));
						spCantidad.setValue(1);
					}
				}
			});
			btAñadirCarrito.setFont(new Font("Arial Black", Font.PLAIN, 12));
		}
		return btAñadirCarrito;
	}
	private JSpinner getSpCantidad() {
		if (spCantidad == null) {
			spCantidad = new JSpinner();
			spCantidad.setModel(new SpinnerNumberModel(1, 1, 99, 1));
			spCantidad.setFont(new Font("Arial Black", Font.PLAIN, 14));
		}
		return spCantidad;
	}
	private JPanel getPnTituloyCarrito() {
		if (pnTituloyCarrito == null) {
			pnTituloyCarrito = new JPanel();
			pnTituloyCarrito.setLayout(new BorderLayout(0, 0));
			pnTituloyCarrito.add(getLabelTienda(), BorderLayout.WEST);
			pnTituloyCarrito.add(getBtCarrito(), BorderLayout.EAST);
		}
		return pnTituloyCarrito;
	}
	private JLabel getLabelTienda() {
		if (LabelTienda == null) {
			LabelTienda = new JLabel("SHOP");
			LabelTienda.setFont(new Font("Arial Black", Font.PLAIN, 22));
		}
		return LabelTienda;
	}
	private JButton getBtCarrito() {
		if (btCarrito == null) {
			btCarrito = new JButton("Carrito");
			btCarrito.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent e) {
					TiendaView tv = obtenerVentana();
					CarritoView cv = new CarritoView(tdc, tv);
					cv.setVisible(true);
					tv.setVisible(false);
				}
			});
			btCarrito.setFont(new Font("Arial Black", Font.PLAIN, 12));
		}
		return btCarrito;
	}
	
	private TiendaView obtenerVentana() {
		return this;
	}
}
