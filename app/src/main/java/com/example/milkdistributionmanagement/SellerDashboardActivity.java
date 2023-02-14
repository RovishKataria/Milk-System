package com.example.milkdistributionmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SellerDashboardActivity extends AppCompatActivity {

    TextView txtViewSellerDashboardName;
    Button buttonAllCustomers, buttonAllDrivers, buttonDailySell, buttonCreateBill, buttonReport,
            buttonSettings, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        txtViewSellerDashboardName = findViewById(R.id.txtViewSellerDashboardName);
        buttonAllCustomers = findViewById(R.id.buttonAllCustomers);
        buttonAllDrivers = findViewById(R.id.buttonAllDrivers);
        buttonDailySell = findViewById(R.id.buttonDailySell);
        buttonCreateBill = findViewById(R.id.buttonCreateBill);
        buttonReport = findViewById(R.id.buttonReport);
        buttonSettings = findViewById(R.id.buttonSettings);
        buttonLogout = findViewById(R.id.buttonLogout);

        buttonAllCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerDashboardActivity.this, SellerDashboardAllCustomersActivity.class);
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerDashboardActivity.this, SelectUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}