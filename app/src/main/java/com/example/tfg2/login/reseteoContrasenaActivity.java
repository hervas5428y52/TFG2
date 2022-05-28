package com.example.tfg2.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfg2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class reseteoContrasenaActivity extends AppCompatActivity {

    Button resetearContraseña;
    EditText campoCorreoReseteoContraseña;
    Intent i;
    FirebaseAuth Auth;
    String mailAddress;
    String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reseteo_contrasena);

        resetearContraseña = findViewById(R.id.btnReseteo);
        campoCorreoReseteoContraseña = findViewById(R.id.editTextMailReseteo);
        resetearContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

    }

    private void validate() {
        mail = campoCorreoReseteoContraseña.getText().toString();

        if(mail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            campoCorreoReseteoContraseña.setError("Correo invalido");
            return;
        }

        sendMail(mail);
    }

    private void sendMail(String mail) {
        Auth = FirebaseAuth.getInstance();
        mailAddress = mail;

        Auth.sendPasswordResetEmail(mailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(reseteoContrasenaActivity.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                    i = new Intent(reseteoContrasenaActivity.this, inicioSesion.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(reseteoContrasenaActivity.this, "Correo invalido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}