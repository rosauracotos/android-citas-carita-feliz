package com.example.citascaritafeliz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.citascaritafeliz.R;
import com.example.citascaritafeliz.model.Medico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarCitaActivity extends AppCompatActivity {

    Spinner spMedico;
    private ArrayList<String> listaMedicos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cita);
        spMedico = findViewById(R.id.spinnerMedicos);
        getMedicos();
    }

    private void getMedicos() {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().obtenerMedicos();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            String getResponse = response.body().string();
                            List<Medico> getMedicos = new ArrayList<Medico>();
                            JSONArray jsonArray = new JSONArray(getResponse);
                            getMedicos.add(new Medico(-1, "Seleccionar Médico", null, null));
                            for (int i=0; i< jsonArray.length(); i++) {
                                Medico medico = new Medico();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                medico.setId(jsonObject.getInt("id"));
                                medico.setNombrecompleto(jsonObject.getString("nombrecompleto"));
                                medico.setEspecialidad(jsonObject.getString("especialidad"));
                                medico.setDni(jsonObject.getString("dni"));
                                getMedicos.add(medico);
                            }
                            for (int i = 0; i < getMedicos.size(); i++) {
                                listaMedicos.add(getMedicos.get(i).getNombrecompleto().toString());
                            }
                            ArrayAdapter<String> spinStateAdapter = new ArrayAdapter<String>(RegistrarCitaActivity.this, android.R.layout.simple_spinner_item,listaMedicos);
                            spinStateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spMedico.setAdapter(spinStateAdapter);
                            spMedico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        } catch (IOException e) {

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegistrarCitaActivity.this,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}