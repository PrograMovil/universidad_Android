package LogicaNegocio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Ciclo implements Serializable {

    private int anio;
    private String numero;
    private String fechaInicio;
    private String fechaFinalizacion;

    public Ciclo(int anio, String numero) {
        this.anio = anio;
        this.numero = numero;
        if (numero == "I") {
            this.fechaInicio = "15 de Febrero";
            this.fechaFinalizacion = "20 de Junio";
        } else if (numero == "II") {
            this.fechaInicio = "20 de Julio";
            this.fechaFinalizacion = "22 de Noviembre";
        }
    }

    public Ciclo() {
        this.anio = 0;
        this.numero = "";
        this.fechaInicio = "";
        this.fechaFinalizacion = "";
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    @Override
    public String toString() {
        return "Ciclo: "+numero+" del año: "+anio+"\nInicia: "+fechaInicio+", finaliza: "+fechaFinalizacion;
    }

    

}
