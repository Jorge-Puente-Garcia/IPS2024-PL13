package giis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import giis.model.Almacenero.Almacenero;

public class Empaquetado {
	private Almacenero a;
	public Empaquetado(Almacenero a) {
		this.a = a;
	}
	public List<OrdenTrabajo> otsPendientesDeEmpaquetar(List<OrdenTrabajo> otsPendientes){
		 List<OrdenTrabajo> otsParaEmpaquetar = new ArrayList<OrdenTrabajo>();
		 for (OrdenTrabajo ot : otsPendientes) {
		        // Verificar si la OT está completa y no tiene incidencias
		        if (ot.estaCompleta() ) {
		            otsParaEmpaquetar.add(ot);
		        }
		    }
		    
		    return otsParaEmpaquetar; // Devolver la lista de órdenes pendientes de empaquetar
		}
	public OrdenTrabajo seleccionarOT(List<OrdenTrabajo> otsParaEmpaquetar) {
	    Scanner scanner = new Scanner(System.in);
	    System.out.println("Órdenes de trabajo pendientes de empaquetar:");
	    
	    for (int i = 0; i < otsParaEmpaquetar.size(); i++) {
	        System.out.println((i + 1) + ". " + otsParaEmpaquetar.get(i).toString());
	    }
	    
	    System.out.print("Seleccione la OT que desea empaquetar: ");
	    int seleccion = scanner.nextInt() - 1;
	    
	    return otsParaEmpaquetar.get(seleccion);
	}
	public void empaquetarProductos(OrdenTrabajo ot) {
	    Scanner scanner = new Scanner(System.in);
	    List<Producto> productos = ot.getProductos();
	    
	    for (Producto producto : productos) {
	        System.out.println("Preparando para empaquetar: " + producto.getReferencia());
	        
	        // Escanear código de barras
	        System.out.print("Escanee el código de barras del producto: ");
	        String codigoEscaneado = scanner.next();
	        
	        // Validar la referencia
	        if (!producto.getReferencia().equals(codigoEscaneado)) {
	            System.out.println("¡Error! El producto escaneado no coincide con el que aparece en la OT.");
	            return; // Terminar el proceso si hay discrepancia
	        }
	        
	        // Simular introducción del producto en la caja
	        System.out.println("Introduciendo producto en la caja: " + producto.getReferencia());
	    }
	    
	    System.out.println("La OT ha sido empaquetada con éxito.");
	}
	public void iniciarProcesoEmpaquetado(List<OrdenTrabajo> listaOts) {
	    List<OrdenTrabajo> otsPendientes = otsPendientesDeEmpaquetar(listaOts);
	    
	    if (otsPendientes.isEmpty()) {
	        System.out.println("No hay OTs pendientes de empaquetar.");
	        return;
	    }
	    
	    OrdenTrabajo otSeleccionada = seleccionarOT(otsPendientes);
	    empaquetarProductos(otSeleccionada);
	}


	}

