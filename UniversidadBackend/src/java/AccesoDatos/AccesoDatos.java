
package AccesoDatos;

import java.sql.ResultSet;


public class AccesoDatos {
    static Database db;

    public AccesoDatos(Database DB) {
        this.db=DB;
    }
    
    public int agregar(String tableAndParams,String values){
        //db = new Database();
        String sql = "insert into "+ tableAndParams + " values(" + values + ")";
        int count = db.executeUpdate(sql);
        //db.desconectar();
        return count; //0 = fallo o registro repetido, 1 = exitoso
    }
    
    public int eliminar(String tableName, String query){
        //db = new Database();
        String sql = "delete from " + tableName + " where " + query;
        int count = db.executeUpdate(sql);
        //db.desconectar();
        return count;
    }
    
    public int actualizar(String tableName, String tableParams){
        //db = new Database();
        String sql = "update " + tableName + " set " + tableParams;
        int count = db.executeUpdate(sql);
        //db.desconectar();
        return count;
    }
    
    public ResultSet obtener(String tableName, String param){
        //db = new Database();
        String sql = "select * from " + tableName + " where " + param;
        ResultSet rs = db.executeQuery(sql);
        //db.desconectar();
        return rs;        
    }
    
    public ResultSet obtenerId(String tableName, String param){
        //db = new Database();
        String sql = "select id from " + tableName + " where " + param;
        ResultSet rs = db.executeQuery(sql);
        //db.desconectar();
        return rs;        
    }
    
    public ResultSet obtenerTodo(String tableName){
        //db = new Database();
        String sql = "select * from " + tableName;
        ResultSet rs = db.executeQuery(sql);
        //db.desconectar();
        return rs;        
    }
    
    public ResultSet obtenerLike(String tablename, String columna, String busqueda){
        //db = new Database();
        String sql="select * from " + tablename + " where " + columna + " like '%%%" + busqueda+"%%%'";
        ResultSet rs = db.executeQuery(sql);
        //db.desconectar();
        return rs;
    }

    
}
