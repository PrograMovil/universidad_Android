package com.gestion_academica;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

import LogicaNegocio.Profesor;

public class AdapterProfesor extends RecyclerView.Adapter<AdapterProfesor.ViewHolder>{


    private static ArrayList<Profesor> mProfesores;
    private static Context mContext;


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
        public ImageButton botonEditar;
        private Context context;



        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            nombre = (TextView) itemView.findViewById(R.id.nombreProfesor);
            cedula = (TextView) itemView.findViewById(R.id.cedulaProfesor);
            botonEditar=(ImageButton) itemView.findViewById(R.id.botonEditarProfesor);


            botonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Profesor profesor = mProfesores.get(position);


                        Fragment newFragment=new editarProfesorFragment().newInstance(profesor);
                        if(mContext instanceof Inicio){
                            FragmentTransaction fragmentTransaction=((Inicio) mContext).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaCarreras").commit();

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
    public void onBindViewHolder(AdapterProfesor.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Profesor profesor = null;
        profesor = (Profesor) mProfesores.get(position);

        // Set item views based on your views and data model
        TextView codigo = viewHolder.cedula;
        codigo.setText(profesor.getCedula());
        TextView nombre = viewHolder.nombre;
        nombre.setText(profesor.getNombre());

    }

    @Override
    public int getItemCount() {
        return mProfesores.size();
    }










}

