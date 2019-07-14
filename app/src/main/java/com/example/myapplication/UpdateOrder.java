package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.OrderApi;
import com.example.myapplication.Models.OrderModel;
import com.example.myapplication.URL.servercon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateOrder extends AppCompatActivity {
    TextView txt_UpdateName, txt_UpdateEmail, txt_UpdateContactNo, txt_UpdateDate, txt_UpdateQuantity, txt_UpdateLocation, txt_UpdateDetails;
    Button button_Update;
    Bundle bundle;
    String id;
    OrderApi orderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);
        bundle = getIntent().getExtras();

        txt_UpdateName = findViewById(R.id.txt_UpdateName);
        txt_UpdateEmail = findViewById(R.id.txt_UpdateEmail);
        txt_UpdateContactNo = findViewById(R.id.txt_UpdateContactNo);
        txt_UpdateDate = findViewById(R.id.txt_UpdateDate);
        txt_UpdateQuantity = findViewById(R.id.txt_UpdateQuantity);
        txt_UpdateLocation = findViewById(R.id.txt_UpdateLocation);
        txt_UpdateDetails = findViewById(R.id.txt_UpdateDetails);
        button_Update = findViewById(R.id.button_Update);

        if (bundle != null) {
            txt_UpdateName.setText(bundle.getString("name"));
            txt_UpdateContactNo.setText(bundle.getString("contactNo"));
            txt_UpdateEmail.setText(bundle.getString("email"));
            txt_UpdateDate.setText(bundle.getString("deliveryDate"));
            txt_UpdateQuantity.setText(bundle.getString("quantity"));
            txt_UpdateLocation.setText(bundle.getString("location"));
            txt_UpdateDetails.setText(bundle.getString("details"));
            id = bundle.getString("id");
        }


        button_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyInstanceCreater();

                OrderModel orderModel=new OrderModel(txt_UpdateName.getText().toString(),txt_UpdateContactNo.getText().toString(),
                        txt_UpdateDate.getText().toString(),txt_UpdateQuantity.getText().toString(),
                        txt_UpdateLocation.getText().toString(),txt_UpdateDetails.getText().toString(),txt_UpdateEmail.getText().toString());


                Call<Void> call = orderApi.updateuserorderdetails(id,orderModel);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(UpdateOrder.this, "Post Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateOrder.this, MyOrders.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(UpdateOrder.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void MyInstanceCreater() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(servercon.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        orderApi = retrofit.create(OrderApi.class);
    }
}
