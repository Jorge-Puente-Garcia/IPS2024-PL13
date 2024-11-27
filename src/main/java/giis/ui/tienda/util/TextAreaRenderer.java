package giis.ui.tienda.util;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TextAreaRenderer extends JTextArea implements TableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextAreaRenderer() {
        setLineWrap(true);          // Habilitar ajuste de línea
        setWrapStyleWord(true);     // Ajuste por palabras completas
        setOpaque(true);            // Necesario para respetar el fondo de la tabla
        
        // Configurar la fuente Arial de tamaño 14
        setFont(new Font("Arial", Font.PLAIN, 14));
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        // Asignar el texto al JTextArea
        setText(value == null ? "" : value.toString());

        // Ajustar colores para selección y fondo
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        // Ajustar el tamaño del JTextArea
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);

        // Ajustar la altura de la fila según el contenido
        int rowHeight = getPreferredSize().height;
        if (table.getRowHeight(row) != rowHeight) {
            table.setRowHeight(row, rowHeight);
        }

        return this;
    }
}
