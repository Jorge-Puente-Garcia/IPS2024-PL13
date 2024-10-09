package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.controller.TiendaController;
import giis.util.Database;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btAbrirTienda;
	
	private static Database db = new Database();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		
		setLocationRelativeTo(null);
	}

	private JButton getBtAbrirTienda() {
		if (btAbrirTienda == null) {
			btAbrirTienda = new JButton("AbrirTienda");
			btAbrirTienda.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new TiendaController(db);
				}
			});
		}
		return btAbrirTienda;
	}
}
