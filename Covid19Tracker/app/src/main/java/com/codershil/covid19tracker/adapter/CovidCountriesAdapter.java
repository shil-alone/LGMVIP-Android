package com.codershil.covid19tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codershil.covid19tracker.R;
import com.codershil.covid19tracker.interfaces.CountryClickListener;
import com.codershil.covid19tracker.model.CountryModel;
import com.codershil.covid19tracker.viewHolders.CovidCountriesViewHolder;

import java.util.ArrayList;


public class CovidCountriesAdapter extends RecyclerView.Adapter<CovidCountriesViewHolder> {
    private ArrayList<CountryModel> countriesList;
    private CountryClickListener countryClickListener;
    private Context context;

    public CovidCountriesAdapter(ArrayList<CountryModel> countriesList, Context context, CountryClickListener countryClickListener) {
        this.countriesList = countriesList;
        this.context = context;
        this.countryClickListener = countryClickListener;
    }

    @NonNull
    @Override
    public CovidCountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CovidCountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidCountriesViewHolder holder, int position) {
        CountryModel countryModel = countriesList.get(holder.getAdapterPosition());
        holder.getTxtCountryName().setText(countryModel.getCountryName());

        Glide.with(context).load(countryModel.getCountryFlag()).into(holder.getImgFlag());

        holder.getImgCountryDetails().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryClickListener.onStateClicked(countryModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    public void searchFilter(ArrayList<CountryModel> filterList){
        countriesList = filterList;
        notifyDataSetChanged();
    }
}
