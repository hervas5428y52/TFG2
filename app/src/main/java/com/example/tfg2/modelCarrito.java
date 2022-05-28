package com.example.tfg2;

public class modelCarrito {

    private String nombreProductoCarrito;
    private String precioProductoCarrito;
    private String tallaProductoCarrito;
    private String urlProductoCarrito;
    private String descipcionProductoCarrito;

    public modelCarrito() {
    }

    public modelCarrito(String nombreProductoCarrito, String precioProductoCarrito, String tallaProductoCarrito, String urlProductoCarrito, String descipcionProductoCarrito) {
        this.nombreProductoCarrito = nombreProductoCarrito;
        this.precioProductoCarrito = precioProductoCarrito;
        this.tallaProductoCarrito = tallaProductoCarrito;
        this.urlProductoCarrito = urlProductoCarrito;
        this.descipcionProductoCarrito = descipcionProductoCarrito;
    }

    public String getNombreProductoCarrito() {
        return nombreProductoCarrito;
    }

    public void setNombreProductoCarrito(String nombreProductoCarrito) {
        this.nombreProductoCarrito = nombreProductoCarrito;
    }

    public String getPrecioProductoCarrito() {
        return precioProductoCarrito;
    }

    public void setPrecioProductoCarrito(String precioProductoCarrito) {
        this.precioProductoCarrito = precioProductoCarrito;
    }

    public String getTallaProductoCarrito() {
        return tallaProductoCarrito;
    }

    public void setTallaProductoCarrito(String tallaProductoCarrito) {
        this.tallaProductoCarrito = tallaProductoCarrito;
    }

    public String getUrlProductoCarrito() {
        return urlProductoCarrito;
    }

    public void setUrlProductoCarrito(String urlProductoCarrito) {
        this.urlProductoCarrito = urlProductoCarrito;
    }

    public String getDescipcionProductoCarrito() {
        return descipcionProductoCarrito;
    }

    public void setDescipcionProductoCarrito(String descipcionProductoCarrito) {
        this.descipcionProductoCarrito = descipcionProductoCarrito;
    }
}
