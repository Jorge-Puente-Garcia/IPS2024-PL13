package giis.model.Tienda;

public class Categorias {

    private String nombre;
    private int id_padre;

    public Categorias(String nombre, int id_padre) {
        this.nombre = nombre;
        this.id_padre = id_padre;
    }

    public Categorias() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_padre() {
        return id_padre;
    }

    public void setId_padre(int id_padre) {
        this.id_padre = id_padre;
    }
}
