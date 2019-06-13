package com.example.mirutapp.Model;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Incident {
    private int id;
    private String creation_time;
    private String updated_time;
    private String type;
    private String link;
    private String direction;

    private String description;

    private Location location;


    public Incident(int id, String creation_time, String updated_time, String type, String link, String direction, String description, Location location) {
        this.id = id;
        this.creation_time = creation_time;
        this.updated_time = updated_time;
        this.type = type;
        this.link = link;
        this.direction = direction;
        this.description = description;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double distance (LatLng other) {
        Double ycoord = Double.parseDouble ( this.location.getPolyline()[0][0]);
        Double xcoord = Double.parseDouble( this.location.getPolyline()[0][1]);

        Double y = Math.abs (ycoord - other.latitude);
        Double x = Math.abs (xcoord- other.longitude);

        Double distance = Math.sqrt((x)*(x) + (y)*(y));
        System.out.printf(ycoord+"restandoY"+other.latitude);
        System.out.printf(xcoord+"restandoX"+other.longitude);
        System.out.println(distance);
        return distance;
    }

    public float distanceInMeters (LatLng other){
        Double lat1 = Double.parseDouble ( this.location.getPolyline()[0][0]);
        Double lng1 = Double.parseDouble( this.location.getPolyline()[0][1]);
        Double lat2 = other.latitude;
        Double lng2 = other.longitude;

        double earthRadius = 6371000; //meters

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        System.out.println(dist);

        return dist;

    }


}



