package com.example.normand.Models;

/**
 * @author <Nguyen Tien Minh Quan - s3931082>
 */

public class Commercial extends Property {
    private int parkingSpace;

    public Commercial(String propertyId, String propertyAddress, double propertySize, double propertyPrice, String propertyType, int parkingSpace, String ownerId) {
        super(propertyId, propertyAddress, propertySize, propertyPrice, propertyType, ownerId);
        this.parkingSpace = parkingSpace;
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
                ", parkingSpace=" + parkingSpace +
                '}';
    }
}
