package com.codershil.covid19tracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.codershil.covid19tracker.R;
import com.codershil.covid19tracker.constants.Constants;
import com.codershil.covid19tracker.controller.VolleySingleTon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CountryDetailsActivity extends AppCompatActivity {
    private int countryPosition;
    private ImageView imgCountryFlag;
    private TextView txtName, txtCountryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
        txtName = findViewById(R.id.txtName);
        imgCountryFlag = findViewById(R.id.imgCountryFlag);
        txtCountryData = findViewById(R.id.txtCountryData);

        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        countryPosition = getIntent().getIntExtra(Constants.POSITION, 0);
        fetchCountryData();
    }

    private void fetchCountryData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject countryJsonObject = jsonArray.getJSONObject(countryPosition);
                    JSONObject countryInfoObject = countryJsonObject.getJSONObject("countryInfo");

                    String countryName = countryJsonObject.getString("country");
                    String countryFlag = countryInfoObject.getString("flag");
                    String cases = countryJsonObject.getString("cases");
                    String todayCases = countryJsonObject.getString("todayCases");
                    String deaths = countryJsonObject.getString("deaths");
                    String todayDeaths = countryJsonObject.getString("todayDeaths");
                    String recovered = countryJsonObject.getString("recovered");
                    String todayRecovered = countryJsonObject.getString("todayRecovered");
                    String active = countryJsonObject.getString("active");
                    String critical = countryJsonObject.getString("critical");

                    Glide.with(CountryDetailsActivity.this).load(countryFlag).into(imgCountryFlag);
                    txtName.setText(countryName);

                    String countryData = "Total Cases :" + cases + "\n" + "Total Deaths :" + deaths + "\n" + "Total Recovered :" + recovered + "\n" + "Today Cases :" + todayCases + "\n" +
                            "Today Deaths :" + todayDeaths + "\n" +
                            "Total Recovered :" + todayRecovered + "\n" + "Active Cases :" + active + "\n" + "Critical Cases :" + critical;
                    txtCountryData.setText(countryData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(CountryDetailsActivity.this, "unable to get data. check you connection", Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleTon.getInstance(CountryDetailsActivity.this).addToRequestQue(stringRequest);
    }
}