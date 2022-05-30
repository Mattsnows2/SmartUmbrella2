package com.example.smartumbrella;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDatabase= FirebaseDatabase.getInstance("https://smartumbralla-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        editTextEmail = findViewById(R.id.emailEditText);
        editTextPassword=findViewById(R.id.passwordEditText);
        buttonRegister = findViewById(R.id.buttonRegister);

        mAuth = FirebaseAuth.getInstance();


        buttonRegister.setOnClickListener(v -> onCreateAccount(editTextEmail.getText().toString().replaceAll("\\s", ""), editTextPassword.getText().toString()));

    }

    private void onCreateAccount(String email, String password){

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){

                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);

                        Intent intentStep1 = new Intent(RegisterActivity.this, SplashScreen.class);
                        startActivity(intentStep1);

                    }else{
                        Log.w("yo", "createWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this,"Authentication failed", Toast.LENGTH_LONG).show();
                        updateUI(null);
                    }
                });
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
}