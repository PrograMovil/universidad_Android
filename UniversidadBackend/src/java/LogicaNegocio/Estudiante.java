
package LogicaNegocio;
import java.io.Serializable;
import java.util.Calendar;

public class Estudiante extends Persona implements Serializable{
    private Calendar fechaNac;
    private Carrera carrera;
    private Usuario usuario;

    public Estudiante(Calendar fechaNac, Carrera carrera, Usuario usuario, String nombre, String cedula, String telefono, String email) {
        super(nombre, cedula, telefono, email);
        this.fechaNac = fechaNac;
        this.carrera = carrera;
        this.usuario = usuario;
    }
    
    public Estudiante() {
        super(null, null, null, null);
    }

    public Calendar getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Calendar fechaNac) {
        this.fechaNac = fechaNac;
    }
    

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
