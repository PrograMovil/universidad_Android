
package AccesoDatos;

import LogicaNegocio.Administrador;
import LogicaNegocio.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Administradores extends AccesoDatos{

    public Administradores() {
    }
    
    public int agregar(Administrador c){
        String tableAndParams = "Administrador(cedula,nombre,telefono,email,Usuario_id)";
        String values = "'%s','%s','%s','%s','%s'";
        values = String.format(values,c.getCedula(),c.getNombre(),c.getTelefono(),c.getEmail(),c.getUsuario().getId());
        return super.agregar(tableAndParams, values);
    }
    
    public int eliminar(Administrador c){
        String tableName = "Administrador";
        String query = "cedula='%s'";
        query = String.format(query, c.getCedula());
        return super.eliminar(tableName, query);
    }
    
    public int actualizar(Administrador c){
        String tableName = "Administrador";
        String tableParams = "nombre='%s', telefono='%s', email='%s' where id='%s'";
        tableParams = String.format(tableParams, c.getNombre(),c.getTelefono(),c.getEmail(), c.getCedula());
        new Usuarios().actualizar(c.getUsuario());
        return super.actualizar(tableName, tableParams);
    }
    
    private Administrador toAdministrador(ResultSet rs) throws Exception {
        Administrador obj = new Administrador();
        obj.setCedula(rs.getString("cedula"));
        obj.setNombre(rs.getString("nombre"));
        obj.setTelefono(rs.getString("telefono"));
        obj.setEmail(rs.getString("email"));
        Usuario u=new Usuarios().obtener(rs.getString("Usuario_id"));
        obj.setUsuario(u);
        
        
        return obj;
    }
    
    public Administrador obtener(String cedula) throws SQLException, Exception{
        String tableName = "Administrador";
        String param = "cedula = '%s'";
        param = String.format(param, cedula);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toAdministrador(rs);
        } else {
            return null;
        }
    }
    
    public ArrayList<Administrador> obtenerTodo() throws Exception{
        
        String tableName = "Administrador";
        ResultSet rs = super.obtenerTodo(tableName);
        ArrayList<Administrador> lista=new ArrayList();
        while (rs.next()) {
            lista.add(toAdministrador(rs));
        }
        return lista;
    }
    
}