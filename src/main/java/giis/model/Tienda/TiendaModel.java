package giis.model.Tienda;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import giis.util.Database;

public class TiendaModel {

    private Database db;

    public TiendaModel(Database db) {
        this.db = db;
    }

    public List<Categorias> getCategoriasPrincipales() {
        List<Categorias> rs = new ArrayList<>();
        String sql = "SELECT * FROM categoria WHERE id_padre IS NULL;";
        rs = db.executeQueryPojo(Categorias.class, sql);
        return rs;
    }

    public List<Categorias> getSubcategorias(String nombre) {
        List<Categorias> rs = new ArrayList<>();
        String sql = "SELECT * FROM categoria WHERE id_padre = (select id from categoria where nombre = ? );";
        rs = db.executeQueryPojo(Categorias.class, sql, nombre);
        return rs;
    }

    private String getIdCategoria(String nombre) {
        String sql = "select id from categoria where nombre = ?";
        List<Object[]> lista = db.executeQueryArray(sql, nombre);
        for (Object[] o : lista) {
            return o[0].toString();
        }
        return null;
    }

    public List<ProductosDto> getProductos(String nombre) {
        String id = getIdCategoria(nombre);

        String sql = "SELECT referencia, datosBasicos, precio, unidades, unidadesMinimas FROM Producto "
            + "WHERE id_categoria = ? ORDER BY referencia;";

        List<Object[]> lista = db.executeQueryArray(sql, id);
        List<ProductosDto> listaProductos = new ArrayList<ProductosDto>();
        ProductosDto dto;

        for (Object[] d : lista) {
            dto = new ProductosDto();
            dto.setReferencia(d[0].toString());
            dto.setDatosbasicos(d[1].toString());
            dto.setPrecioUnitario(d[2].toString());
            dto.setUnidades(Integer.parseInt(d[3].toString()));
            dto.setUnidadesMinimas(Integer.parseInt(d[4].toString()));
            listaProductos.add(dto);
        }
        return listaProductos;
    }

    public List<Categorias> CategoriasAnteriorReferencia(String referencia) {
        String sql = "SELECT * FROM categoria WHERE id_padre = "
            + "(Select id_padre from categoria where id = "
            + "(SELECT id_categoria FROM producto where referencia = ?));";
        List<Categorias> a = db.executeQueryPojo(Categorias.class, sql,
            referencia);
        return a;
    }

    public List<Categorias> CategoriasAnteriorCategoria(String categoria) {        
    	try {
        	String sqlId = "SELECT id_padre FROM categoria where nombre = ?";
            List<Object[]> lista1 = db.executeQueryArray(sqlId, categoria);
            String id_padre = lista1.get(0)[0].toString();
            
            String sqlIdPadre2 = "SELECT id_padre FROM categoria where id = ?";
            List<Object[]> lista2 = db.executeQueryArray(sqlIdPadre2, id_padre);
            Object o = lista2.get(0)[0];
             
            String sql = "SELECT * from categoria where id_padre = ?;";
            return db.executeQueryPojo(Categorias.class, sql, o.toString());
        }catch(NullPointerException e) {
        	return getCategoriasPrincipales();
        }                
    }
    
    
    
}
