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

import com.gestion_academica.Adapters.AdapterCarrera;
import com.gestion_academica.Fragments_Agregar.agregarCarreraFragment;
import com.gestion_academica.Inicio;
import com.gestion_academica.R;
import com.gestion_academica.Variables;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import LogicaNegocio.Carrera;


public class carrerasFragment extends Fragment {


    String urlRequest;
    String result = "";
    String mcodigo=null;
    String mnombre=null;

    public carrerasFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static carrerasFragment newInstance(String codigo, String nombre) {
        carrerasFragment fragment = new carrerasFragment();
        if(codigo!=null)
            fragment.setMcodigo(codigo);
        if(nombre!=null)
            fragment.setMnombre(nombre);

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
        View v=inflater.inflate(R.layout.fragment_carreras, container, false);


        return v;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerV=(RecyclerView) view.findViewById(R.id.listCarrera);

        ImageButton botonBuscar=(ImageButton) view.findViewById(R.id.floatingBuscarCarrera);
        FloatingActionButton botonAgregar=(FloatingActionButton) view.findViewById(R.id.floatingAgregarCarrera);

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment=new agregarCarreraFragment();
                if(v.getContext() instanceof Inicio){
                    FragmentTransaction fragmentTransaction=((Inicio) v.getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaCarreras").commit();

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
                listaSpin.add("codigo");
                listaSpin.add("nombre");
                ArrayAdapter<String> adapter =new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_item,listaSpin);
                spin.setAdapter(adapter);
                layout.addView(spin);


                final EditText busqueda = new EditText(v.getContext());
                busqueda.setHint("Busqueda");
                busqueda.setMaxLines(1);
                busqueda.setSingleLine(true);
                layout.addView(busqueda);

                alert.setView(layout);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String result = busqueda.getText().toString();
                        String selected = spin.getSelectedItem().toString();
                        if (selected.equals("codigo")) {
                            carrerasFragment fragment=new carrerasFragment().newInstance(result,null);
                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, fragment).addToBackStack("ListaCarreras").commit();
                        }
                        else if(selected.equals("nombre")){
                            carrerasFragment fragment=new carrerasFragment().newInstance(null,result);
                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, fragment).addToBackStack("ListaCarreras").commit();
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

        if(mcodigo==null && mnombre==null){
        urlRequest = urlBase + "action=AllCarreras";
        }
        else if(mcodigo!=null && mnombre==null){
            urlRequest = urlBase + "action=BuscarCarrera&codigo="+mcodigo+"&nombre=";
        }
        else urlRequest = urlBase + "action=BuscarCarrera&codigo=&nombre="+mnombre;

        new CarrerasTask(view.getContext(),recyclerV).execute();
    }



    class CarrerasTask extends AsyncTask<String, String, String>
    {
        RecyclerView mRecyclerV;
        Context mContex;
        public CarrerasTask(Context contex, RecyclerView rview)
        {
            this.mRecyclerV=rview;
            this.mContex=contex;
        }


        @Override
        protected void onPostExecute(String result) {

            JSONArray dataArray = null;
            ArrayList<Carrera> carreras = new ArrayList<Carrera>();
            try {
                if (result!=null){
                        carreras.clear();
                        dataArray = new JSONArray(result);
                        Carrera car;
                        for(int i=0; i<dataArray.length(); i++){
                            car = new Carrera();
                            car.setCodigo(dataArray.getJSONObject(i).getString("codigo"));
                            car.setNombre(dataArray.getJSONObject(i).getString("nombre"));
                            car.setTitulo(dataArray.getJSONObject(i).getString("titulo"));
                            carreras.add(car);
                        }
                        AdapterCarrera adapter=new AdapterCarrera(mContex, carreras);
                        mRecyclerV.setAdapter(adapter);
                        mRecyclerV.setLayoutManager(new LinearLayoutManager(mContex));
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


    public void setMcodigo(String mcodigo) {
        this.mcodigo = mcodigo;
    }

    public void setMnombre(String mnombre) {
        this.mnombre = mnombre;
    }
}
