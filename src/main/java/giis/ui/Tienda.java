package giis.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.table.DefaultTableModel;

import giis.controller.TiendaController;
import giis.model.Tienda.CarritoProductos;
import giis.model.Tienda.Categorias;
import giis.model.Tienda.ProductosDto;
import giis.util.TableColumnAdjuster;

public class Tienda extends JFrame {

    private static final long serialVersionUID = 1L;
    private TiendaController tdc;

    // ATRIBUTOS TIENDA
    private JPanel Tienda;

    private JScrollPane scpProductos;
    private JTable tbProductos;
    private JPanel panelControltienda;
    private JPanel pnTituloyCarrito;
    private JLabel LabelTienda;

    // ATRIBUTOS CARRITO
    private JPanel Carrito;
    private JScrollPane scrollPane;
    private JTable table;
    private JPanel panelInteracciones;
    private JPanel panelTotal;
    private JLabel lbTotal;
    private JTextField tfTotalPrecio;
    private JButton btPedido;

    // Propios ventana
    private JPanel contentPane;
    private JPanel panelTituloCarrito;
    private JLabel lbCarrito;
    private JPanel panelCliente;
    private JLabel lbCliente;
    private JTextField tfCliente;
    private JPanel panelEdicionCarrito;
    private JButton btEliminar;
    private JButton btEditar;
    private JSpinner spCantidadEditar;
    private JPanel panelAñadirCarrito;
    private JButton btAñadirCarrito;
    private JSpinner spCantidad;
    private JPanel panel;
    private JButton btAnteriorCategoria;
    private JButton btVolverRaiz;
    private JButton btSiguienteCategoria;

    public Tienda(TiendaController tdc) {
        this.tdc = tdc;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        contentPane.add(getTienda(), BorderLayout.CENTER);
        contentPane.add(getCarrito(), BorderLayout.EAST);

        // crearPanelTienda();

        setMinimumSize(new Dimension(1300, 550));
        setLocationRelativeTo(null);
    }

