
package LogicaNegocio;

import java.io.Serializable;


public class Profesor extends Persona implements Serializable{
    
    private Usuario usuario;

    public Profesor() {
        super(null, null, null, null);
    }

    public Profesor(Usuario usuario, String nombre, String cedula, String telefono, String email) {
        super(nombre, cedula, telefono, email);
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
        
}
