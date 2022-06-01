package com.example.smartumbrella;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class AccountActivity extends AppCompatActivity {

    androidx.appcompat.app.AlertDialog.Builder builder;

  Button changeLanguageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("sfg");
        loadLocale();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            "".isEmpty();
        } else {
            Log.i("y2", "pas connect");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        changeLanguageButton = findViewById(R.id.buttonChangeLanguage);

        changeLanguageButton.setOnClickListener(v -> showChangeLanguageDialog());

        EditText email = (EditText) findViewById(R.id.editEmailText);
        EditText password = (EditText) findViewById(R.id.editTextPassword);
        builder = new androidx.appcompat.app.AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));

        Button submit = (Button) findViewById(R.id.submit);
        Button delete = (Button) findViewById(R.id.delete_account);

        email.setText(user.getEmail());

        submit.setOnClickListener(v -> {
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter the Data", Toast.LENGTH_SHORT).show();
            } else {
                final String[] message = {"Data changed"};
                if (!email.getText().toString().isEmpty()) {
                    if (email.getText().toString().equals(user.getEmail())) {
                        message[0] += " Current Email is equal to new Email" + "\n";
                    } else {
                        user.updateEmail(email.getText().toString()).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                message[0] += " New Email is " + email.getText().toString() + "\n";
                            }
                        });
                    }
                }
                if (!password.getText().toString().isEmpty()) {
                    user.updatePassword(password.getText().toString()).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            message[0] += " New password is " + password.getText().toString() + "\n";
                            password.getText().clear();
                        }
                    });
                }
                Toast.makeText(getApplicationContext(), message[0], Toast.LENGTH_LONG).show();
            }
        });

        delete.setOnClickListener(v -> {
            builder.setMessage(R.string.r_u_sure).setTitle(R.string.confirm_delete)
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> user.delete().addOnCompleteListener(task -> {
                        Intent intentLogin = new Intent(AccountActivity.this, HomePage.class);
                        startActivity(intentLogin);
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Account successfully deleted",
                                Toast.LENGTH_SHORT).show();
                    }))
                    .setNegativeButton("No", (dialog, id) -> {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Action canceled",
                                Toast.LENGTH_SHORT).show();
                    });
            //Creating dialog box
            androidx.appcompat.app.AlertDialog alert = builder.create();
            alert.show();
        });


    }
    private void showChangeLanguageDialog(){
        final String [] listItems = {"English","Danish", "French","Swedish","Norwegians","Finnish", "German"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AccountActivity.this);
        mBuilder.setTitle("Choose language");
        mBuilder.setSingleChoiceItems(listItems, -1, (dialog, which) -> {
            if(which==0){
                changeLanguage("en");
                restartActivity();
            }else  if(which==1){
                changeLanguage("da");
                restartActivity();
            }else  if(which==2){
                changeLanguage("fr");
                restartActivity();
            }else  if(which==3){
                changeLanguage("sv");
                restartActivity();
            }else  if(which==4){
                changeLanguage("se");
                restartActivity();
            } else  if(which==5){
                changeLanguage("fi");
                restartActivity();
            }else  if(which==6){
                changeLanguage("de");
                restartActivity();
            }
            dialog.dismiss();
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    private void changeLanguage(String language){
        Locale locale =new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor=getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang",language);
        editor.apply();


    }

    public void loadLocale(){
        SharedPreferences prefs =  getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language =prefs.getString("My_Lang","");
        changeLanguage(language);
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this,UserChoice.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

