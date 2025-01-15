package com.example.normand.Models;

import java.util.Date;

public class Manager extends Person {

    public Manager(String id, String fullName, String username, String password, Role role, Date dob, String contactInfo) {
        super(id, fullName, username, password, Role.Manager, dob, contactInfo);

    }

    @Override
    public String toString() {
        return "Manager{}";
    }


}
