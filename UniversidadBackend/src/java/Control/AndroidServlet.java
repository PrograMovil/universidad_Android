/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import LogicaNegocio.Carrera;
import LogicaNegocio.Ciclo;
import LogicaNegocio.Curso;
import LogicaNegocio.Estudiante;
import LogicaNegocio.Grupo;
import LogicaNegocio.Horario;
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
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        
        
        ArrayList<Carrera> allCarreras = null;
        
        Curso cursoCurrent = null;
        ArrayList<Grupo> grupos = null;
        ArrayList<Profesor> allProfesores = null;
        ArrayList<Ciclo> ciclos = null;
        Estudiante estudianteCurrent = null;
        Carrera carreraEstudianteCurrent = null;
        ArrayList<Curso> cursosCarrera = null;
        ArrayList<Grupo> gruposCurso = null;
        Ciclo cicloDefault = null;;
        
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
                        carreras.clear();
                        carreras = ctrl.obtenerTodasCarreras();           
                        String carrerasJSON  = gson.toJson(carreras);
                        Object obj = gson.fromJson(carrerasJSON, Object.class);
                        ArrayList<Object> objs = (ArrayList<Object>) obj;
                        for( Object o : objs ) {
                            System.out.println(o.toString());
                        }
                        response.getWriter().write(carrerasJSON);
                    }
                    break;
                    case "AgregarCarrera": {
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
                        String cedula = request.getParameter("cedula");
                        String nombre = request.getParameter("nombre");
                        String telefono = request.getParameter("telefono");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        Usuario user = new Usuario(cedula,password,3);
                        Profesor profe = new Profesor(user,nombre,cedula,telefono,email);
                        if(ctrl.addProfesor(profe) == 1){
                            success.setMsg("Profesor Arregada!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Profesor NO Agregado!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "BuscarProfesor": {
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
                        Carrera carrera = ctrl.getCarrera(idCarrera);
                        Estudiante es = new Estudiante(cal,carrera,user,nombre,cedula,telefono,email);
                        if(ctrl.addEstudiante(es) == 1){
                            success.setMsg("Estudiante Arregado!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Estudiante NO Agregado!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "EditarEstudiante": {
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
                        Carrera carrera = ctrl.getCarrera(idCarrera);
                        Estudiante es = new Estudiante(cal,carrera,user,nombre,cedula,telefono,email);
                        if(ctrl.updateEstudiante(es) == 1){
                            success.setMsg("Estudiante Actualizado!");
                            response.getWriter().write(gson.toJson(success));
                        }else{
                            error.setMsg("ERROR: Estudiante NO Actualizado!");
                            response.getWriter().write(gson.toJson(error));
                        }
                    }
                    break;
                    case "BuscarEstudiante": {
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
                    case "AgregarCurso": {
                        String codigo = request.getParameter("codigo");
                        String nombre = request.getParameter("nombre");
                        String creditos = request.getParameter("creditos");
                        String horasSemanales = request.getParameter("horasSemanales");
                        String idCarrera = request.getParameter("idCarrera");
                        String nivel = request.getParameter("nivel");
                        String ciclo = request.getParameter("ciclo");
                        Carrera ca = ctrl.getCarrera(idCarrera);
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
                    break;
                    case "EditarCurso": {
                        String codigo = request.getParameter("codigo");
                        String nombre = request.getParameter("nombre");
                        String creditos = request.getParameter("creditos");
                        String horasSemanales = request.getParameter("horasSemanales");
                        String idCarrera = request.getParameter("idCarrera");
                        String nivel = request.getParameter("nivel");
                        String ciclo = request.getParameter("ciclo");
                        Carrera ca = ctrl.getCarrera(idCarrera);
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
                    break;
                    case "BuscarCurso": {
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
//                    case "AgregarGrupo": {
//                        String idCurso = request.getParameter("idCurso");
//                        String numeroCiclo = request.getParameter("numeroCiclo");
//                        String numero = request.getParameter("numero");
//                        String[] dias = request.getParameterValues("dias");
//                        String horaInicio = request.getParameter("horaInicio");
//                        String horaFinal = request.getParameter("horaFinal");
//                        String idProfesor = request.getParameter("idProfesor");
//                        String anioCiclo = request.getParameter("anioCiclo");
//                        String diasStr = "";
//                        for(String s : dias){
//                            diasStr = diasStr +" "+ s;
//                        }
//                        Horario hora = new Horario(diasStr,horaInicio,horaFinal);
//                        Profesor profe = ctrl.getProfesor(idProfesor);
//                        Curso cur = ctrl.getCurso(idCurso);
//                        Ciclo ci = new Ciclo(Integer.parseInt(anioCiclo),numeroCiclo);
//                        Grupo gru = new Grupo(Integer.parseInt(numero),hora,profe,cur,ci);
////                        this.printHTML(gru.toString(), response);
//                        if(ctrl.addGrupo(gru) == 1){
//                            grupos = ctrl.gruposPorCurso(cur);
//                            session.setAttribute("grupos", grupos);
//                            session.setAttribute("errores", "");
//                            response.sendRedirect("adminGrupos.jsp");
//                        }else{
//                            String errores = "ERROR: Grupo NO Agregado!";
//                            session.setAttribute("errores", errores);
//                            response.sendRedirect("adminGrupos.jsp");
//                        }
//                    }
//                    break;
//                    case "EditarGrupo": {
//                        String idGrupo= request.getParameter("idGrupo");
//                        String idCurso = request.getParameter("idCurso");
//                        String numeroCiclo = request.getParameter("numeroCiclo");
//                        String numero = request.getParameter("numero");
//                        String[] dias = request.getParameterValues("dias");
//                        String horaInicio = request.getParameter("horaInicio");
//                        String horaFinal = request.getParameter("horaFinal");
//                        String idProfesor = request.getParameter("idProfesor");
//                        String anioCiclo = request.getParameter("anioCiclo");
//                        String diasStr = "";
//                        for(String s : dias){
//                            diasStr = diasStr +" "+ s;
//                        }
//                        Horario hora = new Horario(diasStr,horaInicio,horaFinal);
//                        Profesor profe = ctrl.getProfesor(idProfesor);
//                        Curso cur = ctrl.getCurso(idCurso);
//                        Ciclo ci = new Ciclo(Integer.parseInt(anioCiclo),numeroCiclo);
//                        Grupo gru = new Grupo(Integer.parseInt(idGrupo),Integer.parseInt(numero),hora,profe,cur,ci);
//                        if(ctrl.updateGrupo(gru) == 1){
//                            grupos = ctrl.gruposPorCurso(cur);
//                            session.setAttribute("grupos", grupos);
//                            session.setAttribute("errores", "");
//                            response.sendRedirect("adminGrupos.jsp");
//                        }else{
//                            String errores = "ERROR: Grupo NO Actualizado!";
//                            session.setAttribute("errores", errores);
//                            response.sendRedirect("adminGrupos.jsp");
//                        }
//                    }
//                    break;
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
    
    public void printHTML(String msg, HttpServletResponse response) throws IOException{
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Mensaje</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>"+msg+"</h1>");
        out.println("</body>");
        out.println("</html>");
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
