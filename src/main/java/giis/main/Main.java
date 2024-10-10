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
    	 Almacenero alm=new Almacenero("Juan", "Perez");
         LectorBd l=new LectorBd();
         List<Incidencia> incidencias = new ArrayList<Incidencia>();
         List<Producto>listaProductos= l.getProductoBd(); 
         OrdenTrabajo o1=new OrdenTrabajo(listaProductos, "1",incidencias);
         OrdenTrabajo o2=new OrdenTrabajo(listaProductos, "2",incidencias);
         alm.recogerProductos(o1);
         Empaquetado e=new Empaquetado(alm);
         List<OrdenTrabajo>ots=alm.otsPendientesDeEmpaquetar();
         System.out.println(ots);
         System.out.println(e.seleccionarOT(ots));
         e.iniciarProcesoEmpaquetado(ots);
        

       
    }


}


	 
   
   

