package com.example.milkdistributionmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectUserActivity extends AppCompatActivity {

    private Button btnMilkSeller, btnCustomer, btnDeliveryAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        btnMilkSeller = findViewById(R.id.btnMilkSeller);
        btnCustomer = findViewById(R.id.btnCustomer);
        btnDeliveryAgent = findViewById(R.id.btnDeliveryAgent);

        btnMilkSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectUserActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectUserActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnDeliveryAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectUserActivity.this, DeliveryPersonActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}