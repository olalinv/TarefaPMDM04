package com.robottitto.tarefapmdm04.api.address.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Address implements Serializable {

    @NonNull
    @ColumnInfo(name = "street")
    private String street;

    @NonNull
    @ColumnInfo(name = "city")
    private String city;

    @NonNull
    @ColumnInfo(name = "zip_code")
    private String zipCode;

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Rúa", getStreet());
            obj.put("Cidade", getCity());
            obj.put("Código postal", getZipCode());
            return obj.toString(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}
