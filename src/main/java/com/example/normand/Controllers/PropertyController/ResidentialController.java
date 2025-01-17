package com.example.normand.Controllers.PropertyController;

import com.example.normand.Models.Owner;
import com.example.normand.Models.Residential;

import java.sql.Connection;

public class ResidentialController {
    private final Residential residential;
    private Connection connection;

    public ResidentialController(Residential residential, Connection connection) {
        this.residential = residential;
        this.connection = connection;
    }

    public String getInfo() {
        return  "id= " + residential.getPropertyId() +
                ", address= " + residential.getPropertyAddress() + '\'' +
                ", price= " + residential.getPropertyPrice() +
                ", area= " + residential.getPropertySize() + '\'' +
                ", rooms= " + residential.getRoomAmount() + '\'' +
                ", pet= " + residential.isPet() + '\'' +
                ", garden= " + residential.isGarden() + '\'' +
                '}';
    }
}
