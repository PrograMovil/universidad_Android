package com.gestion_academica.Main_Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gestion_academica.Adapters.AdapterGrupo;
//import com.gestion_academica.Fragments_Agregar.agregarGrupoFragment;
import com.gestion_academica.Inicio;
import com.gestion_academica.R;
import com.gestion_academica.Variables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import LogicaNegocio.Carrera;
import LogicaNegocio.Ciclo;
import LogicaNegocio.Curso;
import LogicaNegocio.Grupo;
import LogicaNegocio.Horario;
import LogicaNegocio.Profesor;
import LogicaNegocio.Usuario;


/**
 * A simple {@link Fragment} subclass.
 */
public class gruposFragment extends Fragment{
    String urlRequest;
    static String urlBase = Variables.getURLBase();
    ArrayList<String> result = new ArrayList<>();
    ArrayList<Horario> mHorarios = new ArrayList<>();
    ArrayList<Curso> mCursos = new ArrayList<>();
    ArrayList<Profesor> mProfesores = new ArrayList<>();
    ArrayList<Ciclo> mCiclos=new ArrayList<>();
    Curso mCurso=null;


    public gruposFragment(){
        Log.e("TagConexionGrupo", "Contructor grupo fragment");
    }

    public static gruposFragment newInstance(Curso c) {
        Log.e("TagConexionGrupo", "Contructor newInstance grupo fragment");
        gruposFragment fragment = new gruposFragment();

        if(c!=null)
            fragment.setmCurso(c);

        Bundle args = new Bundle();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_grupos, container, false);


