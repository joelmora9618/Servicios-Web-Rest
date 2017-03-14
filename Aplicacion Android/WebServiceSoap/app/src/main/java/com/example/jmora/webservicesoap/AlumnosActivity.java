package com.example.jmora.webservicesoap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.Proxy;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class AlumnosActivity extends AppCompatActivity {

    ListView lvAlumnos;
    TextView tvPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

      //  lvAlumnos = (ListView)findViewById(R.id.lvAlumnos);
        tvPrueba = (TextView)findViewById(R.id.tvPrueba);

        try {
            GetJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  tvPrueba.setText(getLocalIpAddress());
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void GetJson() throws IOException {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Operaciones http
            URL url = new URL("http://10.0.2.15:65379/Alumno/Alumnos");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try
            {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                // Acciones a realizar con el flujo de datos

                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }
                tvPrueba.setText(total);
            }finally {
                urlConnection.disconnect();
            }

        } else {
            // Mostrar errores
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }

    }

}
