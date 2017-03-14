package com.example.jmora.webservicesoap;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AltaAlumnoActivity extends AppCompatActivity implements View.OnClickListener {

    int dniAux,
        edadAux;
    EditText etDni,
             etNombre,
             etApellido,
             etEdad,
             etSexo,
             etDivicion;
    Button   btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_alumno);

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

    }




    }


