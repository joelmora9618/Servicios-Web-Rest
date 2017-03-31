package com.example.jmora.webservicesoap;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AlumnosActivity extends AppCompatActivity{

    ListView lvAlumnos;
    ArrayList<Alumno> listaAlumnos;
    AdaptadorAlumnos adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        lvAlumnos = (ListView)findViewById(R.id.lvAlumnos);

        TareaAsincronca tareaAsincronca = new TareaAsincronca(this);
        tareaAsincronca.execute();
    }

    public class TareaAsincronca extends AsyncTask<Void, Integer, Boolean>
    {
        StringBuilder total;
        private ProgressDialog dialog;

        public TareaAsincronca(AlumnosActivity activity)
        {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setTitle("Cargando lista");
            dialog.setMessage("por favor, espere");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            listaAlumnos = new ArrayList<>();
            try {
                String json = String.valueOf(total);
                JSONArray resJSON = new JSONArray(json);

                for(int i=0;i<resJSON.length();i++)
                {
                    JSONObject object = resJSON.getJSONObject(i);
                    Alumno alumno = new Alumno(
                            object.getInt("dni_alumno"),
                            object.getString("nombre"),
                            object.getString("apellido"),
                            object.getInt("edad"),
                            object.getString("sexo"),
                            object.getString("divicion"));

                    listaAlumnos.add(i,alumno);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(dialog.isShowing())
            {
                dialog.dismiss();
            }
            adaptador = new AdaptadorAlumnos(AlumnosActivity.this,listaAlumnos);
            lvAlumnos.setAdapter(adaptador);

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                // Operaciones http
                URL url = null;
                try {
                    url = new URL("http://jemservicerest.azurewebsites.net/Alumnos");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try
                {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    // Acciones a realizar con el flujo de datos

                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    total = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        total.append(line).append('\n');
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

            } else {
                // Mostrar errores
                Toast.makeText(AlumnosActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }

            return null;
        }
    }

    public class AdaptadorAlumnos extends ArrayAdapter<Alumno>
    {
        public AdaptadorAlumnos(Context context, List<Alumno> listaAlumnos) {
            super(context, R.layout.list_item,listaAlumnos);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.list_item,null);

            TextView tvDni = (TextView)item.findViewById(R.id.tvDni);
            tvDni.setText(String.valueOf(listaAlumnos.get(position).getDni_alumno()));

            TextView tvNombre = (TextView)item.findViewById(R.id.tvNombre);
            tvNombre.setText(listaAlumnos.get(position).getNombre());

            TextView tvApellido = (TextView)item.findViewById(R.id.tvApellido);
            tvApellido.setText(listaAlumnos.get(position).getApellido());

            TextView tvEdad = (TextView)item.findViewById(R.id.tvEdad);
            tvEdad.setText(String.valueOf(listaAlumnos.get(position).getEdad()));

            TextView tvSexo = (TextView)item.findViewById(R.id.tvSexo);
            tvSexo.setText(listaAlumnos.get(position).getSexo());

            TextView tvDivicion = (TextView)item.findViewById(R.id.tvDivicion);
            tvDivicion.setText(listaAlumnos.get(position).getDivicion());

            return (item);
        }
    }

}
