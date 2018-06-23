package com.example.mapwithmarker.model;

public class Car {
    private int id;
    private int ownerId;
    private String ownerName;
    private String brand;
    private String model;
    private String registrationNumber;
    private int productionYear;
    private String description;
    private int gruopId;
    private String groupName;
    private boolean rideInProgress;
    private double latitude;
    private double longitude;
    private double speed;

    public double getSpeed() {
        return speed;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public boolean isRideInProgress() {
        return rideInProgress;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGruopId() {
        return gruopId;
    }

    public String getDescription() {
        return description;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public String getBrand() {
        return brand;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getId() {
        return id;
    }
}
