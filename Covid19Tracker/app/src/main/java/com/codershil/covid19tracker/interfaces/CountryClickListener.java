package com.codershil.covid19tracker.interfaces;

import com.codershil.covid19tracker.model.CountryModel;

public interface CountryClickListener {
    void onStateClicked(CountryModel countryModel);
}
