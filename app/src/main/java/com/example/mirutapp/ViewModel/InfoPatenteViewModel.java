package com.example.mirutapp.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.mirutapp.WebService.InfoPatenteWebService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InfoPatenteViewModel extends ViewModel {
    private InfoPatenteWebService webService;
    private String responseBody;

    public void init(String patente) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mirutapp.herokuapp.com/")
                .build();
        webService = retrofit.create(InfoPatenteWebService.class);
        Call<ResponseBody> call = webService.getInfoPatente(patente);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //prints para debug
                System.out.println(response.code());
                try {
                    responseBody = response.body().string();
                    System.out.println(response.body().string());

                } catch(IOException e) {
                    responseBody = "";
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                responseBody = "";
            }
        });
    }
}
