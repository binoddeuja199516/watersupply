package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.myapplication.API.OrderApi;
import com.example.myapplication.AdapterPackage.OrderAdapter;
import com.example.myapplication.Models.OrderModel;
import com.example.myapplication.URL.servercon;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOrders extends AppCompatActivity {

    private RecyclerView recyclerView;
    OrderApi orderApi;
    String token;
    private float dataAceelo;
    private float dataAceelocurrent;
    private float dataAceelolast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        shake();
        MyInstanceCreater();
        recyclerView = findViewById(R.id.recycler_my_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyOrders.this));


        SharedPreferences sharedPreferences = this.getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        Call<List<OrderModel>> listCall = orderApi.getOrderDetails("Bearer " + token);
//        Call<List<OrderModel>> listCall = orderApi.getOrderDetails();
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

    private void shake() {
        SensorManager sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener accelerometerListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                float xaxis = values[0];
                float yaxis = values[1];
                float zaxis = values[2];
                dataAceelolast = dataAceelocurrent;
                dataAceelocurrent = (float) Math.sqrt((double) (xaxis * xaxis + yaxis * yaxis + zaxis * zaxis));
                float delta = dataAceelocurrent - dataAceelolast;
                dataAceelo = dataAceelo * 0.9f + delta;
                if (dataAceelo > 50) {
                    Intent intent = new Intent(MyOrders.this, Dashboard.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        if (sensor == null) {
            Toast.makeText(this, "Accelometer sensor not detected", Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(accelerometerListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}
