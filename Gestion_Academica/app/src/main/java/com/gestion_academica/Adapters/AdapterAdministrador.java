package com.gestion_academica.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gestion_academica.Fragments_Editar.editarAdministradorFragment;
import com.gestion_academica.Inicio;
import com.gestion_academica.R;

import java.util.ArrayList;

import LogicaNegocio.Administrador;

public class AdapterAdministrador extends RecyclerView.Adapter<AdapterAdministrador.ViewHolder>{


    private static ArrayList<Administrador> mAdministradores;
    private static Context mContext;
    private int mExpandedPosition=-1;


    public AdapterAdministrador(Context context, ArrayList<Administrador> administradores) {
        mAdministradores = administradores;
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
            nombre = (TextView) itemView.findViewById(R.id.nombreAdministrador);
            cedula = (TextView) itemView.findViewById(R.id.cedulaAdministrador);
            telefono=(TextView) itemView.findViewById(R.id.telefonoAdministrador);
            email=(TextView) itemView.findViewById(R.id.emailAdministrador);
            textoTel=(TextView) itemView.findViewById(R.id.textView7);
            textoEm=(TextView) itemView.findViewById(R.id.textView8);
            botonEditar=(TextView) itemView.findViewById(R.id.botonEditarAdministrador);


            botonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Administrador administrador = mAdministradores.get(position);


                        Fragment newFragment=new editarAdministradorFragment().newInstance(administrador);
                        if(mContext instanceof Inicio){

                            FragmentTransaction fragmentTransaction=((Inicio) mContext).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaAdministradores").commit();

                        }

                    }
                }
            });

        }


    }





    @Override
    public AdapterAdministrador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fila_administrador, parent, false);

        AdapterAdministrador.ViewHolder viewHolder = new AdapterAdministrador.ViewHolder(context,contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final AdapterAdministrador.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Administrador administrador = null;
        administrador = (Administrador) mAdministradores.get(position);

        final int p=position;
        // Set item views based on your views and data model
        TextView codigo = viewHolder.cedula;
        codigo.setText(administrador.getCedula());
        TextView nombre = viewHolder.nombre;
        nombre.setText(administrador.getNombre());
        TextView telefono = viewHolder.telefono;
        telefono.setText(administrador.getTelefono());
        TextView email = viewHolder.email;
        email.setText(administrador.getEmail());

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
        return mAdministradores.size();
    }










}
