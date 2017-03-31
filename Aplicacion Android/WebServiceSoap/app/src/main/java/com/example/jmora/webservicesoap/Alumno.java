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

    public int getDni_alumno() {
        return dni_alumno;
    }

    public void setDni_alumno(int dni_alumno) {
        this.dni_alumno = dni_alumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDivicion() {
        return divicion;
    }

    public void setDivicion(String divicion) {
        this.divicion = divicion;
    }
}
