package com.example.smartumbrella;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class WeatherFragment extends Fragment {

    EditText etCity, etCountry;
    TextView tvResult;
    String city = "";
    //Button getBtn;

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid="70101acd2500c3938b8552a94e412071";
    FusedLocationProviderClient fusedLocationProviderClient;
    DecimalFormat df = new DecimalFormat("#.##");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            System.out.println(("yo"));
            getLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        etCity = view.findViewById(R.id.etCity);
        etCountry = view.findViewById(R.id.etCountry);
        tvResult = view.findViewById(R.id.tvResult);



        Button getBtn = (Button) view.findViewById(R.id.btnGet);
        getBtn.setOnClickListener(v -> {
            Log.d("fsh","qsdg");
            String tempUrl="";

            String country = etCountry.getText().toString().trim();

            if(city.equals("")){
                tvResult.setText("city field cannot be empty");
            }else{

                if(!country.equals("")){
                    tempUrl= url + "?q=" +city + "&appid=" +appid;

                }else{
                    tempUrl= url + "?q=" +city + "&appid=" +appid;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, response -> {
                    String output="";
                    try{
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray=jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp= jsonObjectMain.getDouble("temp")-273.15;
                        double fellsLike = jsonObjectMain.getDouble("feels_like")-273.15;
                        float pressure =jsonObjectMain.getInt("pressure");
                        int humidity = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String wind =jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String clouds =jsonObjectClouds.getString("all");
                        //      JSONObject jsonObjectSys = jsonResponse.getJSONObject("Sys");
                        //      String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");
                        tvResult.setTextColor(Color.rgb(60,114,199));
                        output += "Current weather of " +cityName + " )"
                                +"\n Temp: " + df.format(temp) + "°C"

                                +"\n Humidity:" +humidity + "%"
                                +"\n Description:"+description
                                +"\n Wind Speed:" +wind +"m/s "
                                +"\n Cloudiness"+clouds+"%"
                                +"\n Pressure" +pressure + "hPa";
                        tvResult.setText(output);
                        Log.d("output",output);

                        if(description.contains("rain")){
                            createNotificationChannel();

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext(), "lemubitA");
                            builder.setContentTitle("Warning");
                            builder.setContentText("It's raining outside");
                            builder.setSmallIcon(R.drawable.ic_launcher_background);
                            builder.setAutoCancel(true);

                            NotificationManagerCompat notificationManagerCompat  = NotificationManagerCompat.from(getActivity());
                            notificationManagerCompat.notify(100, builder.build());

                        }


                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show());
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }
        });
        return view;
    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           CharSequence name= "studentChannel";
           String description ="Channel for student notification";
           int importance = NotificationManager.IMPORTANCE_DEFAULT;
           NotificationChannel channel = new NotificationChannel("lemubitA", name, importance);
           channel.setDescription(description);

           NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
           notificationManager.createNotificationChannel(channel);
        }
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);

                        city=addresses.get(0).getLocality();

                        String tempUrl="";

                        String country = etCountry.getText().toString().trim();

                        if(city.equals("")){
                            tvResult.setText("city field cannot be empty");
                        }else{
                            System.out.println(city);
                            if(!country.equals("")){
                                tempUrl= url + "?q=" +city + "&appid=" +appid;

                            }else{
                                tempUrl= url + "?q=" +city + "&appid=" +appid;
                            }

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, response -> {
                                String output="";
                                try{
                                    JSONObject jsonResponse = new JSONObject(response);
                                    JSONArray jsonArray=jsonResponse.getJSONArray("weather");
                                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                                    String description = jsonObjectWeather.getString("description");
                                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                                    double temp= jsonObjectMain.getDouble("temp")-273.15;
                                    double fellsLike = jsonObjectMain.getDouble("feels_like")-273.15;
                                    float pressure =jsonObjectMain.getInt("pressure");
                                    int humidity = jsonObjectMain.getInt("humidity");
                                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                                    String wind =jsonObjectWind.getString("speed");
                                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                                    String clouds =jsonObjectClouds.getString("all");
                                    //      JSONObject jsonObjectSys = jsonResponse.getJSONObject("Sys");
                                    //      String countryName = jsonObjectSys.getString("country");
                                    String cityName = jsonResponse.getString("name");
                                    tvResult.setTextColor(Color.rgb(60,114,199));
                                    output += "Current weather of " +cityName + " )"
                                            +"\n Temp: " + df.format(temp) + "°C"

                                            +"\n Humidity:" +humidity + "%"
                                            +"\n Description:"+description
                                            +"\n Wind Speed:" +wind +"m/s "
                                            +"\n Cloudiness"+clouds+"%"
                                            +"\n Pressure" +pressure + "hPa";
                                    tvResult.setText(output);
                                    Log.d("output",output);

                                    if(description.contains("rain")){
                                        createNotificationChannel();

                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity().getApplicationContext(), "lemubitA");
                                        builder.setContentTitle("Warning");
                                        builder.setContentText("It's raining outside");
                                        builder.setSmallIcon(R.drawable.ic_launcher_background);
                                        builder.setAutoCancel(true);

                                        NotificationManagerCompat notificationManagerCompat  = NotificationManagerCompat.from(getActivity());
                                        notificationManagerCompat.notify(100, builder.build());

                                    }


                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }, error -> Toast.makeText(getActivity(), error.toString().trim(), Toast.LENGTH_SHORT).show());
                            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                            requestQueue.add(stringRequest);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}