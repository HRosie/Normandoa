package com.example.normand.Controllers.SceneController.Owner;

/**
 * @author sg-random-tut3-group2
 */

import com.example.normand.Database.DatabaseConnection;
import com.example.normand.Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerController {
    private final Owner owner;
    private Connection connection;

    public OwnerController(Owner owner, Connection connection) {
        this.owner = owner;
        this.connection = connection;
    }

    public String getInfo() {
        return "ID: " + owner.getId() + "\n" +
                "Full Name: " + owner.getFullName() + "\n" +
                "Username: " + owner.getUsername() + "\n" +
                "Password: " + owner.getPassword() + "\n" +
                "Dob: " + owner.getDob() + "\n" +
                "Contact Info: " + owner.getContactInfo();

    }


    public List<Person> getUser() {
        List<Person> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT u.*\n" +
                    "FROM Agreement a\n" +
                    "JOIN Users u ON a.hostId = u.userId\n" +
                    "WHERE a.ownerId = '" + owner.getId() + "'";
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
            String query = "SELECT * \n" +
                    "FROM property \n" +
                    "WHERE ownerId = '" + owner.getId() +
                    "'AND Type = 'Residential'";
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
            String query = "SELECT * \n" +
                    "FROM property \n" +
                    "WHERE ownerId = '" + owner.getId() +
                    "'AND Type <> 'Residential'";
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
            String query = "SELECT * FROM property WHERE ownerId = '" + owner.getId() + "'";
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
            String query = "SELECT * FROM agreement WHERE ownerId = '" + owner.getId() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                RentalAgreement rental = new RentalAgreement(
                        resultSet.getString("rentalid"),
                        resultSet.getString("propertyid"),
                        resultSet.getString("ownerid"),
                        resultSet.getString("hostid"),
                        resultSet.getString("tenantid"),
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
