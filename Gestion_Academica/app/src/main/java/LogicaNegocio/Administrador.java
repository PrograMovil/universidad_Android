
package LogicaNegocio;

import java.io.Serializable;


public class Administrador extends Persona implements Serializable{

    private Usuario usuario;

    public Administrador(Usuario usuario, String nombre, String cedula, String telefono, String email) {
        super(nombre, cedula, telefono, email);
        this.usuario = usuario;
    }

    public Administrador() {
        super(null, null, null, null);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    
    
}
