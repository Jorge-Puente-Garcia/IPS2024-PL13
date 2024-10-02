package giis.model;

public class Localizacion implements Comparable<Localizacion> {
    private int estanteria; // Número de estantería
    private int fila;       // Fila dentro de la estantería
    private int columna;     // Altura dentro de la fila
    private char cara;      // Cara de la estantería ('A' o 'B')

    public Localizacion(int estanteria, int fila, int columna, char cara) {
        this.estanteria = estanteria;
        this.fila = fila;
        this.columna=columna;
        this.cara = cara;
    }

    public int getEstanteria() {
        return estanteria;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public char getCara() {
        return cara;
    }

    @Override
    public int compareTo(Localizacion o) {
        if (this.estanteria != o.estanteria) {
            return Integer.compare(this.estanteria, o.estanteria);
        } else if (this.fila != o.fila) {
            return Integer.compare(this.fila, o.fila);
        } else if (this.columna != o.columna) {
            return Integer.compare(this.columna, o.columna);
        } else {
            return Character.compare(this.cara, o.cara);
        }
    }

    @Override
    public String toString() {
        return "Estantería: " + estanteria + ", Fila: " + fila + ", Altura: " + columna + ", Cara: " + cara;
    }
}
