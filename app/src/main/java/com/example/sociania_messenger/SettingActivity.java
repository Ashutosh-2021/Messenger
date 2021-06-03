package com.example.sociania_messenger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sociania_messenger.Models.User;
import com.example.sociania_messenger.databinding.ActivitySettingBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {

    // Create Binding
    ActivitySettingBinding binding;
    // Upload he data in firebase Storage
    FirebaseStorage storage;
    // Use Firebase Auth
    FirebaseAuth auth;
    // Use Firebase Database
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hidden the navigation bar
        getSupportActionBar().hide();

        // Instance all the Variable
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Click on backArrow move to MainActivity
                startActivity(new Intent(SettingActivity.this, MainActivity.class));
                finish();
            }
        });


        // Set the data in setting imageView
        database.getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Get the Image
                        User user = snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile_pic())
                                .placeholder(R.drawable.ic_user)
                                .into(binding.profileImage);

                        binding.etusername.setText(user.getUserName());
                        binding.etstatus.setText(user.getStatus());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        // Save Data Button
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = binding.etusername.getText().toString();
                String status = binding.etstatus.getText().toString();

                // Update data using HasMap
                HashMap<String, Object> map = new HashMap<>();
                map.put("userName", userName);
                map.put("status", status);

                // Update data in Database
                database.getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(map);

                Toast.makeText(SettingActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            }
        });

        // Click on Plus Button Move to Gallery
        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
//                finish();
            }
        });


    }

    // Check the data are selected or not the data are selected so set the image in image view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {

            // Set the image in profileImage imageView
            Uri uri = data.getData();
            binding.profileImage.setImageURI(uri);

            // Upload the image in Firebase Storage
            final StorageReference reference = storage.getReference().child("profile_pic")
                    .child(FirebaseAuth.getInstance().getUid());


            // Upload data in database
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("User")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .child("profile_pic").setValue(uri.toString());
                            Toast.makeText(SettingActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}