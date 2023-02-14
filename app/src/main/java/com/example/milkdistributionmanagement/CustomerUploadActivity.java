package com.example.milkdistributionmanagement;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class CustomerUploadActivity extends AppCompatActivity {

    EditText editTxtSellerAddCustomerName, editTxtSellerAddCustomerMobileNumber, editTxtSellerAddCustomerAddress,
            editTxtSellerAddCustomerMilk, editTxtSellerAddCustomerMilkPrice;
    Button buttonSellerAddCustomer;
    ImageView uploadImage;
    String imageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_upload);

        editTxtSellerAddCustomerName = findViewById(R.id.editTxtSellerAddCustomerName);
        editTxtSellerAddCustomerMobileNumber = findViewById(R.id.editTxtSellerAddCustomerMobileNumber);
        editTxtSellerAddCustomerAddress = findViewById(R.id.editTxtSellerAddCustomerAddress);
        editTxtSellerAddCustomerMilk = findViewById(R.id.editTxtSellerAddCustomerMilk);
        editTxtSellerAddCustomerMilkPrice = findViewById(R.id.editTxtSellerAddCustomerMilkPrice);
        uploadImage = findViewById(R.id.imageViewAddCustomerImage);
        buttonSellerAddCustomer = findViewById(R.id.buttonSellerAddCustomer);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(CustomerUploadActivity.this, "No Image Selected",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        buttonSellerAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Customer Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerUploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadData() {
        String name = editTxtSellerAddCustomerName.getText().toString();
        String phone = editTxtSellerAddCustomerMobileNumber.getText().toString();
        String address = editTxtSellerAddCustomerAddress.getText().toString();
        String milk = editTxtSellerAddCustomerMilk.getText().toString();
        String milkPrice = editTxtSellerAddCustomerMilkPrice.getText().toString();

        CustomerInfo customerInfo = new CustomerInfo(name, phone, address, milk, milkPrice, imageURL, "0");

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Customer Info").child(currentDate)
                .setValue(customerInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(CustomerUploadActivity.this, "Customer Added", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CustomerUploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}












