
package AccesoDatos;

import LogicaNegocio.Curso;
import LogicaNegocio.Estudiante;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Estudiante_Curso extends AccesoDatos{
    
    
    public int agregar(Estudiante est, Curso cur) throws SQLException{
        String tableAndParams = "Estudiante_has_Curso(Estudiante_cedula,Curso_id)";
        String values = "'%s','%s'";
        values = String.format(values,est.getCedula(),new Cursos().obtenerId(cur));
        return super.agregar(tableAndParams, values);
    }
    
    public int eliminar(Estudiante est, Curso cur) throws SQLException{
        String tableName = "Estudiante_has_Curso";
        String query = "Estudiante_cedula='%s' and Curso_id='%s'";
        query = String.format(query, est.getCedula(), new Cursos().obtenerId(cur));
        return super.eliminar(tableName, query);
    }
    
    
    
    
    public ArrayList<Curso> obtenerCursosDeEstudiante(Estudiante est) throws  Exception{
        String tableName = "Estudiante_has_Curso";
        String param = "Estudiante_cedula = '%s'";
        param = String.format(param, est.getCedula());
        ResultSet rs = super.obtener(tableName, param);
        ArrayList<Curso> lista=new ArrayList();
        while (rs.next()) {
            int idCurso=rs.getInt("Curso_id");
            lista.add(new Cursos().obtenerPorId(idCurso));
        }
        return lista;
    }
    
    
    public ArrayList<Estudiante> obtenerEstudiantesDeCurso(Curso cur) throws  Exception{
        String tableName = "Estudiante_has_Curso";
        String param = "Curso_id = '%s'";
        param = String.format(param, new Cursos().obtenerId(cur));
        ResultSet rs = super.obtener(tableName, param);
        ArrayList<Estudiante> lista=new ArrayList();
        while (rs.next()) {
            lista.add(new Estudiantes().obtener(rs.getString("Estudiante_cedula")));
        }
        return lista;
    }
    
    
    
}
