package com.gestion_academica;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import LogicaNegocio.Carrera;


/**
 * A simple {@link Fragment} subclass.
 */
public class editarCarreraFragment extends Fragment {

    Carrera carrera;

    public editarCarreraFragment() {
        // Required empty public constructor
    }

    public static editarCarreraFragment newInstance(Carrera carrera){
        editarCarreraFragment newFragment=new editarCarreraFragment();
        newFragment.setCarrera(carrera);
        return newFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_carrera, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        TextView codigo=(TextView) view.findViewById(R.id.codigoCarrera);
        EditText nombre = (EditText) view.findViewById(R.id.nombreCarrera);
        EditText titulo = (EditText) view.findViewById(R.id.tituloCarrera);
        Button botonGuardar=(Button) view.findViewById(R.id.botonGuardarCarrera);

        codigo.setText(carrera.getCodigo());
        nombre.setText(carrera.getNombre());
        titulo.setText(carrera.getTitulo());

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //se edita en la base
                //codigo para actualizar en base

                //se redirecciona a la lista de estudiantes
                getFragmentManager().popBackStackImmediate();
            }
        });

    }


    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }
}
