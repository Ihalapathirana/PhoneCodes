package com.example.anushai.phonecode.model;

/**
 * Created by anushai on 12/22/17.
 */

public class Country {

    private int id;
    private String countryName;
    private String countryCode;
    private String countryIdd;

    public Country(int id, String countryName, String countryCode, String countryIdd) {
        this.id = id;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.countryIdd = countryIdd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryIdd() {
        return countryIdd;
    }

    public void setCountryIdd(String countryIdd) {
        this.countryIdd = countryIdd;
    }
}
