package com.example.jmora.webservicesoap.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jmora.webservicesoap.R;
import com.example.jmora.webservicesoap.models.Empleado;
import com.example.jmora.webservicesoap.validator.Form;
import com.example.jmora.webservicesoap.validator.Validate;
import com.example.jmora.webservicesoap.validator.validator.NotEmptyValidator;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etDni,
                     etPass;
    private Button   btnLogin,
                     btnSign;
    ArrayList<Empleado> listaAlumnos;
    TextView tvValidation,
             tvUserValidation,
             tvPassValidation;
    Activity _Activity;
    private boolean validateUser;
    TareaAsincronca loadUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _Activity = this;

        loadUsers = new TareaAsincronca(this);
        loadUsers.execute();
        etDni = (EditText)findViewById(R.id.etDni);
        etPass = (EditText)findViewById(R.id.etPass);
        tvValidation = (TextView)findViewById(R.id.tvValidation);
        tvUserValidation = (TextView)findViewById(R.id.tvUserValidation);
        tvPassValidation = (TextView)findViewById(R.id.tvPassValidation);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnSign = (Button)findViewById(R.id.btnSign);
        btnLogin.setOnClickListener(this);
        btnSign.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin)
        {
            if(ValidateUser(listaAlumnos,etDni,etPass))
            {
                Intent intent = new Intent(this, NavigationDrawerActivity.class);
                startActivity(intent);
            }

        }

        if(v == btnSign)
        {
            Intent intent = new Intent(this, AltaAlumnoActivity.class);
            startActivity(intent);
        }

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


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    _Activity.getSystemService(Context.CONNECTIVITY_SERVICE);
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
                Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }

            return null;
        }
    }

    public boolean ValidateUser(ArrayList<Empleado>listaAlumnos, EditText user, EditText pass)
    {
        boolean validateUser = false,
                validatePass = false,
                validate = false;

            tvUserValidation.setVisibility(View.GONE);
            tvPassValidation.setVisibility(View.GONE);

        if(validations())
        {
            for (Empleado alumno:listaAlumnos) {

                String stringUser = String.valueOf(alumno.getDni_empleado()).toString();
                String stringPass = String.valueOf(alumno.getDni_empleado()).toString();

                String userIngresed = user.getText().toString();
                String passIngresed = pass.getText().toString();

                //Validate User
                if(userIngresed.equals(stringUser))
                {
                    validateUser = true;
                }

                //Validate Password
                if(passIngresed.equals(stringPass))
                {
                    validatePass = true;
                }
            }

            //Label
            if(validateUser)
            {
                if(validatePass)
                {
                    validate = true;
                    tvUserValidation.setVisibility(View.GONE);
                    tvPassValidation.setVisibility(View.GONE);
                }else
                {
                    tvPassValidation.setVisibility(View.VISIBLE);
                    tvPassValidation.setText("*la clave ingresada no es valida");
                }
            }else
            {
                tvUserValidation.setVisibility(View.VISIBLE);
                tvUserValidation.setText("*el usuario ingresado no es valido");
            }
        }

        return validate;
    }

    private boolean validations() {
        final EditText  txtUsuario = (EditText)findViewById(R.id.etDni);
        final EditText txtContrasena = (EditText)findViewById(R.id.etPass);

        Validate usuarioField = new Validate(txtUsuario);
        usuarioField.addValidator(new NotEmptyValidator(getBaseContext()));

        Validate contraField = new Validate(txtContrasena);
        contraField.addValidator(new NotEmptyValidator(getBaseContext()));

        Form mForm = new Form();
        mForm.addValidates(usuarioField);
        mForm.addValidates(contraField);

        return mForm.validate();
    }
}
