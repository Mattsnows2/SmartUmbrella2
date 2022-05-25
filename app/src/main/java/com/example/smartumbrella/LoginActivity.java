package com.example.smartumbrella;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button buttonSingin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail=findViewById(R.id.editTextLoginEmail);
        editTextPassword=findViewById(R.id.editTextLoginPassword);
        buttonSingin=findViewById(R.id.buttonSignin);

        buttonSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();




                                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);



                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("yo", "createWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this,"Authentication failed", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });

    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }
}