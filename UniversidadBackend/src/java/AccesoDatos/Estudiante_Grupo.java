
package AccesoDatos;

import LogicaNegocio.Estudiante;
import LogicaNegocio.Grupo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Estudiante_Grupo extends AccesoDatos{
    
    
    
    public int agregar(Estudiante est, Grupo gru) throws SQLException{
        String tableAndParams = "Grupo_has_Estudiante(Grupo_id,Estudiante_cedula)";
        String values = "'%s','%s'";
        values = String.format(values,est.getCedula(),gru.getId());
        return super.agregar(tableAndParams, values);
    }
    
    public int eliminar(Estudiante est, Grupo gru) throws SQLException{
        String tableName = "Grupo_has_Estudiante";
        String query = "Grupo_id='%s' and Estudiante_cedula='%s'";
        query = String.format(query, est.getCedula(), gru.getId());
        return super.eliminar(tableName, query);
    }
    
    
    
    
    public ArrayList<Grupo> obtenerGruposDeEstudiante(Estudiante est) throws  Exception{
        String tableName = "Grupo_has_Estudiante";
        String param = "Estudiante_cedula = '%s'";
        param = String.format(param, est.getCedula());
        ResultSet rs = super.obtener(tableName, param);
        ArrayList<Grupo> lista=new ArrayList();
        while (rs.next()) {
            int idGrupo=rs.getInt("Grupo_id");
            lista.add(new Grupos().obtenerPorId(idGrupo));
        }
        return lista;
    }
    
    
    public ArrayList<Estudiante> obtenerEstudiantesDeGrupo(Grupo gru) throws  Exception{
        String tableName = "Grupo_has_Estudiante";
        String param = "Grupo_id = '%s'";
        param = String.format(param, gru.getId());
        ResultSet rs = super.obtener(tableName, param);
        ArrayList<Estudiante> lista=new ArrayList();
        while (rs.next()) {
            lista.add(new Estudiantes().obtener(rs.getString("Estudiante_cedula")));
        }
        return lista;
    }
    
    
    
    
    
}
