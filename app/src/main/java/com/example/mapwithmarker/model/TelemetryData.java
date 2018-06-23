package com.example.mapwithmarker.model;

public class TelemetryData {
    private int id;
    private int carId;
    private int rideId;
    private String date;
    private double longitude;
    private double latitude;
    private int angle;
    private int speedGPS;
    private int carSpeed;
    private boolean isIgnited;
    private int greenDrivingType;
    private double greenDrivingValue;
    private double shortTermFuelTrim;

    public double getShortTermFuelTrim() {
        return shortTermFuelTrim;
    }

    public double getGreenDrivingValue() {
        return greenDrivingValue;
    }

    public int getGreenDrivingType() {
        return greenDrivingType;
    }

    public boolean isIgnited() {
        return isIgnited;
    }

    public int getCarSpeed() {
        return carSpeed;
    }

    public int getSpeedGPS() {
        return speedGPS;
    }

    public int getAngle() {
        return angle;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDate() {
        return date;
    }

    public int getRideId() {
        return rideId;
    }

    public int getCarId() {
        return carId;
    }

    public int getId() {
        return id;
    }
}
