
package AccesoDatos;

import LogicaNegocio.Curso;
import LogicaNegocio.Estudiante;
import LogicaNegocio.Nota;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Notas  extends AccesoDatos {

    public Notas(Database db) {
        super(db);
    }
    
    
    
    public int agregar(Nota c) throws SQLException{
        String tableAndParams = "Nota(calificacion,Estudiante_cedula,Curso_id)";
        String values = "'%s','%s','%s'";
       
        
        values = String.format(values,c.getCalificacion(),c.getEstudiante().getCedula(),new Cursos(db).obtenerId(c.getCurso()));
        return super.agregar(tableAndParams, values);
    }
    
    public int eliminar(Nota c) throws SQLException{
        String tablename = "Nota";
        String query = "id='%s'";
        query = String.format(query, obtenerId(c));
        return super.eliminar(tablename, query);
    }
    
    public int actualizar(Nota c) throws SQLException{
        String tableName = "Nota";
        String tableParams = "calificacion='%s', Estudiante_cedula = '%s', Curso_id = '%s' where id='%s'";
        
        tableParams = String.format(tableParams, c.getCalificacion(), c.getEstudiante().getCedula(), new Cursos(db).obtenerId(c.getCurso()), obtenerId(c));
        return super.actualizar(tableName, tableParams);
    }
    
    private Nota toNota(ResultSet rs) throws Exception {
        Nota obj = new Nota();
        
        obj.setCalificacion(rs.getFloat("calificacion"));
        obj.setEstudiante(new Estudiantes(db).obtener(rs.getString("Estudiante_cedula")));
        obj.setCurso(new Cursos(db).obtener(rs.getString("Curso_id")));
        return obj;
    }
    
//    public Nota obtener(float calificacion, String Estudiante_cedula, String Codigo_Curso) throws SQLException, Exception{
//        String tableName = "Nota";
//        String param = "calificacion = '%s' and Estudiante_cedula= '%s' and Curso_id= '%s'";
//        
//        
//        param = String.format(param, calificacion,Estudiante_cedula);
//        ResultSet rs = super.obtener(tableName, param);
//        if (rs.next()) {
//            return toCiclo(rs);
//        } else {
//            return null;
//        }
//    }
    
    
    public int obtenerId(Nota c) throws SQLException{
        //obtener id de Curso manualmente:
        String tablename="Nota";
        String param2 = "calificacion = '%s', Estudiante_cedula = '%s', Curso_id = '%s'";
        param2 = String.format(param2, c.getCalificacion(),c.getEstudiante().getCedula(),new Cursos(db).obtenerId(c.getCurso()));
        ResultSet rs2 = super.obtenerId(tablename, param2);
        int idNota=0;
        if(rs2.next())
            idNota=rs2.getInt("id");
        //fin de obtener id de Curso desde BD
        return idNota;
    }
    
}

