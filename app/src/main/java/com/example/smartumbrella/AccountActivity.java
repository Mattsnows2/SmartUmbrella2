package com.example.smartumbrella;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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

        changeLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });

    }
    private void showChangeLanguageDialog(){
        final String [] listItems = {"English","Danish", "French","Swedish","Norwegians"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AccountActivity.this);
        mBuilder.setTitle("Choose language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    changeLanguage("en");
                    recreate();
                }else  if(which==1){
                    changeLanguage("da");
                    recreate();
                }else  if(which==2){
                    changeLanguage("fr");
                    recreate();
                }else  if(which==3){
                    changeLanguage("sv");
                    recreate();
                }else  if(which==4){
                    changeLanguage("se");
                    recreate();
                }

                dialog.dismiss();
            }
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
}

