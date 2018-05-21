package com.example.julia.delivery.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lavrov on 18.05.2018.
 */

public class Address {
    @SerializedName("id")
    @Expose
    private int id ;

    @SerializedName("country")
    @Expose
    private String country ;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("homeNumber")
    @Expose
    private int homeNumber ;

    @SerializedName("latitude")
    @Expose
    private float latitude;

    @SerializedName("longitude")
    @Expose
    private float longitude;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(int homeNumber) {
        this.homeNumber = homeNumber;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
