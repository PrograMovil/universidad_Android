package com.gestion_academica;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import LogicaNegocio.Matriculador;

/**
 * Created by Denis on 14/4/2017.
 */

public class AdapterMatriculador extends RecyclerView.Adapter<AdapterMatriculador.ViewHolder>{


    private static ArrayList<Matriculador> mMatriculadores;
    private static Context mContext;


    public AdapterMatriculador(Context context, ArrayList<Matriculador> matriculadores) {
        mMatriculadores = matriculadores;
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
            nombre = (TextView) itemView.findViewById(R.id.nombreMatriculador);
            cedula = (TextView) itemView.findViewById(R.id.cedulaMatriculador);
            botonEditar=(ImageButton) itemView.findViewById(R.id.botonEditarMatriculador);


            botonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Matriculador matriculador = mMatriculadores.get(position);


                        Fragment newFragment=new editarMatriculadorFragment().newInstance(matriculador);
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
    public AdapterMatriculador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fila_matriculador, parent, false);

        AdapterMatriculador.ViewHolder viewHolder = new AdapterMatriculador.ViewHolder(context,contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(AdapterMatriculador.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Matriculador matriculador = null;
        matriculador = (Matriculador) mMatriculadores.get(position);

        // Set item views based on your views and data model
        TextView codigo = viewHolder.cedula;
        codigo.setText(matriculador.getCedula());
        TextView nombre = viewHolder.nombre;
        nombre.setText(matriculador.getNombre());

    }

    @Override
    public int getItemCount() {
        return mMatriculadores.size();
    }










}

