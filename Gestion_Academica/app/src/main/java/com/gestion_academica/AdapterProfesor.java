package com.gestion_academica;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

import LogicaNegocio.Profesor;

public class AdapterProfesor extends RecyclerView.Adapter<AdapterProfesor.ViewHolder>{


    private static ArrayList<Profesor> mProfesores;
    private static Context mContext;
    private int mExpandedPosition=-1;


    public AdapterProfesor(Context context, ArrayList<Profesor> profesores) {
        mProfesores = profesores;
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
        public TextView botonEditar;
        public TextView textoTel;
        public TextView textoEm;
        private Context context;



        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            nombre = (TextView) itemView.findViewById(R.id.nombreProfesor);
            cedula = (TextView) itemView.findViewById(R.id.cedulaProfesor);
            telefono=(TextView) itemView.findViewById(R.id.telefonoProfesor);
            email=(TextView) itemView.findViewById(R.id.emailProfesor);
            textoTel=(TextView) itemView.findViewById(R.id.textView7);
            textoEm=(TextView) itemView.findViewById(R.id.textView8);
            botonEditar=(TextView) itemView.findViewById(R.id.botonEditarProfesor);


            botonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Profesor profesor = mProfesores.get(position);


                        Fragment newFragment=new editarProfesorFragment().newInstance(profesor);
                        if(mContext instanceof Inicio){

                            FragmentTransaction fragmentTransaction=((Inicio) mContext).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaProfesores").commit();

                        }

                    }
                }
            });

        }


    }





    @Override
    public AdapterProfesor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fila_profesor, parent, false);

        AdapterProfesor.ViewHolder viewHolder = new AdapterProfesor.ViewHolder(context,contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final AdapterProfesor.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Profesor profesor = null;
        profesor = (Profesor) mProfesores.get(position);

        final int p=position;
        // Set item views based on your views and data model
        TextView codigo = viewHolder.cedula;
        codigo.setText(profesor.getCedula());
        TextView nombre = viewHolder.nombre;
        nombre.setText(profesor.getNombre());
        TextView telefono = viewHolder.telefono;
        telefono.setText(profesor.getTelefono());
        TextView email = viewHolder.email;
        email.setText(profesor.getEmail());

        final boolean isExpanded = position== mExpandedPosition;
        viewHolder.telefono.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.email.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.botonEditar.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.textoTel.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.textoEm.setVisibility(isExpanded?View.VISIBLE:View.GONE);
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
        return mProfesores.size();
    }










}

