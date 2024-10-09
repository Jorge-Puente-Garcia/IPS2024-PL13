package giis.model.Almacenero;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import giis.model.OrdenTrabajo;
import giis.model.Producto;

public class Almacenero {
    private String nombre;
    private String apellido;
    private List<OrdenTrabajo> otsCompletas=new ArrayList<OrdenTrabajo>();
    public Almacenero(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

	// Método para recoger productos asociados a una OT (Orden de Trabajo)
	public void recogerProductos(OrdenTrabajo ot) {
	    List<Producto> productosPendientes = ot.getProductos();
	    List<OrdenTrabajo> listaOts=new ArrayList<OrdenTrabajo>();
	    Scanner scanner = new Scanner(System.in);

	    for (Producto producto : productosPendientes) {
	        System.out.println("Preparando para recoger: " + producto.getReferencia());
	        System.out.println("Unidades a recoger: " + producto.getUnidades());

	        // Permitir al almacenero ingresar el número de unidades que va a recoger
	        System.out.print("Ingrese el número de unidades a recoger: ");
	        int unidadesARecoger = scanner.nextInt();
	        // Validar la cantidad de unidades a recoger
	        if (unidadesARecoger != producto.getUnidades()) {
	            
	            Incidencia incidencia = new Incidencia(
	                1, 
	                producto.getReferencia(),
	                "Cantidad solicitada (" + unidadesARecoger + ") distinta a la de la OT",
	                producto.getLocalizacion().getFila(),
	                producto.getLocalizacion().getColumna(),
	                producto.getLocalizacion().getEstanteria(),
	                producto.getLocalizacion().getCara(),
	                producto.getLocalizacion().getPasillo(),
	                LocalDateTime.now()
	            );

	            ot.agregarIncidencia(incidencia);
	            System.out.println("¡Error! La cantidad solicitada no corresponde con la cantidad que aparece en la OT");
	            continue; // Continuar con el siguiente producto
	        }
	        // Validar el código de barras escaneado
	        System.out.print("Escanee el código de barras del producto: ");
	        String codigoEscaneado = scanner.next();

	        // Validar la referencia escaneada con la referencia del producto
	        if (!producto.getReferencia().equals(codigoEscaneado)) {
	            // Crear una incidencia por referencia incorrecta
	            Incidencia incidencia = new Incidencia(
	                2, 
	                producto.getReferencia(),
	                "Referencia escaneada no coincide con la OT. Escaneado: " + codigoEscaneado,
	                producto.getLocalizacion().getFila(),
	                producto.getLocalizacion().getColumna(),
	                producto.getLocalizacion().getEstanteria(),
	                producto.getLocalizacion().getCara(),
	                producto.getLocalizacion().getPasillo(),
	                LocalDateTime.now()
	            );

	            ot.agregarIncidencia(incidencia);
	            System.out.println("¡Error! La referencia escaneada no coincide con la OT.");
	            continue; // Continuar con el siguiente producto
	        }

	       

	        // Simular la recogida
	        System.out.println("Recogiendo " + unidadesARecoger + " unidades de " + producto.getReferencia());

	        // Actualizar el producto (reducir unidades)
	        producto.setUnidades(producto.getUnidades() - unidadesARecoger);

	        // Mostrar estado actual de la OT después de la recogida
	        System.out.println("Estado actual de la OT:");
	        for (Producto p : ot.getProductos()) {
	            System.out.println(p.getReferencia() + ": " + p.getUnidades() + " unidades restantes.");
	        }

	        
	    }

	    // Comprobar si la OT está completa y puede pasar a empaquetado
	    if (ot.estaCompleta()) {
	        System.out.println("La OT está completa y lista para empaquetado.");
	        otsCompletas.add(ot);
	        
	    } else {
	        System.out.println("La OT no está completa, no puede pasar a empaquetado.");
	    }
	}
	public List<OrdenTrabajo> otsPendientesDeEmpaquetar() {
        return otsCompletas;
    }
}
