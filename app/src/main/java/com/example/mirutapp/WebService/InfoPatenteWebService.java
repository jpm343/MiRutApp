package com.example.mirutapp.WebService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InfoPatenteWebService {
    @GET("revision_tecnica/{patente}/verificar.json/")
    Call<ResponseBody> getInfoPatente(@Path("patente") String patente);
}
