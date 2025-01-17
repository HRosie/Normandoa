package com.example.normand.Models;

/**
 * @author sg-random-tut3-group2
 */

import java.util.Date;
import java.util.List;

public class Tenant extends Person {
    private List<String> paymentId;

    public Tenant(String id, String fullName, String username, String password, Role role, Date dob, String contactInfo) {
        super(id, fullName, username, password, role, dob, contactInfo);
    }



    public List<String> getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(List<String> paymentId) {
        this.paymentId = paymentId;
    }



    @Override
    public String toString() {
        return super.toString() +
                ", paymentId=" + paymentId +
                '}';
    }
}
