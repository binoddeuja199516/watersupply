package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {

    Button neworder,updateorder,myorders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        neworder =findViewById(R.id.btnOrder);
        updateorder=findViewById(R.id.btnUpdateOrder);
        myorders=findViewById(R.id.btnViewOrder);

        neworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Dashboard.this, NewOrder.class);
                startActivity(intent);
            }
        });
        updateorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Dashboard.this,UpdateOrder.class);
                startActivity(intent);
            }
        });

        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Dashboard.this, MyOrders.class);
                startActivity(intent);
            }
        });
    }
}
