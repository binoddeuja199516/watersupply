package com.example.myapplication.API;


import com.example.myapplication.Models.LoginResponse;
import com.example.myapplication.Models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/login")
    Call<LoginResponse> loginUser(@Body UserModel loginModel);

    @POST("/register")
    Call<Void> registerUser(@Body UserModel registerModel);







}
