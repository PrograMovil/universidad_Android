package com.gestion_academica;


import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import LogicaNegocio.Carrera;


/**
 * A simple {@link Fragment} subclass.
 */
public class agregarCarreraFragment extends Fragment {

    String urlRequest;
    String result;

    public agregarCarreraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_carrera, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final EditText codigo=(EditText) view.findViewById(R.id.codigoCarrera);
        final EditText nombre = (EditText) view.findViewById(R.id.nombreCarrera);
        final EditText titulo = (EditText) view.findViewById(R.id.tituloCarrera);
        Button botonGuardar=(Button) view.findViewById(R.id.botonGuardarCarrera);




        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View vi=v;
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Confirmacion");
                alert.setMessage("Desea crear esta carrera?");
                alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //reiniciar los errores
                        codigo.setError(null);
                        nombre.setError(null);
                        titulo.setError(null);
                        String cod=codigo.getText().toString();
                        String nom=nombre.getText().toString();
                        String tit=titulo.getText().toString();

                        boolean cancel=false;
                        View focusView=null;

                        if(TextUtils.isEmpty(cod)){
                            codigo.setError("Codigo Vacio");
                            focusView=codigo;
                            cancel=true;
                        }

                        if(TextUtils.isEmpty(nom)){
                            nombre.setError("Nombre Vacio");
                            focusView=nombre;
                            cancel=true;
                        }

                        if(TextUtils.isEmpty(tit)){
                            titulo.setError("Titulo Vacio");
                            focusView=titulo;
                            cancel=true;
                        }

                        if (cancel) {
                            focusView.requestFocus();
                        } else {

                            String urlBase = Variables.getURLBase();
                            urlRequest = urlBase + "action=AgregarCarrera"+"&codigo="+cod+"&nombre="+nom+"&titulo="+tit;
                            new AgregarCarreraTask(vi.getContext()).execute();
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

    }

    public class AgregarCarreraTask extends AsyncTask<String,Void,String>{


        Context mContex;
        public AgregarCarreraTask(Context contex)
        {
            this.mContex=contex;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                if (result != null){
                    JSONObject data = new JSONObject(result);
                    String msg = data.getString("type");

                    if (msg.equals("Success")){
                        Toast.makeText(mContex, "Carrera agregada", Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStackImmediate();
                    } else {
                        Toast.makeText(mContex, "Error al agregar la carrera", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContex, "Error al consultar la base de datos", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(urlRequest);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String valueResult = bf.readLine();
                System.out.println("Resultao de agregar carrera: "+ valueResult);

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
    }


