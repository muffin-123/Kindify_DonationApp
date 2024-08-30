package com.example.tawfiqthefooddonationapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText, phoneEditText;
    private AppCompatButton regBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        regBtn = findViewById(R.id.regBtn);

        passwordEditText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    togglePasswordVisibility();
                    return true;
                }
            }
            return false;
        });

        regBtn.setOnClickListener(v -> registerUser());

        findViewById(R.id.signIntxt).setOnClickListener(v -> startActivity(new Intent(SignUp.this, SignIn.class)));
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setInputType(129); // 129 is InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.custom_password_icon, 0, R.drawable.eye_closed, 0);
        } else {
            passwordEditText.setInputType(144); // 144 is InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            passwordEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.custom_password_icon, 0, R.drawable.eye_open, 0);
        }
        passwordEditText.setSelection(passwordEditText.getText().length());
        isPasswordVisible = !isPasswordVisible;
    }

    private void registerUser() {
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (fullName.isEmpty()) {
            fullNameEditText.setError("Full name is required");
            fullNameEditText.requestFocus();
            return;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Valid email is required");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordEditText.setError("Password should be at least 6 characters long");
            passwordEditText.requestFocus();
            return;
        }

        if (phone.isEmpty() || phone.length() < 10) {
            phoneEditText.setError("Valid phone number is required");
            phoneEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                String userId = user.getUid();

                Map<String, Object> userData = new HashMap<>();
                userData.put("fullName", fullName);
                userData.put("email", email);
                userData.put("phone", phone);

                db.collection("users").document(userId).set(userData, SetOptions.merge())
                        .addOnSuccessListener(aVoid -> Toast.makeText(SignUp.this, "User registered successfully", Toast.LENGTH_LONG).show())
                        .addOnFailureListener(e -> Toast.makeText(SignUp.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            } else {
                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    emailEditText.setError("This email is already registered");
                    emailEditText.requestFocus();
                } else {
                    Toast.makeText(SignUp.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}