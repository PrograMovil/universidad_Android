package com.gestion_academica.Adapters;

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

import com.gestion_academica.Inicio;
import com.gestion_academica.R;
import com.gestion_academica.Fragments_Editar.editarEstudianteFragment;

import java.util.List;

import LogicaNegocio.Estudiante;


public class AdapterEstudiante extends RecyclerView.Adapter<AdapterEstudiante.ViewHolder> {


    private static List<Estudiante> mEstudiantes;
    private static Context mContext;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nombreTextView;
        public TextView idTextView;
        public ImageButton botonEditar;
        private Context context;



        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            nombreTextView = (TextView) itemView.findViewById(R.id.nombre);
            idTextView = (TextView) itemView.findViewById(R.id.identificacion);
            botonEditar=(ImageButton) itemView.findViewById(R.id.botonEditar);

            botonEditar.setOnClickListener(this);

            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Estudiante user = mEstudiantes.get(position);
                // We can access the data within the views
                Toast.makeText(context, user.getCedula(), Toast.LENGTH_SHORT).show();
                Fragment newFragment=new editarEstudianteFragment().newInstance(user);
                if(mContext instanceof Inicio){
                    FragmentTransaction fragmentTransaction=((Inicio) mContext).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaEstudiantes").commit();

                    //((Inicio)mContext).switchContent(newFragment);

                }

            }
        }
    }




    public AdapterEstudiante(Context context, List<Estudiante> estudiantes) {
        mEstudiantes = estudiantes;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }


    @Override
    public AdapterEstudiante.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fila_estudiante, parent, false);

        ViewHolder viewHolder = new ViewHolder(context,contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterEstudiante.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Estudiante estudiante = mEstudiantes.get(position);

        // Set item views based on your views and data model
        TextView textNombre = viewHolder.nombreTextView;
        textNombre.setText(estudiante.getNombre());
        TextView textIdentificacion = viewHolder.idTextView;
        textIdentificacion.setText(estudiante.getCedula());

    }

    @Override
    public int getItemCount() {
        return mEstudiantes.size();
    }


}
