package com.gestion_academica.Fragments_Editar;


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
import android.widget.TextView;

import com.gestion_academica.R;
import com.gestion_academica.Variables;
import com.gestion_academica.asyncTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import LogicaNegocio.Carrera;
import LogicaNegocio.Ciclo;
import LogicaNegocio.Curso;
import LogicaNegocio.Grupo;
import LogicaNegocio.Horario;
import LogicaNegocio.Profesor;


/**
 * A simple {@link Fragment} subclass.
 */
public class editarGrupoFragment extends Fragment {

    Grupo grupo;
    ArrayList<Ciclo> mCiclos;
    ArrayList<Profesor> mProfesores;
    ArrayList<Curso> mCursos;
    ArrayList<Horario> mHorarios;
    String urlRequest;
    String result;

    public editarGrupoFragment() {
        // Required empty public constructor
    }

    public static editarGrupoFragment newInstance(Grupo prof, ArrayList<Ciclo> ci, ArrayList<Profesor> pr, ArrayList<Curso> cu, ArrayList<Horario> ho){
        editarGrupoFragment newFragment=new editarGrupoFragment();
        newFragment.setmCiclos(ci);
        newFragment.setmProfesores(pr);
        newFragment.setmCursos(cu);
        newFragment.setmHorarios(ho);
        newFragment.setGrupo(prof);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_grupo, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final EditText numero = (EditText) view.findViewById(R.id.numeroGrupo);
        final TextView curso = (TextView) view.findViewById(R.id.cursoGrupo);
        final TextView profesor=(TextView) view.findViewById(R.id.profesorGrupo);
        final TextView ciclo=(TextView) view.findViewById(R.id.cicloGrupo);
        final TextView horario=(TextView) view.findViewById(R.id.horarioGrupo);
        Button botonGuardar=(Button) view.findViewById(R.id.botonGuardarGrupo);

        numero.setText(""+grupo.getNumero());

        SpannableString content = new SpannableString(grupo.getCurso().getNombre());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        curso.setText(content);

        SpannableString content2 = new SpannableString(grupo.getProfesor().getNombre());
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        profesor.setText(content2);

        SpannableString content3 = new SpannableString(grupo.getCiclo().getNumero()+" "+grupo.getCiclo().getAnio());
        content3.setSpan(new UnderlineSpan(), 0, content3.length(), 0);
        ciclo.setText(content3);

        SpannableString content4 = new SpannableString(grupo.getHorario().getDias()+" "+grupo.getHorario().getHoraInicial());
        content4.setSpan(new UnderlineSpan(), 0, content4.length(), 0);
        horario.setText(content4);



        ArrayList<String> cursos=new ArrayList<>();
        for (int i=0;i<mCursos.size();i++)
            cursos.add(mCursos.get(i).getNombre());

        final ArrayAdapter<String> spinner_cursos = new  ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item, cursos);


        ArrayList<String> profesores=new ArrayList<>();
        for (int i=0;i<mProfesores.size();i++)
            profesores.add(mProfesores.get(i).getNombre());

        final ArrayAdapter<String> spinner_profesores = new  ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item, profesores);


        ArrayList<String> ciclos=new ArrayList<>();
        for (int i=0;i<mCiclos.size();i++)
            ciclos.add(mCiclos.get(i).getNumero()+" "+mCiclos.get(i).getAnio());

        final ArrayAdapter<String> spinner_ciclos = new  ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item, ciclos);



        ArrayList<String> horarios=new ArrayList<>();
        for (int i=0;i<mHorarios.size();i++)
            horarios.add(mHorarios.get(i).getDias()+" hora inicial:"+mHorarios.get(i).getHoraInicial());

        final ArrayAdapter<String> spinner_horarios = new  ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item, horarios);


        curso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Elija el curso")
                        .setAdapter(spinner_cursos, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                grupo.setCurso(mCursos.get(which));
                                SpannableString content = new SpannableString(mCursos.get(which).getNombre());
                                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                curso.setText(content);
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });


        profesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Elija la carreras")
                        .setAdapter(spinner_profesores, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                grupo.setProfesor(mProfesores.get(which));
                                SpannableString content = new SpannableString(mProfesores.get(which).getNombre());
                                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                profesor.setText(content);
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        ciclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Elija la carreras")
                        .setAdapter(spinner_ciclos, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                grupo.setCiclo(mCiclos.get(which));
                                SpannableString content = new SpannableString(mCiclos.get(which).getNumero()+" "+mCiclos.get(which).getAnio());
                                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                ciclo.setText(content);
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Elija la carreras")
                        .setAdapter(spinner_horarios, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                grupo.setHorario(mHorarios.get(which));
                                SpannableString content = new SpannableString(mHorarios.get(which).getDias()+" "+mHorarios.get(which).getHoraInicial());
                                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                horario.setText(content);
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });



        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(numero.getWindowToken(), 0);

                final View vi=v;
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Confirmacion");
                alert.setMessage("Desea editar este grupo?");
                alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //reiniciar los errores
                        numero.setError(null);
                        curso.setError(null);
                        profesor.setError(null);
                        ciclo.setError(null);
                        horario.setError(null);
                        String num=numero.getText().toString();
                        String cur=curso.getText().toString();
                        String prof=profesor.getText().toString();
                        String cic=ciclo.getText().toString();
                        String hor=horario.getText().toString();

                        boolean cancel=false;
                        View focusView=null;

                        if(TextUtils.isEmpty(num)){
                            numero.setError("Numero Vacio");
                            focusView=numero;
                            cancel=true;
                        }


                        if (cancel) {
                            focusView.requestFocus();
                        } else {


                            String urlBase = Variables.getURLBase();
                            urlRequest = urlBase + "action=EditarGrupo"+
                                    "&idGrupo="+grupo.getId()+
                                    "&idCurso="+grupo.getCurso().getCodigo()+
                                    "&numeroCiclo="+grupo.getCiclo().getNumero()+
                                    "&anioCiclo="+grupo.getCiclo().getAnio()+
                                    "&numero="+num+
                                    "&dias="+grupo.getHorario().getDias()+
                                    "&horaInicio="+grupo.getHorario().getHoraInicial()+
                                    "&horaFinal="+grupo.getHorario().getHoraFinal()+
                                    "&idProfesor="+grupo.getProfesor().getCedula();

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


    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setmCiclos(ArrayList<Ciclo> mCiclos) {
        this.mCiclos = mCiclos;
    }

    public void setmProfesores(ArrayList<Profesor> mProfesores) {
        this.mProfesores = mProfesores;
    }

    public void setmCursos(ArrayList<Curso> mCursos) {
        this.mCursos = mCursos;
    }

    public void setmHorarios(ArrayList<Horario> mHorarios) {
        this.mHorarios = mHorarios;
    }
}
