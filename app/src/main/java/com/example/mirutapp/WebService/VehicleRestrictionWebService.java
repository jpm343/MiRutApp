package com.example.mirutapp.WebService;

import com.example.mirutapp.Model.VehicleRestriction;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VehicleRestrictionWebService {
    // temporary url
    @GET("api/home2/")
    Call<VehicleRestriction> getVehicleRestriction();
}
