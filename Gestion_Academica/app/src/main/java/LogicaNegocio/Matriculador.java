
package LogicaNegocio;

import java.io.Serializable;


public class Matriculador extends Persona implements Serializable{

    private Usuario usuario;

    public Matriculador(Usuario usuario, String nombre, String cedula, String telefono, String email) {
        super(nombre, cedula, telefono, email);
        this.usuario = usuario;
    }

    public Matriculador() {
        super(null, null, null, null);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    
    
}