    private JPanel getTienda() {
        if (Tienda == null) {
            Tienda = new JPanel();
            Tienda.setLayout(new BorderLayout(0, 0));
            Tienda.add(getPnTituloyCarrito(), BorderLayout.NORTH);
            Tienda.add(getScpProductos(), BorderLayout.CENTER);
            Tienda.add(getPanelControltienda(), BorderLayout.SOUTH);
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

    // TIENDA

    private JScrollPane getScpProductos() {
        if (scpProductos == null) {
            scpProductos = new JScrollPane();
            scpProductos.setViewportView(getTbProductos());
        }
        return scpProductos;
    }

    private JTable getTbProductos() {
        if (tbProductos == null) {
            DefaultTableModel tableModel = new DefaultTableModel(
                new Object[] { "Categorias" }, 0) {
                private static final long serialVersionUID = 1L;

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Deshabilita la edición de celdas
                }
            };

            // Rellenar la tabla con los productos
            for (Categorias c : tdc.getCategoriasIniciales()) {
                tableModel.addRow(new Object[] { c.getNombre() });
            }
            tbProductos = new JTable(tableModel);
            tbProductos.setFont(new Font("Arial", Font.PLAIN, 14));
            tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        return tbProductos;
    }

    private JPanel getPanelControltienda() {
        if (panelControltienda == null) {
            panelControltienda = new JPanel();
            panelControltienda
                .setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            panelControltienda.setAlignmentX(Component.RIGHT_ALIGNMENT);
            panelControltienda.setLayout(new BorderLayout(0, 0));
            panelControltienda.add(getPanel_1(), BorderLayout.EAST);
            panelControltienda.add(getPanel(), BorderLayout.WEST);
        }
        return panelControltienda;
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

    // CARRITO

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
            panelInteracciones.add(getBtPedido(), BorderLayout.SOUTH);
            panelInteracciones.add(getPanelEdicionCarrito(), BorderLayout.WEST);
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

    private JTextField getTfTotalPrecio() {
        if (tfTotalPrecio == null) {
            tfTotalPrecio = new JTextField();
            tfTotalPrecio.setEditable(false);
            tfTotalPrecio.setFont(new Font("Arial Black", Font.PLAIN, 12));
            tfTotalPrecio.setColumns(10);
            actualizarTotal();
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
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!tdc.devolverCarrito().isEmpty()) {
                        String mensaje = "¿Está seguro de que desea continuar? Su pedido se realizará";
                        String titulo = "Confirmación";

                        // Mostrar el cuadro de diálogo de confirmación
                        int respuesta = JOptionPane.showConfirmDialog(null,
                            mensaje, titulo, JOptionPane.YES_NO_OPTION);

                        // Procesar la respuesta
                        if (respuesta == JOptionPane.YES_OPTION) {
                            tdc.crearPedido();
                            ActualizarCarrito();
                        } else if (respuesta == JOptionPane.NO_OPTION) {

                        }
                    } else {
                        String mensaje = "El carrito no puede estar vacío. Por favor, añade artículos antes de continuar.";
                        String titulo = "Advertencia";
                        JOptionPane.showMessageDialog(null, mensaje, titulo,
                            JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            btPedido.setFont(new Font("Arial Black", Font.PLAIN, 12));
        }
        return btPedido;
    }

    private DefaultTableModel ActualizarCarrito() {
        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[] { "Referencia", "Cantidad", "Precio" }, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilita la edición de celdas
            }
        };
        for (CarritoProductos cp : tdc.devolverCarrito()) {
            tableModel.addRow(new Object[] { cp.getReferencia(),
                cp.getCantidad(), cp.getPrecio() });
        }
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
            lbCliente = new JLabel("Cliente: ");
            lbCliente.setFont(new Font("Arial", Font.BOLD, 12));
        }
        return lbCliente;
    }

    private JTextField getTfCliente() {
        if (tfCliente == null) {
            tfCliente = new JTextField();
            tfCliente.setEditable(false);
            tfCliente.setFont(new Font("Arial", Font.BOLD, 12));
            tfCliente.setColumns(10);
            tfCliente.setText(tdc.getCliente().getDni());

        }
        return tfCliente;
    }

    private JPanel getPanelEdicionCarrito() {
        if (panelEdicionCarrito == null) {
            panelEdicionCarrito = new JPanel();
            panelEdicionCarrito.add(getBtEliminar());
            panelEdicionCarrito.add(getBtEditar());
            panelEdicionCarrito.add(getSpCantidadEditar());
        }
        return panelEdicionCarrito;
    }

    private JButton getBtEliminar() {
        if (btEliminar == null) {
            btEliminar = new JButton("Eliminar");
            btEliminar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        tdc.eliminarDelCarrito(
                            table.getValueAt(row, 0).toString());
                        ActualizarComponentesCarrito();
                    }
                }
            });
            btEliminar.setFont(new Font("Arial Black", Font.PLAIN, 12));
        }
        return btEliminar;
    }

    private JButton getBtEditar() {
        if (btEditar == null) {
            btEditar = new JButton("Editar");
            btEditar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        tdc.cambiarCantidad(table.getValueAt(row, 0).toString(),
                            Integer.parseInt(
                                spCantidadEditar.getValue().toString()));
                        spCantidadEditar.setValue(1);
                        ActualizarComponentesCarrito();
                    }
                }
            });
            btEditar.setFont(new Font("Arial Black", Font.PLAIN, 12));
        }
        return btEditar;
    }

    private JSpinner getSpCantidadEditar() {
        if (spCantidadEditar == null) {
            spCantidadEditar = new JSpinner();
            spCantidadEditar.setModel(new SpinnerNumberModel(Integer.valueOf(1),
                Integer.valueOf(1), null, Integer.valueOf(1)));
            spCantidadEditar.setFont(new Font("Arial Black", Font.PLAIN, 12));
        }
        return spCantidadEditar;
    }

    private JPanel getPanel_1() {
        if (panelAñadirCarrito == null) {
            panelAñadirCarrito = new JPanel();
            panelAñadirCarrito.add(getBtAñadirCarrito());
            panelAñadirCarrito.add(getSpCantidad());
        }
        return panelAñadirCarrito;
    }

    private JButton getBtAñadirCarrito() {
        if (btAñadirCarrito == null) {
            btAñadirCarrito = new JButton("Añadir al carrito");
            btAñadirCarrito.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (tbProductos.getModel().getColumnCount() == 3) {
                        int row = tbProductos.getSelectedRow();
                        if (row >= 0) {
                            tdc.agregarAlCarrito(
                                tbProductos.getValueAt(row, 0).toString(),
                                Integer.parseInt(
                                    spCantidad.getValue().toString()));
                            spCantidad.setValue(1);
                            ActualizarComponentesCarrito();
                        }
                    }
                }
            });
            btAñadirCarrito.setFont(new Font("Arial Black", Font.PLAIN, 14));
        }
        return btAñadirCarrito;
    }

    private JSpinner getSpCantidad() {
        if (spCantidad == null) {
            spCantidad = new JSpinner();
            spCantidad.setModel(new SpinnerNumberModel(Integer.valueOf(1),
                Integer.valueOf(1), null, Integer.valueOf(1)));
            spCantidad.setFont(new Font("Arial Black", Font.PLAIN, 14));
        }
        return spCantidad;
    }

    private JPanel getPanel() {
        if (panel == null) {
            panel = new JPanel();
            panel.add(getBtAnteriorCategoria());
            panel.add(getBtSiguienteCategoria());
            panel.add(getBtVolverRaiz());
        }
        return panel;
    }

    private JButton getBtAnteriorCategoria() {
        if (btAnteriorCategoria == null) {
            btAnteriorCategoria = new JButton("Anterior");
            btAnteriorCategoria.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String elemento = tbProductos.getValueAt(0, 0).toString();
                    List<Categorias> categorias = new ArrayList<>();
                    if (elemento.startsWith("REF")) {
                        categorias = tdc.mostrarCategoriasReferencia(elemento);
                    } else {
                        categorias = tdc.mostrarCategoriasPadre(elemento);
                    }

                    DefaultTableModel tableModel = new DefaultTableModel(
                        new Object[] { "Categorias" }, 0) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false; // Deshabilita la edición de celdas
                        }
                    };
                    // Rellenar la tabla con los productos
                    for (Categorias c : categorias) {
                        tableModel.addRow(new Object[] { c.getNombre() });
                    }
                    tbProductos.setModel(tableModel);
                    tbProductos.revalidate();
                    tbProductos.repaint();
                }
            });
            btAnteriorCategoria
                .setFont(new Font("Arial Black", Font.PLAIN, 14));
        }
        return btAnteriorCategoria;
    }

    private JButton getBtVolverRaiz() {
        if (btVolverRaiz == null) {
            btVolverRaiz = new JButton("Volver a la categoría raiz");
            btVolverRaiz.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DefaultTableModel tableModel = new DefaultTableModel(
                        new Object[] { "Categorias" }, 0) {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false; // Deshabilita la edición de celdas
                        }
                    };
                    // Rellenar la tabla con los productos
                    for (Categorias c : tdc.getCategoriasIniciales()) {
                        tableModel.addRow(new Object[] { c.getNombre() });
                    }
                    tbProductos.setModel(tableModel);
                    tbProductos.revalidate();
                    tbProductos.repaint();
                }
            });
            btVolverRaiz.setFont(new Font("Arial Black", Font.PLAIN, 14));
        }
        return btVolverRaiz;
    }

    private void ActualizarTablaProductos() {
        if (tbProductos.getModel().getColumnCount() == 1) {
            DefaultTableModel tableModel;
            int row = tbProductos.getSelectedRow();
            List<Categorias> categorias = tdc
                .getSubCategorias(tbProductos.getValueAt(row, 0).toString());

            if (categorias.size() != 0) {
                tableModel = new DefaultTableModel(
                    new Object[] { "Categorias" }, 0) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Deshabilita la edición de celdas
                    }
                };
                // Rellenar la tabla con las categorías
                for (Categorias c : categorias) {
                    tableModel.addRow(new Object[] { c.getNombre() });
                }
                tbProductos.setModel(tableModel);

            } else {
                tableModel = new DefaultTableModel(
                    new Object[] { "Referencia", "Precio", "Descripción" }, 0) {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Deshabilita la edición de celdas
                    }
                };
                for (ProductosDto pr : tdc
                    .getProductos(tbProductos.getValueAt(row, 0).toString())) {
                    tableModel.addRow(new Object[] { pr.getReferencia(),
                        pr.getPrecioUnitario(), pr.getDatosbasicos() });
                }
                tbProductos.setModel(tableModel);
                TableColumnAdjuster tca=new TableColumnAdjuster(tbProductos);
        		tca.adjustColumns();
            }
            tbProductos.revalidate();
            tbProductos.repaint();
        }

    }

    private JButton getBtSiguienteCategoria() {
        if (btSiguienteCategoria == null) {
            btSiguienteCategoria = new JButton("Siguiente");
            btSiguienteCategoria.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ActualizarTablaProductos();
                }
            });
            btSiguienteCategoria
                .setFont(new Font("Arial Black", Font.PLAIN, 14));
        }
        return btSiguienteCategoria;
    }
}
