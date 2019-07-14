package com.example.watersupply_kathmandu.API;

import com.example.watersupply_kathmandu.Models.LoginResponse;
import com.example.watersupply_kathmandu.Models.OrderModel;
import com.example.watersupply_kathmandu.Models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/login")
    Call<LoginResponse> loginUser(@Body UserModel loginModel);

    @POST("/register")
    Call<Void> registerUser(@Body UserModel registerModel);





}
