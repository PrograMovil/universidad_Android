package com.gestion_academica.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gestion_academica.Inicio;
import com.gestion_academica.R;
import com.gestion_academica.Fragments_Editar.editarMatriculadorFragment;

import java.util.ArrayList;

import LogicaNegocio.Matriculador;



public class AdapterMatriculador extends RecyclerView.Adapter<AdapterMatriculador.ViewHolder>{


    private static ArrayList<Matriculador> mMatriculadores;
    private static Context mContext;
    private int mExpandedPosition=-1;


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
        public TextView telefono;
        public TextView email;
        public TextView botonEditar;
        public TextView textoTel;
        public TextView textoEm;
        private Context context;



        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            nombre = (TextView) itemView.findViewById(R.id.nombreMatriculador);
            cedula = (TextView) itemView.findViewById(R.id.cedulaMatriculador);
            telefono=(TextView) itemView.findViewById(R.id.telefonoMatriculador);
            email=(TextView) itemView.findViewById(R.id.emailMatriculador);
            textoTel=(TextView) itemView.findViewById(R.id.textView7);
            textoEm=(TextView) itemView.findViewById(R.id.textView8);
            botonEditar=(TextView) itemView.findViewById(R.id.botonEditarMatriculador);


            botonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Matriculador matriculador = mMatriculadores.get(position);


                        Fragment newFragment=new editarMatriculadorFragment().newInstance(matriculador);
                        if(mContext instanceof Inicio){
                            FragmentTransaction fragmentTransaction=((Inicio) mContext).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaMatriculadores").commit();

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
    public void onBindViewHolder(final AdapterMatriculador.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Matriculador matriculador = null;
        matriculador = (Matriculador) mMatriculadores.get(position);
        Log.e("TagConexion", "creando las variables de la lista");

        final int p=position;
        // Set item views based on your views and data model
        TextView codigo = viewHolder.cedula;
        codigo.setText(matriculador.getCedula());
        TextView nombre = viewHolder.nombre;
        nombre.setText(matriculador.getNombre());
        TextView telefono = viewHolder.telefono;
        telefono.setText(matriculador.getTelefono());
        TextView email = viewHolder.email;
        email.setText(matriculador.getEmail());

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
        return mMatriculadores.size();
    }










}

