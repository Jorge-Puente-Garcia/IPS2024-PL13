package giis.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import giis.model.Almacenero;
import giis.model.LectorBd;
import giis.model.Localizacion;
import giis.model.OrdenTrabajo;
import giis.model.Pedido;
import giis.model.Producto;


import java.util.Arrays;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Crear sistema de almacén
        Almacenero alm=new Almacenero("Juan", "Perez");
    	List<Producto> productos=new ArrayList<Producto>();
        // Crear algunos productos
        Producto p1 = new Producto("REF001", "Smartphone", 10, new Localizacion(1, 5, 3, 'A'));
        Producto p2 = new Producto("REF002", "Laptop", 5, new Localizacion(1, 3, 1, 'B'));
        Producto p3 = new Producto("REF003", "Tablet", 8, new Localizacion(1, 1, 2, 'B'));
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);
        // Crear algunos pedidos
        Pedido pedido1 = new Pedido(2, "22-5-2001",22.2,"Disponible",2, productos);
       
        OrdenTrabajo o=new OrdenTrabajo(pedido1, alm);
        o.mostrarOrdenTrabajo();
        LectorBd l=new LectorBd();
        l.getLocalizacionBd();

       
    }


}


	 
   
   

