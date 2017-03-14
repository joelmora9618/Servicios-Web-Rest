package com.example.jmora.webservicesoap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnAlta,
           btnMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAlta = (Button)findViewById(R.id.btnAlta);
        btnMostrar = (Button)findViewById(R.id.btnMostrar);

        btnAlta.setOnClickListener(this);
        btnMostrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnAlta)
        {
            Intent intent = new Intent(this,AltaAlumnoActivity.class);
            startActivity(intent);
        }
        if(v == btnMostrar)
        {
            Intent intent = new Intent(this,AlumnosActivity.class);
            startActivity(intent);
        }
    }
}
