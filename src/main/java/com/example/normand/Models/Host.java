package com.example.normand.Models;

/**
 * @author sg-random-tut3-group2
 */

import java.util.Date;

public class Host extends Person {
//    private List<String> ownerId;

    public Host(String id, String fullName, String username, String password, Role role, Date dob, String contactInfo) {
        super(id, fullName, username, password, role, dob, contactInfo);
//        this.ownerId = new ArrayList<>();
//        this.rentalId = new ArrayList<>();
    }

//    public List<String> getOwnerId() {
//        return ownerId;
//    }
//
//    public void setOwnerId(List<String> ownerId) {
//        this.ownerId = ownerId;
//    }

//    public List<String> getRentalId() {
//        return rentalId;
//    }
//
//    public void setRentalId(List<String> rentalId) {
//        this.rentalId = rentalId;
//    }
//
//
//
//    public void addRental(String id) {rentalId.add(id);}
//    public void removeRental(String id) {rentalId.remove(id);}
//
//    public boolean ownsRental(String rentalCheck) {
//        return rentalId.contains(rentalCheck);
//    }

    @Override
    public String toString() {
        return super.toString();
    }
}
