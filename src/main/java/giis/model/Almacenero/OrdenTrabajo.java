package giis.model.Almacenero;

import java.util.Comparator;
import java.util.List;

import giis.model.Localizacion;

public class OrdenTrabajo {
    List<Producto> productos;
 
    private List<Incidencia> incidencias;
    String id;
    public OrdenTrabajo(List<Producto> productos, String id,List<Incidencia> incidencias) {
        this.productos=productos;
        this.id=id;
        this.incidencias=incidencias;
        
       }

   

    public List<Producto> getProductos() {
		return productos;
	}
    public void agregarIncidencia(Incidencia incidencia) {
        this.incidencias.add(incidencia);
    }


	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	  public List<Incidencia> getIncidencias() {
	        return incidencias;
	    }



	public String getId() {
		return id;
	}



	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("OrdenTrabajo [id=").append(id).append(", incidencias=").append(incidencias).append("]\n");
	    sb.append("Productos:\n");
	    
	    for (Producto producto : productos) {
	        sb.append(producto.toString()).append("\n"); // Asegúrate de que Producto tenga un método toString() adecuado
	    }
	    
	    return sb.toString();
	}




	public void setId(String id) {
		this.id = id;
	}



	public void mostrarOrdenTrabajo() {
        // Obtener la lista de productos y ordenarla por estantería, cara, fila y columna
        List<Producto> productosOrdenados = productos;
        productosOrdenados.sort(Comparator.comparing((Producto p) ->p.getLocalizacion().getPasillo())
        									.thenComparing(p->p.getLocalizacion().getEstanteria())   // Ordenar por estantería
                                           .thenComparing(p -> p.getLocalizacion().getCara())               // Luego por cara
                                           .thenComparing(p -> p.getLocalizacion().getFila())               // Luego por fila
                                           .thenComparing(p -> p.getLocalizacion().getColumna()));           // Finalmente por altura

        // Mostrar los productos ordenados con toda la información relevante
        for (Producto producto : productosOrdenados) {
            Localizacion loc = producto.getLocalizacion(); // Obtener la localización del producto
            System.out.println("Referencia: " + producto.getReferencia() +
                               ", Cantidad: " + producto.getUnidades() +
                               ", Pasillo:" + loc.getPasillo() +
                               ", Estantería: " + loc.getEstanteria() +
                               ", Cara: " + loc.getCara() +
                               ", Fila: " + loc.getFila() +
                               ", Altura: " + loc.getColumna() +
                               ", Datos básicos: " + producto.getDatosBasicos());
        }
    }
	public boolean estaCompleta() {
	
	    for (Producto producto : productos) {
	        if (producto.getUnidades() > 0) {
	            return false; // Si quedan unidades por recoger, no está completa
	        }
	    }
	  
	    return true; // Todos los productos han sido recogidos
	}


}
