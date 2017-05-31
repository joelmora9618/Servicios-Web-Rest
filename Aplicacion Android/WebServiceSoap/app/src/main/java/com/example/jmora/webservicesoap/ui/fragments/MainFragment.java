package com.example.jmora.webservicesoap.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.jmora.webservicesoap.R;
import com.example.jmora.webservicesoap.adapters.GroupAdapter;
import com.example.jmora.webservicesoap.models.Group;
import com.example.jmora.webservicesoap.ui.activities.ApuestasActivity;
import com.example.jmora.webservicesoap.ui.activities.FacturasActivity;
import com.example.jmora.webservicesoap.ui.activities.FutbolActivity;
import com.example.jmora.webservicesoap.ui.activities.HomeActivity;
import com.example.jmora.webservicesoap.ui.activities.YerbaActivity;

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
import java.util.ArrayList;

public class MainFragment extends Fragment{

    ListView lvGrupos;
    GroupAdapter groupAdapter;
    ArrayList<Group> listGroup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        lvGrupos = (ListView)view.findViewById(R.id.lvGrupos);
        Resources res = getResources();

        TareaAsincronca tareaAsincronca = new TareaAsincronca(getActivity());
        tareaAsincronca.execute();

        TabHost tabs=(TabHost)view.findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Grupos");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Muro");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

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
            listGroup = new ArrayList<>();
            try {
                String json = String.valueOf(total);
                JSONArray resJSON = new JSONArray(json);

                for(int i=0;i<resJSON.length();i++)
                {
                    JSONObject object = resJSON.getJSONObject(i);
                    Group empleado = new Group(
                            object.getInt("id_grupo"),
                            object.getString("nombre_grupo"),
                            object.getInt("estado"));

                    listGroup.add(i,empleado);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(dialog.isShowing())
            {
                dialog.dismiss();
            }
            groupAdapter = new GroupAdapter(getActivity(),listGroup);
            lvGrupos.setAdapter(groupAdapter);

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
                    url = new URL("http://jemwebapi.somee.com/api/Grupo");
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
                Toast.makeText(getActivity(),"Error", Toast.LENGTH_SHORT).show();
            }

            return null;
        }
    }


}
