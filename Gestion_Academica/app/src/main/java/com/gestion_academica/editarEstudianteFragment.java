package com.gestion_academica;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import LogicaNegocio.Estudiante;


/**
 * A simple {@link Fragment} subclass.
 */
public class editarEstudianteFragment extends Fragment{

    Estudiante estudiante;
    Calendar myCalendar = Calendar.getInstance();
    TextView fechaNa;

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
        fechaNa=(TextView) view.findViewById(R.id.fechaNac);
        Button botonGuardar=(Button) view.findViewById(R.id.botonGuardar);
        TextView fechaText=(TextView) view.findViewById(R.id.fechaNacText);

        cedula.setText(estudiante.getCedula());
        nombre.setText(estudiante.getNombre());
        telefono.setText(estudiante.getTelefono());
        email.setText(estudiante.getEmail());
        //fechaNa.setText(estudiante.getFechaNac());


        fechaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fechaNa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fechaNa.setText(sdf.format(myCalendar.getTime()));
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };


    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
