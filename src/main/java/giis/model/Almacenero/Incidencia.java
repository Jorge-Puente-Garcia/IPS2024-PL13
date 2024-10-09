package giis.model.Almacenero;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Incidencia {
	private int id;
    private String referenciaProducto;
    private String descripcion;
    private int fila;
    private int columna;
    private int estanteria;
    private char cara;
    private int pasillo;
    private LocalDateTime fecha;
    // Constructor
    public Incidencia(int id, String referenciaProducto, String descripcion, int fila, int columna, int estanteria, char cara, int pasillo, LocalDateTime fecha) {
        this.id = id;
        this.referenciaProducto = referenciaProducto;
        this.descripcion = descripcion;
        this.fila = fila;
        this.columna = columna;
        this.estanteria = estanteria;
        this.cara = cara;
        this.pasillo = pasillo; // Asignar pasillo
        this.fecha = fecha;
    }
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReferenciaProducto() {
        return referenciaProducto;
    }

    public void setReferenciaProducto(String referenciaProducto) {
        this.referenciaProducto = referenciaProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", referencia='" + referenciaProducto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaHora=" + getFechaHora() +
                '}';
    }
}
