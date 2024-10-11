package giis.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import giis.model.Empaquetado;
import giis.model.LectorBd;
import giis.model.Localizacion;
import giis.model.OrdenTrabajo;
import giis.model.Pedido;
import giis.model.Producto;
import giis.model.ProductoDto;
import giis.model.Almacenero.Almacenero;
import giis.model.Almacenero.Incidencia;
import giis.model.Almacenero.PedidoARecogerDto;

import java.util.Arrays;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Crear sistema de almacén
    	 Almacenero alm1=new Almacenero("Juan", "Perez");
         LectorBd l=new LectorBd();
         Localizacion l1=new Localizacion(1, 2, 3, 4,'A');
         Localizacion l2=new Localizacion(3, 2, 3, 1,'B');
         Producto p1=new Producto("REF001", "Smartphone de última generación con pantalla AMOLED de 6.5 pulgadas y 128GB de almacenamiento", 5, 199.99, l1);
         Producto p2=new Producto("REF002", "Laptop ultraligera de 14 pulgadas, procesador Intel Core i7, 16GB RAM y 512GB SSD", 6,1199.99, l2);
         List<Incidencia> incidencias = new ArrayList<Incidencia>();
         List<Producto>listaProductos1=new ArrayList<Producto>();  
         listaProductos1.add(p1);
         listaProductos1.add(p2);
         OrdenTrabajo o1=new OrdenTrabajo(listaProductos1, "1",incidencias); 
         alm1.recogerProductos(o1);
         Empaquetado e=new Empaquetado(alm1);
         List<OrdenTrabajo>ots1=alm1.otsPendientesDeEmpaquetar();
         System.out.println(ots1);
         e.iniciarProcesoEmpaquetado(ots1);
        

       
    }


}


	 
   
   

