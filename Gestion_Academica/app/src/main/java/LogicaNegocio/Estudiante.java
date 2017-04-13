
package LogicaNegocio;
import java.io.Serializable;
import java.util.ArrayList;
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

    //para pruebas
    public Estudiante(String cedula, String nombre){
        super(nombre,cedula,"889900","@g.c");
        this.fechaNac = null;
        this.carrera = null;
        this.usuario = null;
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

    private static int lastContactId = 0;

    public static ArrayList<Estudiante> createContactsList(int numContacts) {
        ArrayList<Estudiante> contacts = new ArrayList<Estudiante>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Estudiante(" " + ++lastContactId, "Estudiante"));
        }

        return contacts;
    }


}
