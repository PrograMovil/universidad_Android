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

import com.gestion_academica.Adapters.AdapterAdministrador;
import com.gestion_academica.Fragments_Agregar.agregarAdministradorFragment;
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
import java.util.List;

import LogicaNegocio.Administrador;
import LogicaNegocio.Usuario;


/**
 * A simple {@link Fragment} subclass.
 */
public class administradoresFragment extends Fragment{
    String urlRequest;
    String result = "";
    String mCedula=null;
    String mNombre=null;


    public administradoresFragment(){

    }

    public static administradoresFragment newInstance(String cedula, String nombre) {
        administradoresFragment fragment = new administradoresFragment();
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
        View v=inflater.inflate(R.layout.fragment_administradores, container, false);


        return v;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerV=(RecyclerView) view.findViewById(R.id.listAdministrador);
        ImageButton botonBuscar=(ImageButton) view.findViewById(R.id.floatingBuscarAdministrador);
        FloatingActionButton botonAgregar=(FloatingActionButton) view.findViewById(R.id.floatingAgregarAdministrador);


        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment=new agregarAdministradorFragment();
                if(v.getContext() instanceof Inicio){
                    FragmentTransaction fragmentTransaction=((Inicio) v.getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaAdministradores").commit();

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
                            administradoresFragment fragment=new administradoresFragment().newInstance(result,null);
                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, fragment).addToBackStack("ListaAdministradores").commit();
                        }
                        else if(selected.equals("nombre")){
                            administradoresFragment fragment=new administradoresFragment().newInstance(null,result);
                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, fragment).addToBackStack("ListaAdministradores").commit();
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





        String urlBase = Variables.getURLBase();

        if(mCedula==null && mNombre==null){
            urlRequest = urlBase + "action=AllAdministradores";
        }
        else if(mCedula!=null && mNombre==null){
            urlRequest = urlBase + "action=BuscarAdministrador&cedula="+mCedula+"&nombre=";
        }
        else urlRequest = urlBase + "action=BuscarAdministrador&cedula=&nombre="+mNombre;

        new administradoresFragment.AdministradorTask(view.getContext(),recyclerV).execute();
    }


    class AdministradorTask extends AsyncTask<String, String, String>
    {
        RecyclerView mRecyclerV;
        Context mContex;
        public AdministradorTask(Context contex, RecyclerView rview)
        {
            this.mRecyclerV=rview;
            this.mContex=contex;
        }


        @Override
        protected void onPostExecute(String result) {

            JSONArray dataArray = null;
            ArrayList<Administrador> administradores = new ArrayList<Administrador>();
            try {
                if (result!=null){
                    administradores.clear();
                    dataArray = new JSONArray(result);
                    Administrador prof;
                    for(int i=0; i<dataArray.length(); i++){

                        Usuario user=new Usuario();
                        JSONObject u=dataArray.getJSONObject(i).getJSONObject("usuario");
                        user.setId(u.getString("id"));
                        user.setClave(u.getString("clave"));
                        user.setTipo(u.getInt("tipo"));

                        prof = new Administrador();
                        prof.setNombre(dataArray.getJSONObject(i).getString("nombre"));
                        prof.setCedula(dataArray.getJSONObject(i).getString("cedula"));
                        prof.setTelefono(dataArray.getJSONObject(i).getString("telefono"));
                        prof.setEmail(dataArray.getJSONObject(i).getString("email"));

                        prof.setUsuario(user);

                        administradores.add(prof);
                    }
                    AdapterAdministrador adapter=new AdapterAdministrador(mContex, administradores);
                    mRecyclerV.setLayoutManager(new LinearLayoutManager(mContex));
                    mRecyclerV.setAdapter(adapter);

                }
                else Toast.makeText(mContex, "Error al consultar la base de datos", Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params)
        {
            try {
                URL url = new URL(urlRequest);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String valueResult = bf.readLine();

                result = valueResult;

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
