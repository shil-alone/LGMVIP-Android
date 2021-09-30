package com.codershil.covid19tracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codershil.covid19tracker.R;
import com.codershil.covid19tracker.adapter.CovidCountriesAdapter;
import com.codershil.covid19tracker.constants.Constants;
import com.codershil.covid19tracker.controller.VolleySingleTon;
import com.codershil.covid19tracker.interfaces.CountryClickListener;
import com.codershil.covid19tracker.model.CountryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CountryClickListener {
    private RecyclerView countryRecyclerView;
    private ArrayList<CountryModel> countriesList = new ArrayList<>();
    private CovidCountriesAdapter adapter;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countryRecyclerView = findViewById(R.id.countryRecyclerView);
        searchBar = findViewById(R.id.searchBar);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchFilter(s.toString());
            }
        });

        fetchCountries();
    }

    private void searchFilter(String search){
        ArrayList<CountryModel> filterList = new ArrayList<>();
        for (CountryModel item : countriesList){
            if(item.getCountryName().toLowerCase().contains(search.toLowerCase())){
                filterList.add(item);
            }
        }
        adapter.searchFilter(filterList);

    }

    private void fetchCountries() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int countryIndex = 0; countryIndex < jsonArray.length(); countryIndex++) {
                        JSONObject countryJsonObject = jsonArray.getJSONObject(countryIndex);
                        JSONObject countryInfoObject = countryJsonObject.getJSONObject("countryInfo");
                        String countryName = countryJsonObject.getString("country");
                        String countryFlag = countryInfoObject.getString("flag");
                        CountryModel countryModel = new CountryModel(countryName,countryFlag, countryIndex);
                        countriesList.add(countryModel);
                    }
                    countryRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new CovidCountriesAdapter(countriesList,MainActivity.this, MainActivity.this);
                    countryRecyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "unable to get data. check you connection", Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleTon.getInstance(MainActivity.this).addToRequestQue(stringRequest);
    }


    @Override
    public void onStateClicked(CountryModel countryModel) {
        Intent intent = new Intent(MainActivity.this,CountryDetailsActivity.class);
        intent.putExtra(Constants.POSITION,countryModel.getCountryPosition());
        startActivity(intent);
    }

}