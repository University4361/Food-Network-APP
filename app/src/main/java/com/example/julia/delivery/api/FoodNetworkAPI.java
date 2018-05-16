package com.example.julia.delivery.api;

import com.example.julia.delivery.api.models.AuthRequest;
import com.example.julia.delivery.api.models.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Lavrov on 16.05.2018.
 */

public interface FoodNetworkAPI {
    @POST("api/Login")
    Call<AuthResponse> loginCourier(@Body AuthRequest request);
}
