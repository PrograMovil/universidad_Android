
package AccesoDatos;

import LogicaNegocio.Usuario;
import java.sql.ResultSet;

public class Usuarios extends AccesoDatos{

    public Usuarios(Database db) {
        super(db);
    }

    public int agregar(Usuario c){
        String tableAndParams = "Usuario(idUsuario,clave,tipo)";
        String values = "'%s','%s','%s'";
        values = String.format(values,c.getId(),c.getClave(),c.getTipo());
        return super.agregar(tableAndParams, values);
    }
    
    public int eliminar(Usuario c){
        String tableName = "Usuario";
        String query = "idUsuario='%s'";
        query = String.format(query, c.getId());
        return super.eliminar(tableName, query);
    }
    
    public int actualizar(Usuario c){
        String tableName = "Usuario";
        String tableParams = "clave='%s', tipo='%s' where idUsuario='%s'";
        tableParams = String.format(tableParams, c.getClave(),c.getTipo(),c.getId());
        return super.actualizar(tableName, tableParams);
    }
    
    private Usuario toUsuario(ResultSet rs) throws Exception {
        Usuario obj = new Usuario();
        obj.setId(rs.getString("idUsuario"));
        obj.setClave(rs.getString("clave"));
        obj.setTipo(rs.getInt("tipo"));
        
        
        return obj;
    }
    
    public Usuario obtener(String idUsuario) throws  Exception{
        String tableName = "Usuario";
        String param = "idUsuario = '%s'";
        param = String.format(param, idUsuario);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toUsuario(rs);
        } else {
            return null;
        }
    }
    
    
    public int verificaUsuario(String user, String pass) throws Exception{
        String tableName = "Usuario";
        String param = "idUsuario = '%s' and clave = '%s'";
        param = String.format(param, user, pass);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toUsuario(rs).getTipo();
        } else {
            return 0;
        }
        
    }
    
    
    
}