package giis.model;

import java.util.ArrayList;
import java.util.List;

import giis.util.Database;

public class AlmaceneroModel {
	
	private Database db=new Database();
	public List<PedidoDto> getPedidosPendientesRecogida() {
		db.createDatabase(false);
		db.loadDatabase();
		String sql="SELECT p.fecha, SUM(pp.cantidad), p.estado FROM Pedido p LEFT JOIN PedidoProducto pp "
				+ "ON pp.pedido_id = p.id WHERE p.estado = 'Pendiente de recogida' GROUP BY p.id, p.fecha, p.estado ORDER BY p.fecha;";
		
		List<Object[]> lista=db.executeQueryArray(sql,new Object[0]);
		List<PedidoDto> listaPedidosNoRecogidos=new ArrayList<PedidoDto>();
		PedidoDto dto;
		
		for(Object[] d: lista) {
			dto=new PedidoDto();
			dto.setFecha(d[0].toString());
			dto.setTamano(Integer.parseInt(d[1].toString()));
			dto.setEstado(d[2].toString());
			listaPedidosNoRecogidos.add(dto);
		}
		for(PedidoDto d:listaPedidosNoRecogidos) {
			System.out.println(d);
		}
		return listaPedidosNoRecogidos;
	}
	
	public static void main(String[] args) {
		//getPedidosPendientesRecogida();
	}
}
