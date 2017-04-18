package com.gestion_academica.Adapters;


import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gestion_academica.Fragments_Editar.editarGrupoFragment;
import com.gestion_academica.Inicio;
import com.gestion_academica.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import LogicaNegocio.Ciclo;
import LogicaNegocio.Curso;
import LogicaNegocio.Grupo;
import LogicaNegocio.Horario;
import LogicaNegocio.Profesor;

public class AdapterGrupo  extends RecyclerView.Adapter<AdapterGrupo.ViewHolder>{


    private static ArrayList<Grupo> mGrupos;
    private static ArrayList<Ciclo> mCiclos;
    private static ArrayList<Profesor> mProfesores;
    private static ArrayList<Curso> mCursos;
    private static ArrayList<Horario> mHorarios;
    private static Context mContext;
    private int mExpandedPosition=-1;


    public AdapterGrupo(Context context, ArrayList<Grupo> grupos, ArrayList<Ciclo> ci, ArrayList<Profesor> pr, ArrayList<Curso> cu, ArrayList<Horario> ho) {
        Log.e("TagConexionGrupo", "Constructor Adapter lista grupos");
        mGrupos = grupos;
        mCiclos=ci;
        mProfesores=pr;
        mCursos=cu;
        mHorarios=ho;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView numero;
        public TextView curso;
        public TextView profesor;
        public TextView ciclo;
        public TextView dias;
        public TextView botonEditar;
        public FloatingActionButton botonAgregar;
        public TextView textoProf;
        public TextView textoCic;
        public TextView textoDi;
        private Context context;



        public ViewHolder(Context context, View itemView) {
            super(itemView);

            this.context = context;
            numero = (TextView) itemView.findViewById(R.id.numeroGrupo);
            curso = (TextView) itemView.findViewById(R.id.cursoGrupo);
            profesor=(TextView) itemView.findViewById(R.id.profesorGrupo);
            ciclo=(TextView) itemView.findViewById(R.id.cicloGrupo);
            dias=(TextView) itemView.findViewById(R.id.diasGrupo);
            textoProf=(TextView) itemView.findViewById(R.id.textView7);
            textoCic=(TextView) itemView.findViewById(R.id.textView9);
            textoDi=(TextView) itemView.findViewById(R.id.textView10);
            botonEditar=(TextView) itemView.findViewById(R.id.botonEditarGrupo);



            botonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                        Grupo grupo = mGrupos.get(position);
                        Fragment newFragment=new editarGrupoFragment().newInstance(grupo,mCiclos,mProfesores,mCursos,mHorarios);
                        if(mContext instanceof Inicio){

                            FragmentTransaction fragmentTransaction=((Inicio) mContext).getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frament_container, newFragment).addToBackStack("listaGrupos").commit();

                        }

                    }
                }
            });
        }


    }





    @Override
    public AdapterGrupo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.fila_grupo, parent, false);

        AdapterGrupo.ViewHolder viewHolder = new AdapterGrupo.ViewHolder(context,contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final AdapterGrupo.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Log.e("TagConexionGrupo2", "Cargando grupos al viewholder desde el adapter");
        Grupo grupo = null;
        grupo = (Grupo) mGrupos.get(position);

        final int p=position;
        // Set item views based on your views and data model
        TextView curso = viewHolder.curso;
        curso.setText(grupo.getCurso().getNombre());
        TextView numero = viewHolder.numero;
        numero.setText(""+grupo.getNumero());
        TextView profesor = viewHolder.profesor;
        profesor.setText(grupo.getProfesor().getNombre());
        TextView ciclo = viewHolder.ciclo;
        ciclo.setText(grupo.getCiclo().getNumero()+" "+grupo.getCiclo().getAnio());

        TextView dias = viewHolder.dias;
        dias.setText(grupo.getHorario().getDias());

        final boolean isExpanded = position== mExpandedPosition;
        viewHolder.profesor.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.ciclo.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.dias.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.botonEditar.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.textoProf.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.textoCic.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        viewHolder.textoDi.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        viewHolder.itemView.setActivated(isExpanded);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:p;
                TransitionManager.beginDelayedTransition((ViewGroup)v);
                notifyDataSetChanged();
            }
        });
        Log.e("TagConexionGrupo2", "****Carga view holder finalizada****");
    }

    @Override
    public int getItemCount() {
        return mGrupos.size();
    }



}