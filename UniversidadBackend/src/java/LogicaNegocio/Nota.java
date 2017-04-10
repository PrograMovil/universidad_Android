
package LogicaNegocio;

import java.io.Serializable;


public class Nota implements Serializable{
    private float calificacion;
    private Estudiante estudiante;
    private Curso curso;

    public Nota(float calificacion, Estudiante estudiante, Curso curso) {
        this.calificacion = calificacion;
        this.estudiante = estudiante;
        this.curso = curso;
    }

    public Nota() {
    }
    

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    
    
    
}
