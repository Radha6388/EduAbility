package com.example.eduability;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 101;

    ImageView imgProfile;
    EditText etName;
    TextView tvEmail;
    Button btnSave;

    FirebaseAuth auth;
    FirebaseFirestore db;
    StorageReference storageRef;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgProfile = findViewById(R.id.imgProfile);
        etName = findViewById(R.id.etName);
        tvEmail = findViewById(R.id.tvEmail);
        btnSave = findViewById(R.id.btnSave);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("profiles");

        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            tvEmail.setText(user.getEmail());
            loadProfile(user.getUid());
        }

        imgProfile.setOnClickListener(v -> chooseImage());
        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imgProfile.setImageURI(imageUri);

            Toast.makeText(this,
                    "📷 Image selected. Don’t forget to save!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProfile() {

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) return;

        String name = etName.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this,
                    "⚠️ Please enter your name",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this,
                "Saving your profile...",
                Toast.LENGTH_SHORT).show();

        if (imageUri != null) {

            StorageReference fileRef = storageRef.child(user.getUid() + ".jpg");

            fileRef.putFile(imageUri)
                    .addOnSuccessListener(task ->
                            fileRef.getDownloadUrl()
                                    .addOnSuccessListener(uri ->
                                            saveToFirestore(user.getUid(), name, uri.toString())
                                    )
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(this,
                                    "❌ Image upload failed",
                                    Toast.LENGTH_SHORT).show()
                    );

        } else {
            saveToFirestore(user.getUid(), name, null);
        }
    }

    private void saveToFirestore(String uid, String name, String imageUrl) {

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);

        if (imageUrl != null) {
            map.put("image", imageUrl);
        }

        db.collection("users")
                .document(uid)
                .set(map)
                .addOnSuccessListener(unused ->
                        Toast.makeText(this,
                                "✅ Profile saved successfully!",
                                Toast.LENGTH_LONG).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                "❌ Failed to save profile",
                                Toast.LENGTH_SHORT).show()
                );
    }

    // 🔥 FIXED: Load image + name
    private void loadProfile(String uid) {

        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {

                        // Load name
                        etName.setText(doc.getString("name"));

                        // 🔥 Load image from URL
                        String imageUrl = doc.getString("image");
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(ProfileActivity.this)
                                    .load(imageUrl)
                                    .placeholder(R.drawable.user)
                                    .into(imgProfile);
                        }
                    }
                });
    }
}
