package com.gestion_academica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginBtn;
    String urlRequest;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        if(Variables.getUser(LoginActivity.this) != null){
            Toast.makeText(LoginActivity.this, "Bienvenido "+Variables.getUser(LoginActivity.this), Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LoginActivity.this,Inicio.class);
            LoginActivity.this.startActivity(intent);
        }
    }


    public void iniciarSesion(View v){

        username = (EditText) findViewById(R.id.cedula);
        password = (EditText) findViewById(R.id.contrasena);

        loginBtn = (Button) findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlBase = Variables.getURLBase();
                String id = username.getText().toString();
                String pass = password.getText().toString();
//                urlRequest = urlBase + "action=Testing";
                urlRequest = urlBase + "action=Ingresar"+"&id="+id+"&password="+pass;
                new LoginTask().execute();
            }
        });

    }

    public class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {

            try {
                if (result != null){
                    JSONObject data = new JSONObject(result);
                    String id = data.getString("id");
                    Toast.makeText(LoginActivity.this, id, Toast.LENGTH_LONG).show();
                    Variables.saveUser(id,LoginActivity.this);

                    int tipoUsuario = data.getInt("tipo");
                    if (tipoUsuario != 0){
                        switch (tipoUsuario){
                            case 1 : {
                                Intent intent = new Intent(LoginActivity.this, Inicio.class);
                                LoginActivity.this.startActivity(intent);
                            }
                            break;
                            case 2 : {
                                Intent intent = new Intent(LoginActivity.this, Inicio.class);
                                LoginActivity.this.startActivity(intent);
                            }
                            break;
                            case 3 : {
                                Intent intent = new Intent(LoginActivity.this, Inicio.class);
                                LoginActivity.this.startActivity(intent);
                            }
                            break;
                            case 4 : {
                                Intent intent = new Intent(LoginActivity.this, Inicio.class);
                                LoginActivity.this.startActivity(intent);
                            }
                            break;
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Error en tipo de usuario!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Identificación o Contraseña Incorrecta!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(urlRequest);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String valueResult = bf.readLine();
                System.out.println("Resultao del Login: "+ valueResult);

                result = valueResult;

                Intent intent=new Intent(LoginActivity.this,Inicio.class);
                LoginActivity.this.startActivity(intent);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
