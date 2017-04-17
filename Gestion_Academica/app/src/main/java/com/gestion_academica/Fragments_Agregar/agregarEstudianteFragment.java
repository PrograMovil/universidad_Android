package com.gestion_academica.Fragments_Agregar;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.gestion_academica.R;
import com.gestion_academica.Variables;
import com.gestion_academica.asyncTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import LogicaNegocio.Carrera;


public class agregarEstudianteFragment extends Fragment {

    String urlRequest;
    String result;
    ArrayList<Carrera> mCarreras;
    TextView fechaNac;
    Calendar myCalendar = Calendar.getInstance();

    public agregarEstudianteFragment() {
        // Required empty public constructor
    }

    public static agregarEstudianteFragment newInstance(ArrayList<Carrera> carreras){
        agregarEstudianteFragment newFragment=new agregarEstudianteFragment();
        newFragment.setmCarreras(carreras);
        return newFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_estudiante, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final EditText cedula=(EditText) view.findViewById(R.id.cedulaEstudiante);
        final EditText nombre = (EditText) view.findViewById(R.id.nombreEstudiante);
        final EditText telefono = (EditText) view.findViewById(R.id.telefonoEstudiante);
        final EditText email = (EditText) view.findViewById(R.id.emailEstudiante);
        final EditText pass = (EditText) view.findViewById(R.id.contrasenaEstudiante);
        fechaNac = (TextView) view.findViewById(R.id.fechaNac);
        final Spinner spinCarreras=(Spinner) view.findViewById(R.id.spinnerEstudiante);
        Button botonGuardar=(Button) view.findViewById(R.id.botonGuardarEstudiante);

        SpannableString content = new SpannableString("Eligir");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        fechaNac.setText(content);



        ArrayList<String> nombresCarreras=new ArrayList<>();
        for (int i=0;i<mCarreras.size();i++)
            nombresCarreras.add(mCarreras.get(i).getNombre());

        final ArrayAdapter<String> spinner_carreras = new  ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item, nombresCarreras);

        spinCarreras.setAdapter(spinner_carreras);


        fechaNac.setOnClickListener(new View.OnClickListener() {
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
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pass.getWindowToken(), 0);
                final View vi=v;
                //reiniciar los errores
                cedula.setError(null);
                nombre.setError(null);
                telefono.setError(null);
                email.setError(null);
                pass.setError(null);
                fechaNac.setError(null);


                final String ced=cedula.getText().toString();
                final String nom=nombre.getText().toString();
                final String tel=telefono.getText().toString();
                final String ema=email.getText().toString();
                final String con=pass.getText().toString();
                final String fech=fechaNac.getText().toString();

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
                if(fech.equals("Eligir")){
                    fechaNac.setError("Toque para elegir");
                    focusView=fechaNac;
                    cancel=true;
                }

                if (cancel) {
                    focusView.requestFocus();
                }
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Confirmacion");
                    alert.setMessage("Desea crear este estudiante?");
                    alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            String urlBase = Variables.getURLBase();

                            urlRequest = urlBase + "action=AgregarEstudiante" + "&cedula=" + ced + "&nombre=" + nom + "&telefono=" + tel + "&email=" + ema + "&password=" + con +"&fechaNac="+sdf.format(myCalendar.getTime())+ "&idCarrera="+ buscarCarrera(spinCarreras.getSelectedItem().toString());
                            new asyncTask(vi.getContext(), getFragmentManager(), urlRequest).execute();


                        }
                    });
                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
                    alert.show();
                }
            }
        });

    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fechaNac.setText(sdf.format(myCalendar.getTime()));
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

    private String buscarCarrera(String nombre){
        for(Carrera car:mCarreras){
            if(car.getNombre().equals(nombre))
                return car.getCodigo();
        }
        return "";
    }

    public void setmCarreras(ArrayList<Carrera> mCarreras) {
        this.mCarreras = mCarreras;
    }
}
