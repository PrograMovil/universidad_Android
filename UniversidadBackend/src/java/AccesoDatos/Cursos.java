
package AccesoDatos;

import LogicaNegocio.Carrera;
import LogicaNegocio.Curso;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cursos extends AccesoDatos {

    public Cursos(Database db) {
        super(db);
    }
    
    
    
    public int agregar(Curso c) throws SQLException{
        String tableAndParams = "Curso(codigo,nombre,creditos,horas_semanales,nivel,ciclo,Carrera_id)";
        String values = "'%s','%s','%s','%s','%s','%s','%s'";
        values = String.format(values,c.getCodigo(),c.getNombre(),c.getCreditos(),c.getHorasSemanales(),c.getNivel(),c.getCiclo(),new Carreras(db).obtenerId(c.getCarrera()));
        return super.agregar(tableAndParams, values);
    }
    
    public int eliminar(Curso c){
        String tableName = "curso";
        String query = "codigo='%s'";
        query = String.format(query, c.getCodigo());
        return super.eliminar(tableName, query);
    }
    
    public int actualizar(Curso c) throws SQLException{
        String tableName = "curso";
        String tableParams = "nombre='%s', creditos='%s', horas_semanales='%s', nivel='%s', ciclo='%s' where codigo='%s'";
        tableParams = String.format(tableParams,c.getNombre(),c.getCreditos(),c.getHorasSemanales(),c.getNivel(),c.getCiclo(), c.getCodigo());
        new Carreras(db).actualizar(c.getCarrera());
        return super.actualizar(tableName, tableParams);
    }
    
    private Curso toCurso(ResultSet rs) throws Exception {
        Curso obj = new Curso();
        obj.setCodigo(rs.getString("codigo"));
        obj.setNombre(rs.getString("nombre"));
        obj.setCreditos(rs.getInt("creditos"));
        obj.setHorasSemanales(rs.getInt("horas_semanales"));
        obj.setNivel(rs.getString("nivel"));
        obj.setCiclo(rs.getString("ciclo"));
        obj.setCarrera(new Carreras(db).obtenerPorId(rs.getInt("Carrera_id")));
        
        
        return obj;
    }
    
    public Curso obtener(String codigo) throws SQLException, Exception{
        String tableName = "Curso";
        String param = "codigo = '%s'";
        param = String.format(param, codigo);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toCurso(rs);
        } else {
            return null;
        }
    }
    
    public Curso obtenerPorId(int id) throws Exception{
        String tableName = "Curso";
        String param = "id = '%s'";
        param = String.format(param, id);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toCurso(rs);
        } else {
            return null;
        }
    }
    
    
    public int obtenerId(Curso c) throws SQLException{
        
        //obtener id de Curso manualmente:
        String tablename="Curso";
        String param2 = "codigo = '%s'";
        param2 = String.format(param2, c.getCodigo());
        ResultSet rs2 = super.obtenerId(tablename, param2);
        int idCurso=0;
        if(rs2.next())
            idCurso=rs2.getInt("id");
        //fin de obtener id de Curso desde BD
        return idCurso;
    }
    
    
    public ArrayList<Curso> obtenerCursosPorNombre(String nombre) throws Exception{
        String tableName = "Curso";
        String param = "nombre";
        ResultSet rs = super.obtenerLike(tableName, param, nombre);
        ArrayList<Curso> lista=new ArrayList();
        while (rs.next()) {
            lista.add(toCurso(rs));
        }
        return lista;
    }
    
    public ArrayList<Curso> obtenerCursosPorCarrera(Carrera carrera) throws Exception{
        String tableName = "Curso";
        String param = "Carrera_id = '%s'";
        
        param = String.format(param, new Carreras(db).obtenerId(carrera));
        ResultSet rs = super.obtener(tableName, param);
        ArrayList<Curso> lista=new ArrayList();
        while (rs.next()) {
            lista.add(toCurso(rs));
        }
        return lista;
    }
    
    public ArrayList<Curso> obtenerTodo() throws Exception{
        
        String tableName = "Curso";
        ResultSet rs = super.obtenerTodo(tableName);
        ArrayList<Curso> lista=new ArrayList();
        while (rs.next()) {
            lista.add(toCurso(rs));
        }
        return lista;
    }
    
    
}
