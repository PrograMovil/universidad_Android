package com.gestion_academica;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class asyncTask extends AsyncTask<String,Void,String> {


    Context mContex;
    FragmentManager fragmentManager;
    String result;
    String urlRequest;

    public asyncTask(Context contex, FragmentManager fr, String urlReq)
    {
        this.mContex=contex;
        this.fragmentManager=fr;
        this.urlRequest=urlReq;
    }

    @Override
    protected void onPostExecute(String result) {

        try {
            if (!result.equals("null") || result!=null){
                JSONObject data = new JSONObject(result);
                String dataResult = data.getString("type");
                String msg = data.getString("msg");


                if (dataResult.equals("Success")){
                    Toast.makeText(mContex, msg, Toast.LENGTH_LONG).show();
                    fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.frament_container)).commit();
                    fragmentManager.popBackStackImmediate();
                } else {
                    Toast.makeText(mContex, msg, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(mContex, "Error al consultar la base de datos", Toast.LENGTH_LONG).show();
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
            System.out.println("Resultado "+ valueResult);

            result = valueResult;

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
