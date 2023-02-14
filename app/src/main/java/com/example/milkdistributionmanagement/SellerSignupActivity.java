package com.example.milkdistributionmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellerSignupActivity extends AppCompatActivity {

    EditText editTxtSellerSignupName, editTxtSellerSignupMobileNumber,
            editTxtSellerSignupEmailId, editTxtSellerSignupPassword;
    Button btnSellerSignup;
    TextView txtViewSellerAlreadyRegistered;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_signup);

        editTxtSellerSignupName = findViewById(R.id.editTxtSellerSignupName);
        editTxtSellerSignupMobileNumber = findViewById(R.id.editTxtSellerSignupMobileNumber);
        editTxtSellerSignupEmailId = findViewById(R.id.editTxtSellerSignupEmailId);
        editTxtSellerSignupPassword = findViewById(R.id.editTxtSellerSignupPassword);
        btnSellerSignup = findViewById(R.id.btnSellerSignup);
        txtViewSellerAlreadyRegistered = findViewById(R.id.txtViewSellerAlreadyRegistered);
        auth = FirebaseAuth.getInstance();

        btnSellerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateName() | !validateMobileNumber() | !validateEmail() | !validatePassword()) {

                } else {
                    String name = editTxtSellerSignupName.getText().toString();
                    String phone = editTxtSellerSignupMobileNumber.getText().toString();
                    String email = editTxtSellerSignupEmailId.getText().toString();
                    String pass = editTxtSellerSignupPassword.getText().toString();

                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                createSellerDatabase(name, phone, email, pass);
                            } else {
                                Toast.makeText(SellerSignupActivity.this, "Signup Failed" +
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        txtViewSellerAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerSignupActivity.this, SellerLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void createSellerDatabase(String name, String phone, String email, String pass) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Seller List");

        MilkSeller milkSeller = new MilkSeller(name, phone, email, pass);
        reference.child(name).setValue(milkSeller);

        Toast.makeText(SellerSignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SellerSignupActivity.this, SellerDashboardActivity.class);
        startActivity(intent);
        finish();
    }

    public Boolean validateName() {
        String val = editTxtSellerSignupName.getText().toString();
        if(val.isEmpty()){
            editTxtSellerSignupName.setError("Name cannot be empty");
            return false;
        } else {
            editTxtSellerSignupName.setError(null);
            return true;
        }
    }

    public Boolean validateMobileNumber() {
        String val = editTxtSellerSignupMobileNumber.getText().toString();
        if(val.isEmpty()){
            editTxtSellerSignupMobileNumber.setError("Mobile Number cannot be empty");
            return false;
        } else {
            editTxtSellerSignupMobileNumber.setError(null);
            return true;
        }
    }

    public Boolean validateEmail() {
        String val = editTxtSellerSignupEmailId.getText().toString();
        if(val.isEmpty()){
            editTxtSellerSignupEmailId.setError("Email ID cannot be empty");
            return false;
        } else {
            editTxtSellerSignupEmailId.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = editTxtSellerSignupPassword.getText().toString();
        if(val.isEmpty()){
            editTxtSellerSignupPassword.setError("Password cannot be empty");
            return false;
        } else {
            editTxtSellerSignupPassword.setError(null);
            return true;
        }
    }
}