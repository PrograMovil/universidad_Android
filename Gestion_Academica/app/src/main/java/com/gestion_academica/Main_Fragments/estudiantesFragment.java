package com.gestion_academica.Main_Fragments;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.gestion_academica.Adapters.AdapterEstudiante;
import com.gestion_academica.Fragments_Agregar.agregarEstudianteFragment;
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
import LogicaNegocio.Estudiante;
import LogicaNegocio.Usuario;

public class estudiantesFragment extends Fragment{
    String urlRequest;
    static String urlBase = Variables.getURLBase();
    ArrayList<String> result = new ArrayList<>();
    String mCedula=null;
    String mNombre=null;
    ArrayList<Carrera> mCarreras=new ArrayList<>();


    public estudiantesFragment(){
        Log.e("TagConexionEstudiante", "Contructor estudiante fragment");
    }

    public static estudiantesFragment newInstance(String cedula, String nombre) {
        Log.e("TagConexionEstudiante", "Contructor newInstance estudiante fragment");
        estudiantesFragment fragment = new estudiantesFragment();
        if(cedula!=null)
            fragment.setmCedula(cedula);
        if(nombre!=null)
            fragment.setmNombre(nombre);

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
        View v=inflater.inflate(R.layout.fragment_estudiantes, container, false);


        return v;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerV=(RecyclerView) view.findViewById(R.id.listEstudiante);
        ImageButton botonBuscar=(ImageButton) view.findViewById(R.id.floatingBuscarEstudiante);
        FloatingActionButton botonAgregar=(FloatingActionButton) view.findViewById(R.id.floatingAgregarEstudiante);

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment=new agregarEstudianteFragment().newInstance(mCarreras);
                if(v.getContext() instanceof Inicio){
                    FragmentTransaction fragmentTransaction=((Inicio) v.getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaEstudiantes").commit();

                }
            }
        });


        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());


                LinearLayout layout=new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);


                final TextView texto1 = new TextView(v.getContext());
                texto1.setText("Elija el tipo de busqueda");
                texto1.setTextSize(18);
                layout.addView(texto1);

                final Spinner spin=new Spinner(v.getContext());
                List<String> listaSpin=new ArrayList<String>();
                listaSpin.add("cedula");
                listaSpin.add("nombre");
                ArrayAdapter<String> adapter =new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_item,listaSpin);
                spin.setAdapter(adapter);
                layout.addView(spin);


                final EditText busqueda = new EditText(v.getContext());
                busqueda.setMaxLines(1);
                busqueda.setSingleLine(true);
                busqueda.setHint("Busqueda");
                layout.addView(busqueda);

                alert.setView(layout);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String result = busqueda.getText().toString();
                        String selected = spin.getSelectedItem().toString();
                        if (selected.equals("cedula")) {
                            estudiantesFragment fragment=new estudiantesFragment().newInstance(result,null);
                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, fragment).addToBackStack("ListaEstudiantes").commit();
                        }
                        else if(selected.equals("nombre")){
                            estudiantesFragment fragment=new estudiantesFragment().newInstance(null,result);
                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, fragment).addToBackStack("ListaEstudiantes").commit();
                        }
                    }
                });
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();


            }
        });







        if(mCedula==null && mNombre==null){
            Log.e("TagConexionEstudiante", "Mostrar todos los estudiantes");
            urlRequest = urlBase + "action=AllEstudiantes";
        }
        else if(mCedula!=null && mNombre==null){
            urlRequest = urlBase + "action=BuscarEstudiante&cedula="+mCedula+"&nombre=";
        }
        else urlRequest = urlBase + "action=BuscarEstudiante&cedula=&nombre="+mNombre;


        new EstudianteTask(view.getContext(),recyclerV).execute();
    }


    class EstudianteTask extends AsyncTask<String, String, List<String>>
    {
        RecyclerView mRecyclerV;
        Context mContex;
        public EstudianteTask(Context contex, RecyclerView rview)
        {
            this.mRecyclerV=rview;
            this.mContex=contex;
        }


        @Override
        protected void onPostExecute(List<String> result) {

            JSONArray dataArray = null;
            JSONArray dataArray2 = null;
            ArrayList<Estudiante> estudiantes=new ArrayList<>();
            try {
                if (result.get(0)!=null && result.get(1)!=null){
                    estudiantes.clear();
                    mCarreras.clear();
                    Log.e("TagConexionEstudiante", "Cargando Estudiantes a la nueva lista");
                    dataArray = new JSONArray(result.get(0));
                    Estudiante prof;
                    for(int i=0; i<dataArray.length(); i++){
                        prof = new Estudiante();
                        Usuario user=new Usuario();
                        Carrera car=new Carrera();


                        JSONObject u=dataArray.getJSONObject(i).getJSONObject("usuario");
                        user.setId(u.getString("id"));
                        user.setClave(u.getString("clave"));
                        user.setTipo(u.getInt("tipo"));
                        prof.setUsuario(user);

                        Calendar fechaNa = new GregorianCalendar();
                        JSONObject objFecha=dataArray.getJSONObject(i).getJSONObject("fechaNac");
                        fechaNa.set(objFecha.getInt("year"),objFecha.getInt("month"),objFecha.getInt("dayOfMonth"));
                        prof.setFechaNac(fechaNa);


                        JSONObject u2=dataArray.getJSONObject(i).getJSONObject("carrera");
                        car.setCodigo(u2.getString("codigo"));
                        car.setNombre(u2.getString("nombre"));
                        car.setTitulo(u2.getString("titulo"));
                        prof.setCarrera(car);

                        prof.setNombre(dataArray.getJSONObject(i).getString("nombre"));
                        prof.setCedula(dataArray.getJSONObject(i).getString("cedula"));
                        prof.setTelefono(dataArray.getJSONObject(i).getString("telefono"));
                        prof.setEmail(dataArray.getJSONObject(i).getString("email"));


                        estudiantes.add(prof);
                    }

                    Log.e("TagConexionEstudiante", "Lista:Estudiante 1:"+ estudiantes.get(0).getNombre());
                    Log.e("TagConexionEstudiante", "JSON:Estudiante 1:"+ dataArray.getJSONObject(0).getString("nombre"));
                    dataArray2 = new JSONArray(result.get(1));
                    for(int i=0; i<dataArray2.length(); i++){
                        Carrera carr=new Carrera();
                        carr.setTitulo(dataArray2.getJSONObject(i).getString("titulo"));
                        carr.setCodigo(dataArray2.getJSONObject(i).getString("codigo"));
                        carr.setNombre(dataArray2.getJSONObject(i).getString("nombre"));
                        mCarreras.add(carr);
                    }
                    Log.e("TagConexionEstudiante", "Lista de estudiantes y carreras lista");

                    AdapterEstudiante adapter=new AdapterEstudiante(mContex, estudiantes, mCarreras);
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
                Log.e("TagConexionEstudiante", "urlRequest:"+urlRequest);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String valueResult = bf.readLine();

                result.add(valueResult);
                Log.e("TagConexionEstudiante", "Agregado el result 1:"+valueResult);

                URL url2 = new URL(urlBase+"action=AllCarreras");
                Log.e("TagConexionEstudiante", "urlRequest:"+url2);
                HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                connection2.setRequestMethod("GET");
                connection2.connect();

                BufferedReader bf2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                String valueResult2 = bf2.readLine();
                result.add(valueResult2);
                Log.e("TagConexionEstudiante", "Agregador el result 2");
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }





    public void setmCedula(String mCedula) {
        this.mCedula = mCedula;
    }

    public void setmNombre(String mNombre) {
        this.mNombre = mNombre;
    }
}
