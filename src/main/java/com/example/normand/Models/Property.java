package com.example.normand.Models;

/**
 * @author sg-random-tut3-group2
 */

public class Property {
    private String propertyId;
    private String propertyAddress;
    private double propertySize;
    private double propertyPrice;
    //private String propertyStatus;
    private String OwnerId;
    private String propertyType;

    public Property(String propertyId, String propertyAddress, double propertySize, double propertyPrice, String OwnerId, String propertyType) {
        this.propertyId = propertyId;
        this.propertyAddress = propertyAddress;
        this.propertySize = propertySize;
        this.propertyPrice = propertyPrice;
        //this.propertyStatus = propertyStatus;
        this.OwnerId = OwnerId;
        this.propertyType = propertyType;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public double getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(double propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public double getPropertySize() {
        return propertySize;
    }

    public void setPropertySize(double propertySize) {
        this.propertySize = propertySize;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    @Override
    public String toString() {
        return  "id= " + propertyId +
                ", address= " + propertyAddress + '\'' +
                ", price= " + propertyPrice +
                ", ownerId= " + OwnerId + '\'' +
                '}';
    }
}
