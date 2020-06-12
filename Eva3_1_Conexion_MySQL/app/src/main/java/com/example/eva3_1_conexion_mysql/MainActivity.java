package com.example.eva3_1_conexion_mysql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<JSONObject> listaJson = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.ListView);
        new ConexionOtros().execute("http://192.168.1.72:3000/actors/100");


    }

    class Conexion extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String sRuta = strings[0];
            String sResult = null;
            try {
                URL url = new URL(sRuta);
                HttpURLConnection http = (HttpURLConnection)url.openConnection();

                if(http.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStreamReader inputStream = new InputStreamReader(http.getInputStream());
                    BufferedReader br = new BufferedReader(inputStream);
                    sResult = br.readLine();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        listaJson.add(jsonObject);
                    }
                    listView.setAdapter(new adapter(MainActivity.this,R.layout.layout_actors,listaJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class ConexionOtros extends  AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String sRuta = strings[0];
            String sResult = null;
            try {
                URL url = new URL(sRuta);
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setRequestMethod("DELETE");
                http.setDoInput(true);
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "aplication/json;charset=utf-8");
                http.connect();

                InputStreamReader inputStream = new InputStreamReader(http.getInputStream());
                BufferedReader br = new BufferedReader(inputStream);
                sResult = br.readLine();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        }
    }


}
