package com.example.rmu.week6.model;

/**
 * Created by rmu on 1/21/2015.
 */
public class Location {

    public boolean needs_recoding;
    public String longitude;
    public String latitude;

    public boolean isNeeds_recoding() {
        return needs_recoding;
    }

    public void setNeeds_recoding(boolean needs_recoding) {
        this.needs_recoding = needs_recoding;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getHuman_address() {
        return human_address;
    }

    public void setHuman_address(String human_address) {
        this.human_address = human_address;
    }

    public String human_address;
}
