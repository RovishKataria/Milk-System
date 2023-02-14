package com.example.milkdistributionmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<CustomerInfo> customerInfoList;

    public MyAdapter(Context context, List<CustomerInfo> customerInfoList) {
        this.context = context;
        this.customerInfoList = customerInfoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(customerInfoList.get(position).getCustomerImage()).into(holder.recCustomerImage);
        holder.recCustomerName.setText(customerInfoList.get(position).getCustomerName());
        holder.recCustomerMobile.setText(customerInfoList.get(position).getCustomerMobile());
        holder.recCustomerAddress.setText(customerInfoList.get(position).getCustomerAddress());
        holder.recCustomerMilk.setText(customerInfoList.get(position).getCustomerMilk());
        holder.recCustomerMilkPrice.setText(customerInfoList.get(position).getCustomerMilkPrice());
        holder.recCustomerDays.setText(customerInfoList.get(position).getCount());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomerDetailActivity.class);
                intent.putExtra("Name", customerInfoList.get(holder.getAdapterPosition()).getCustomerName());
                intent.putExtra("Image", customerInfoList.get(holder.getAdapterPosition()).getCustomerImage());
                intent.putExtra("Mobile", customerInfoList.get(holder.getAdapterPosition()).getCustomerMobile());
                intent.putExtra("Address", customerInfoList.get(holder.getAdapterPosition()).getCustomerAddress());
                intent.putExtra("Milk", customerInfoList.get(holder.getAdapterPosition()).getCustomerMilk());
                intent.putExtra("Milk Price", customerInfoList.get(holder.getAdapterPosition()).getCustomerMilkPrice());
                intent.putExtra("Days", customerInfoList.get(holder.getAdapterPosition()).getCount());
                intent.putExtra("Key", customerInfoList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerInfoList.size();
    }

    public void searchDataList(ArrayList<CustomerInfo> searchList) {
        customerInfoList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recCustomerImage;
    TextView recCustomerName, recCustomerMobile, recCustomerAddress, recCustomerMilk, recCustomerMilkPrice, recCustomerDays;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recCustomerImage = itemView.findViewById(R.id.recCustomerImage);
        recCustomerName = itemView.findViewById(R.id.recCustomerName);
        recCustomerMobile = itemView.findViewById(R.id.recCustomerMobile);
        recCustomerAddress = itemView.findViewById(R.id.recCustomerAddress);
        recCustomerMilk = itemView.findViewById(R.id.recCustomerMilk);
        recCustomerMilkPrice = itemView.findViewById(R.id.recCustomerMilkPrice);
        recCustomerDays = itemView.findViewById(R.id.recCustomerDays);
        recCard = itemView.findViewById(R.id.recCard);
    }
}
