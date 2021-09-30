package com.codershil.covid19tracker.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codershil.covid19tracker.R;

public class CovidCountriesViewHolder extends RecyclerView.ViewHolder {
    private ImageView imgCountryDetails,imgFlag;
    private TextView txtCountryName;

    public CovidCountriesViewHolder(@NonNull View itemView) {
        super(itemView);
        imgCountryDetails = itemView.findViewById(R.id.imgCountryDetails);
        imgFlag = itemView.findViewById(R.id.imgFlag);
        txtCountryName = itemView.findViewById(R.id.txtCountryName);
    }

    public ImageView getImgCountryDetails() {
        return imgCountryDetails;
    }

    public ImageView getImgFlag(){
        return imgFlag;
    }
    public TextView getTxtCountryName() {
        return txtCountryName;
    }
}
