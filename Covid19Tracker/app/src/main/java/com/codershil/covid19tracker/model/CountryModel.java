package com.codershil.covid19tracker.model;


public class CountryModel {
    private String countryName;
    private String countryFlag;
    private int countryPosition;

    public CountryModel(String countryName, String countryFlag, int countryPosition) {
        this.countryName = countryName;
        this.countryFlag = countryFlag;
        this.countryPosition = countryPosition;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public int getCountryPosition() {
        return countryPosition;
    }
}
