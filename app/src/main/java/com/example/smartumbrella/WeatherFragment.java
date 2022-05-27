package com.example.smartumbrella;

import android.graphics.Color;
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
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class WeatherFragment extends Fragment {

    EditText etCity, etCountry;
    TextView tvResult;
    //Button getBtn;

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid="70101acd2500c3938b8552a94e412071";
    DecimalFormat df = new DecimalFormat("#.##");

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        etCity = view.findViewById(R.id.etCity);
        etCountry = view.findViewById(R.id.etCountry);
        tvResult = view.findViewById(R.id.tvResult);

        Button getBtn = (Button) view.findViewById(R.id.btnGet);
        getBtn.setOnClickListener(v -> {
            Log.d("fsh","qsdg");
            String tempUrl="";
            String city = etCity.getText().toString().trim();
            String country = etCountry.getText().toString().trim();

            if(city.equals("")){
                tvResult.setText("city field cannot be empty");
            }else{
                if(!country.equals("")){
                    tempUrl=url + "?q=" +city + "," +country + "&appid="+ appid;

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
}