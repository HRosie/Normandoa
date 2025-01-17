package com.example.normand.Controllers.SceneController.Host;

import com.example.normand.Models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HostController {
    private final Host host;
    private Connection connection;

    public HostController(Host host, Connection connection) {
        this.host = host;
        this.connection = connection;
    }

    public String getInfo() {
        return "ID: " + host.getId() + "\n" +
                "Full Name:" + host.getFullName() + "\n" +
                "Username:" + host.getUsername() + "\n" +
                "Password:" + host.getPassword() + "\n" +
                "Dob: " + host.getDob() + "\n" +
                "Contact Info: " + host.getContactInfo();

    }

    public List<Person> getUser() {
        List<Person> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("role"));
                Person user = new Person(
                    resultSet.getString("userId"),
                        resultSet.getString("fullname"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        role,
                        resultSet.getDate("dob"),
                        resultSet.getString("contact")
                );
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Residential> getResidential() {
        List<Residential> residentials = new ArrayList<>();
        List<Commercial> commercials = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM property";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String type = resultSet.getString("type");
                if (type.equals("residential")) {
                    Residential property = new Residential(
                            resultSet.getString("propertyId"),
                            resultSet.getString("address"),
                            resultSet.getDouble("price"),
                            resultSet.getDouble("area"),
                            resultSet.getString("type"),
                            resultSet.getString("ownerId"),
                            resultSet.getInt("room"),
                            resultSet.getBoolean("garden"),
                            resultSet.getBoolean("pet")
                    );
                    residentials.add(property);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return residentials;
    }

    public List<Commercial> getCommercial() {
        List<Commercial> commercials = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM property";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String type = resultSet.getString("type");
                if (type.equals("residential")) {
                    Commercial property = new Commercial(
                            resultSet.getString("propertyId"),
                            resultSet.getString("address"),
                            resultSet.getDouble("price"),
                            resultSet.getDouble("area"),
                            resultSet.getString("type"),
                            resultSet.getInt("parking"),
                            resultSet.getString("ownerId")
                    );
                    commercials.add(property);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commercials;
    }

    public List<Property> getProperty() {
        List<Property> properties = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM property";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String type = resultSet.getString("type");
                    Property property = new Property(
                            resultSet.getString("propertyId"),
                            resultSet.getString("address"),
                            resultSet.getDouble("area"),
                            resultSet.getDouble("price"),
                            resultSet.getString("ownerId"),
                            type
                    );
                    properties.add(property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public List<RentalAgreement> getRentalAgreement() {
        List<RentalAgreement> rentals = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM agreement";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                RentalAgreement rental = new RentalAgreement(
                        resultSet.getString("rentalId"),
                        resultSet.getString("propertyId"),
                        resultSet.getString("ownerId"),
                        resultSet.getString("hostId"),
                        resultSet.getString("tenantId"),
                        resultSet.getDate("startDate"),
                        resultSet.getDate("endDate"),
                        resultSet.getDouble("fee"),
                        resultSet.getString("status")
                );
                rentals.add(rental);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rentals;
    }
}
