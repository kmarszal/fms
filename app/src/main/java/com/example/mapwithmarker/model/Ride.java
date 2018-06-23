package com.example.mapwithmarker.model;

public class Ride {
    private int id;
    private int carId;
    private String carRegistrationNumber;
    private String carBrand;
    private String carModel;
    private int userId;
    private String userName;
    private String dateFrom;
    private String dateTo;
    private Address startAddress;
    private Address endAddress;
    private double totalDistance;

    public int getId() {
        return id;
    }

    public int getCarId() {
        return carId;
    }

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public Address getStartAddress() {
        return startAddress;
    }

    public Address getEndAddress() {
        return endAddress;
    }

    public double getTotalDistance() {
        return totalDistance;
    }
}
