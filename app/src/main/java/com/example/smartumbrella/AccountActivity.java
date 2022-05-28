package com.example.smartumbrella;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AccountActivity extends AppCompatActivity {


  Button changeLanguageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("sfg");
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        changeLanguageButton = findViewById(R.id.buttonChangeLanguage);

        changeLanguageButton.setOnClickListener(v -> showChangeLanguageDialog());

    }
    private void showChangeLanguageDialog(){
        final String [] listItems = {"English","Danish", "French","Swedish","Norwegians"};
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

