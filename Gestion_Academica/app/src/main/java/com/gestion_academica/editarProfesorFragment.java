package com.gestion_academica;


import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import LogicaNegocio.Profesor;


public class editarProfesorFragment extends Fragment {

    Profesor profesor;
    String urlRequest;
    String result;

    public editarProfesorFragment() {
        // Required empty public constructor
    }

    public static editarProfesorFragment newInstance(Profesor prof){
        editarProfesorFragment newFragment=new editarProfesorFragment();
        newFragment.setProfesor(prof);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_profesor, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final TextView cedula=(TextView) view.findViewById(R.id.cedulaProfesor);
        final EditText nombre = (EditText) view.findViewById(R.id.nombreProfesor);
        final EditText telefono = (EditText) view.findViewById(R.id.telefonoProfesor);
        final EditText email = (EditText) view.findViewById(R.id.emailProfesor);
        final EditText pass = (EditText) view.findViewById(R.id.contrasenaProfesor);
        Button botonGuardar=(Button) view.findViewById(R.id.botonGuardarProfesor);

        cedula.setText(profesor.getCedula());
        nombre.setText(profesor.getNombre());
        telefono.setText(profesor.getTelefono());
        email.setText(profesor.getEmail());
        pass.setText(profesor.getUsuario().getClave());

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pass.getWindowToken(), 0);

                final View vi=v;
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Confirmacion");
                alert.setMessage("Desea editar este profesor?");
                alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //reiniciar los errores
                        cedula.setError(null);
                        nombre.setError(null);
                        telefono.setError(null);
                        email.setError(null);
                        pass.setError(null);
                        String ced=cedula.getText().toString();
                        String nom=nombre.getText().toString();
                        String tel=telefono.getText().toString();
                        String ema=email.getText().toString();
                        String con=pass.getText().toString();

                        boolean cancel=false;
                        View focusView=null;

                        if(TextUtils.isEmpty(ced)){
                            cedula.setError("Cedula Vacio");
                            focusView=cedula;
                            cancel=true;
                        }

                        if(TextUtils.isEmpty(nom)){
                            nombre.setError("Nombre Vacio");
                            focusView=nombre;
                            cancel=true;
                        }

                        if(TextUtils.isEmpty(tel)){
                            telefono.setError("Telefono Vacio");
                            focusView=telefono;
                            cancel=true;
                        }
                        if(TextUtils.isEmpty(ema)){
                            email.setError("Email Vacio");
                            focusView=email;
                            cancel=true;
                        }
                        if(TextUtils.isEmpty(con)){
                            pass.setError("Contraseña Vacio");
                            focusView=pass;
                            cancel=true;
                        }


                        if (cancel) {
                            focusView.requestFocus();
                        } else {

                            String urlBase = Variables.getURLBase();
                            urlRequest = urlBase + "action=EditarProfesor"+"&cedula="+ced+"&nombre="+nom+"&telefono="+tel+"&email="+ema+"&password="+con;
                            new asyncTask(vi.getContext(),getFragmentManager(),urlRequest).execute();
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






    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
}
