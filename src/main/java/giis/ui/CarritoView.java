package giis.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import giis.controller.TiendaController;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CarritoView extends JFrame {

	private static final long serialVersionUID = 1L;
	private TiendaController tdc;
	private TiendaView tv;
	
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JTable table;
	private JPanel panelInteracciones;
	private JPanel panelTotal;
	private JLabel lbTotal;
	private JButton btEliminar;
	private JTextField tfTotalPrecio;
	private JButton btPedido;	

	/**
	 * Create the frame.
	 * @param tdc 
	 * @param tv 
	 */
	public CarritoView(TiendaController tdc, TiendaView tv) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				tv.setVisible(true);
			}
		});
		this.tdc = tdc;
		this.tv = tv;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		contentPane.add(getLblNewLabel(), BorderLayout.NORTH);
		contentPane.add(getPanelInteracciones(), BorderLayout.SOUTH);
		tfTotalPrecio.setText(tdc.getTotal());
		
		setMinimumSize(new Dimension(550, 275));
        setLocationRelativeTo(null);
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Carrito");
			lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 16));
		}
		return lblNewLabel;
	}
	private JTable getTable() {
		if (table == null) {
			DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Referencia", "Cantidad", "Precio Unitario"}, 0) {
	            private static final long serialVersionUID = 1L;
				@Override
	            public boolean isCellEditable(int row, int column) {
	                return column == 1;
	            }
	        };
	        
			for (Map.Entry<String, Object[]> entry : tdc.devolverCarrito().entrySet()) {
                String referencia = entry.getKey();
                Object[] ob= entry.getValue();
                int unidades = (int) ob[0];
                double precio = (double) ob[1];
                tableModel.addRow(new Object[]{referencia, unidades, precio});
			}		
			
	        table = new JTable(tableModel);
	        
	        tableModel.addTableModelListener(e -> {
	            if (e.getType() == TableModelEvent.UPDATE) {
	                int row = e.getFirstRow(); // Fila modificada
	                int column = e.getColumn(); // Columna modificada

	                if (column == 1) { // Si la columna editada es "Cantidad"
	                    String referencia = (String) tableModel.getValueAt(row, 0); // Referencia del producto
	                    Object value = tableModel.getValueAt(row, column); // Nueva cantidad
	                    int nuevaCantidad = Integer.parseInt(value.toString());
	                    
	                    if (nuevaCantidad < 1) {
	                        // Mostrar mensaje de error
	                        JOptionPane.showMessageDialog(null, "La cantidad no puede ser menor que 1.");
	                        
	                        // Restablecer el valor anterior, suponiendo que el valor anterior era correcto
	                        int cantidadAnterior = (int) tdc.devolverCarrito().get(referencia)[0];
	                        tableModel.setValueAt(cantidadAnterior, row, column);
	                    } else {
	                        // Actualizar la lógica de negocio con la nueva cantidad
	                    	tdc.cambiarCantidad(referencia, nuevaCantidad);
	                    }
	                 actualizarTotal();
	                }
	            }
	        });
		}
		return table;
	}
	private JPanel getPanelInteracciones() {
		if (panelInteracciones == null) {
			panelInteracciones = new JPanel();
			panelInteracciones.setLayout(new BorderLayout(0, 0));
			panelInteracciones.add(getPanelTotal(), BorderLayout.EAST);
			panelInteracciones.add(getBtEliminar(), BorderLayout.WEST);
			panelInteracciones.add(getBtPedido(), BorderLayout.SOUTH);
		}
		return panelInteracciones;
	}
	private JPanel getPanelTotal() {
		if (panelTotal == null) {
			panelTotal = new JPanel();
			panelTotal.add(getLbTotal());
			panelTotal.add(getTfTotalPrecio());
		}
		return panelTotal;
	}
	private JLabel getLbTotal() {
		if (lbTotal == null) {
			lbTotal = new JLabel("Total:");
			lbTotal.setFont(new Font("Arial Black", Font.PLAIN, 12));
		}
		return lbTotal;
	}
	private JButton getBtEliminar() {
		if (btEliminar == null) {
			btEliminar = new JButton("Eliminar");
			btEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int fila = table.getSelectedRow();
					if(fila >= 0) {
						tdc.eliminarDelCarrito(table.getValueAt(fila, 0).toString());
						DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
						tableModel.removeRow(fila);
						actualizarTotal();
					}
				}
			});
			btEliminar.setFont(new Font("Arial Black", Font.PLAIN, 12));
		}
		return btEliminar;
	}
	private JTextField getTfTotalPrecio() {
		if (tfTotalPrecio == null) {
			tfTotalPrecio = new JTextField();
			tfTotalPrecio.setEditable(false);
			tfTotalPrecio.setFont(new Font("Arial Black", Font.PLAIN, 12));
			tfTotalPrecio.setColumns(10);
		}
		return tfTotalPrecio;
	}
	
	private void actualizarTotal() {
		tfTotalPrecio.setText(tdc.getTotal());
	}
	private JButton getBtPedido() {
		if (btPedido == null) {
			btPedido = new JButton("Realizar Pedido");
			btPedido.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!tdc.devolverCarrito().isEmpty()) {
						String mensaje = "¿Está seguro de que desea continuar? Su pedido se realizará";
				        String titulo = "Confirmación";
	
				        // Mostrar el cuadro de diálogo de confirmación
				        int respuesta = JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
	
				        // Procesar la respuesta
				        if (respuesta == JOptionPane.YES_OPTION) {			         
				            tdc.crearPedido();
				            obtenerCarritoView().dispose();
				            tv.setVisible(true);
				        } else if (respuesta == JOptionPane.NO_OPTION) {
				        	
				        } 
					}else {
				        	String mensaje = "El carrito no puede estar vacío. Por favor, añade artículos antes de continuar.";
					        String titulo = "Advertencia";
					        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.WARNING_MESSAGE);				
				     }
				}
			});
			btPedido.setFont(new Font("Arial Black", Font.PLAIN, 12));
		}
		return btPedido;
	}
	
	private CarritoView obtenerCarritoView() {
		return this;
	}
}
