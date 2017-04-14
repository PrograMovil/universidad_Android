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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.gestion_academica.R.drawable.estudiante;


public class AdapterCarrera extends RecyclerView.Adapter<AdapterCarrera.ViewHolder> {

    private static JSONArray mCarreras;
    private static Context mContext;


    public class AdapterItem{

    }


    public AdapterCarrera(Context context, JSONArray carreras) {
        mCarreras = carreras;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nombre;
        public TextView codigo;
        public TextView titulo;
        public ImageButton botonEditar;
        private Context context;



        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            nombre = (TextView) itemView.findViewById(R.id.nombreCarrera);
            codigo = (TextView) itemView.findViewById(R.id.codigoCarrera);
            titulo = (TextView) itemView.findViewById(R.id.tituloCarrera);
            botonEditar=(ImageButton) itemView.findViewById(R.id.botonEditarCarrera);

            botonEditar.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                JSONObject carrera=null;
                try {
                    carrera = mCarreras.getJSONObject(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // We can access the data within the views
                try {
                    Toast.makeText(context, mCarreras.getJSONObject(position).getString("codigo"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Fragment newFragment=new editarCarreraFragment().newInstance(carrera);
                if(mContext instanceof Inicio){
                    FragmentTransaction fragmentTransaction=((Inicio) mContext).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaCarreras").commit();

                }

            }
        }
    }







    @Override
    public AdapterCarrera.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fila_carrera, parent, false);

        AdapterCarrera.ViewHolder viewHolder = new AdapterCarrera.ViewHolder(context,contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(AdapterCarrera.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        JSONObject carrera=null;
        try {
            carrera = mCarreras.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set item views based on your views and data model
        try {
        TextView codigo = viewHolder.codigo;
        codigo.setText(carrera.getString("codigo"));
        TextView nombre = viewHolder.nombre;
        nombre.setText(carrera.getString("nombre"));
        TextView titulo = viewHolder.titulo;
        titulo.setText(carrera.getString("titulo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mCarreras.length();
    }


}
