
package AccesoDatos;

import LogicaNegocio.Ciclo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Ciclos extends AccesoDatos {
    
    public int agregar(Ciclo c){
        String tableAndParams = "Ciclo(anio,numero,fecha_Inicio,fecha_Finalizacion)";
        String values = "'%s','%s','%s','%s'";
        
        if (c.getNumero().equals("I")) {
            c.setFechaInicio("15 de Febrero");
            c.setFechaFinalizacion("20 de Junio");
        } else if (c.getNumero().equals("II")) {
            c.setFechaInicio("20 de Julio");
            c.setFechaFinalizacion("22 de Noviembre");
        }
        
        values = String.format(values,c.getAnio(),c.getNumero(),c.getFechaInicio(),c.getFechaFinalizacion());
        return super.agregar(tableAndParams, values);
    }
    
    public int eliminar(Ciclo c){
        String tableName = "Ciclo";
        String query = "anio='%s' and numero='%s'";
        query = String.format(query, c.getAnio(),c.getNumero());
        return super.eliminar(tableName, query);
    }
    
    //no se usa porque las fechas son fijas
//    public int actualizar(Ciclo c){//actualizar fecha de inicio y fecha final
//        String tableName = "ciclo";
//        String tableParams = "fecha_Inicio='%s', fecha_Finalizacion='%s' where anio='%s' and numero='%s'";
//        
//        tableParams = String.format(tableParams, c.getFechaInicio(), c.getFechaFinalizacion(), c.getAnio(), c.getNumero());
//        return super.actualizar(tableName, tableParams);
//    }
    
    private Ciclo toCiclo(ResultSet rs) throws Exception {
        Ciclo obj = new Ciclo();
        obj.setAnio(rs.getInt("anio"));
        obj.setNumero(rs.getString("numero"));
        obj.setFechaInicio(rs.getString("fecha_Inicio"));
        obj.setFechaFinalizacion(rs.getString("fecha_Finalizacion"));
        return obj;
    }
    
    public Ciclo obtenerPorAnioYNumero(int anio, String numero) throws SQLException, Exception{
        String tableName = "ciclo";
        String param = "anio = '%s' and numero= '%s'";
        param = String.format(param, anio, numero);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toCiclo(rs);
        } else {
            return null;
        }
    }
    
    
    public Ciclo obtener(Ciclo ciclo) throws SQLException, Exception{
        String tableName = "ciclo";
        String param = "anio = '%s' and numero= '%s'";
        param = String.format(param, ciclo.getAnio(), ciclo.getNumero());
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toCiclo(rs);
        } else {
            return null;
        }
    }
    
    public Ciclo obtener(int anio) throws SQLException, Exception{
        String tableName = "ciclo";
        String param = "anio = '%s'";
        param = String.format(param, anio);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toCiclo(rs);
        } else {
            return null;
        }
    }
    
    public ArrayList<Ciclo> obtenerTodo() throws Exception{
        
        String tableName = "Ciclo";
        ResultSet rs = super.obtenerTodo(tableName);
        ArrayList<Ciclo> lista=new ArrayList();
        while (rs.next()) {
            lista.add(toCiclo(rs));
        }
        return lista;
    }
    
    public int cambiarCicloActivo(int anio, String numero) throws Exception{
        
        String tableName = "CicloActivo";
        String tableParams = "anio='%s', numero='%s'";
        
        tableParams = String.format(tableParams, anio, numero);
        String sql = "update " + tableName + " set " + tableParams + " where id='1'";
        int count = db.executeUpdate(sql);
        return count;
        
    }
    
    public Ciclo obtenerCicloActivo() throws Exception{
        
        String tableName = "CicloActivo";
        String sql = "select * from " + tableName ;
        ResultSet rs = db.executeQuery(sql);
        if (rs.next()) {
            Ciclo obj = obtenerPorAnioYNumero(rs.getInt("anio"), rs.getString("numero"));
            return obj;
        } else {
            return null;
        } 
        
    }
    
}
