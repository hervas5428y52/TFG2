package com.example.tfg2;

public class modelNotificaciones {

    String fecha;
    String identificadorCompra;

    public modelNotificaciones() {
    }

    public modelNotificaciones(String fecha, String identificadorCompra) {
        this.fecha = fecha;
        this.identificadorCompra = identificadorCompra;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdentificadorCompra() {
        return identificadorCompra;
    }

    public void setIdentificadorCompra(String identificadorCompra) {
        this.identificadorCompra = identificadorCompra;
    }
}
