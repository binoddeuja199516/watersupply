package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.API.OrderApi;
import com.example.myapplication.Models.OrderModel;
import com.example.myapplication.URL.servercon;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewOrder extends AppCompatActivity {


    EditText name, contactno;
    EditText deliverydate;
    EditText quantity;
    EditText deliverylocation, otherdetails, user_email;
    Button creatorder;
    DatePickerDialog picker;
    OrderApi orderAPi;
    String token;

    private float dataAceelo;
    private float dataAceelocurrent;
    private float dataAceelolast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders);

        shake();

        name = findViewById(R.id.txt_OrderName);
        contactno = findViewById(R.id.txt_OrderContactNo);
        quantity = findViewById(R.id.txt_OrderQuantity);
        deliverydate = findViewById(R.id.txt_OrderDate);
        deliverylocation = findViewById(R.id.txt_OrderLocation);
        otherdetails = findViewById(R.id.txt_OrderDetails);
        user_email = findViewById(R.id.txt_OrderEmail);
        creatorder = findViewById(R.id.button_Orderit);
        SharedPreferences sharedPreferences = this.getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        deliverydate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    picker = new DatePickerDialog(NewOrder.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    deliverydate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                            }, year, month, day);
                    picker.show();
                }
            }
        });

        creatorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyInstanceCreater();

                OrderModel orderModel = new OrderModel(name.getText().toString(), contactno.getText().toString(),
                        deliverydate.getText().toString(),
                        quantity.getText().toString(),
                        deliverylocation.getText().toString(),
                        otherdetails.getText().toString(),
                        user_email.getText().toString());
                Call<Void> registerCall = orderAPi.addorders("Bearer " + token, orderModel);
                registerCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(NewOrder.this, response.message(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewOrder.this, MyOrders.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(NewOrder.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });


    }


    private void MyInstanceCreater() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(servercon.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        orderAPi = retrofit.create(OrderApi.class);
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
                    Intent intent = new Intent(NewOrder.this, Dashboard.class);
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
