package com.example.normand.Controllers.UsersController;

import com.example.normand.Models.Owner;

import java.sql.Connection;

public class OwnerController {
    private final Owner owner;
    private Connection connection;

    public OwnerController(Owner owner, Connection connection) {
        this.owner = owner;
        this.connection = connection;
    }

    public String getInfo() {
        return  "id=" + owner.getId() +
                ", fullName='" + owner.getFullName() + '\'' +
                ", dob=" + owner.getDob() +
                ", contactInfo='" + owner.getContactInfo() + '\'' +
                '}';
    }


}
