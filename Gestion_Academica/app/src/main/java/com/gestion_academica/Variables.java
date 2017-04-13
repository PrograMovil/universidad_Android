package com.gestion_academica;


import android.app.Application;

public class Variables extends Application{

//    URL base del backend
    private static final String URLBase = "http://192.168.2.175:8084/UniversidadBackend/AndroidServlet?"; // Cambiarla segun donde se ejecute el backend

    public static String getURLBase() {
        return URLBase;
    }
}
