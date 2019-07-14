package com.example.watersupply_kathmandu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

public class spalsh_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        SharedPreferences sharedPreferences= this.getSharedPreferences("Auths", Context.MODE_PRIVATE);
        final String userexist = sharedPreferences.getString("token", "");

        final Intent goToMain=new Intent(this,login_screen.class);
        final Intent gotodashboarrd = new Intent(this, Dashboard.class);
        //Sending navigation to main by makaing splash screen for defined time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!TextUtils.isEmpty(userexist)){
                    //                startActivity(goToMain);
                    startActivity(goToMain);
                    finish();

                }else {
                    startActivity(goToMain);
                    finish();
                }


            }

        },3800);
    }
}
