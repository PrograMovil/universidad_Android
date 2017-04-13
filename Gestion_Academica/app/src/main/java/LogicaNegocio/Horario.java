
package LogicaNegocio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Horario implements Serializable{
    private String dias;
    private String horaInicial;
    private String horaFinal;

    public Horario() {
    }

    public Horario(String dias, String horaInicial, String horaFinal) {
        this.dias = dias;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
    }
    
    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    @Override
    public String toString() {
        return " Dias: " + dias + ", Hora inicial: " + horaInicial  + ", Hora final: " + horaFinal;
    }
    
}
