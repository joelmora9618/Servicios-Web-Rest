package com.example.jmora.webservicesoap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by JMora on 08/03/2017.
 */

public class Alumno {

    public int dni_alumno;
    public String nombre;
    public String apellido;
    public int edad;
    public String sexo;
    public String divicion;

    public Alumno(JSONObject jsonObject) throws JSONException {
        this.dni_alumno = jsonObject.getInt("dni_alumno");
        this.nombre = jsonObject.getString("nombre");
        this.apellido = jsonObject.getString("apellido");
        this.edad = jsonObject.getInt("edad");
        this.sexo = jsonObject.getString("sexo");
        this.divicion = jsonObject.getString("divicion");
    }

    public Alumno(int dni_alumno, String nombre, String apellido, int edad, String sexo, String divicion)
    {
        this.dni_alumno = dni_alumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo = sexo;
        this.divicion = divicion;
    }

}
