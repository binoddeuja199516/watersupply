package com.example.myapplication.AdapterPackage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.API.OrderApi;
import com.example.myapplication.Dashboard;
import com.example.myapplication.Models.OrderModel;
import com.example.myapplication.R;
import com.example.myapplication.URL.servercon;
import com.example.myapplication.UpdateOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderAdapterHolder> {

    private Context context;
    private List<OrderModel> orderModels;
    OrderApi orderApi;
    String token;
    public OrderAdapter(Context context, List<OrderModel> orderModels) {
        this.context = context;
        this.orderModels = orderModels;
    }

    @NonNull
    @Override
    public OrderAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orders, viewGroup, false);
        return new OrderAdapterHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapterHolder orderAdapterHolder, int i) {
        final OrderModel orderModel = orderModels.get(i);
        orderAdapterHolder.view_txtDeliveryLocation.setText(orderModel.getDeliverylocation());
        orderAdapterHolder.view_txtQty.setText(orderModel.getQuantity());
        orderAdapterHolder.view_txtDeliveryDate.setText(orderModel.getDeliverydate());
        orderAdapterHolder.view_txtEmail.setText(orderModel.getUser_email());
        orderAdapterHolder.view_txtContact.setText(orderModel.getContactno());

        SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");


        orderAdapterHolder.view_txtName.setText(orderModel.getName());
        final String id = orderModel.get_id();
        System.out.println("lllllll"+id);
        orderAdapterHolder.btn_UpdateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context updateContext = v.getContext();
                Intent intent = new Intent(context, UpdateOrder.class);
                intent.putExtra("id", id);
                intent.putExtra("name", orderModel.getName());
                intent.putExtra("contactNo", orderModel.getContactno());
                intent.putExtra("email", orderModel.getUser_email());
                intent.putExtra("deliveryDate", orderModel.getDeliverydate());
                intent.putExtra("quantity", orderModel.getQuantity());
                intent.putExtra("location", orderModel.getDeliverylocation());
                intent.putExtra("details",orderModel.getOtherdetails());
                updateContext.startActivity(intent);
            }
        });

        orderAdapterHolder.btn_CancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MyInstanceCreater();
                Call<Void> calldlt = orderApi.deleteit("Bearer " + token, orderModel.get_id());
                calldlt.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(context, "Cancelation failure", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Order cancled", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(context, Dashboard.class);
                             context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

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

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class OrderAdapterHolder extends RecyclerView.ViewHolder {
        TextView view_txtDeliveryLocation, view_txtQty, view_txtDeliveryDate, view_txtEmail, view_txtContact, view_txtName;
        Button btn_UpdateOrder, btn_CancelOrder;

        public OrderAdapterHolder(@NonNull View itemView) {
            super(itemView);
            view_txtDeliveryLocation = itemView.findViewById(R.id.view_txtDeliveryLocation);
            view_txtQty = itemView.findViewById(R.id.view_txtQty);
            view_txtDeliveryDate = itemView.findViewById(R.id.view_txtDeliveryDate);
            view_txtEmail = itemView.findViewById(R.id.view_txtEmail);
            view_txtContact = itemView.findViewById(R.id.view_txtContact);
            view_txtName = itemView.findViewById(R.id.view_txtName);
            btn_UpdateOrder = itemView.findViewById(R.id.btn_UpdateOrder);
            btn_CancelOrder = itemView.findViewById(R.id.btn_CancelOrder);
        }
    }
}
