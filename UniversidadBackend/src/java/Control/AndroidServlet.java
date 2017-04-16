/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import LogicaNegocio.Administrador;
import LogicaNegocio.Carrera;
import LogicaNegocio.Ciclo;
import LogicaNegocio.Curso;
import LogicaNegocio.Estudiante;
import LogicaNegocio.Grupo;
import LogicaNegocio.Horario;
import LogicaNegocio.Matriculador;
import LogicaNegocio.Profesor;
import LogicaNegocio.Usuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet(name = "AndroidServlet", urlPatterns = {"/AndroidServlet"})
public class AndroidServlet extends HttpServlet {
    
    private class SuccessMsg {
        
        String type = "Success";
        String msg;

        public SuccessMsg(String msg) {
            this.msg = msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
        
    }
    
    private class ErrorMsg {
        
        String type = "Error";
        String msg;

        public ErrorMsg(String msg) {
            this.msg = msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
        
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
//        Define cual es la accion que se va a realizar
        String accion = request.getParameter("action");
                
//        Listas de Objetos
        ArrayList<Carrera> carreras = new ArrayList<Carrera>();
        ArrayList<Profesor> profesores = new ArrayList<Profesor>();
        ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
        ArrayList<Matriculador> matriculadores = new ArrayList<Matriculador>();
        ArrayList<Administrador> administradores = new ArrayList<Administrador>();
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        ArrayList<Grupo> grupos = new ArrayList<Grupo>();
        
//        ArrayList<Carrera> allCarreras = null;
//        
//        Curso cursoCurrent = null;
//        ArrayList<Grupo> grupos = null;
//        ArrayList<Profesor> allProfesores = null;
//        ArrayList<Ciclo> ciclos = null;
//        Estudiante estudianteCurrent = null;
//        Carrera carreraEstudianteCurrent = null;
//        ArrayList<Curso> cursosCarrera = null;
//        ArrayList<Grupo> gruposCurso = null;
//        Ciclo cicloDefault = null;;
        
        Control ctrl = new Control();
        ErrorMsg error = new ErrorMsg("");
        SuccessMsg success = new SuccessMsg("");
        //        Acciones
        if(accion != null){
            try{
                PrintWriter out = response.getWriter();
                Gson gson = new Gson();
                switch (accion) {
                    
                    case "Testing": {
                        out.println("Hello Android !!!!");
                    }
                    break;
                    case "Ingresar": {
                        System.out.println("Ingresando...");
                        String id = request.getParameter("id");
                        String pass = request.getParameter("password");
                        int tipoUsuario = ctrl.verificaUsuario(id, pass);
                        System.out.println("El tipo del usuatio es: "+tipoUsuario);
                        if(tipoUsuario != 0){
                            Usuario user;
                            switch(tipoUsuario){ 
                                case 1: //ADMINISTRADOR                                
                                    System.out.println("Es administrador");
                                    user = new Usuario(id,pass,tipoUsuario);
                                    response.getWriter().write(gson.toJson(user));
                                    break;
                                case 2: //MATRICULADOR
                                    System.out.println("Es matriculador");
                                    user = new Usuario(id,pass,tipoUsuario);
                                    response.getWriter().write(gson.toJson(user));
                                    break;
                                case 3: //PROFESOR
                                    System.out.println("Es profesor");
                                    user = new Usuario(id,pass,tipoUsuario);
                                    response.getWriter().write(gson.toJson(user));
                                    break;
                                case 4: //ESTUDIANTE
                                    System.out.println("Es estudiante");
                                    user = new Usuario(id,pass,tipoUsuario);
                                    response.getWriter().write(gson.toJson(user));
                                    break;
                            }
                        }else{
                            error.setMsg("ERROR: Identificación o Contraseña Incorrecta!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "AllCarreras" : {
                        System.out.println("Mostrando todas las Carreras...");
                        carreras.clear();
                        carreras = ctrl.obtenerTodasCarreras();  
                        if(carreras.size() == 0){
                            error.setMsg("Aun no existen Profesores Registrados!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            String carrerasJSON  = gson.toJson(carreras);
//                            Object obj = gson.fromJson(carrerasJSON, Object.class);
//                            ArrayList<Object> objs = (ArrayList<Object>) obj;
//                            for( Object o : objs ) {
//                                System.out.println(o.toString());
//                            }
                            response.getWriter().write(carrerasJSON);   
                        }
                    }
                    break;
                    case "AllProfesores" : {
                        System.out.println("Mostrando todos los Profesores...");
                        profesores.clear();
                        profesores = ctrl.obtenerTodosLosProfesores();   
                        if(profesores.size() == 0){
                            error.setMsg("Aun no existen Profesores Registrados!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            String profesoresJSON  = gson.toJson(profesores);
                            response.getWriter().write(profesoresJSON);
                        }
                    }
                    break;
                    case "AllEstudiantes" : {
                        System.out.println("Mostrando todos los Estudiantes...");
                        estudiantes.clear();
                        estudiantes = ctrl.obtenerTodosLosEstudiantes();  
                        if(estudiantes.size() == 0){
                            error.setMsg("Aun no existen Estudiantes Registrados!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            String estudiantesJSON  = gson.toJson(estudiantes);
                            response.getWriter().write(estudiantesJSON);
                        }
                    }
                    break;
                    case "AllMatriculadores" : {
                        System.out.println("Mostrando todos los Matriculadores...");
                        matriculadores.clear();
                        matriculadores = ctrl.obtenerTodosLosMatriculadores();           
                        if(matriculadores.size() == 0){
                            error.setMsg("Aun no existen Matriculadores Registrados!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            String matriculadoresJSON  = gson.toJson(matriculadores);
                            response.getWriter().write(matriculadoresJSON);
                        }
                    }
                    break;
                    case "AllAdministradores" : {
                        System.out.println("Mostrando todos los Administradores...");
                        administradores.clear();
                        administradores = ctrl.obtenerTodosLosAdministradores(); 
                        if(administradores.size() == 0){
                            error.setMsg("Aun no existen Administradores Registrados!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            String administradoresJSON  = gson.toJson(administradores);
                            response.getWriter().write(administradoresJSON);
                        }                        
                    }
                    break;
                    case "AllCursos" : {
                        System.out.println("Mostrando todos los Cursos...");
                        cursos.clear();
                        cursos = ctrl.obtenerTodosLosCursos();    
                        if(cursos.size() == 0){
                            error.setMsg("Aun no existen Cursos Registrados!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            String cursosJSON  = gson.toJson(cursos);
                            response.getWriter().write(cursosJSON);
                        }                        
                    }
                    break;
                    case "AllGruposPorCurso" : {
                        String codigoCurso = request.getParameter("codigoCurso");
                        grupos.clear();
                        Curso curso;
                        if((curso = ctrl.getCurso(codigoCurso)) == null){
                            error.setMsg("ERROR: Código de Curso Incorrecto!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            System.out.println(curso.toString());
                            System.out.println("Mostrando todos los Grupos de " + curso.getNombre() + "...");
                            grupos = ctrl.gruposPorCurso(curso);    
                            if(grupos.size() == 0){
                                error.setMsg("Aun no existen Grupos Registrados!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                String gruposJSON  = gson.toJson(grupos);
                                response.getWriter().write(gruposJSON);
                            }
                        }                        
                    }
                    break;
                    case "AgregarCarrera": {
                        System.out.println("Agregando Carrera...");
                        String codigo = request.getParameter("codigo");
                        String nombre = request.getParameter("nombre");
                        String titulo = request.getParameter("titulo");
                        Carrera ca = new Carrera(codigo,nombre,titulo);
                        if(ctrl.addCarrera(ca) == 1){
                            success.setMsg("Carrera Arregada!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Carrera NO Agregada!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "BuscarCarrera": {
                        System.out.println("Buscando Carreras...");
                        String codigo = request.getParameter("codigo");
                        String nombre = request.getParameter("nombre");
                        if(codigo != "" && nombre == ""){
                            Carrera ca;
                            if((ca = ctrl.getCarrera(codigo)) == null){
                                error.setMsg("ERROR: Carrera NO Encontrada!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write("[" + gson.toJson(ca) + "]") ;                                
                            }

                        }else if(nombre != "" && codigo == ""){
                            carreras.clear();
                            carreras = ctrl.obtenerCarreraPorNombre(nombre);
                            if(carreras.size() == 0){
                                error.setMsg("ERROR: Carreras NO Encontradas!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write(gson.toJson(carreras));
                            }                            
                        }else if(nombre == "" && codigo == ""){
                            carreras.clear();
                            carreras = ctrl.obtenerTodasCarreras();                            
                            response.getWriter().write(gson.toJson(carreras));
                        }
                    }
                    break;
                    case "EditarCarrera":{
                        System.out.println("Editando Carrera...");
                        String codigo = request.getParameter("codigo");
                        String nombre = request.getParameter("nombre");
                        String titulo = request.getParameter("titulo");
                        Carrera ca = new Carrera(codigo,nombre,titulo);
                        System.out.println(ca.toString());
                        if(ctrl.updateCarrera(ca) == 1){
                            success.setMsg("Carrera Actualizada!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Carrera NO Actualizada!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "AgregarProfesor": {
                        System.out.println("Agregando Profesor...");
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        String telefono = request.getParameter("telefono");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        Usuario user = new Usuario(cedula,password,3);
                        Profesor profe = new Profesor(user,nombre,cedula,telefono,email);
                        if(ctrl.addProfesor(profe) == 1){
                            success.setMsg("Profesor Arregado!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Profesor NO Agregado!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "BuscarProfesor": {
                        System.out.println("Buscando Profesores...");
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        if(cedula != "" && nombre == ""){
                            Profesor pro;
                            if((pro = ctrl.getProfesor(cedula)) == null){
                                error.setMsg("ERROR: Profesor NO Encontrado!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write("[" + gson.toJson(pro) + "]");
                            }

                        }else if(nombre != "" && cedula == ""){
                            profesores.clear();
                            profesores = ctrl.obtenerProfesoresPorNombre(nombre);
                            if(profesores.size() == 0){
                                error.setMsg("ERROR: Profesores NO Encontrados!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write(gson.toJson(profesores));
                            }  
                        }else if(nombre == "" && cedula == ""){
                            profesores.clear();
                            profesores = ctrl.obtenerTodosLosProfesores();
                            response.getWriter().write(gson.toJson(profesores));
                        }
                    }
                    break;
                    case "EditarProfesor":{
                        System.out.println("Editando Profesor...");
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        String telefono = request.getParameter("telefono");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        Usuario user = new Usuario(cedula,password,3);
                        Profesor profe = new Profesor(user,nombre,cedula,telefono,email);
                        System.out.println(profe.toString());
                        if(ctrl.updateProfesor(profe) == 1){
                            success.setMsg("Profesor Actualizado!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Profesor NO Actualizado!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "AgregarEstudiante": {
                        System.out.println("Agregando Estudiante...");
                        String cedula = request.getParameter("cedula");
                        String fechaNacString = request.getParameter("fechaNac");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(sdf.parse(fechaNacString));
                        String nombre = request.getParameter("nombre");
                        String idCarrera = request.getParameter("idCarrera");
                        String telefono = request.getParameter("telefono");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        Usuario user = new Usuario(cedula,password,4);
                        Carrera carrera;
                        if((carrera = ctrl.getCarrera(idCarrera)) == null){
                            error.setMsg("ERROR: Código de Carrera Incorrecto!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            Estudiante es = new Estudiante(cal,carrera,user,nombre,cedula,telefono,email);
                            if(ctrl.addEstudiante(es) == 1){
                                System.out.println("Estudiante Arregado!");
                                success.setMsg("Estudiante Arregado!");
                                response.getWriter().write(gson.toJson(success));
                            }else{
                                System.out.println("ERROR: Estudiante NO Agregado!");
                                error.setMsg("ERROR: Estudiante NO Agregado!");
                                response.getWriter().write(gson.toJson(error));
                            }
                        }                        
                    }
                    break;
                    case "EditarEstudiante": {
                        System.out.println("Editando Estudiante...");
                        String cedula = request.getParameter("cedula");
                        String fechaNacString = request.getParameter("fechaNac");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(sdf.parse(fechaNacString));
                        String nombre = request.getParameter("nombre");
                        String idCarrera = request.getParameter("idCarrera");
                        String telefono = request.getParameter("telefono");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        Usuario user = new Usuario(cedula,password,4);
                        Carrera carrera;
                        if((carrera = ctrl.getCarrera(idCarrera)) == null){
                            System.out.println("ERROR: Código de Carrera Incorrecto!");
                            error.setMsg("ERROR: Código de Carrera Incorrecto!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            Estudiante es = new Estudiante(cal,carrera,user,nombre,cedula,telefono,email);
                            if(ctrl.updateEstudiante(es) == 1){
                                System.out.println("Estudiante Actualizado!");
                                success.setMsg("Estudiante Actualizado!");
                                response.getWriter().write(gson.toJson(success));
                            }else{
                                System.out.println("ERROR: Estudiante NO Actualizado!");
                                error.setMsg("ERROR: Estudiante NO Actualizado!");
                                response.getWriter().write(gson.toJson(error));
                            }
                        }                        
                    }
                    break;
                    case "BuscarEstudiante": {
                        System.out.println("Buscando Estudiantes...");
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        String idCarrera = request.getParameter("idCarrera");
                        if(cedula != "" && nombre == "" && idCarrera == ""){
                            Estudiante es;
                            if((es = ctrl.getEstudiante(cedula)) == null){
                                error.setMsg("ERROR: Estudiante NO Encontrado!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write("[" + gson.toJson(es) + "]");
                            }
                        }else if(nombre != "" && cedula == "" && idCarrera == ""){
                            estudiantes.clear();
                            estudiantes = ctrl.obtenerEstudiantePorNombre(nombre);
                            if(estudiantes.size() == 0){
                                error.setMsg("ERROR: Estudiantes NO Encontrados!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write(gson.toJson(estudiantes));
                            }  
                        }else if(idCarrera != "" && nombre == "" && cedula == ""){
                            estudiantes.clear();
                            Carrera ca = ctrl.getCarrera(idCarrera);
                            estudiantes = ctrl.obtenerEstudiantesPorCarrera(ca);
                            if(estudiantes.size() == 0){
                                error.setMsg("ERROR: Estudiantes NO Encontrados!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write(gson.toJson(estudiantes));
                            }  
                        }else if(nombre == "" && cedula == "" && idCarrera == ""){
                            estudiantes.clear();
                            estudiantes = ctrl.obtenerTodosLosEstudiantes();
                            response.getWriter().write(gson.toJson(estudiantes));
                        }
                    }
                    break;
                    case "AgregarMatriculador": {
                        System.out.println("Agregando Matriculador...");
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        String telefono = request.getParameter("telefono");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        Usuario user = new Usuario(cedula,password,2);
                        Matriculador matriculador = new Matriculador(user,nombre,cedula,telefono,email);
                        if(ctrl.addMatriculador(matriculador) == 1){
                            success.setMsg("Matriculador Arregado!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Matriculador NO Agregado!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "BuscarMatriculador": {
                        System.out.println("Buscando Matriculadores...");
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        if(cedula != "" && nombre == ""){
                            Matriculador ma;
                            if((ma = ctrl.getMatriculador(cedula)) == null){
                                error.setMsg("ERROR: Matriculador NO Encontrado!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write("[" + gson.toJson(ma) + "]");
                            }

                        }else if(nombre != "" && cedula == ""){
                            matriculadores.clear();
                            matriculadores = ctrl.obtenerMatriculadoresPorNombre(nombre);
                            if(matriculadores.size() == 0){
                                error.setMsg("ERROR: Matriculadores NO Encontrados!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write(gson.toJson(matriculadores));
                            }  
                        }else if(nombre == "" && cedula == ""){
                            matriculadores.clear();
                            matriculadores = ctrl.obtenerTodosLosMatriculadores();
                            response.getWriter().write(gson.toJson(matriculadores));
                        }
                    }
                    break;
                    case "EditarMatriculador":{
                        System.out.println("Editando Matriculador...");
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        String telefono = request.getParameter("telefono");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        Usuario user = new Usuario(cedula,password,2);
                        Matriculador ma = new Matriculador(user,nombre,cedula,telefono,email);
                        System.out.println(ma.toString());
                        if(ctrl.updateMatriculador(ma) == 1){
                            success.setMsg("Matriculador Actualizado!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Matriculador NO Actualizado!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "AgregarAdministrador": {
                        System.out.println("Agregando Administrador...");
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        String telefono = request.getParameter("telefono");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        Usuario user = new Usuario(cedula,password,2);
                        Administrador administrador = new Administrador(user,nombre,cedula,telefono,email);
                        if(ctrl.addAdministrador(administrador) == 1){
                            success.setMsg("Administrador Arregado!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Administrador NO Agregado!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "BuscarAdministrador": {
                        System.out.println("Buscando Administradores...");
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        if(cedula != "" && nombre == ""){
                            Administrador admi;
                            if((admi = ctrl.getAdministrador(cedula)) == null){
                                error.setMsg("ERROR: Administrador NO Encontrado!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write("[" + gson.toJson(admi) + "]");
                            }

                        }else if(nombre != "" && cedula == ""){
                            administradores.clear();
                            administradores = ctrl.obtenerAdministradoresPorNombre(nombre);
                            if(administradores.size() == 0){
                                error.setMsg("ERROR: Administradores NO Encontrados!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write(gson.toJson(administradores));
                            }  
                        }else if(nombre == "" && cedula == ""){
                            administradores.clear();
                            administradores = ctrl.obtenerTodosLosAdministradores();
                            response.getWriter().write(gson.toJson(administradores));
                        }
                    }
                    break;
                    case "EditarAdministrador":{
                        System.out.println("Editando Administrador...");
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        String telefono = request.getParameter("telefono");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        Usuario user = new Usuario(cedula,password,2);
                        Administrador admi = new Administrador(user,nombre,cedula,telefono,email);
                        System.out.println(admi.toString());
                        if(ctrl.updateAdministrador(admi) == 1){
                            success.setMsg("Administrador Actualizado!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Administrador NO Actualizado!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "AgregarCurso": {
                        System.out.println("Agregando Curso...");
                        String codigo = request.getParameter("codigo");
                        String nombre = request.getParameter("nombre");
                        String creditos = request.getParameter("creditos");
                        String horasSemanales = request.getParameter("horasSemanales");
                        String idCarrera = request.getParameter("idCarrera");
                        String nivel = request.getParameter("nivel");
                        String ciclo = request.getParameter("ciclo");
                        Carrera ca;
                        if((ca = ctrl.getCarrera(idCarrera)) == null){
                            error.setMsg("ERROR: Código de Carrera Incorrecto!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            Curso cu = new Curso(codigo,nombre,Integer.parseInt(creditos),Integer.parseInt(horasSemanales),ca,nivel);
                            cu.setCiclo(ciclo);
                            if(ctrl.addCurso(cu) == 1){
                                success.setMsg("Curso Agregado!");
                                response.getWriter().write(gson.toJson(success));
                            }else{
                                error.setMsg("ERROR: Curso NO Agregado!");
                                response.getWriter().write(gson.toJson(error));
                            }
                        }                        
                    }
                    break;
                    case "EditarCurso": {
                        System.out.println("Editando Curso...");
                        String codigo = request.getParameter("codigo");
                        String nombre = request.getParameter("nombre");
                        String creditos = request.getParameter("creditos");
                        String horasSemanales = request.getParameter("horasSemanales");
                        String idCarrera = request.getParameter("idCarrera");
                        String nivel = request.getParameter("nivel");
                        String ciclo = request.getParameter("ciclo");
                        Carrera ca;
                        if((ca = ctrl.getCarrera(idCarrera)) == null){
                            error.setMsg("ERROR: Código de Carrera Incorrecto!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            Curso cu = new Curso(codigo,nombre,Integer.parseInt(creditos),Integer.parseInt(horasSemanales),ca,nivel);
                            cu.setCiclo(ciclo);
                            if(ctrl.updateCurso(cu) == 1){
                                success.setMsg("Curso Actualizado!");
                                response.getWriter().write(gson.toJson(success));
                            }else{
                                error.setMsg("ERROR: Curso NO Actualizado!");
                                response.getWriter().write(gson.toJson(error));
                            }
                        }
                    }
                    break;
                    case "BuscarCurso": {
                        System.out.println("Buscando Cursos...");
                        String codigo = request.getParameter("codigo");
                        String nombre = request.getParameter("nombre");
                        String idCarrera = request.getParameter("idCarrera");
                        if(codigo != "" && nombre == "" && idCarrera == ""){
                            Curso cu;
                            if((cu = ctrl.getCurso(codigo)) == null){
                                error.setMsg("ERROR: Curso NO Encontrado!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write("[" + gson.toJson(cu) + "]");
                            }
                        }else if(nombre != "" && codigo == "" && idCarrera == ""){
                            cursos.clear();
                            cursos = ctrl.getCursoPorNombre(nombre);
                            if(estudiantes.size() == 0){
                                error.setMsg("ERROR: Estudiantes NO Encontrados!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write(gson.toJson(cursos));
                            }   
                        }else if(idCarrera != "" && nombre == "" && codigo == ""){
                            cursos.clear();
                            Carrera ca = ctrl.getCarrera(idCarrera);
                            cursos = ctrl.getCursoPorCarrera(ca);
                            if(estudiantes.size() == 0){
                                error.setMsg("ERROR: Estudiantes NO Encontrados!");
                                response.getWriter().write(gson.toJson(error));
                            }else{
                                response.getWriter().write(gson.toJson(cursos));
                            }
                        }else if(nombre == "" && codigo == "" && idCarrera == ""){
                            cursos.clear();
                            cursos = ctrl.obtenerTodosLosCursos();
                            response.getWriter().write(gson.toJson(cursos));
                        }
                    }
                    break;
                    case "AgregarGrupo": {
                        String idCurso = request.getParameter("idCurso");
                        String numeroCiclo = request.getParameter("numeroCiclo");
                        String numero = request.getParameter("numero");
                        String[] dias = request.getParameterValues("dias");
                        String horaInicio = request.getParameter("horaInicio");
                        String horaFinal = request.getParameter("horaFinal");
                        String idProfesor = request.getParameter("idProfesor");
                        String anioCiclo = request.getParameter("anioCiclo");
                        String diasStr = "";
                        for(String s : dias){
                            diasStr = diasStr +" "+ s;
                        }
                        Horario hora = new Horario(diasStr,horaInicio,horaFinal);
                        Ciclo ci = new Ciclo(Integer.parseInt(anioCiclo),numeroCiclo);
                        Profesor profe;
                        Curso cur;
                        if((profe = ctrl.getProfesor(idProfesor)) == null){
                            error.setMsg("ERROR: Cedula del Profesor Incorrecta!");
                            response.getWriter().write(gson.toJson(error));
                        }else if((cur = ctrl.getCurso(idCurso)) == null){
                            error.setMsg("ERROR: Código de Curso Incorrecto!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            System.out.println("Agregando Grupo a " + cur.getNombre() + " con Profesor: " + profe.getNombre() + "...");
                            Grupo gru = new Grupo(Integer.parseInt(numero),hora,profe,cur,ci);
                            if(ctrl.addGrupo(gru) == 1){
                                success.setMsg("Grupo Agregado!");
                                response.getWriter().write(gson.toJson(success));
                            }else{
                                error.setMsg("ERROR: Grupo NO Agregado!");
                                response.getWriter().write(gson.toJson(error));
                            }
                        }
                    }
                    break;
                    case "EditarGrupo": {
                        String idGrupo= request.getParameter("idGrupo");
                        String idCurso = request.getParameter("idCurso");
                        String numeroCiclo = request.getParameter("numeroCiclo");
                        String numero = request.getParameter("numero");
                        String[] dias = request.getParameterValues("dias");
                        String horaInicio = request.getParameter("horaInicio");
                        String horaFinal = request.getParameter("horaFinal");
                        String idProfesor = request.getParameter("idProfesor");
                        String anioCiclo = request.getParameter("anioCiclo");
                        String diasStr = "";
                        for(String s : dias){
                            diasStr = diasStr +" "+ s;
                        }
                        Horario hora = new Horario(diasStr,horaInicio,horaFinal);
                        Ciclo ci = new Ciclo(Integer.parseInt(anioCiclo),numeroCiclo);
                        Profesor profe;
                        Curso cur;
                        if((profe = ctrl.getProfesor(idProfesor)) == null){
                            error.setMsg("ERROR: Cedula del Profesor Incorrecta!");
                            response.getWriter().write(gson.toJson(error));
                        }else if((cur = ctrl.getCurso(idCurso)) == null){
                            error.setMsg("ERROR: Código de Curso Incorrecto!");
                            response.getWriter().write(gson.toJson(error));
                        }else{
                            Grupo gru = new Grupo(Integer.parseInt(idGrupo),Integer.parseInt(numero),hora,profe,cur,ci);
                            if(ctrl.updateGrupo(gru) == 1){
                                success.setMsg("Grupo Actualizado!");
                                response.getWriter().write(gson.toJson(success));                                   
                            }else{
                                error.setMsg("ERROR: Grupo NO Actualizado!");
                                response.getWriter().write(gson.toJson(error));
                            }
                        }                        
                    }
                    break;
//                    case "Matricular": {
//                        cicloDefault = ctrl.obtenerCicloActivo();
//                        System.out.println(cicloDefault);
//                        session.setAttribute("cicloDefault", cicloDefault);
//                        String idEstudiante= request.getParameter("idEstudiante");
//                        String idGrupo= request.getParameter("idGrupo");
////                        if((ctrl.addEstudianteAGrupo(ctrl.getEstudiante(idEstudiante), ctrl)) == 1){
////                            
////                        }
//                        this.printHTML("Matriculado "+idEstudiante+" en: "+idGrupo, response);
//                    }
//                    break;
//                    case "CicloDefault": {
//                        String anio= request.getParameter("anio");
//                        String numero= request.getParameter("numero");
//                        this.printHTML("Matriculado "+anio+" en: "+numero, response);
//                        if((ctrl.cambiarCicloActivo(Integer.parseInt(anio), numero)) == 1){
//                            session.setAttribute("errores", "");
//                            session.setAttribute("exito", "Ciclo Cambiado Correctamente");
//                            response.sendRedirect("adminCiclos.jsp");
//                        }else{
//                            session.setAttribute("errores", "");
//                            session.setAttribute("exito", "");
//                            response.sendRedirect("adminCiclos.jsp");
//                        }
//                    }
//                    break;
                }
            }catch (Exception ex) {
                Logger.getLogger(AndroidServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   

        
        
    }

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
