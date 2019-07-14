package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.API.UserApi;
import com.example.myapplication.Models.LoginResponse;
import com.example.myapplication.Models.UserModel;
import com.example.myapplication.URL.servercon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login_screen extends AppCompatActivity {

    EditText Login_userEmail;
    EditText Login_userPassword;
    Button button_login;
    Button button_Register;
    UserApi userApi;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        v.vibrate(400);

        Login_userEmail=findViewById(R.id.Login_userEmail);
        Login_userPassword=findViewById(R.id.Login_userPassword);
        button_login=findViewById(R.id.button_login);
        button_Register=findViewById(R.id.button_register);

        button_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_screen.this,user_registration.class);
                startActivity(intent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("patur"+Login_userEmail.getText().toString());
                InstanceCreater();
                Call<LoginResponse> call=userApi.loginUser(new UserModel(""
                        ,Login_userEmail.getText().toString()
                        ,Login_userPassword.getText().toString(),""));

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(login_screen.this, "Unable to login", Toast.LENGTH_SHORT).show();
                        } else {
                            editor.putString("token", response.body().getToken());
                            editor.putString("username", response.body().getUsername());
                            editor.putString("userId", response.body().getUsername());
                            editor.commit();
                            Toast.makeText(login_screen.this, "Logged In", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login_screen.this, Dashboard.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });

            }
        });




    }

    private void InstanceCreater() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(servercon.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApi = retrofit.create(UserApi.class);
    }

}
