package com.example.android.oyan.Domain;

/**
 * Created by Eldar on 10/22/2015.
 */
public class CountryDomain extends BaseDomain {

    private String countryName;
    private long countryId;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }
}
