package com.example.normand.Models;

/**
 * @author <Nguyen Tien Minh Quan - s3931082>
 */

public class Residential extends Property {
    private int roomAmount;
    private boolean garden;
    private boolean pet;

    public Residential(String propertyId, String propertyAddress, double propertySize, double propertyPrice, String propertyStatus, String propertyType, String ownerID, int roomAmount, boolean garden, boolean pet) {
        super(propertyId, propertyAddress, propertyPrice, propertySize, propertyStatus, propertyType, ownerID);
        this.roomAmount = roomAmount;
        this.garden = garden;
        this.pet = pet;
    }

    public int getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(int roomAmount) {
        this.roomAmount = roomAmount;
    }

    public boolean isGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    public boolean isPet() {
        return pet;
    }

    public void setPet(boolean pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", roomAmount=" + roomAmount +
                ", garden=" + garden +
                ", pet=" + pet +
                '}';
    }
}
