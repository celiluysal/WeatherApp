
package com.example.weatherapp.PlaceApi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hit {

    @SerializedName("country")
    @Expose
    private Country country;
    @SerializedName("county")
    @Expose
    private County county;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("administrative")
    @Expose
    private List<String> administrative = null;
    @SerializedName("locale_names")
    @Expose
    private LocaleNames localeNames;
    @SerializedName("_geoloc")
    @Expose
    private Geoloc geoloc;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<String> getAdministrative() {
        return administrative;
    }

    public void setAdministrative(List<String> administrative) {
        this.administrative = administrative;
    }

    public LocaleNames getLocaleNames() {
        return localeNames;
    }

    public void setLocaleNames(LocaleNames localeNames) {
        this.localeNames = localeNames;
    }

    public Geoloc getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(Geoloc geoloc) {
        this.geoloc = geoloc;
    }

}
