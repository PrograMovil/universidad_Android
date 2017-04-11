package com.gestion_academica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
    }


    public void iniciarSesion(View v){

        EditText username = (EditText) findViewById(R.id.cedula);
        EditText password = (EditText) findViewById(R.id.contrasena);
        Intent intent=new Intent(LoginActivity.this,Inicio.class);

        LoginActivity.this.startActivity(intent);

    }

}
