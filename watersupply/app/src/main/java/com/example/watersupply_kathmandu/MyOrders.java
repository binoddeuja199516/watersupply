package com.example.watersupply_kathmandu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.watersupply_kathmandu.API.OrderApi;
import com.example.watersupply_kathmandu.AdapterPackage.OrderAdapter;
import com.example.watersupply_kathmandu.Models.OrderModel;
import com.example.watersupply_kathmandu.URL.servercon;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOrders extends AppCompatActivity {

    private RecyclerView recyclerView;
    OrderApi orderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        MyInstanceCreater();
        recyclerView = findViewById(R.id.recycler_my_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyOrders.this));

        Call<List<OrderModel>> listCall = orderApi.getOrderDetails();
        listCall.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(Call<List<OrderModel>> call, Response<List<OrderModel>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MyOrders.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<OrderModel> orderModels = response.body();
                recyclerView.setAdapter(new OrderAdapter(MyOrders.this, orderModels));
            }

            @Override
            public void onFailure(Call<List<OrderModel>> call, Throwable t) {
                Toast.makeText(MyOrders.this, "No location details", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void MyInstanceCreater(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(servercon.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        orderApi = retrofit.create(OrderApi.class);
    }
}

