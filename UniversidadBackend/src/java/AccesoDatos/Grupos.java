
package AccesoDatos;

import LogicaNegocio.Curso;
import LogicaNegocio.Grupo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Grupos extends AccesoDatos {

    public Grupos(Database db) {
        super(db);
    }
    
    
    public int agregar(Grupo c) throws SQLException, Exception{
        String query = "'%s','%s','%s','%s','%s','%s'";
        String tableAndParams="Grupo(numero,Horario_id,Curso_id,Profesor_cedula,Ciclo_anio,Ciclo_numero)";
        
        Horarios horario=new Horarios(db);
        horario.agregar(c.getHorario());
        if(horario.obtener(c.getHorario())==null)
            horario.agregar(c.getHorario());
        Ciclos ciclo=new Ciclos(db);
        if(ciclo.obtener(c.getCiclo())==null)
            ciclo.agregar(c.getCiclo());
        
        query = String.format(query,c.getNumero(),horario.obtenerId(c.getHorario()),new Cursos(db).obtenerId(c.getCurso()),c.getProfesor().getCedula(),c.getCiclo().getAnio(),c.getCiclo().getNumero());
        return super.agregar(tableAndParams, query);
    }
    
    public int eliminar(Grupo c) throws SQLException{
        String tableName = "Grupo";
        String query = "id = '%s'";
        query = String.format(query, c.getId());
        return super.eliminar(tableName, query);
    }
    
    public int actualizar(Grupo c) throws SQLException, Exception{
        String tableName = "Grupo";
        String tableParams = "numero = '%s',Horario_id='%s', Curso_id='%s', Profesor_cedula='%s', Ciclo_anio='%s', Ciclo_numero='%s' where id='%s'";
        
        Horarios horarios=new Horarios(db);
        if(horarios.obtener(c.getHorario())==null)
            horarios.agregar(c.getHorario());
        Ciclos ciclo=new Ciclos(db);
        if(ciclo.obtener(c.getCiclo())==null)
            ciclo.agregar(c.getCiclo());
        
        tableParams = String.format(tableParams, c.getNumero(), new Horarios(db).obtenerId(c.getHorario()), new Cursos(db).obtenerId(c.getCurso()), c.getProfesor().getCedula(),c.getCiclo().getAnio(),c.getCiclo().getNumero(), c.getId());
        return super.actualizar(tableName, tableParams);
    }
    
    private Grupo toGrupo(ResultSet rs) throws Exception {
        Grupo obj = new Grupo();
        
        obj.setId(rs.getInt("id"));
        obj.setNumero(rs.getInt("numero"));
        obj.setHorario(new Horarios(db).obtenerPorId(rs.getInt("Horario_id")));
        obj.setCurso(new Cursos(db).obtenerPorId(rs.getInt("id")));
        obj.setProfesor(new Profesores(db).obtener(rs.getString("Profesor_cedula")));
        int anio=rs.getInt("Ciclo_anio");
            String num=rs.getString("Ciclo_numero");
            obj.setCiclo(new Ciclos(db).obtenerPorAnioYNumero(anio,num));
        return obj;
    }
    
    public Grupo obtener(Grupo c) throws Exception{
        String tableName = "Grupo";
        String param = "id = '%s'";
        param = String.format(param, c.getId());
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toGrupo(rs);
        } else {
            return null;
        }
    }
    
    public Grupo obtenerPorId(int id) throws Exception{
        String tableName = "Grupo";
        String param = "id = '%s'";
        param = String.format(param, id);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toGrupo(rs);
        } else {
            return null;
        }
    }
    
    
    public int obtenerId(Grupo c) throws SQLException{
        
        String tablename="Grupo";
        String param2 = "numero = '%s' and Horario_id = '%s' and Curso_id = '%s' and Profesor_cedula = '%s' and Ciclo_anio='%s' and Ciclo_numero='%s'";
        param2 = String.format(param2, c.getNumero(), new Horarios(db).obtenerId(c.getHorario()), new Cursos(db).obtenerId(c.getCurso()), c.getProfesor().getCedula(),c.getCiclo().getAnio(),c.getCiclo().getNumero());
        ResultSet rs2 = super.obtenerId(tablename, param2);
        int id=0;
        if (rs2.next()) {
            id=rs2.getInt("id");
        }
        //fin de obtener id de Gupo desde BD
        return id;
    }
    
    
    public ArrayList<Grupo> gruposPorProfesor(String cedula) throws Exception{
        
        String tableName = "Grupo";
        String param = "Profesor_cedula = '%s'";
        param = String.format(param, cedula);
        ResultSet rs = super.obtener(tableName, param);
        ArrayList<Grupo> lista=new ArrayList<>();
        while (rs.next()) {
            Grupo obj = new Grupo();
            obj.setId(rs.getInt("id"));
            obj.setNumero(rs.getInt("numero"));
            obj.setHorario(new Horarios(db).obtenerPorId(rs.getInt("Horario_id")));
            obj.setCurso(new Cursos(db).obtenerPorId(rs.getInt("id")));
            obj.setProfesor(new Profesores(db).obtener(rs.getString("Profesor_cedula")));
            int anio=rs.getInt("Ciclo_anio");
            String num=rs.getString("Ciclo_numero");
            obj.setCiclo(new Ciclos(db).obtenerPorAnioYNumero(anio,num));
            lista.add(obj);
            
        }
        return lista;
    }

    public ArrayList<Grupo> gruposPorCurso(Curso curso) throws Exception{
        
        String tableName = "Grupo";
        String param = "Curso_id = '%s'";
        param = String.format(param, new Cursos(db).obtenerId(curso));
        ResultSet rs = super.obtener(tableName, param);
        ArrayList<Grupo> lista=new ArrayList<>();
        while (rs.next()) {
            Grupo obj = new Grupo();
            obj.setId(rs.getInt("id"));
            obj.setNumero(rs.getInt("numero"));
            obj.setHorario(new Horarios(db).obtenerPorId(rs.getInt("Horario_id")));
            obj.setCurso(new Cursos(db).obtenerPorId(rs.getInt("Curso_id")));
            obj.setProfesor(new Profesores(db).obtener(rs.getString("Profesor_cedula")));
            int anio=rs.getInt("Ciclo_anio");
            String num=rs.getString("Ciclo_numero");
            obj.setCiclo(new Ciclos(db).obtenerPorAnioYNumero(anio,num));
            lista.add(obj);
            
        }
        return lista;
    }
    
    
}
