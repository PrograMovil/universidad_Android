
package AccesoDatos;

import java.sql.ResultSet;


public class AccesoDatos {
    static Database db;

    public AccesoDatos() {
        db = new Database();
    }
    
    public int agregar(String tableAndParams,String values){
        String sql = "insert into "+ tableAndParams + " values(" + values + ")";
        int count = db.executeUpdate(sql);
        return count; //0 = fallo o registro repetido, 1 = exitoso
    }
    
    public int eliminar(String tableName, String query){
        String sql = "delete from " + tableName + " where " + query;
        int count = db.executeUpdate(sql);
        return count;
    }
    
    public int actualizar(String tableName, String tableParams){
        String sql = "update " + tableName + " set " + tableParams;
        int count = db.executeUpdate(sql);
        return count;
    }
    
    public ResultSet obtener(String tableName, String param){
        String sql = "select * from " + tableName + " where " + param;
        ResultSet rs = db.executeQuery(sql);
        return rs;        
    }
    
    public ResultSet obtenerTodo(String tableName){
        String sql = "select * from " + tableName;
        ResultSet rs = db.executeQuery(sql);
        return rs;        
    }
    
    public ResultSet obtenerLike(String tablename, String columna, String busqueda){
        String sql="select * from " + tablename + " where " + columna + " like '%%%" + busqueda+"%%%'";
        ResultSet rs = db.executeQuery(sql);
        return rs;
    }

    
}
