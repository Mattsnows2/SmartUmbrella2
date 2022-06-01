package com.example.smartumbrella;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TrackerFragment extends Fragment {

    Button buttonTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
        buttonTracker=(Button) view.findViewById(R.id.buttonTracker);


        buttonTracker.setOnClickListener(v->{
            Uri uri = Uri.parse("https://www.google.dk/maps/place/VIA+University+College/@55.8635667,9.8357956,17z/data=!3m1!4b1!4m5!3m4!1s0x464c63be87e17d19:0xa65113d28ba174ba!8m2!3d55.8635637!4d9.8379843");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });



        return view;

    }



}
