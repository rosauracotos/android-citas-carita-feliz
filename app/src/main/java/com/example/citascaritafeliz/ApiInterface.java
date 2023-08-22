package com.example.citascaritafeliz;

import com.example.citascaritafeliz.model.Paciente;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("paciente/guardar")
    Call<ResponseBody> guardarPaciente(@Body Paciente paciente);

}
