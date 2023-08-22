package com.example.citascaritafeliz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText dni, password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarFirebase();

        // Campos
        dni = findViewById(R.id.txtDNI);
        password = findViewById(R.id.txtPassword);

        // Boton Login
        Button btnLoguearse = findViewById(R.id.btnLogin);

        // Boton Registrar
        TextView btnRegistrar = findViewById(R.id.registroBtn);

        // Accion Boton
        btnLoguearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dniIngresado = dni.getText().toString().trim();
                String passwordIngresado = password.getText().toString().trim();
                if (dniIngresado.equals("") || passwordIngresado.equals("")) {
                    validacion();
                } else {
                    validarLogin(dniIngresado, passwordIngresado);
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrarActivity.class));
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void validacion() {
        String dniInput = dni.getText().toString();
        String passwordInput = password.getText().toString();

        if (dniInput.equals("")) {
            dni.setError("Required");
        }

        if (passwordInput.equals("")) {
            password.setError("Required");
        }
    }

    private void validarLogin(String dni, String password) {
        databaseReference.child("Paciente").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(dni)) {
                    final String getPassword = snapshot.child(dni).child("password").getValue(String.class);
                    if (getPassword.equals(password)) {
                        Toast.makeText(LoginActivity.this,"Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, InicioActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this,"Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,"Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}