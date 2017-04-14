package com.gestion_academica;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//asign task en fragment
//http://stackoverflow.com/questions/18558084/implement-asynctask-in-fragment-android
public class carrerasFragment extends Fragment {


    String urlRequest;
    String result = "";

    public carrerasFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static carrerasFragment newInstance(String param1, String param2) {
        carrerasFragment fragment = new carrerasFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_carreras, container, false);


        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView recyclerV=(RecyclerView) view.findViewById(R.id.rvCarreras);

        String urlBase = Variables.getURLBase();
//                urlRequest = urlBase + "action=Testing";
        urlRequest = urlBase + "action=BuscarCarrera&codigo=&nombre=";
        new CarrerasTask(view.getContext(),recyclerV).execute();
    }



    class CarrerasTask extends AsyncTask<String, String, String>
    {
        RecyclerView mRecyclerV;
        Context mContex;
        public CarrerasTask(Context contex, RecyclerView rview)
        {
            this.mRecyclerV=rview;
            this.mContex=contex;
        }


        @Override
        protected void onPostExecute(String result) {
            JSONArray data=null;
            try {
                if (result != null){
                    data = new JSONArray(result);
                    //poner datos en el array
                    AdapterCarrera adapter=new AdapterCarrera(mContex, data);
                    mRecyclerV.setAdapter(adapter);
                    mRecyclerV.setLayoutManager(new LinearLayoutManager(mContex));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

        @Override
        protected String doInBackground(String... params)
        {
            try {
                URL url = new URL(urlRequest);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String valueResult = bf.readLine();

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


}
