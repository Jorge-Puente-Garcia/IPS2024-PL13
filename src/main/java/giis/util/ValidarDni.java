package giis.util;

import javax.swing.JOptionPane;

public class ValidarDni {

	private static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";

    public static boolean esDNIValido(String dni) {
        if (dni == null || dni.length() != 9) {
            mostrarMensajeError("El formato del DNI es incorrecto.");
            return false;
        }

        String numeroParte = dni.substring(0, 8);
        char letraParte = dni.charAt(8);

        if (!numeroParte.matches("\\d{8}")) {
            mostrarMensajeError("Los primeros 8 caracteres deben ser dígitos.");
            return false;
        }

        int numero = Integer.parseInt(numeroParte);
        char letraCalculada = LETRAS.charAt(numero % 23);

        if (letraCalculada != Character.toUpperCase(letraParte)) {
            mostrarMensajeError("La letra del DNI es incorrecta.");
            return false;
        }

        // Si el DNI es válido, devolver true
        return true;
    }

    // Método para mostrar el mensaje de error
    private static void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
}

