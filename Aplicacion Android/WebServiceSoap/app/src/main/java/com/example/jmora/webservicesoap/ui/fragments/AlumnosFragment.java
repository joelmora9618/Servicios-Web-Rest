package com.example.jmora.webservicesoap.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jmora.webservicesoap.R;
import com.example.jmora.webservicesoap.models.Empleado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class AlumnosFragment extends Fragment {

    ListView lvAlumnos;
    ArrayList<Empleado> listaAlumnos;
    AdaptadorAlumnos adaptador;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alumnos, container, false);

        lvAlumnos = (ListView)view.findViewById(R.id.lvAlumnos);
        TareaAsincronca tareaAsincronca = new TareaAsincronca(getActivity());
        tareaAsincronca.execute();

        return view;
    }


    public class TareaAsincronca extends AsyncTask<Void, Integer, Boolean>
    {
        StringBuilder total;
        private ProgressDialog dialog;

        public TareaAsincronca(FragmentActivity activity)
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
                    Empleado empleado = new Empleado(
                            object.getInt("dni_empleado"),
                            object.getString("nombre"),
                            object.getString("apellido"),
                            object.getString("fecha_de_nacimiento"),
                            object.getString("sexo"),
                            object.getString("sector"),
                            object.getString("piso"),
                            object.getString("password"));

                    listaAlumnos.add(i,empleado);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(dialog.isShowing())
            {
                dialog.dismiss();
            }
            adaptador = new AdaptadorAlumnos(getActivity(),listaAlumnos);
            lvAlumnos.setAdapter(adaptador);

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ConnectivityManager connMgr = (ConnectivityManager)
                            getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                // Operaciones http
                URL url = null;
                try {
                    url = new URL("http://jemwebapi.somee.com/api/Empleados");
                }catch (MalformedURLException e) {
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
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
            }

            return null;
        }
    }
    public class AdaptadorAlumnos extends ArrayAdapter<Empleado>
    {
        public AdaptadorAlumnos(Context context, List<Empleado> listaAlumnos) {
            super(context, R.layout.list_item,listaAlumnos);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.list_item,null);

            TextView tvNombre = (TextView)item.findViewById(R.id.tvNombre);
            tvNombre.setText(listaAlumnos.get(position).getNombre());

            TextView tvApellido = (TextView)item.findViewById(R.id.tvApellido);
            tvApellido.setText(listaAlumnos.get(position).getApellido());

            TextView tvEdad = (TextView)item.findViewById(R.id.tvEdad);
            try {
                tvEdad.setText(String.valueOf(listaAlumnos.get(position).getEdad()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            TextView tvDivicion = (TextView)item.findViewById(R.id.tvDivicion);
            tvDivicion.setText(listaAlumnos.get(position).getSector());

            return (item);
        }
    }
}
