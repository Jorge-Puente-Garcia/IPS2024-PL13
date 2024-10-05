package giis.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import giis.model.LectorBd;
import giis.model.Localizacion;
import giis.model.OrdenTrabajo;
import giis.model.Pedido;
import giis.model.Producto;
import giis.model.ProductoDto;
import giis.model.Almacenero.Almacenero;

import java.util.Arrays;
import java.util.Date;

public class Main {
	public static void main(String[] args) {
        // Crear sistema de almacén
        Almacenero alm=new Almacenero("Juan", "Perez");
        LectorBd l=new LectorBd();
        List<ProductoDto>listaProductos= l.getProductoBd();
        // Crear algunos pedidos
        Pedido pedido1 = new Pedido(2, "22-5-2001",22.2,"Disponible",2, listaProductos);
       
        OrdenTrabajo o=new OrdenTrabajo(pedido1, alm);
        o.mostrarOrdenTrabajo();
       
       
    

       
    }

}
