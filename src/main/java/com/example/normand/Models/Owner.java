package com.example.normand.Models;

/**
 * @author sg-random-tut3-group2
 */

import java.util.Date;

public class Owner extends Person {
    //private List<String> propertyId;

    //private List<String> hostId;


    public Owner(String id, String fullName, String username, String password, Role role, Date dob, String contactInfo) {
        super(id, fullName, username, password, role, dob, contactInfo);
        //this.propertyId = new ArrayList<>();
        //this.rentalId = new ArrayList<>();
        //this.hostId = new ArrayList<>();

    }

//    public List<String> getPropertyId() {
//        return propertyId;
//    }
//
//    public void setPropertyId(List<String> propertyId) {
//        this.propertyId = propertyId;
//    }
//
//    public List<String> getRentalId() {
//        return rentalId;
//    }
//
//    public void setRentalId(List<String> rentalId) {
//        this.rentalId = rentalId;
//    }
//
//    public boolean ownsProperty(String propertyIdCheck) {
//        return propertyId.contains(propertyIdCheck);
//    }




//    public List<String> getHostId() {
//        return hostId;
//    }
//
//    public void setHostId(List<String> hostId) {
//        this.hostId = hostId;
//    }


}
