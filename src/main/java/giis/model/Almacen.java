package giis.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Almacen {
	
	    private List<Pedido> pedidosPendientes = new ArrayList<>();
	    private List<Localizacion> listaLocalizaciones= new ArrayList<Localizacion>();

	    public void agregarPedido(Pedido pedido) {
	        pedidosPendientes.add(pedido);
	    }
	    public void agregarLocalizacion(Localizacion localizacion) {
	        listaLocalizaciones.add(localizacion);
	    }
	    
}
