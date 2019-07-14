package com.example.watersupply_kathmandu.API;

import com.example.watersupply_kathmandu.Models.OrderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderApi {
    @GET("/showorderdetails")
    Call<List<OrderModel>> getOrderDetails();

    @POST("addorderinfo")
    Call<Void> addorders(@Header("Authorization") String auth, @Body OrderModel orderModel);

}
