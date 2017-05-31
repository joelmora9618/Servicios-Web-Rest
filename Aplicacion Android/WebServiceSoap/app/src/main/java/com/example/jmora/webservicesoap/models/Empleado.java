package com.example.jmora.webservicesoap.models;

import android.text.format.DateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import static android.R.attr.format;

/**
 * Created by JMora on 08/03/2017.
 */

public class Empleado {

    private int dni_empleado;
    private String nombre;
    private String apellido;
    private String fecha_de_nacimiento;
    private String sexo;
    private String sector;
    private String piso;
    private int id_faturas;
    private int id_yerba;
    private int id_futbol;
    private String password;

    public Empleado(JSONObject jsonObject) throws JSONException {
        this.dni_empleado = jsonObject.getInt("dni_alumno");
        this.nombre = jsonObject.getString("nombre");
        this.apellido = jsonObject.getString("apellido");
        this.fecha_de_nacimiento = jsonObject.getString("fecha_de_nacimiento");
        this.sexo = jsonObject.getString("sexo");
        this.sector = jsonObject.getString("sector");
        this.piso = jsonObject.getString("piso");
        this.id_faturas = jsonObject.getInt("id_faturas");
        this.id_yerba = jsonObject.getInt("id_yerba");
        this.id_futbol = jsonObject.getInt("id_futbol");
        this.password = jsonObject.getString("password");
    }

    public Empleado(int dni_empleado, String nombre, String apellido, String fecha_de_nacimiento, String sexo, String sector,
                    String piso, String password)
    {
        this.dni_empleado = dni_empleado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_de_nacimiento = fecha_de_nacimiento;
        this.sexo = sexo;
        this.sector = sector;
        this.piso = piso;
        this.password = password;
    }

    public int getDni_empleado() {
        return dni_empleado;
    }

    public void setDni_empleado(int dni_alumno) {
        this.dni_empleado = dni_alumno;
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

    public String getFecha_de_nacimiento() {
        return fecha_de_nacimiento;
    }

    public void setFecha_de_nacimiento(String fecha_de_nacimiento) {
        this.fecha_de_nacimiento = fecha_de_nacimiento;
    }

    public int getEdad() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = format.parse(fecha_de_nacimiento);

        String day = (String) DateFormat.format("dd",   date);
        String monthNumber  = (String) DateFormat.format("MM",   date);
        String year         = (String) DateFormat.format("yyyy", date);

        int dia = Integer.parseInt(day);
        int mes = Integer.parseInt(monthNumber);
        int año = Integer.parseInt(year);

        return getAge(año,mes,dia);
    }


    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public int getId_faturas() {
        return id_faturas;
    }

    public void setId_faturas(int id_faturas) {
        this.id_faturas = id_faturas;
    }

    public int getId_yerba() {
        return id_yerba;
    }

    public void setId_yerba(int id_yerba) {
        this.id_yerba = id_yerba;
    }

    public int getId_futbol() {
        return id_futbol;
    }

    public void setId_futbol(int id_futbol) {
        this.id_futbol = id_futbol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);

        return ageInt;
    }
}
