package giis.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import giis.controller.TiendaController;
import giis.model.Tienda.ProductosDto;
import giis.util.ValidarDni;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Map;

public class Tienda extends JFrame {

	private static final long serialVersionUID = 1L;
	private TiendaController tdc;
	
	//ATRIBUTOS TIENDA
	private JPanel Tienda;
	
	private JScrollPane scpProductos;
	private JTable tbProductos;
	private JPanel panel;
	private JButton btAñadirCarrito;
	private JSpinner spCantidad;
	private JPanel pnTituloyCarrito;
	private JLabel LabelTienda;
	
	//ATRIBUTOS CARRITO
	private JPanel Carrito;
	private JScrollPane scrollPane;
	private JTable table;
	private JPanel panelInteracciones;
	private JPanel panelTotal;
	private JLabel lbTotal;
	private JButton btEliminar;
	private JTextField tfTotalPrecio;
	private JButton btPedido;	
	
	//Propios ventana
	private JPanel contentPane;
	private JPanel panelTituloCarrito;
	private JLabel lbCarrito;
	private JPanel panelCliente;
	private JLabel lbCliente;
	private JTextField tfCliente;
	
	

	public Tienda(TiendaController tdc) {
		this.tdc = tdc;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(getTienda(), BorderLayout.CENTER);
		contentPane.add(getCarrito(), BorderLayout.EAST);
		
		//crearPanelTienda();
		
		setMinimumSize(new Dimension(1300,550));
        setLocationRelativeTo(null);
	}
	
	
	private JPanel getTienda() {
		if (Tienda == null) {
			Tienda = new JPanel();
			Tienda.setLayout(new BorderLayout(0, 0));
			Tienda.add(getPnTituloyCarrito(), BorderLayout.NORTH);
			Tienda.add(getScpProductos(), BorderLayout.CENTER);
			Tienda.add(getPanel(), BorderLayout.SOUTH);	
		}
		return Tienda;
	}
	
	private JPanel getCarrito() {
		if (Carrito == null) {
			Carrito = new JPanel();
			Carrito.setLayout(new BorderLayout(0, 0));
			Carrito.setLayout(new BorderLayout(0, 0));
			Carrito.add(getScrollPane(), BorderLayout.CENTER);
			Carrito.add(getPanelInteracciones(), BorderLayout.SOUTH);
			tfTotalPrecio.setText(tdc.getTotal());			
			Carrito.add(getPanelTituloCarrito(), BorderLayout.NORTH);
		}
		return Carrito;
	}
	
	//TIENDA
	    
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
            tbProductos.getColumnModel().getColumn(1).setPreferredWidth(500);
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
					int row = tbProductos.getSelectedRow();
					if(row >= 0) {
						tdc.agregarAlCarrito(tbProductos.getValueAt(row, 0).toString(), Integer.parseInt(spCantidad.getValue().toString()));
						spCantidad.setValue(1);
						ActualizarComponentesCarrito();
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

	//CARRITO
	

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {	
	        table = new JTable(ActualizarCarrito());
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
					if(!tdc.devolverCarrito().isEmpty() && !tdc.getCliente()) {
						String mensaje = "¿Está seguro de que desea continuar? Su pedido se realizará";
				        String titulo = "Confirmación";
	
				        // Mostrar el cuadro de diálogo de confirmación
				        int respuesta = JOptionPane.showConfirmDialog(null, mensaje, titulo, JOptionPane.YES_NO_OPTION);
	
				        // Procesar la respuesta
				        if (respuesta == JOptionPane.YES_OPTION) {			         
				            tdc.crearPedido();
				            ActualizarCarrito();
				        } else if (respuesta == JOptionPane.NO_OPTION) {
				        	
				        } 
					}else {
				        	String mensaje = "El carrito o cliete no puede estar vacío. Por favor, añade artículos antes de continuar o añade el dni.";
					        String titulo = "Advertencia";
					        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.WARNING_MESSAGE);				
				     }
				}
			});
			btPedido.setFont(new Font("Arial Black", Font.PLAIN, 12));
		}
		return btPedido;
	}
	
	private DefaultTableModel ActualizarCarrito() {
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
		return tableModel;
	}
	
	private void ActualizarComponentesCarrito() {
		table.setModel(ActualizarCarrito());
		table.revalidate();
		table.repaint();
		actualizarTotal();
	}
	private JPanel getPanelTituloCarrito() {
		if (panelTituloCarrito == null) {
			panelTituloCarrito = new JPanel();
			panelTituloCarrito.setLayout(new BorderLayout(0, 0));
			panelTituloCarrito.add(getlbCarrito());
			panelTituloCarrito.add(getPanelCliente(), BorderLayout.EAST);
		}
		return panelTituloCarrito;
	}
	private JLabel getlbCarrito() {
		if (lbCarrito == null) {
			lbCarrito = new JLabel("Carrito");
			lbCarrito.setFont(new Font("Arial Black", Font.PLAIN, 22));
		}
		return lbCarrito;
	}
	private JPanel getPanelCliente() {
		if (panelCliente == null) {
			panelCliente = new JPanel();
			panelCliente.setLayout(new BorderLayout(0, 0));
			panelCliente.add(getLbCliente(), BorderLayout.WEST);
			panelCliente.add(getTfCliente(), BorderLayout.CENTER);
		}
		return panelCliente;
	}
	private JLabel getLbCliente() {
		if (lbCliente == null) {
			lbCliente = new JLabel("DNI: ");
			lbCliente.setFont(new Font("Arial", Font.BOLD, 12));
		}
		return lbCliente;
	}
	private JTextField getTfCliente() {
		if (tfCliente == null) {
			tfCliente = new JTextField();
			tfCliente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					validarDNI();						
				}
			});
			tfCliente.addFocusListener(new FocusListener() {
	            @Override
	            public void focusLost(FocusEvent e) {
	                validarDNI();
	            }

	            @Override
	            public void focusGained(FocusEvent e) {
	                // Puedes dejarlo vacío si no necesitas hacer nada al ganar el foco
	            }
	        });
			tfCliente.setFont(new Font("Arial", Font.BOLD, 12));
			tfCliente.setColumns(10);
			
		}
		return tfCliente;
	}
	
	private void validarDNI() {
	    String dni = tfCliente.getText();
	    if (ValidarDni.esDNIValido(dni)) {
	        tdc.setCliente(dni);
	    } else {
	        // Si el DNI no es válido, limpiar el campo
	        tfCliente.setText("");
	    }
	
	}
}
