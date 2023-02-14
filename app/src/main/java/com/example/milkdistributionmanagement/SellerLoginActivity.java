package com.example.milkdistributionmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SellerLoginActivity extends AppCompatActivity {

    EditText  editTxtSellerLoginEmail, editTxtSellerLoginPassword;
    Button btnSellerLogin;
    TextView txtViewSellerSignUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        auth = FirebaseAuth.getInstance();
        editTxtSellerLoginEmail = findViewById(R.id.editTxtSellerLoginEmail);
        editTxtSellerLoginPassword = findViewById(R.id.editTxtSellerLoginPassword);
        btnSellerLogin = findViewById(R.id.btnSellerLogin);
        txtViewSellerSignUp = findViewById(R.id.txtViewSellerSignUp);

        btnSellerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTxtSellerLoginEmail.getText().toString();
                String pass = editTxtSellerLoginPassword.getText().toString();

                if (!validateEmail() | !validatePassword()) {

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SellerLoginActivity.this);
                    builder.setCancelable(false);
                    builder.setView(R.layout.progress_layout);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(SellerLoginActivity.this, SellerDashboardActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(SellerLoginActivity.this, "Login Failed Credentials not matched",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
//                    checkEmail(email.trim(), pass.trim());
                }
            }
        });

        txtViewSellerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerLoginActivity.this, SellerSignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public Boolean validateEmail() {
        String val = editTxtSellerLoginEmail.getText().toString();
        if(val.isEmpty()){
            editTxtSellerLoginEmail.setError("Email ID cannot be empty");
            return false;
        } else {
            editTxtSellerLoginEmail.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String val = editTxtSellerLoginPassword.getText().toString();
        if(val.isEmpty()){
            editTxtSellerLoginPassword.setError("Password cannot be empty");
            return false;
        } else {
            editTxtSellerLoginPassword.setError(null);
            return true;
        }
    }

//    public void checkEmail(String email, String pass){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Seller List");
//        Query checkUserDatabase = reference.orderByChild("name").equalTo(name);
//
//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()) {
//                    editTxtSellerLoginEmailId.setError(null);
//                    String emailFromDB = snapshot.child(name).child("email").getValue(String.class);
//                    String passFromDB = snapshot.child(name).child("password").getValue(String.class);
//
//                    if(emailFromDB.equals(email) & passFromDB.equals(pass)) {
//                        editTxtSellerLoginEmailId.setError(null);
////                        passing data using internet
//                        String nameFromDB = snapshot.child(name).child("name").getValue(String.class);
//                        String phoneFromDB = snapshot.child(name).child("mobile_number").getValue(String.class);
//
//                        Intent intent = new Intent(SellerLoginActivity.this, SellerDashboardActivity.class);
//                        intent.putExtra("name", nameFromDB);
//                        intent.putExtra("email", emailFromDB);
//                        intent.putExtra("mobile_number", phoneFromDB);
//                        intent.putExtra("password", passFromDB);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        editTxtSellerLoginPassword.setError("Invalid Credentials");
//                        editTxtSellerLoginPassword.requestFocus();
//                    }
//                } else {
//                    editTxtSellerLoginEmailId.setError("User does not exists!");
//                    editTxtSellerLoginEmailId.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Intent intent = new Intent(SellerLoginActivity.this, SellerDashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }
}