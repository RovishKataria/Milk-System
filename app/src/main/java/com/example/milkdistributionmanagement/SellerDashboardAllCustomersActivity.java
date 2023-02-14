package com.example.milkdistributionmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellerDashboardAllCustomersActivity extends AppCompatActivity {

    FloatingActionButton buttonAddCustomers;
    RecyclerView recyclerView;
    List<CustomerInfo> customerInfoList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard_all_customers);

        buttonAddCustomers = findViewById(R.id.buttonAddCustomers);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchCustomer);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(SellerDashboardAllCustomersActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(SellerDashboardAllCustomersActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        customerInfoList = new ArrayList<>();

        adapter = new MyAdapter(SellerDashboardAllCustomersActivity.this, customerInfoList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Customer Info");
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                customerInfoList.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    CustomerInfo customerInfo = itemSnapshot.getValue(CustomerInfo.class);
                    customerInfo.setKey(itemSnapshot.getKey());
                    customerInfoList.add(customerInfo);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        buttonAddCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerDashboardAllCustomersActivity.this, CustomerUploadActivity.class);
                startActivity(intent);
            }
        });
    }

    public void searchList(String text) {
        ArrayList<CustomerInfo> searchList = new ArrayList<>();
        for(CustomerInfo customerInfo: customerInfoList) {
            if(customerInfo.getCustomerName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(customerInfo);
            }
        }
        adapter.searchDataList(searchList);
    }
}