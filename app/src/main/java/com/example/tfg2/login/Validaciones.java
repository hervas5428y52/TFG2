package com.example.tfg2.login;

import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

    boolean nombre;
    Pattern nombrePattern;
    Matcher nombreMatcher;

    boolean apellidos;
    Pattern apellidosPattern;
    Matcher apellidosMatcher;

    boolean mail;
    Pattern mailPattern;
    Matcher mailMatcher;

    boolean contraseña;
    Pattern contraseñaPattern;
    Matcher contraseñaMatcher;

    boolean confirmarContraseña;
    Pattern confirmarContraseñaPattern;
    Matcher confirmarContraseñaMatcher;

    public Validaciones() {
    }

    public Validaciones(boolean nombre, boolean apellidos, boolean mail, boolean contraseña, boolean confirmarContraseña) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.mail = mail;
        this.contraseña = contraseña;
        this.confirmarContraseña = confirmarContraseña;
    }

    public boolean isNombre() {
        return nombre;
    }

    public void setNombre(boolean nombre) {
        this.nombre = nombre;
    }

    public boolean isApellidos() {
        return apellidos;
    }

    public void setApellidos(boolean apellidos) {
        this.apellidos = apellidos;
    }

    public boolean isMail() {
        return mail;
    }

    public void setMail(boolean mail) {
        this.mail = mail;
    }

    public boolean isContraseña() {
        return contraseña;
    }

    public void setContraseña(boolean contraseña) {
        this.contraseña = contraseña;
    }

    public boolean isConfirmarContraseña() {
        return confirmarContraseña;
    }

    public void setConfirmarContraseña(boolean confirmarContraseña) {
        this.confirmarContraseña = confirmarContraseña;
    }

    public void validarMail(String mailString) {

        mailPattern = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-z]+\\.+[a-z]+$");
        mailMatcher = mailPattern.matcher(mailString);
        if (!mailMatcher.matches() || mailString.equals("")) {
            mail = false;
        } else {
            mail = true;
        }

    }

    public void validarNombre(String nombreString) {

        nombrePattern = Pattern.compile("[A-Z][a-z]*");
        nombreMatcher = nombrePattern.matcher(nombreString);
        if (!nombreMatcher.matches() || nombreString.equals("")) {
            nombre = false;
        } else {
            nombre = true;
        }
    }

    public void validarApellido(String apellidoString) {

        apellidosPattern = Pattern.compile("[A-Z][a-z]*");
        apellidosMatcher = nombrePattern.matcher(apellidoString);
        if (!apellidosMatcher.matches() || apellidoString.equals("")) {
            apellidos = false;
        } else {
            apellidos = true;
        }
    }

    public void validarContraseña(String clave1 /*, String clave2*/) {
        //http://w3.unpocodetodo.info/utiles/regex-ejemplos.php?type=psw
        //La contraseña debe tener al entre 8 y 16 caracteres, al menos un dígito, al menos una
        // minúscula y al menos una mayúscula.
        contraseñaPattern = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
        contraseñaMatcher = contraseñaPattern.matcher(clave1);
        //contraseñaMatcher = contraseñaPattern.matcher(clave2);
        if (!contraseñaMatcher.matches() || clave1.equals("")) {
            contraseña = false;
        } else {
            contraseña = true;
        }
    }

    public void validarConfirmarContraseña(String clave1 , String clave2) {
        //http://w3.unpocodetodo.info/utiles/regex-ejemplos.php?type=psw
        //La contraseña debe tener al entre 8 y 16 caracteres, al menos un dígito, al menos una
        // minúscula y al menos una mayúscula.
        contraseñaPattern = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
        contraseñaMatcher = contraseñaPattern.matcher(clave2);
        if (clave2.equals("")) {
            confirmarContraseña = false;
        } else {
            if (!clave2.equals(clave1)) {
                confirmarContraseña = false;
            } else {
                confirmarContraseña = true;
            }
        }
    }

}
