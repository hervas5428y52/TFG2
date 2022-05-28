package com.example.tfg2.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class inicioSesion extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    //Inicio de sesion
    EditText ETmailIniciarSesion;
    EditText ETcontraseñaIniciarSesion;
    Button btnIniciarSesion;
    TextView noEstoyRegistrado;
    TextView olvidoContraseña;

    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        mAuth = FirebaseAuth.getInstance();

        //Formulario Inicio de Sesion
        ETmailIniciarSesion = findViewById(R.id.editTextMailInicio);
        ETcontraseñaIniciarSesion = findViewById(R.id.editTextContraseñaInicio);
        btnIniciarSesion = findViewById(R.id.btnInicioSesion);
        noEstoyRegistrado = findViewById(R.id.textParaRegistrarse);
        olvidoContraseña = findViewById(R.id.txtHasOlvidadoLaContraseña);


        noEstoyRegistrado.setOnClickListener(this);
        btnIniciarSesion.setOnClickListener(this);
        olvidoContraseña.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnInicioSesion:

                enterValues();

                break;
            case R.id.textParaRegistrarse:
                finish();
                startActivity(new Intent(this, Registro.class));
                finish();
                break;
            case R.id.txtHasOlvidadoLaContraseña:
                startActivity(new Intent(this, reseteoContrasenaActivity.class));
                break;

        }
    }

    public void enterValues() {

        if (ETmailIniciarSesion.getText().toString().equals("")) {
            ETmailIniciarSesion.setError("Introduce un mail");
            if (ETcontraseñaIniciarSesion.getText().toString().equals("")) {
                ETcontraseñaIniciarSesion.setError("Introduce una contraseña");
            }
        } else if (ETcontraseñaIniciarSesion.getText().toString().equals("")) {
            ETcontraseñaIniciarSesion.setError("Introduce una contraseña");
            if (ETmailIniciarSesion.getText().toString().equals("")) {
                ETmailIniciarSesion.setError("Introduce un mail");
            }
        } else {
            iniciarSesion();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            Log.i("Info", "el usuario ya esta registrado");
        } else {
            Log.i("Info", "el usuario no esta registrado");
        }
    }

    private void iniciarSesion() {
        mAuth.signInWithEmailAndPassword(ETmailIniciarSesion.getText().toString(), ETcontraseñaIniciarSesion.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Sesion iniciada",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            i = 0;
                        } else {
                            Toast.makeText(getApplicationContext(), "Revise bien los datos introducidos",
                                    Toast.LENGTH_SHORT).show();
                            i++;
                            if (i == 3) {
                                olvidoContraseña.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
    }
}