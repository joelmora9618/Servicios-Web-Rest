package com.example.jmora.webservicesoap.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import com.example.jmora.webservicesoap.models.Empleado;
import com.example.jmora.webservicesoap.R;

import java.util.ArrayList;

public class AlumnosActivity extends AppCompatActivity{

    ListView lvAlumnos;
    ArrayList<Empleado> listaAlumnos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        lvAlumnos = (ListView)findViewById(R.id.lvAlumnos);


    }





}
