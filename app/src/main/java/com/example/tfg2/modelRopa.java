package com.example.tfg2;

public class modelRopa {

    String descripcion, nombre, precio, url;

    public modelRopa() {
    }

    public modelRopa(String descripcion, String nombre, String precio, String url) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.precio = precio;
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
