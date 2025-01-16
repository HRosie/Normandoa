package com.example.normand.Database;

//import ui.gp.com.example.normand.Models.Role;

import com.example.normand.Models.*;

import java.sql.*;

//import ui.gp.com.example.normand.Models.Users.*;
//import ui.gp.com.example.normand.Controllers.SceneController.Function.LoadingSceneController;

public class DatabaseConnection
{
    private static final String URL = "jdbc:postgresql://aws-0-ap-southeast-1.pooler.supabase.com:6543/postgres?user=postgres.xiejixespexqnarbhpli&password=N0rmand219001021a";
    private static final String username = "postgres.xiejixespexqnarbhpli";
    private static final String password = "N0rmand219001021a";
    private static Connection connection;
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        connection();
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }


    public static Connection connection()
    {
        try
        {
            connection = DriverManager.getConnection(URL,username,password);
            System.out.println("Connection to database successful");
        } catch (SQLException e)
        {
            System.out.println("Connection to database failed");
            e.printStackTrace();
        }
        return connection;
    }

    public void disconnect()
    {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  Connection getConnection()
    {
        return connection;

    }

    public Role getUserRole(String username)
    {
        Connection connection = getConnection();
        Role role = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT role FROM Users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = Role.valueOf(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    public Person getUser(String username, String password) {
        Person user = null;
        try {
            PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString("userid");
                String fullname = resultSet.getString("fullname");
                String contactInfo = resultSet.getString("contact");
                Date dob = resultSet.getDate("dob");
                Role role = Role.valueOf(resultSet.getString("role"));

                switch (role)
                {
                    case Manager:
                        user = new Manager(id, fullname, username, password, role, dob, contactInfo);
                        break;
                    case Owner:
                        user = new Owner(id, fullname, username, password, role, dob, contactInfo);
                        break;
                    case Host:
                        user = new Host(id, fullname, username, password, role, dob, contactInfo);
                        break;
                    case Tenant:
                        user = new Tenant(id, fullname, username, password, role, dob, contactInfo);
                        break;
//                    case Visitor:
//                        user = new PolicyHolder(id, username, password, role, fullname, email, phoneNumber, address);
//                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ResultSet getManagerData(String username, String password)
    {
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "' AND role = 'Manager'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getOwnerData(String username, String password)
    {
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "' AND role = 'Owner'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getTenantData(String username, String password)
    {
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "' AND role = 'Tenant'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getHostData(String username, String password)
    {
        Statement statement;
        ResultSet resultSet = null;
        try
        {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "' AND role = 'Host'");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }




}