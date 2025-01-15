package com.example.normand.Models;

/**
 * @author <Nguyen Tien Minh Quan - s3931082>
 */

public class Property {
    private String propertyId;
    private String propertyAddress;
    private double propertySize;
    private double propertyPrice;
    private String propertyStatus;
    private String OwnerId;

    public Property(String propertyId, String propertyAddress, double propertySize, double propertyPrice, String propertyStatus, String OwnerId) {
        this.propertyId = propertyId;
        this.propertyAddress = propertyAddress;
        this.propertySize = propertySize;
        this.propertyPrice = propertyPrice;
        this.propertyStatus = propertyStatus;
        this.OwnerId = OwnerId;
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

    public String getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(String propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    @Override
    public String toString() {
        return  "propertyId=" + propertyId +
                ", propertyAddress='" + propertyAddress + '\'' +
                ", propertyPrice=" + propertyPrice +
                ", propertyStatus='" + propertyStatus + '\'' +
                ", OwnerId='" + OwnerId + '\'' +
                '}';
    }
}
