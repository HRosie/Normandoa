package com.example.normand.Controllers.SceneController.Manager;

import com.example.normand.Models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManagerController {
    private final Manager manager;
    private Connection connection;

    public ManagerController(Manager manager, Connection connection) {
        this.manager = manager;
        this.connection = connection;
    }

    public String getInfo() {
        return "ID: " + manager.getId() + "\n" +
                "Full Name:" + manager.getFullName() + "\n" +
                "Username:" + manager.getUsername() + "\n" +
                "Password:" + manager.getPassword() + "\n" +
                "Dob: " + manager.getDob() + "\n" +
                "Contact Info: " + manager.getContactInfo();

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
            String query = "SELECT * FROM property WHERE type = 'residential'";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                String type = resultSet.getString("type");
                if (type.equals("residential")) {
                    Residential property = new Residential(
                            resultSet.getString("propertyId"),
                            resultSet.getString("address"),
                            resultSet.getDouble("price"),
                            resultSet.getDouble("area"),
                            resultSet.getString("status"),
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
}
