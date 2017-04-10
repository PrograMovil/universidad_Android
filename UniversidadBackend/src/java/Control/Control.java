
package Control;

import AccesoDatos.*;
import LogicaNegocio.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Control {
    Carreras carreras;
    Ciclos ciclos;
    Cursos cursos;
    Estudiantes estudiantes;
    Grupos grupos;
    Horarios horarios;
    Matriculadores matriculadores;
    Notas notas;
    Profesores profesores;
    Usuarios usuarios;
    Administradores administradores;
    Estudiante_Curso estudiante_curso;
    Estudiante_Grupo estudiante_grupo;

    

    public Control() {
        carreras=new Carreras();
        ciclos=new Ciclos();
        cursos=new Cursos();
        estudiantes=new Estudiantes();
        grupos=new Grupos();
        horarios=new Horarios();
        matriculadores=new Matriculadores();
        notas=new Notas();
        profesores=new Profesores();
        usuarios=new Usuarios();
        administradores=new Administradores();
        estudiante_grupo=new Estudiante_Grupo();
        estudiante_curso=new Estudiante_Curso();
    }
    
    
    
    public int verificaUsuario(String username, String clave){
        try {
            return usuarios.verificaUsuario(username, clave);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Cursos">
    /**
     *
     * @param nombre
     * Nombre del curso
     * @return
     * devuelve la lista de cursos que tengan ese nombre
     * En caso de no encontrar devuelve la lista vacia
     * En caso de excepcion devuelve null
     */
    public ArrayList<Curso> getCursoPorNombre(String nombre){
        try {
            return cursos.obtenerCursosPorNombre(nombre);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     *
     * @param carrera
     * Objeto Carrera, utilizado para buscar el id de la misma en la base de datos (compara todos los atributos)
     * @return
     * devuelve la lista de cursos que pertenecen a esa carrera
     */
    public ArrayList<Curso> getCursoPorCarrera(Carrera carrera){
        try {
            return cursos.obtenerCursosPorCarrera(carrera);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Curso> obtenerTodosLosCursos(){
        try {
            return cursos.obtenerTodo();
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public int addCurso(Curso ca){
        try {
            return this.cursos.agregar(ca);
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int deleteCurso(Curso ca){
        return this.cursos.eliminar(ca);
    }
    
    public int updateCurso(Curso ca){
        try {
            return this.cursos.actualizar(ca);
        } catch (SQLException ex) {
            System.err.println("Error al actualizar curso");
        }
        return 0;
    }
    
    /**
     *
     * @param codigo
     * Obtener un solo curso por codigo (atributo unico en la BD)
     * @return
     * Devuelve un solo curso
     * @throws Exception
     */
    public Curso getCurso(String codigo) throws Exception{
        return this.cursos.obtener(codigo);
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Profesor">
    public ArrayList<Grupo> gruposDelProfesor(String cedula){
        try {
            grupos.gruposPorProfesor(cedula);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    /**
     *
     * @param username
     * Nombre de usuario usado en el login por el profesor
     * @return
     * Devuelve un objeto profesor
     */
    public Profesor obtenerProfesorPorUsuario(String username){
        try {
            return profesores.obtenerPorUsuario(username);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Profesor> obtenerTodosLosProfesores(){
        try {
            return profesores.obtenerTodos();
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public ArrayList<Profesor> obtenerProfesoresPorNombre(String nombre){
        try {
            return profesores.obtenerPorNombre(nombre);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int addProfesor(Profesor ca){
        try{
        this.usuarios.agregar(ca.getUsuario());
        return this.profesores.agregar(ca);
        }
        catch(Exception e){
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
    
    public int deleteProfesor(Profesor ca){
        return this.profesores.eliminar(ca);
    }
    
    public int updateProfesor(Profesor ca){
        return this.profesores.actualizar(ca);
    }
    
    public Profesor getProfesor(String codigo){
        try {
            return this.profesores.obtener(codigo);
        } catch (Exception ex) {
            System.err.println("Error al obtener profesor");
        }
        return null;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos de Grupos">
    
    
    
    public int addGrupo(Grupo ca){
        try {
            return this.grupos.agregar(ca);
        } catch (Exception ex) {
            System.err.println("Error al agregar grupo");
        }
        return 0;
    }
    
    public int deleteGrupo(Grupo ca){
        try {
            return this.grupos.eliminar(ca);
        } catch (SQLException ex) {
            System.err.println("Error al eliminar grupo");
        }
        return 0;
    }
    
    public int updateGrupo(Grupo ca){
        try {
            return this.grupos.actualizar(ca);
        } catch (Exception ex) {
            System.err.println("Error al actualizar grupo");
        }
        return 0;
    }
    
    public ArrayList<Grupo> gruposPorProfesor(String cedula){
        try {
            return this.grupos.gruposPorProfesor(cedula);
        } catch (Exception ex) {
            System.err.println("Error al obtener los grupos");
        }
        return null;
        
    }
    
    public ArrayList<Grupo> gruposPorCurso(Curso curso){
        try {
            return this.grupos.gruposPorCurso(curso);
        } catch (Exception ex) {
            System.err.println("Error al obtener los grupos");
        }
        return null;
        
    }
    
    //para obtener el grupo se ocupa algo especifica, ya que solo tiene un numero como atributo, lo demas son llaves foraneas
//    public Grupo getGrupo(int numero) throws Exception{
//        return this.grupos.obtener(numero);
//    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Carreras">
    public ArrayList<Carrera> obtenerTodasCarreras(){
        try {
            return carreras.obtenerTodo();
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<Carrera> obtenerCarreraPorNombre(String nombre){
        try {
            return carreras.obtenerPorNombre(nombre);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int addCarrera(Carrera ca){
        return this.carreras.agregar(ca);
    }
    
    public int deleteCarrera(Carrera ca){
        return this.carreras.eliminar(ca);
    }
    
    public int updateCarrera(Carrera ca){
        try {
            return this.carreras.actualizar(ca);
        } catch (SQLException ex) {
            System.err.println("Error al actualizar carrera");
        }
        return 0;
    }
    
    public Carrera getCarrera(String id) throws Exception{
        return this.carreras.obtener(id);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Ciclos">
    
    public int addCiclo(Ciclo ca){
        return this.ciclos.agregar(ca);
    }
    
    public int deleteCiclo(Ciclo ca){
        return this.ciclos.eliminar(ca);
    }
    
    //No se usa porque las fechas son fijas, y lo demas es primary key (tendria que enviar una copia)
//    public int updateCiclo(Ciclo ca){
//        return this.ciclos.actualizar(ca);
//    }
    
    public Ciclo getCiclo(int anio) throws Exception{
        return this.ciclos.obtener(anio);
    }
    
    public ArrayList<Ciclo> obtenerTodosLosCiclos(){
        try {
            return ciclos.obtenerTodo();
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Estudiante">
    
    public Estudiante getEstudiantePorCarrera(String cedula){
         try {
            return this.estudiantes.obtener(cedula);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    /**
     * 
     * @param nombre
     * Obtiene el Nombre del estudiante (String)
     * @return 
     * Devuelve la lista de estudiantes que tengan el nombre LIKE el parametro en la base de datos
     */
    public ArrayList<Estudiante> obtenerEstudiantePorNombre(String nombre){
        try {
            return this.estudiantes.obtenerPorNombre(nombre);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    /**
     * 
     * @return devuelve la lista de todos los estudiantes en la case de datos
     */
    public ArrayList<Estudiante> obtenerTodosLosEstudiantes(){
        try {
            return this.estudiantes.obtenerTodos();
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * 
     * @param ca objeto de Tipo Estudiante
     * @return devuelve 1 en caso de agregar al estudiante correctamente, 0 en caso contrario
     */
    public int addEstudiante(Estudiante ca){
        try{
        this.usuarios.agregar(ca.getUsuario());
        return this.estudiantes.agregar(ca);
        }
        catch(Exception e){
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
    
    /**
     * 
     * @param carrera obtiene un objeto carrera para buscarlo en la BD
     * @return devuelve la lista de estudiantes asociado a esa carrera, en caso de error retorna null
     */
    public ArrayList<Estudiante> obtenerEstudiantesPorCarrera(Carrera carrera){
        try {
            return this.estudiantes.obtenerPorCarrera(carrera);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public int deleteEstudiante(Estudiante ca){
        return this.estudiantes.eliminar(ca);
    }
    
    public int updateEstudiante(Estudiante ca){
        try {
            return this.estudiantes.actualizar(ca);
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    /**
     * 
     * @param cedula del estudiante a consultar
     * @return devuelve solo el estudiante con esa cedula, no usa LIKE
     */
    public Estudiante getEstudiante(String cedula){
         try {
            return this.estudiantes.obtener(cedula);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Horario">
    
    public ArrayList<Horario> obtenerTodosLosHorarios(){
        try {
            return this.horarios.obtenerTodo();
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public int addHorario(Horario ca){
        return this.horarios.agregar(ca);
    }
    
    public int deleteHorario(Horario ca){
        try {
            return this.horarios.eliminar(ca);
        } catch (SQLException ex) {
            System.err.println("Error al eliminar horario");
        }
        return 0;
    }
    
    public int updateHorario(Horario ca){
        try {
            return this.horarios.actualizar(ca);
        } catch (SQLException ex) {
            System.err.println("Error al actualizar horario");
        }
        return 0;
    }
    
    public Horario getHorario(String dias, Date horaInicial, Date horaFinal) throws Exception{
        return this.horarios.obtener(dias,horaInicial,horaFinal);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Matriculador">
    
    public ArrayList<Matriculador> obtenerTodosLosMatriculadores(){
        try {
            return this.matriculadores.obtenerTodo();
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    
    public int addMatriculador(Matriculador ca){
        try{
        this.usuarios.agregar(ca.getUsuario());
        return this.matriculadores.agregar(ca);
        }
        catch(Exception e){
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
    
    public int deleteMatriculador(Matriculador ca){
        return this.matriculadores.eliminar(ca);
    }
    
    public int updateMatriculador(Matriculador ca){
        return this.matriculadores.actualizar(ca);
    }
    
    public Matriculador getMatriculador(String cedula){
        try {
            return this.matriculadores.obtener(cedula);
        } catch (Exception ex) {
            System.err.println("Error al obtener Matriculador");
        }
        return null;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Matriculador">
    
    public ArrayList<Administrador> obtenerTodosLosAdministradores(){
        try {
            return this.administradores.obtenerTodo();
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    
    public int addAdministrador(Administrador ca){
        try{
        this.usuarios.agregar(ca.getUsuario());
        return this.administradores.agregar(ca);
        }
        catch(Exception e){
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
    
    public int deleteAdministrador(Administrador ca){
        return this.administradores.eliminar(ca);
    }
    
    public int updateAdministrador(Administrador ca){
        return this.administradores.actualizar(ca);
    }
    
    public Administrador getAdministrador(String cedula){
        try {
            return this.administradores.obtener(cedula);
        } catch (Exception ex) {
            System.err.println("Error al obtener administrador");
        }
        return null;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Notas">
    
    public int addNota(Nota ca){
        try {
            return this.notas.agregar(ca);
        } catch (SQLException ex) {
            System.err.println("Error al agregar Nota");
        }
        return 0;
    }
    
    public int deleteNota(Nota ca){
        try {
            return this.notas.eliminar(ca);
        } catch (SQLException ex) {
            System.err.println("Error al borrar nota");
        }
        return 0;
    }
    
    public int updateNota(Nota ca){
        try {
            return this.notas.actualizar(ca);
        } catch (SQLException ex) {
            System.err.println("Error al actualizar nota");
        }
        return 0;
    }
    
//    public Nota getNota(float calificacion, String Estudiante_cedula, String Codigo_Curso) throws Exception{
//        return this.notas.obtener(calificacion,Estudiante_cedula,Codigo_Curso);
//    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Usuario">
    
    public int addUsuario(Usuario ca){
        return this.usuarios.agregar(ca);
    }
    
    public int deleteUsuario(Usuario ca){
        return this.usuarios.eliminar(ca);
    }
    
    public int updateUsuario(Usuario ca){
        return this.usuarios.actualizar(ca);
    }
    
    public Usuario getUsuario(String codigo){
        try {
            return this.usuarios.obtener(codigo);
        } catch (Exception ex) {
            System.err.println("Error al obtener usuario");
        }
        return null;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos Relacion Estudiante-Grupo">
    
    public int addEstudianteAGrupo(Estudiante est, Grupo gru){
        try {
            return this.estudiante_grupo.agregar(est, gru);
        } catch (Exception ex) {
            System.err.println("Error al obtener usuario");
        }
        return 0;
    }
    
    public int deleteEstudianteDeGrupo(Estudiante est, Grupo gru){
        try {
            return this.estudiante_grupo.eliminar(est, gru);
        } catch (Exception ex) {
            System.err.println("Error al obtener usuario");
        }
        return 0;
    }
    
    public ArrayList<Grupo> obtenerGruposDeEstudiante(Estudiante est){
        
        try {
            return this.estudiante_grupo.obtenerGruposDeEstudiante(est);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public ArrayList<Estudiante> obtenerEstudiantesDeGrupo(Grupo gru){
        try {
            return this.estudiante_grupo.obtenerEstudiantesDeGrupo(gru);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos relacion Estudiante-Curso">
    
    public int addEstudianteACurso(Estudiante est, Curso cur){
        try {
            return this.estudiante_curso.agregar(est, cur);
        } catch (Exception ex) {
            System.err.println("Error al agregar el estudiante");
        }
        return 0;
    }
    
    public int deleteEstudianteDeCurso(Estudiante est, Curso cur){
        try {
            return this.estudiante_curso.eliminar(est, cur);
        } catch (Exception ex) {
            System.err.println("Error al borrar la relacion estudiante - curso");
        }
        return 0;
    }
    
    public ArrayList<Curso> obtenerCursosDeEstudiante(Estudiante est){
        
        try {
            return this.estudiante_curso.obtenerCursosDeEstudiante(est);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public ArrayList<Estudiante> obtenerEstudiantesDeCurso(Curso cur){
        try {
            return this.estudiante_curso.obtenerEstudiantesDeCurso(cur);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Ciclo activo">
    
    public int cambiarCicloActivo(int anio, String numero){
        try {
            return ciclos.cambiarCicloActivo(anio, numero);
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    
    public Ciclo obtenerCicloActivo(){
        try {
            return ciclos.obtenerCicloActivo();
        } catch (Exception ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//</editor-fold>
    
    
}
