package com.example.milkdistributionmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class CustomerLoginActivity extends AppCompatActivity {

    EditText editTextCustomerUsername, editTextCustomerPassword;
    Button buttonCustomerLogin;
    TextView textViewCustomerSignup;

    private FirebaseAuth mAuth;
    private EditText editPhone, editOtp;
    private Button generateOtpButton, verifyButton;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        editTextCustomerUsername = findViewById(R.id.editTextCustomerUsername);
        editTextCustomerPassword = findViewById(R.id.editTextCustomerPassword);
        buttonCustomerLogin = findViewById(R.id.buttonCustomerLogin);
        textViewCustomerSignup = findViewById(R.id.textViewCustomerSignup);

        buttonCustomerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (editTextCustomerUsername.getText().toString().equals("user") &&
                            editTextCustomerPassword.getText().toString().equals("1234")) {
                        Toast.makeText(CustomerLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CustomerLoginActivity.this, CustomerDashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CustomerLoginActivity.this, "Login Failed! Kindly Signup First", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CustomerLoginActivity.this, CustomerSignupActivity.class);
                        startActivity(intent);
                    }
            }
        });

        textViewCustomerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CustomerSignupActivity.class));
            }
        });

//        This is code for login using mobile number

//        buttonSellerLoginVerifyOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (TextUtils.isEmpty(editTextSellerLoginOtp.getText().toString())) {
//                    Toast.makeText(SellerLoginActivity.this, "Wrong OTP Entered",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    verifyCode(editTextSellerLoginOtp.getText().toString());
//                }
//            }
//        });

//        private void sendVerificationCode(String phoneNumber) {
//            PhoneAuthOptions options =
//                    PhoneAuthOptions.newBuilder(mAuth)
//                            .setPhoneNumber("+91" + phoneNumber)       // Phone number to verify
//                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                            .setActivity(this)                 // Activity (for callback binding)
//                            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                            .build();
//            PhoneAuthProvider.verifyPhoneNumber(options);
//        }
//
//        private PhoneAuthProvider.OnVerificationStateChangedCallbacks
//                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
//                final String code = credential.getSmsCode();
//                if(code != null) {
//                    verifyCode(code);
//                }
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//                Toast.makeText(SellerLoginActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String s,
//                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                super.onCodeSent(s, token);
//                verificationID = s;
//                Toast.makeText(SellerLoginActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();
//                buttonSellerLoginVerifyOtp.setEnabled(true);
//                progressBar.setVisibility(View.INVISIBLE);
//            }
//        };
//
//        private void verifyCode(String Code) {
//            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, Code);
//            signinbyCredentials(credential);
//        }
//
//        private void signinbyCredentials(PhoneAuthCredential credential) {
//            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful()) {
//                        Toast.makeText(SellerLoginActivity.this, "Login Successful",
//                                Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(SellerLoginActivity.this, SellerDashboardActivity.class));
//                        finish();
//                    }
//                }
//            });
//        }
    }
}














