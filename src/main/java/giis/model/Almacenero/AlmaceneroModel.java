package giis.model.Almacenero;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import giis.model.Estado;
import giis.util.Database;

public class AlmaceneroModel {
	
	private  Database db;
	
	public AlmaceneroModel() {
		this.db = new Database();
		db.createDatabase(false);
		db.loadDatabase();
		
	}
	public static void main(String[] args) {
		//getPedidosPendientesRecogida();
	}

	public  List<PedidoARecogerRecord> getPedidosPendientesRecogida() {	
		String sql="SELECT p.id ,p.fecha, SUM(pp.cantidad), p.estado FROM Pedido p LEFT JOIN ProductosPedido pp "
				+ "ON pp.pedido_id = p.id WHERE p.estado = 'Pendiente de recogida' GROUP BY p.id, p.fecha, p.estado ORDER BY p.fecha;";
		
		List<Object[]> lista=db.executeQueryArray(sql,new Object[0]);
		List<PedidoARecogerRecord> listaPedidosNoRecogidos=new ArrayList<PedidoARecogerRecord>();
		PedidoARecogerRecord dto;
		
		for(Object[] d: lista) {
			dto=new PedidoARecogerRecord();
			dto.setId(d[0].toString());
			dto.setFecha(d[1].toString());
			dto.setTamano(Integer.parseInt(d[2].toString()));
			String estado = d[3].toString();
			switch (estado) {
			case "Pendiente de recogida" -> dto.setEstado(Estado.PendienteDeRecogida);
			case "Recogido" ->	dto.setEstado(Estado.Recogido);
			default -> throw new IllegalArgumentException();
			}
			
			
			listaPedidosNoRecogidos.add(dto);
		}
		
		return listaPedidosNoRecogidos;
	}
	
	
	public void ponEnRecogidaElPedido(PedidoARecogerRecord par) {
		String sqlUpdate="UPDATE PEDIDO SET estado='Recogido' WHERE id='"+par.getId()+"';";
		db.executeUpdate(sqlUpdate);
	}
	public boolean isOkIdAlmacenero(String id) {
		String sql="SELECT nombre from ALMACENERO WHERE id='"+id+"';";
		List<Object[]> lista=db.executeQueryArray(sql);
		if(lista.isEmpty()) {
			return false;
		}
		return true;
	}
	public void creaOrdenDeTrabajo(int almaceneroId) {
		String sqlInsert="INSERT INTO OrdenTrabajo (fecha_creacion,estado,almacenero_id,incidencia) "
				+ "VALUES ('"+LocalDate.now().toString()+"' , 'En recogida' , '"+almaceneroId+"' ,'Ninguna');";
		db.executeUpdate(sqlInsert);
		
	}
	public List<OrdenTrabajoRecord> getOrdenesDeTrabajoDelAlmaceneroPorId(int almaceneroId) {
		String sql="SELECT id, fecha_creacion, estado, incidencia, almacenero_id FROM OrdenTrabajo WHERE almacenero_id="+1+";";
		
		List<Object[]> lista=db.executeQueryArray(sql,new Object[0]);
		List<OrdenTrabajoRecord> listaPedidosNoRecogidos=new ArrayList<OrdenTrabajoRecord>();
		OrdenTrabajoRecord dto;
		for(Object[] d: lista) {
			dto=new OrdenTrabajoRecord();
			dto.setId(d[0].toString());
			dto.setFechaCreacion(d[1].toString());
			String estado = d[2].toString();
			switch (estado) {
			case "En recogida" -> dto.setEstado(Estado.EnRecogida);
			case "Pendiente de empaquetado" -> dto.setEstado(Estado.PendienteDeEmpaquetado);
			case "Empaquetado" ->	dto.setEstado(Estado.Empaquetado);
			default -> throw new IllegalArgumentException();
			}
			if(d[3]==null) {
				dto.setIncidencias("Sin incidencias");
			}else {
				dto.setIncidencias(d[3].toString());
			}
			
			dto.setAlmaceneroId(d[4].toString());
			
			listaPedidosNoRecogidos.add(dto);
		}
		
		return listaPedidosNoRecogidos;
	}
	
	

}
