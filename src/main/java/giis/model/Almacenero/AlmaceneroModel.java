package giis.model.Almacenero;

import java.util.ArrayList;
import java.util.List;

import giis.model.Estado;
import giis.util.Database;

public class AlmaceneroModel {
	
	private static Database db=new Database();
	
	public static List<PedidoARecoger> getPedidosPendientesRecogida() {
		
		db.createDatabase(false);
		db.loadDatabase();
		
		String sql="SELECT p.fecha, SUM(pp.cantidad), p.estado FROM Pedido p LEFT JOIN ProductosPedido pp "
				+ "ON pp.pedido_id = p.id WHERE p.estado = 'Pendiente de recogida' GROUP BY p.id, p.fecha, p.estado ORDER BY p.fecha;";
		
		List<Object[]> lista=db.executeQueryArray(sql,new Object[0]);
		List<PedidoARecoger> listaPedidosNoRecogidos=new ArrayList<PedidoARecoger>();
		PedidoARecoger dto;
		
		for(Object[] d: lista) {
			dto=new PedidoARecoger();
			dto.setFecha(d[0].toString());
			dto.setTamano(Integer.parseInt(d[1].toString()));
			String estado = d[2].toString();
			switch (estado) {
			case "Pendiente de recogida" -> dto.setEstado(Estado.PendienteDeRecogida);
			case "Recogido" ->	dto.setEstado(Estado.Recogido);
			default -> throw new IllegalArgumentException();
			}
			
			
			listaPedidosNoRecogidos.add(dto);
		}
		
		for(PedidoARecoger d:listaPedidosNoRecogidos) {
			System.out.println(d);
		}
		
		return listaPedidosNoRecogidos;
	}
	
	public static void main(String[] args) {
		getPedidosPendientesRecogida();
	}
}
