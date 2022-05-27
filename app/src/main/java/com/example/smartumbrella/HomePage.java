package com.example.smartumbrella;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class HomePage extends AppCompatActivity {

    Button buttonLogin;
    Button buttonRegister;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_home_page);

        buttonLogin=findViewById(R.id.buttonLogin);
        buttonRegister=findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, LoginActivity.class);
            startActivity(intent);
        });

        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, RegisterActivity.class);
            startActivity(intent);
        });

    }
}