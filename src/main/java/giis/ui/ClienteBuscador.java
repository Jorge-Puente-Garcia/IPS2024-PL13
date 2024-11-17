package giis.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClienteBuscador {
    public static String[] lanzarRegistro() {
        String[] rs = new String[0];
        boolean dniValido = false;

        // Crear el panel para organizar los campos de entrada
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Etiquetas y campos de texto
        JLabel labelDni = new JLabel("*DNI:");
        JTextField campoDni = new JTextField();

        // Agregar componentes al panel
        panel.add(labelDni);
        panel.add(campoDni);

        // Bucle para validar el DNI
        while (!dniValido) {
            int resultado = JOptionPane.showConfirmDialog(null, panel,
                "Introduzca sus datos", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) {
                String dni = campoDni.getText();

                // Verificación de la longitud del DNI
                if (dni.length() >= 9) {
                    // Si el DNI es válido, asigna los valores al arreglo
                    rs = new String[] { dni };
                    dniValido = true; // Salir del bucle
                } else {
                    // Mostrar mensaje de error si el DNI no es válido
                    JOptionPane.showMessageDialog(null,
                        "El DNI debe tener al menos 9 caracteres.",
                        "DNI inválido", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                break; // Rompe el bucle si el usuario presiona "Cancelar"
            }
        }
        return rs;
    }
}
