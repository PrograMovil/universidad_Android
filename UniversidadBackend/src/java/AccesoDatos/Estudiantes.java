package AccesoDatos;

import LogicaNegocio.Carrera;
import LogicaNegocio.Estudiante;
import LogicaNegocio.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Estudiantes extends AccesoDatos{

    public Estudiantes() {
    }
    
    public int agregar(Estudiante c) throws Exception{
        String tableAndParams = "Estudiante(cedula,nombre,telefono,email,fechaNac,Usuario_id,Carrera_id)";
        String values = "'%s','%s','%s','%s','%s','%s','%s'";
        java.sql.Date fechaNa = new java.sql.Date(c.getFechaNac().getTimeInMillis());
        values = String.format(values,c.getCedula(),c.getNombre(),c.getTelefono(),c.getEmail(),fechaNa,c.getUsuario().getId(),new Carreras().obtenerId(c.getCarrera()));
        return super.agregar(tableAndParams, values);
    }
    
    public int eliminar(Estudiante c){
        String tableName = "Estudiante";
        String query = "cedula='%s'";
        query = String.format(query, c.getCedula());
        return super.eliminar(tableName, query);
    }
    
    public int actualizar(Estudiante c) throws SQLException{
        String tableName = "Estudiante";
        String tableParams = "nombre='%s', telefono='%s', email='%s', fechaNac='%s' where cedula='%s'";
        java.sql.Date fechaNa = new java.sql.Date(c.getFechaNac().getTimeInMillis());
        tableParams = String.format(tableParams, c.getNombre(),c.getTelefono(),c.getEmail(),fechaNa,c.getCedula());
        new Carreras().actualizar(c.getCarrera());
        new Usuarios().actualizar(c.getUsuario());
        return super.actualizar(tableName, tableParams);
    }
    
    private Estudiante toEstudiante(ResultSet rs) throws Exception {
        Estudiante obj = new Estudiante();
        obj.setCedula(rs.getString("cedula"));
        obj.setNombre(rs.getString("nombre"));
        obj.setTelefono(rs.getString("telefono"));
        obj.setEmail(rs.getString("email"));
        Calendar fechaNa = new GregorianCalendar();
        fechaNa.setTime(rs.getDate("fechaNac"));
        obj.setFechaNac(fechaNa);
        Carrera ca=new Carreras().obtenerPorId(rs.getInt("Carrera_id"));
        obj.setCarrera(ca);
        Usuario u=new Usuarios().obtener(rs.getString("Usuario_id"));
        obj.setUsuario(u);
        
        
        return obj;
    }
    
    public Estudiante obtener(String cedula) throws SQLException, Exception{
        String tableName = "Estudiante";
        String param = "cedula = '%s'";
        param = String.format(param, cedula);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toEstudiante(rs);
        } else {
            return null;
        }
    }
    
    public ArrayList<Estudiante> obtenerPorNombre(String nombre) throws Exception{
        String tableName = "Estudiante";
        String param = "nombre";
        ResultSet rs = super.obtenerLike(tableName, param, nombre);
        ArrayList<Estudiante> lista=new ArrayList();
        while (rs.next()) {
            lista.add(toEstudiante(rs));
        }
        return lista;
    }
    
    public ArrayList<Estudiante> obtenerTodos() throws Exception{
        String tableName = "Estudiante";
        ResultSet rs = super.obtenerTodo(tableName);
        ArrayList<Estudiante> lista=new ArrayList();
        while (rs.next()) {
            lista.add(toEstudiante(rs));
        }
        return lista;
    }
    
    public ArrayList<Estudiante> obtenerPorCarrera(Carrera carrera) throws Exception{
        String tableName = "Estudiante";
        String param = "Carrera_id = '%s'";
        param = String.format(param, new Carreras().obtenerId(carrera));
        ResultSet rs = super.obtener(tableName, param);
        ArrayList<Estudiante> lista=new ArrayList();
        while (rs.next()) {
            lista.add(toEstudiante(rs));
        }
        return lista;
    }
    
}
