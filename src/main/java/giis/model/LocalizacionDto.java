package giis.model;

public class LocalizacionDto {
	 private int estanteria; // Número de estantería
	    private int fila;       // Fila dentro de la estantería
	    private int columna;     // Altura dentro de la fila
	    private char cara;      // Cara de la estantería ('A' o 'B')

	    public LocalizacionDto(int pasillo,int fila, int columna, int estanteria, char cara) {
	        this.setEstanteria(estanteria);
	        this.setFila(fila);
	        this.setColumna(columna);
	        this.setCara(cara);
	    }

		public int getEstanteria() {
			return estanteria;
		}

		public void setEstanteria(int estanteria) {
			this.estanteria = estanteria;
		}

		public int getFila() {
			return fila;
		}

		public void setFila(int fila) {
			this.fila = fila;
		}

		public int getColumna() {
			return columna;
		}

		public void setColumna(int columna) {
			this.columna = columna;
		}

		public char getCara() {
			return cara;
		}

		public void setCara(char cara) {
			this.cara = cara;
		}
		 @Override
		    public String toString() {
		        return "Estantería: " + estanteria + ", Fila: " + fila + ", Columna: " + columna + ", Cara: " + cara;
		    }
}
