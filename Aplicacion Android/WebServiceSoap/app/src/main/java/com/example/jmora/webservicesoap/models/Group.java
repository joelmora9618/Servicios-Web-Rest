package com.example.jmora.webservicesoap.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JMora on 31/05/2017.
 */

public class Group {

    private int id_grupo;
    private String nombre_grupo;
    private int estado;

    public Group(JSONObject jsonObject) throws JSONException {
        this.id_grupo = jsonObject.getInt("id_grupo");
        this.nombre_grupo = jsonObject.getString("nombre_grupo");
        this.estado = jsonObject.getInt("estado");
    }

    public Group(int id_grupo, String nombre_grupo, int estado) {
        this.id_grupo = id_grupo;
        this.nombre_grupo = nombre_grupo;
        this.estado = estado;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
