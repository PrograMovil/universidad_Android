
package LogicaNegocio;

import java.io.Serializable;

public class Grupo implements Serializable{
    private int id; //es lo que se manda en la query para pedir el grupo en la BD y se setea en la base
    private int numero;
    private Horario horario;
    private Profesor profesor;
    private Curso curso;
    private Ciclo ciclo;

    public Grupo() {
    }

    public Grupo(int id, int numero, Horario horario, Profesor profesor, Curso curso, Ciclo ciclo) {
        this.id = id;
        this.numero = numero;
        this.horario = horario;
        this.profesor = profesor;
        this.curso = curso;
        this.ciclo = ciclo;
    }
    
    public Grupo(int numero, Horario horario, Profesor profesor, Curso curso, Ciclo ciclo) {
        this.id = -1;
        this.numero = numero;
        this.horario = horario;
        this.profesor = profesor;
        this.curso = curso;
        this.ciclo = ciclo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
