package com.example.mirutapp.Model;

import java.util.Arrays;

public class Location {
    private String[][] polyline;
    private String street;

    public Location(String[][] polyline, String street) {
        this.polyline = polyline;
        this.street = street;
    }

    public String[][] getPolyline() {
        return polyline;
    }

    public void setPolyline(String[][] polyline) {
        this.polyline = polyline;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Location{" +
                "polyline=" + Arrays.toString(polyline[0]) +
                ", street='" + street + '\'' +
                '}';
    }
}
