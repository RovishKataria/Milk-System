package com.example.milkdistributionmanagement;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CustomerUpdateActivity extends AppCompatActivity {

    ImageView updateCustomerImage;
    EditText updateCustomerName, updateCustomerMobile, updateCustomerAddress, updateCustomerMilk, updateCustomerMilkPrice;
    Button buttonUpdateCustomer;
    String name, mobile, address, milk, milkPrice, imageUrl, key, oldImageUrl;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update);

        updateCustomerImage = findViewById(R.id.updateCustomerImage);
        updateCustomerName = findViewById(R.id.updateCustomerName);
        updateCustomerMobile = findViewById(R.id.updateCustomerMobile);
        updateCustomerAddress = findViewById(R.id.updateCustomerAddress);
        updateCustomerMilk = findViewById(R.id.updateCustomerMilk);
        updateCustomerMilkPrice = findViewById(R.id.updateCustomerMilkPrice);
        buttonUpdateCustomer = findViewById(R.id.buttonUpdateCustomer);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            updateCustomerImage.setImageURI(uri);
                        } else {
                            Toast.makeText(CustomerUpdateActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Glide.with(CustomerUpdateActivity.this).load(bundle.getString("Image")).into(updateCustomerImage);
            updateCustomerName.setText(bundle.getString("Name"));
            updateCustomerMobile.setText(bundle.getString("Mobile"));
            updateCustomerAddress.setText(bundle.getString("Address"));
            updateCustomerMilk.setText(bundle.getString("Milk"));
            updateCustomerMilkPrice.setText(bundle.getString("Milk Price"));
            key = bundle.getString("key");
            oldImageUrl = bundle.getString("Image");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer Info").child(key);

        updateCustomerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        buttonUpdateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(CustomerUpdateActivity.this, CustomerDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void saveData() {
        storageReference = FirebaseStorage.getInstance().getReference().child("Customer Images").child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerUpdateActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updataData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void updataData() {
        name = updateCustomerName.getText().toString().trim();
        mobile = updateCustomerMobile.getText().toString().trim();
        address = updateCustomerAddress.getText().toString().trim();
        milk = updateCustomerMilk.getText().toString().trim();
        milkPrice = updateCustomerMilkPrice.getText().toString().trim();

        CustomerInfo customerInfo = new CustomerInfo(name, mobile, address, milk, milkPrice, imageUrl, "0");

        databaseReference.setValue(customerInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                    reference.delete();
                    Toast.makeText(CustomerUpdateActivity.this, "Customer Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CustomerUpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}