
package AccesoDatos;

import LogicaNegocio.Matriculador;
import LogicaNegocio.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Matriculadores extends AccesoDatos{

    public Matriculadores() {
    }
    
    public int agregar(Matriculador c){
        String tableAndParams = "Matriculador(cedula,nombre,telefono,email,Usuario_id)";
        String values = "'%s','%s','%s','%s','%s'";
        values = String.format(values,c.getCedula(),c.getNombre(),c.getTelefono(),c.getEmail(),c.getUsuario().getId());
        return super.agregar(tableAndParams, values);
    }
    
    public int eliminar(Matriculador c){
        String tableName = "Matriculador";
        String query = "cedula='%s'";
        query = String.format(query, c.getCedula());
        return super.eliminar(tableName, query);
    }
    
    public int actualizar(Matriculador c){
        String tableName = "Matriculador";
        String tableParams = "nombre='%s', telefono='%s', email='%s' where id='%s'";
        tableParams = String.format(tableParams, c.getNombre(),c.getTelefono(),c.getEmail(),c.getUsuario().getId(), c.getCedula());
        new Usuarios().actualizar(c.getUsuario());
        return super.actualizar(tableName, tableParams);
    }
    
    private Matriculador toMatriculador(ResultSet rs) throws Exception {
        Matriculador obj = new Matriculador();
        obj.setCedula(rs.getString("cedula"));
        obj.setNombre(rs.getString("nombre"));
        obj.setTelefono(rs.getString("telefono"));
        obj.setEmail(rs.getString("email"));
        Usuario u=new Usuarios().obtener(rs.getString("Usuario_id"));
        obj.setUsuario(u);
        
        
        return obj;
    }
    
    public Matriculador obtener(String cedula) throws SQLException, Exception{
        String tableName = "Matriculador";
        String param = "cedula = '%s'";
        param = String.format(param, cedula);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toMatriculador(rs);
        } else {
            return null;
        }
    }
    
    public ArrayList<Matriculador> obtenerTodo() throws Exception{
        
        String tableName = "Matriculador";
        ResultSet rs = super.obtenerTodo(tableName);
        ArrayList<Matriculador> lista=new ArrayList();
        while (rs.next()) {
            lista.add(toMatriculador(rs));
        }
        return lista;
    }
    
}