package giis.model;

import java.util.Comparator;
import java.util.List;

public class OrdenTrabajo {
    private Pedido pedido;
    private Almacenero almacenero;

    public OrdenTrabajo(Pedido pedido, Almacenero almacenero) {
        this.pedido = pedido;
        this.almacenero = almacenero;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Almacenero getAlmacenero() {
        return almacenero;
    }

    public void mostrarOrdenTrabajo() {
        // Obtener la lista de productos y ordenarla por estantería, cara, fila y columna
        List<Producto> productosOrdenados = pedido.getProductos();
        productosOrdenados.sort(Comparator.comparing((Producto p) -> p.getLocalizacion().getEstanteria())   // Ordenar por estantería
                                           .thenComparing(p -> p.getLocalizacion().getCara())               // Luego por cara
                                           .thenComparing(p -> p.getLocalizacion().getFila())               // Luego por fila
                                           .thenComparing(p -> p.getLocalizacion().getColumna()));           // Finalmente por altura

        // Mostrar los productos ordenados con toda la información relevante
        for (Producto producto : productosOrdenados) {
            Localizacion loc = producto.getLocalizacion(); // Obtener la localización del producto
            System.out.println("Referencia: " + producto.getReferencia() +
                               ", Cantidad: " + producto.getUnidades() +
                               ", Estantería: " + loc.getEstanteria() +
                               ", Cara: " + loc.getCara() +
                               ", Fila: " + loc.getFila() +
                               ", Altura: " + loc.getColumna() +
                               ", Datos básicos: " + producto.getDatosBasicos());
        }
    }

}
