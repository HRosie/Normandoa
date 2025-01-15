package com.example.normand.Models;

/**
 * @author <Nguyen Tien Minh Quan - s3931082>
 */

public class Commercial extends Property {
    private String type;
    private int parkingSpace;

    public Commercial(String propertyId, String propertyAddress, double propertySize, double propertyPrice, String propertyStatus, String type, int parkingSpace, String ownerId) {
        super(propertyId, propertyAddress, propertySize, propertyPrice, propertyStatus, ownerId);
        this.type = type;
        this.parkingSpace = parkingSpace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(int parkingSpace) {
        this.parkingSpace = parkingSpace;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", type='" + type + '\'' +
                ", parkingSpace=" + parkingSpace +
                '}';
    }
}
