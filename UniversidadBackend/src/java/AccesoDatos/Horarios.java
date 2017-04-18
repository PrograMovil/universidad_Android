package AccesoDatos;

import LogicaNegocio.Horario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Horarios extends AccesoDatos {

    public Horarios(Database db) {
        super(db);
    }

    
    public int agregar(Horario c) {
        String tableAndParams = "Horario(dias,horaInicial,horaFinal)";
        String values = "'%s','%s','%s'";
        values = String.format(values, c.getDias(), c.getHoraInicial(), c.getHoraFinal());
        return super.agregar(tableAndParams, values);
    }

    public int eliminar(Horario c) throws SQLException {
        String tableName = "Horario";
        String query = "dias='%s' and horaInicial='%s' and horaFinal='%s'";

        query = String.format(query, c.getDias(), c.getHoraInicial(), c.getHoraFinal());
        return super.eliminar(tableName, query);
    }

    public int actualizar(Horario c) throws SQLException {
        String tableName = "Horario";
        String tableParams = "dias='%s', horaInicial='%s', horaFinal='%s' where id='%s'";
        int idHorario = obtenerId(c);
        tableParams = String.format(tableParams, c.getDias(), c.getHoraInicial(), c.getHoraFinal(), idHorario);
        return super.actualizar(tableName, tableParams);
    }

    private Horario toHorario(ResultSet rs) throws Exception {
        Horario obj = new Horario();
        obj.setDias(rs.getString("dias"));
        obj.setHoraInicial(rs.getString("horaInicial"));
        obj.setHoraFinal(rs.getString("horaFinal"));
        return obj;
    }

    public Horario obtener(String dias, Date horaInicial, Date horaFinal) throws SQLException, Exception {
        String tableName = "Horario";
        String param = "dias = '%s' and horaInicial = '%s' and horaFinal = '%s'";
        param = String.format(param, dias, horaInicial, horaFinal);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toHorario(rs);
        } else {
            return null;
        }
    }
    
    public Horario obtener(Horario horario) throws SQLException, Exception {
        String tableName = "Horario";
        String param = "dias = '%s' and horaInicial = '%s' and horaFinal = '%s'";
        param = String.format(param, horario.getDias(), horario.getHoraInicial(), horario.getHoraFinal());
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toHorario(rs);
        } else {
            return null;
        }
    }

    public int obtenerId(Horario h) throws SQLException {
        
        String tablename="Horario";
        String param2 = "dias = '%s' AND horaInicial = '%s' AND HoraFinal = '%s'";
        param2 = String.format(param2, h.getDias(), h.getHoraInicial(), h.getHoraFinal());
        ResultSet rs2 = super.obtenerId(tablename, param2);
        int idHorario = 0;
        if (rs2.next()) {
            idHorario = rs2.getInt("id");
        }
        //fin de obtener id de horario desde BD
        return idHorario;
    }

    public Horario obtenerPorId(int id) throws Exception {
        String tableName = "Horario";
        String param = "id = '%s'";
        param = String.format(param, id);
        ResultSet rs = super.obtener(tableName, param);
        if (rs.next()) {
            return toHorario(rs);
        } else {
            return null;
        }
    }
    
    public ArrayList<Horario> obtenerTodo() throws Exception{
        
        String tableName = "Horario";
        ResultSet rs = super.obtenerTodo(tableName);
        ArrayList<Horario> lista=new ArrayList();
        while (rs.next()) {
            lista.add(toHorario(rs));
        }
        return lista;
    }

}
