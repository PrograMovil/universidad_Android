package com.gestion_academica;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import LogicaNegocio.Estudiante;


/**
 * A simple {@link Fragment} subclass.
 */
public class editarEstudianteFragment extends Fragment{

    Estudiante estudiante;

    public editarEstudianteFragment() {
        // Required empty public constructor
    }

    public static editarEstudianteFragment newInstance(Estudiante e){
        editarEstudianteFragment newFragment=new editarEstudianteFragment();
        newFragment.setEstudiante(e);
        return newFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_editar_estudiante, container, false);



        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView cedula=(TextView) view.findViewById(R.id.cedula);
        EditText nombre = (EditText) view.findViewById(R.id.nombre);
        EditText telefono = (EditText) view.findViewById(R.id.telefono);
        EditText email=(EditText) view.findViewById(R.id.email);
        Button botonGuardar=(Button) view.findViewById(R.id.botonGuardar);

        cedula.setText(estudiante.getCedula());
        nombre.setText(estudiante.getNombre());
        telefono.setText(estudiante.getTelefono());
        email.setText(estudiante.getEmail());


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




    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
