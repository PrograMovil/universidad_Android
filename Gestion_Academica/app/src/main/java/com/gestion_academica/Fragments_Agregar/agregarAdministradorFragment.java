package com.gestion_academica.Fragments_Agregar;


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

import com.gestion_academica.R;
import com.gestion_academica.Variables;
import com.gestion_academica.asyncTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class agregarAdministradorFragment extends Fragment {

    String urlRequest;
    String result;

    public agregarAdministradorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_administrador, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final EditText cedula=(EditText) view.findViewById(R.id.cedulaAdministrador);
        final EditText nombre = (EditText) view.findViewById(R.id.nombreAdministrador);
        final EditText telefono = (EditText) view.findViewById(R.id.telefonoAdministrador);
        final EditText email = (EditText) view.findViewById(R.id.emailAdministrador);
        final EditText pass = (EditText) view.findViewById(R.id.contrasenaAdministrador);
        Button botonGuardar=(Button) view.findViewById(R.id.botonGuardarAdministrador);




        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pass.getWindowToken(), 0);
                final View vi=v;
                //reiniciar los errores
                cedula.setError(null);
                nombre.setError(null);
                telefono.setError(null);
                email.setError(null);
                pass.setError(null);
                final String ced=cedula.getText().toString();
                final String nom=nombre.getText().toString();
                final String tel=telefono.getText().toString();
                final String ema=email.getText().toString();
                final String con=pass.getText().toString();

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
                }
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Confirmacion");
                    alert.setMessage("Desea crear este administrador?");
                    alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {


                            String urlBase = Variables.getURLBase();
                            urlRequest = urlBase + "action=AgregarAdministrador" + "&cedula=" + ced + "&nombre=" + nom + "&telefono=" + tel + "&email=" + ema + "&password=" + con;
                            new asyncTask(vi.getContext(), getFragmentManager(), urlRequest).execute();


                        }
                    });
                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
                    alert.show();
                }
            }
        });

    }

}