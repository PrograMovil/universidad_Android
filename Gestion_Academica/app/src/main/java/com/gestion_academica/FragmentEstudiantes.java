package com.gestion_academica;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import LogicaNegocio.Estudiante;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEstudiantes extends Fragment {

    ArrayList<Estudiante> estudiantes;


    public FragmentEstudiantes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.fragment_estudiantes, container, false);



        // Inflate the layout for this fragment
        return fragmentView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Lookup the recyclerview in activity layout
        RecyclerView reciclerEstudiante = (RecyclerView) view.findViewById(R.id.rvEstudiantes);
        // Initialize contacts
        estudiantes = Estudiante.createContactsList(20);
        // Create adapter passing in the sample user data
        AdapterEstudiante adapter = new AdapterEstudiante(view.getContext(), estudiantes);
        // Attach the adapter to the recyclerview to populate items
        reciclerEstudiante.setAdapter(adapter);
        // Set layout manager to position the items
        reciclerEstudiante.setLayoutManager(new LinearLayoutManager(this.getContext()));
        // That's all!


        //reciclerEstudiante.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            //@Override
           // public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            //    return false;
           // }

           // @Override
           // public void onTouchEvent(RecyclerView recycler, MotionEvent event) {

           // }

           // @Override
           // public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

           // }

       // });

    }

    public void editarEstudiante(){

    }

}
