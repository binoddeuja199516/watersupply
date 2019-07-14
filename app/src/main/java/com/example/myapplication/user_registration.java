package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.API.UserApi;
import com.example.myapplication.Models.UserModel;
import com.example.myapplication.URL.servercon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class user_registration extends AppCompatActivity {

    Button register;
    UserApi userApi;
    EditText name,username,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        name=findViewById(R.id.rname);
        username=findViewById(R.id.rusername);
        email=findViewById(R.id.remail);
        password=findViewById(R.id.registration_Password);


        register=findViewById(R.id.button_registerfinal);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyInstanceCreater();
                UserModel registerModel=new UserModel(
                        name.getText().toString(),
                        username.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString()
                );
                Toast.makeText(user_registration.this, registerModel.toString() , Toast.LENGTH_SHORT).show();
                Call<Void> registerCall=userApi.registerUser(registerModel);
                registerCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(user_registration.this, "unable to register", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(user_registration.this, "Registered", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(user_registration.this,login_screen.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(user_registration.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
    private void MyInstanceCreater(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(servercon.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApi = retrofit.create(UserApi.class);
    }
}
