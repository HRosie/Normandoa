package com.example.normand.Models;

/**
 * @author <Nguyen Tien Minh Quan - s3931082>
 */

import java.util.Date;
import java.util.List;

public class Tenant extends Person {
    private List<String> paymentId;

    public Tenant(String id, String fullName, String username, String password, Role role, Date dob, String contactInfo) {
        super(id, fullName, username, password, role, dob, contactInfo);
    }

//    public List<String> getRentalId() {
//        return rentalId;
//    }
//
//    public void setRentalId(List<String> rentalId) {
//        this.rentalId = rentalId;
//    }

    public List<String> getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(List<String> paymentId) {
        this.paymentId = paymentId;
    }

//    public void addRental(String id) {rentalId.add(id);};
//    public void removeRental(String id) {rentalId.remove(id);};
//
//    public boolean ownsRental(String rentalCheck) {
//        return rentalId.contains(rentalCheck);
//    }

    @Override
    public String toString() {
        return super.toString() +
                ", paymentId=" + paymentId +
                '}';
    }
}
