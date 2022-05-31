package com.example.tfg2.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg2.BD.BaseDatos;
import com.example.tfg2.MainActivity;
import com.example.tfg2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private boolean check;
    private String id;

    //Registro
    private EditText nombreRegistro;
    private EditText apellidoRegistro;
    private EditText mailRegistro;
    private EditText contraseñaRegistro;
    private EditText confirmarContraseñaRegistro;
    private Button Registrarse;
    private TextView yaEstoyRegistrado;

    private Validaciones validaciones = new Validaciones();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //Formulario Registro
        nombreRegistro = findViewById(R.id.editTextNombreRegistro);
        apellidoRegistro = findViewById(R.id.editTextApellidosRegistro);
        mailRegistro = findViewById(R.id.editTextMailRegistro);
        contraseñaRegistro = findViewById(R.id.editTextContraseñaRegistro);
        confirmarContraseñaRegistro = findViewById(R.id.editTextReContraseñaRegistro);
        Registrarse = findViewById(R.id.btnRegistro);
        yaEstoyRegistrado = findViewById(R.id.textParaInicioSesion);
        Registrarse.setOnClickListener(this);
        yaEstoyRegistrado.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth.getCurrentUser() != null) {
            Log.i("Info", "el usuario ya esta registrado");
        } else {
            Log.i("Info", "el usuario no esta registrado");
        }
        //updateUI(currentUser);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnRegistro:
                insertValues();
                break;
            case R.id.textParaInicioSesion:
                finish();
                startActivity(new Intent(this, inicioSesion.class));
                finish();
                break;
        }

    }

    public void insertValues() {

        revisionDatos();

    }

    public void validaciones() {

        validaciones.validarNombre(nombreRegistro.getText().toString());
        validaciones.validarApellido(apellidoRegistro.getText().toString());
        validaciones.validarMail(mailRegistro.getText().toString());
        validaciones.validarContraseña(contraseñaRegistro.getText().toString());
        validaciones.validarConfirmarContraseña(contraseñaRegistro.getText().toString(), confirmarContraseñaRegistro.getText().toString());

    }

    public void revisionDatos() {
        validaciones();
        if (!validaciones.isNombre() || !validaciones.isApellidos() || !validaciones.isMail() || !validaciones.isContraseña() || !validaciones.isConfirmarContraseña()) {
            if (!validaciones.isNombre())
                nombreRegistro.setError("Introduzca un formato de nombre correcto");
            if (!validaciones.isApellidos())
                apellidoRegistro.setError("Introduzca un formato de apellido correcto");
            if (!validaciones.isMail())
                mailRegistro.setError("Introduzca un formato de mail correcto \n\n- XXX@XXX.XXX");
            if (!validaciones.isContraseña())
                contraseñaRegistro.setError("Introduzca un formato de contraseña correcto \n\n- 8 a 16 caracteres\n-Al menos 1 digito\n-Al menos 1 minuscula\n-Al menos 1 mayuscula");
            if (!validaciones.isConfirmarContraseña())
                confirmarContraseñaRegistro.setError("Las contraseñas no coinciden");
        } else {
            verificarCorreoRegistrado();
        }
    }

    private void registrarUsuario() {
        mAuth.createUserWithEmailAndPassword(mailRegistro.getText().toString(), contraseñaRegistro.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Map<String, Object> map = new HashMap<>();
                            map.put("nombre", nombreRegistro.getText().toString());
                            map.put("apellido", apellidoRegistro.getText().toString());
                            map.put("correo", mailRegistro.getText().toString());

                            id = mAuth.getCurrentUser().getUid();

                            databaseReference.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if (task2.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Usuario crceado", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        Log.i("Info", "El correo ya existe");

                                    }

                                }
                            });


                        }
                    }
                });
    }

    public void verificarCorreoRegistrado() {
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(mailRegistro.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task2) {
                if (task2.isSuccessful()) {
                    check = !task2.getResult().getSignInMethods().isEmpty();
                    if (check) {
                        Toast.makeText(getApplicationContext(), "El email esta en uso", Toast.LENGTH_LONG).show();
                    } else {
                        registrarUsuario();
                    }
                }
            }
        });
    }

}