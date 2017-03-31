package com.example.jmora.webservicesoap;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AltaAlumnoActivity extends AppCompatActivity implements View.OnClickListener {

    int dniAux,
        edadAux;
    String nombreAux,
           apellidoAux,
           sexoAux,
           divicionAux;
    EditText etDni,
             etNombre,
             etApellido,
             etEdad,
             etSexo,
             etDivicion;
    Button   btnEnviar;
    JSONObject atributo;
    StringEntity stringEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_alumno);

        atributo = new JSONObject();
        etDni = (EditText)findViewById(R.id.etDni);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etApellido = (EditText)findViewById(R.id.etApellido);
        etEdad = (EditText)findViewById(R.id.etEdad);
        etSexo = (EditText)findViewById(R.id.etSexo);
        etDivicion = (EditText)findViewById(R.id.etDivicion);
        btnEnviar = (Button)findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        PostService postService = new PostService();
        postService.execute(
                etDni.getText().toString(),
                etNombre.getText().toString(),
                etApellido.getText().toString(),
                etEdad.getText().toString(),
                etSexo.getText().toString(),
                etDivicion.getText().toString());
    }

    public class PostService extends AsyncTask<String, Integer, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://jemservicerest.azurewebsites.net/alumno/Alumno");
            post.setHeader("content-type","aplication/json");

            try {

                atributo.put("dni_alumno", Integer.parseInt(params[0]));
                atributo.put("nombre", params[1]);
                atributo.put("apellido", params[2]);
                atributo.put("edad", Integer.parseInt(params[3]));
                atributo.put("sexo", params[4]);
                atributo.put("divicion", params[5]);

                stringEntity = new StringEntity(atributo.toString());
                post.setEntity(stringEntity);

                HttpResponse response = httpClient.execute(post);
                String resString = EntityUtils.toString(response.getEntity());

                if(!resString.equals("true"))
                {
                    result = false;
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AltaAlumnoActivity.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
                result = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result)
            {
                Toast.makeText(AltaAlumnoActivity.this,"Alumno insertado Exitosamente",Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(AltaAlumnoActivity.this,"Error al insertar al Alumno",Toast.LENGTH_SHORT).show();
            }
        }

    }

    }


