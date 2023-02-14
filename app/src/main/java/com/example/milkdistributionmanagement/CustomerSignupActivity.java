package com.example.milkdistributionmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerSignupActivity extends AppCompatActivity {

    EditText editTextCustomerSignupUsername, editTextCustomerSignupMobileNumber,
            editTextCustomerSignupAddress, editTextCustomerSignupPassword;
    Button buttonCustomerSignup;
    TextView textViewCustomerAlreadyRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);

        editTextCustomerSignupUsername = findViewById(R.id.editTextCustomerSignupUsername);
        editTextCustomerSignupMobileNumber = findViewById(R.id.editTextCustomerSignupMobileNumber);
        editTextCustomerSignupAddress = findViewById(R.id.editTextCustomerSignupAddress);
        editTextCustomerSignupPassword = findViewById(R.id.editTextCustomerSignupPassword);
        buttonCustomerSignup = findViewById(R.id.buttonCustomerSignup);
        textViewCustomerAlreadyRegistered = findViewById(R.id.textViewCustomerAlreadyRegistered);

        buttonCustomerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextCustomerSignupUsername.getText().toString().isEmpty() ||
                        editTextCustomerSignupMobileNumber.getText().toString().isEmpty() ||
                        editTextCustomerSignupAddress.getText().toString().isEmpty() ||
                        editTextCustomerSignupPassword.getText().toString().isEmpty()) {
                    Toast.makeText(CustomerSignupActivity.this, "Kindly fill all the details",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CustomerSignupActivity.this, "Signup Successfull",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewCustomerAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CustomerLoginActivity.class));
            }
        });
    }
}