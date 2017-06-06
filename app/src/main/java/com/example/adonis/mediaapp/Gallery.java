package com.example.adonis.mediaapp;


public class Gallery {
    String name;
    String absolute;
    String extension;
    private double latitude;
    private double longitude;

    public Gallery() {
    }

    public Gallery(String name, String absolute, String extension) {
        this.name = name;
        this.absolute = absolute;
        this.extension = extension;
    }

    public Gallery(String name, String absolute, double latitude, double longitude, String extension) {
        this.name = name;
        this.absolute = absolute;
        this.latitude = latitude;
        this.longitude = longitude;
        this.extension = extension;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsolute() {
        return absolute;
    }

    public void setAbsolute(String absolute) {
        this.absolute = absolute;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
