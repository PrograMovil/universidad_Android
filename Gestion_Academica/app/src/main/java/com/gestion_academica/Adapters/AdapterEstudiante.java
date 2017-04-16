package com.gestion_academica.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gestion_academica.Fragments_Editar.editarEstudianteFragment;
import com.gestion_academica.Inicio;
import com.gestion_academica.R;
import com.gestion_academica.Fragments_Editar.editarEstudianteFragment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import LogicaNegocio.Carrera;
import LogicaNegocio.Estudiante;
import LogicaNegocio.Estudiante;


public class AdapterEstudiante extends RecyclerView.Adapter<AdapterEstudiante.ViewHolder>{


    private static ArrayList<Estudiante> mEstudiantes;
    private static ArrayList<Carrera> mCarreras;
    private static Context mContext;
    private int mExpandedPosition=-1;


    public AdapterEstudiante(Context context, ArrayList<Estudiante> estudiantees, ArrayList<Carrera> carreras) {
        mEstudiantes = estudiantees;
        mCarreras=carreras;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre;
        public TextView cedula;
        public TextView telefono;
        public TextView email;
        public TextView nacimiento;
        public TextView carrera;
        public TextView botonEditar;
        public TextView botonMatricular;
        public TextView textoTel;
        public TextView textoEm;
        public TextView textoNa;
        public TextView textoCa;
        private Context context;



        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            nombre = (TextView) itemView.findViewById(R.id.nombreEstudiante);
            cedula = (TextView) itemView.findViewById(R.id.cedulaEstudiante);
            telefono=(TextView) itemView.findViewById(R.id.telefonoEstudiante);
            email=(TextView) itemView.findViewById(R.id.emailEstudiante);
            nacimiento=(TextView) itemView.findViewById(R.id.fechaNacEstudiante);
            carrera=(TextView) itemView.findViewById(R.id.carreraEstudiante);
            textoTel=(TextView) itemView.findViewById(R.id.textView7);
            textoEm=(TextView) itemView.findViewById(R.id.textView8);
            textoCa=(TextView) itemView.findViewById(R.id.textView9);
            textoNa=(TextView) itemView.findViewById(R.id.textView10);
            botonEditar=(TextView) itemView.findViewById(R.id.botonEditarEstudiante);
            botonMatricular=(TextView) itemView.findViewById(R.id.botonMatricularEstudiante);


            botonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Estudiante estudiante = mEstudiantes.get(position);


                        Fragment newFragment=new editarEstudianteFragment().newInstance(estudiante,mCarreras);
                        if(mContext instanceof Inicio){

                            FragmentTransaction fragmentTransaction=((Inicio) mContext).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaEstudiantes").commit();

                        }

                    }
                }
            });

        }


    }





    @Override
    public AdapterEstudiante.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fila_estudiante, parent, false);

        AdapterEstudiante.ViewHolder viewHolder = new AdapterEstudiante.ViewHolder(context,contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final AdapterEstudiante.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Estudiante estudiante = null;
        estudiante = (Estudiante) mEstudiantes.get(position);

        final int p=position;
        // Set item views based on your views and data model
        TextView codigo = viewHolder.cedula;
        codigo.setText(estudiante.getCedula());
        TextView nombre = viewHolder.nombre;
        nombre.setText(estudiante.getNombre());
        TextView telefono = viewHolder.telefono;
        telefono.setText(estudiante.getTelefono());
        TextView email = viewHolder.email;
        email.setText(estudiante.getEmail());
        TextView carrera = viewHolder.carrera;
        carrera.setText(estudiante.getCarrera().getNombre());
        TextView nacimiento = viewHolder.nacimiento;
        java.util.Date fe=estudiante.getFechaNac().getTime();
        nacimiento.setText(fe.getDay()+"/"+fe.getMonth()+"/"+(fe.getYear()+1900));

        final boolean isExpanded = position== mExpandedPosition;
        viewHolder.telefono.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.email.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.carrera.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.nacimiento.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.botonEditar.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.textoTel.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.textoEm.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.textoCa.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.textoNa.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        viewHolder.itemView.setActivated(isExpanded);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:p;
                TransitionManager.beginDelayedTransition((ViewGroup)v);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mEstudiantes.size();
    }










}