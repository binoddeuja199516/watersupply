package com.example.watersupply_kathmandu.AdapterPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.watersupply_kathmandu.Models.OrderModel;
import com.example.watersupply_kathmandu.Models.UserModel;
import com.example.watersupply_kathmandu.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderAdapterHolder> {

    private Context context;
    private List<OrderModel> orderModels;

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
        OrderModel orderModel = orderModels.get(i);
        orderAdapterHolder.view_txtDeliveryLocation.setText(orderModel.getDeliverylocation());
        orderAdapterHolder.view_txtQty.setText(orderModel.getQuantity().toString());
        orderAdapterHolder.view_txtDeliveryDate.setText(orderModel.getDeliverydate().toString());
        orderAdapterHolder.view_txtEmail.setText(orderModel.getUser_email());
        orderAdapterHolder.view_txtContact.setText(orderModel.getContactno());
        orderAdapterHolder.view_txtName.setText(orderModel.getName());
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
