package com.gestion_academica.Fragments_Editar;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gestion_academica.R;
import com.gestion_academica.Variables;
import com.gestion_academica.asyncTask;

import LogicaNegocio.Administrador;


/**
 * A simple {@link Fragment} subclass.
 */
public class editarAdministradorFragment extends Fragment {

    Administrador administrador;
    String urlRequest;
    String result;

    public editarAdministradorFragment() {
        // Required empty public constructor
    }

    public static editarAdministradorFragment newInstance(Administrador prof){
        editarAdministradorFragment newFragment=new editarAdministradorFragment();
        newFragment.setAdministrador(prof);
        return newFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_administrador, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final TextView cedula=(TextView) view.findViewById(R.id.cedulaAdministrador);
        final EditText nombre = (EditText) view.findViewById(R.id.nombreAdministrador);
        final EditText telefono = (EditText) view.findViewById(R.id.telefonoAdministrador);
        final EditText email = (EditText) view.findViewById(R.id.emailAdministrador);
        final EditText pass = (EditText) view.findViewById(R.id.contrasenaAdministrador);
        Button botonGuardar=(Button) view.findViewById(R.id.botonGuardarAdministrador);

        cedula.setText(administrador.getCedula());
        nombre.setText(administrador.getNombre());
        telefono.setText(administrador.getTelefono());
        email.setText(administrador.getEmail());
        pass.setText(administrador.getUsuario().getClave());

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pass.getWindowToken(), 0);

                final View vi=v;
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Confirmacion");
                alert.setMessage("Desea editar este administrador?");
                alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //reiniciar los errores
                        cedula.setError(null);
                        nombre.setError(null);
                        telefono.setError(null);
                        email.setError(null);
                        pass.setError(null);
                        String ced=cedula.getText().toString();
                        String nom=nombre.getText().toString();
                        String tel=telefono.getText().toString();
                        String ema=email.getText().toString();
                        String con=pass.getText().toString();

                        boolean cancel=false;
                        View focusView=null;

                        if(TextUtils.isEmpty(ced)){
                            cedula.setError("Cedula Vacio");
                            focusView=cedula;
                            cancel=true;
                        }

                        if(TextUtils.isEmpty(nom)){
                            nombre.setError("Nombre Vacio");
                            focusView=nombre;
                            cancel=true;
                        }

                        if(TextUtils.isEmpty(tel)){
                            telefono.setError("Telefono Vacio");
                            focusView=telefono;
                            cancel=true;
                        }
                        if(TextUtils.isEmpty(ema)){
                            email.setError("Email Vacio");
                            focusView=email;
                            cancel=true;
                        }
                        if(TextUtils.isEmpty(con)){
                            pass.setError("Contraseña Vacio");
                            focusView=pass;
                            cancel=true;
                        }


                        if (cancel) {
                            focusView.requestFocus();
                        } else {

                            String urlBase = Variables.getURLBase();
                            urlRequest = urlBase + "action=EditarAdministrador"+"&cedula="+ced+"&nombre="+nom+"&telefono="+tel+"&email="+ema+"&password="+con;
                            new asyncTask(vi.getContext(),getFragmentManager(),urlRequest).execute();
                        }

                    }
                });
                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();


            }
        });

    }






    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
}
