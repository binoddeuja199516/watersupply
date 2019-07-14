package com.example.myapplication.API;

import com.example.myapplication.Models.OrderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApi {
    @GET("/showorderdetails")
    Call<List<OrderModel>> getOrderDetails(@Header("Authorization") String auth);

    @POST("addorderinfo")
    Call<Void> addorders(@Header("Authorization") String auth, @Body OrderModel orderModel);

    @PUT("/updateuserorderdetails/{id}")
    Call<Void> updateuserorderdetails(@Path("id") String id,@Body OrderModel orderModel);

    @DELETE("deleteorder/{id}")
    Call<Void> deleteit(@Header("Authorization") String auth, @Path("id") String id);
}
