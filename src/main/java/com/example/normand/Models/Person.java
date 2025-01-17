package com.example.normand.Models;

/**
 * @author sg-random-tut3-group2
 */

import java.util.Date;

public class Person {
    private String id;
    private String fullName;
    private Date dob;
    private String contactInfo;
    //public List<String> rentalId;
    public String username;
    public String password;
    public Role role;

    public Person(String id, String fullName, String username, String password, Role role, Date dob, String contactInfo) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.role = role;
        this.contactInfo = contactInfo;
        //this.rentalId = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

//    public boolean ownsRental(String rentalCheck) {
//        return rentalId.contains(rentalCheck);
//    }
//
//    public List<String> getRentalId() {
//        return rentalId;
//    }
//
//    public void setRentalId(List<String> rentalId) {
//        this.rentalId = rentalId;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//    public void addRental(String id) {rentalId.add(id);}
//    public void removeRental(String id) {rentalId.remove(id);}

    @Override
    public String toString() {
        return  "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dob=" + dob +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }
}
