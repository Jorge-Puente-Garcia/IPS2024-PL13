package giis.model.Tienda;

import java.util.ArrayList;
import java.util.List;

import giis.util.Database;

public class TiendaModel {
	
	private Database db;
	
	public TiendaModel(Database db) {
		this.db = db;
	}

	public List<ProductosDto> getProductos() {
		String sql="SELECT referencia, datosBasicos from Producto order by referencia;";
		
		List<Object[]> lista = db.executeQueryArray(sql,new Object[0]);
		List<ProductosDto> listaProductos = new ArrayList<ProductosDto>();
		ProductosDto dto;
		
		for(Object[] d: lista) {
			dto=new ProductosDto();
			dto.setReferencia(d[0].toString());
			dto.setDatosbasicos(d[1].toString());
			
			listaProductos.add(dto);
		}
		return listaProductos;
	}
}
