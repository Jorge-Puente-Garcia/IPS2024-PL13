package main;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.controller.TiendaController;
import giis.ui.AlmaceneroView;
import giis.ui.ClienteBuscador;
import giis.util.Database;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btAbrirTienda;

    private static Database db = new Database();
    private JButton btnAbrirAlmacen;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {

                    db.createDatabase(false);
                    db.loadDatabase();

                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(3, 0, 0, 0));
        contentPane.add(getBtAbrirTienda());
        contentPane.add(getBtnAbrirAlmacen());

        setLocationRelativeTo(null);
    }

    private JButton getBtAbrirTienda() {
        if (btAbrirTienda == null) {
            btAbrirTienda = new JButton("AbrirTienda");
            btAbrirTienda.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] clienteData = ClienteBuscador.lanzarRegistro();
                    if (clienteData.length != 0) {
                        new TiendaController(db, clienteData);
                    }
                }
            });
        }
        return btAbrirTienda;
    }

    private JButton getBtnAbrirAlmacen() {
        if (btnAbrirAlmacen == null) {
            btnAbrirAlmacen = new JButton("AbrirAlmacen");
            btnAbrirAlmacen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                AlmaceneroView window = new AlmaceneroView();
                                window.getFrameTerminalPortatil()
                                    .setVisible(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
        return btnAbrirAlmacen;
    }
}
