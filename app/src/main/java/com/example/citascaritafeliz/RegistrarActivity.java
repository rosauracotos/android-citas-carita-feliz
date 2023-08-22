package com.example.citascaritafeliz;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citascaritafeliz.model.Paciente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarActivity extends AppCompatActivity {

    EditText nombre, apellidos, dni, password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_main);

        // Campos
        nombre = findViewById(R.id.txtNombre);
        apellidos = findViewById(R.id.txtApellido);
        dni = findViewById(R.id.txtDNI);
        password = findViewById(R.id.txtPassword);

        // Boton Registrar
        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        TextView btnLoginRegresar = findViewById(R.id.loginRegresarBtn);

        inicializarFirebase();

        // Accion Boton
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreIngresado = nombre.getText().toString().trim();
                String apellidosIngresado = apellidos.getText().toString().trim();
                String dniIngresado = dni.getText().toString().trim();
                String passwordIngresado = password.getText().toString().trim();
                if (nombreIngresado.equals("") || apellidosIngresado.equals("") || dniIngresado.equals("") || passwordIngresado.equals("")) {
                    validacion();
                } else {
                    registrarPaciente(nombreIngresado, apellidosIngresado, dniIngresado, passwordIngresado);
                }
            }
        });

        btnLoginRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void registrarPaciente(String nombre, String apellido, String dni, String password) {
        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setPassword(password);
        databaseReference.child("Paciente").child(paciente.getDni()).setValue(paciente);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().guardarPaciente(paciente);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    Toast.makeText(RegistrarActivity.this,s, Toast.LENGTH_LONG).show();
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegistrarActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void validacion() {
        String nombreInput = nombre.getText().toString();
        String apellidosInput = apellidos.getText().toString();
        String dniInput = dni.getText().toString();
        String passwordInput = password.getText().toString();

        if (nombreInput.equals("")) {
            nombre.setError("Required");
        }

        if (apellidosInput.equals("")) {
            apellidos.setError("Required");
        }

        if (dniInput.equals("")) {
            dni.setError("Required");
        }

        if (passwordInput.equals("")) {
            password.setError("Required");
        }
    }
}