        return v;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        RecyclerView recyclerV=(RecyclerView) view.findViewById(R.id.listGrupo);
        //ImageButton botonBuscar=(ImageButton) view.findViewById(R.id.floatingBuscarGrupo);
        FloatingActionButton botonAgregar=(FloatingActionButton) view.findViewById(R.id.floatingAgregarGrupo);

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment newFragment=new agregarGrupoFragment().newInstance(mHorarios,mCursos,mProfesores,mCiclos);
                if(v.getContext() instanceof Inicio){
                    FragmentTransaction fragmentTransaction=((Inicio) v.getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaGrupos").commit();

                }*/
            }
        });

        if(mCurso!=null){
            Log.e("TagConexionCurso", "Mostrar grupos por curso");
            urlRequest = urlBase + "action=AllGruposPorCurso&codigoCurso="+mCurso.getCodigo();
        }
        else{
            urlRequest = urlBase + "action=AllGruposPorCurso&codigoCurso=EIF-200";
        }


        new gruposFragment.GrupoTask(view.getContext(),recyclerV).execute();
    }


    class GrupoTask extends AsyncTask<String, String, List<String>>
    {
        RecyclerView mRecyclerV;
        Context mContex;
        public GrupoTask(Context contex, RecyclerView rview)
        {
            this.mRecyclerV=rview;
            this.mContex=contex;
        }


        @Override
        protected void onPostExecute(List<String> result) {

            JSONArray dataArray = null;
            JSONArray dataArrayHorarios = null;
            JSONArray dataArrayCursos = null;
            JSONArray dataArrayProfesores = null;
            JSONArray dataArrayCiclos = null;
            ArrayList<Grupo> grupos=new ArrayList<>();

            try {
                if (result.get(0)!=null && result.get(1)!=null){

                    grupos.clear();
                    mHorarios.clear();
                    mCursos.clear();
                    mProfesores.clear();
                    mCiclos.clear();

                    Log.e("TagConexionGrupo", "Cargando Grupos a la nueva lista");
                    dataArray = new JSONArray(result.get(0));
                    Grupo grup;
                    Horario h;
                    Curso c;
                    Profesor p;
                    Ciclo ci;
                    for(int i=0; i<dataArray.length(); i++){

                        grup = new Grupo();
                        h=new Horario();
                        c=new Curso();
                        p=new Profesor();
                        ci=new Ciclo();


                        JSONObject Jhorario=dataArray.getJSONObject(i).getJSONObject("horario");
                        h.setDias(Jhorario.getString("dias"));
                        h.setHoraInicial(Jhorario.getString("horaInicial"));
                        h.setHoraFinal(Jhorario.getString("horaFinal"));
                        grup.setHorario(h);


                        JSONObject JCurso=dataArray.getJSONObject(i).getJSONObject("curso");
                        c.setCodigo(JCurso.getString("codigo"));
                        c.setNombre(JCurso.getString("nombre"));
                        c.setCreditos(JCurso.getInt("creditos"));
                        c.setHorasSemanales(JCurso.getInt("horasSemanales"));
                        JSONObject JCursoCarrera=JCurso.getJSONObject("carrera");
                        Carrera carr=new Carrera(JCursoCarrera.getString("codigo"),JCursoCarrera.getString("nombre"),JCursoCarrera.getString("titulo"));
                        c.setCarrera(carr);
                        c.setNivel(JCurso.getString("nivel"));
                        c.setCiclo(JCurso.getString("ciclo"));
                        grup.setCurso(c);

                        JSONObject JProfesor=dataArray.getJSONObject(i).getJSONObject("profesor");
                        p.setNombre(JProfesor.getString("nombre"));
                        p.setEmail(JProfesor.getString("email"));
                        p.setCedula(JProfesor.getString("cedula"));
                        p.setTelefono(JProfesor.getString("telefono"));
                        JSONObject JProfesorUsuario=JProfesor.getJSONObject("usuario");
                        Usuario us=new Usuario(JProfesorUsuario.getString("id"),JProfesorUsuario.getString("clave"),JProfesorUsuario.getInt("tipo"));
                        p.setUsuario(us);

                        JSONObject JCiclo=dataArray.getJSONObject(i).getJSONObject("ciclo");
                        ci.setAnio(JCiclo.getInt("anio"));
                        ci.setNumero(JCiclo.getString("numero"));
                        ci.setFechaInicio(JCiclo.getString("fechaInicio"));
                        ci.setFechaFinalizacion(JCiclo.getString("fechaFinalizacion"));

                        grup.setHorario(h);
                        grup.setCurso(c);
                        grup.setProfesor(p);
                        grup.setCiclo(ci);

                        grup.setId(dataArray.getJSONObject(i).getInt("id"));
                        grup.setNumero(dataArray.getJSONObject(i).getInt("numero"));


                        grupos.add(grup);
                    }



                    dataArrayHorarios = new JSONArray(result.get(1));
                    for(int i=0; i<dataArrayHorarios.length(); i++){
                        Horario hor=new Horario();
                        hor.setDias(dataArrayHorarios.getJSONObject(i).getString("dias"));
                        hor.setHoraInicial(dataArrayHorarios.getJSONObject(i).getString("horaInicial"));
                        hor.setHoraFinal(dataArrayHorarios.getJSONObject(i).getString("horaFinal"));
                        mHorarios.add(hor);
                    }


                    dataArrayCursos = new JSONArray(result.get(2));
                    for(int i=0; i<dataArrayCursos.length(); i++){
                        Curso curr=new Curso();
                        JSONObject JCurso2=dataArrayCursos.getJSONObject(i);
                        curr.setCodigo(JCurso2.getString("codigo"));
                        curr.setNombre(JCurso2.getString("nombre"));
                        curr.setCreditos(JCurso2.getInt("creditos"));
                        curr.setHorasSemanales(JCurso2.getInt("horasSemanales"));
                        JSONObject JCursoCarrera=JCurso2.getJSONObject("carrera");
                        Carrera carr=new Carrera(JCursoCarrera.getString("codigo"),JCursoCarrera.getString("nombre"),JCursoCarrera.getString("titulo"));
                        curr.setCarrera(carr);
                        curr.setNivel(JCurso2.getString("nivel"));
                        curr.setCiclo(JCurso2.getString("ciclo"));
                        mCursos.add(curr);
                    }

                    dataArrayProfesores = new JSONArray(result.get(3));
                    for(int i=0; i<dataArrayProfesores.length(); i++){
                        Profesor p2=new Profesor();
                        JSONObject JProfesor=dataArrayProfesores.getJSONObject(i);
                        p2.setNombre(JProfesor.getString("nombre"));
                        p2.setEmail(JProfesor.getString("email"));
                        p2.setCedula(JProfesor.getString("cedula"));
                        p2.setTelefono(JProfesor.getString("telefono"));
                        JSONObject JProfesorUsuario=JProfesor.getJSONObject("usuario");
                        Usuario us=new Usuario(JProfesorUsuario.getString("id"),JProfesorUsuario.getString("clave"),JProfesorUsuario.getInt("tipo"));
                        p2.setUsuario(us);
                        mProfesores.add(p2);
                    }

                    dataArrayCiclos = new JSONArray(result.get(4));
                    for(int i=0; i<dataArrayCiclos.length(); i++){
                        Ciclo ci2=new Ciclo();
                        JSONObject JCiclo=dataArrayCiclos.getJSONObject(i);
                        ci2.setAnio(JCiclo.getInt("anio"));
                        ci2.setNumero(JCiclo.getString("numero"));
                        ci2.setFechaInicio(JCiclo.getString("fechaInicio"));
                        ci2.setFechaFinalizacion(JCiclo.getString("fechaFinalizacion"));
                        mCiclos.add(ci2);
                    }


                    AdapterGrupo adapter=new AdapterGrupo(mContex, grupos, mCiclos,mProfesores,mCursos,mHorarios);
                    mRecyclerV.setLayoutManager(new LinearLayoutManager(mContex));
                    mRecyclerV.setAdapter(adapter);

                }
                else Toast.makeText(mContex, "Error al consultar la base de datos", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected List<String> doInBackground(String... params)
        {

            try {
                result=new ArrayList<>();
                URL url = new URL(urlRequest);
                Log.e("TagConexionGrupo", "urlRequest:"+urlRequest);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String valueResult = bf.readLine();
                result.add(valueResult);
                Log.e("TagConexionGrupo", "Agregado el result 1 de Grupos por curso");



                URL url2 = new URL(urlBase+"action=AllHorarios");
                Log.e("TagConexionGrupo", "urlRequest:"+url2);
                HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                connection2.setRequestMethod("GET");
                connection2.connect();
                BufferedReader bf2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                String valueResult2 = bf2.readLine();
                result.add(valueResult2);
                Log.e("TagConexionGrupo", "Agregador el result 2 de horarios");


                URL url3 = new URL(urlBase+"action=AllCursos");
                Log.e("TagConexionGrupo", "urlRequest:"+url3);
                HttpURLConnection connection3 = (HttpURLConnection) url3.openConnection();
                connection3.setRequestMethod("GET");
                connection3.connect();
                BufferedReader bf3 = new BufferedReader(new InputStreamReader(connection3.getInputStream()));
                String valueResult3 = bf3.readLine();
                result.add(valueResult3);
                Log.e("TagConexionGrupo", "Agregador el result 3 de Cursos");


                URL url4 = new URL(urlBase+"action=AllProfesores");
                Log.e("TagConexionGrupo", "urlRequest:"+url4);
                HttpURLConnection connection4 = (HttpURLConnection) url4.openConnection();
                connection4.setRequestMethod("GET");
                connection4.connect();
                BufferedReader bf4 = new BufferedReader(new InputStreamReader(connection4.getInputStream()));
                String valueResult4 = bf4.readLine();
                result.add(valueResult4);
                Log.e("TagConexionGrupo", "Agregador el result 4 de Profesores");

                URL url5 = new URL(urlBase+"action=AllCiclos");
                Log.e("TagConexionGrupo", "urlRequest:"+url5);
                HttpURLConnection connection5 = (HttpURLConnection) url5.openConnection();
                connection5.setRequestMethod("GET");
                connection5.connect();
                BufferedReader bf5 = new BufferedReader(new InputStreamReader(connection5.getInputStream()));
                String valueResult5 = bf5.readLine();
                result.add(valueResult5);
                Log.e("TagConexionGrupo", "Agregador el result 5 de Ciclos");

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    public void setmCurso(Curso mCurso) {
        this.mCurso = mCurso;
    }

}