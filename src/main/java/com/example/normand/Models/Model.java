package com.example.normand.Models;

import com.example.normand.Controllers.SceneController.ViewFactory;
import com.example.normand.Database.DatabaseConnection;

import java.sql.ResultSet;
import java.util.Date;


public class Model {
    private static Model model;
    private final ViewFactory view;
    private final DatabaseConnection databaseConnection;
    private Role loginRole;
    private Manager manager;
    private Owner owner;
    private Host host;
    private Tenant tenant;
    private Visitor visitor;
    private boolean loginSuccess;

    public Model() {
        this.databaseConnection = DatabaseConnection.getInstance();
        this.view = new ViewFactory(databaseConnection);
        this.manager = new Manager("", "", "","" , Role.Manager, null, "");
        this.owner = new Owner("", "", "", "", Role.Owner, null, "");
        this.tenant = new Tenant("", "", "", "", Role.Tenant, null, "");
        //this.visitor = new Visitor("", "", "", Role.Visitor, "", "", "", "");
        this.host = new Host("", "", "", "", Role.Host, null, "");
        this.loginSuccess = false;
    }


    public Manager getManager() {
        return manager;
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getView() {
        return view;
    }

    public DatabaseConnection getDatabaseConnection() {
        return databaseConnection;
    }

    public Role getLoginRole() {
        return loginRole;
    }

    public boolean getLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginRole(Role loginRole) {
        this.loginRole = loginRole;
    }

    public void evaluateUserCred(String username, String password) {
        Role role = databaseConnection.getUserRole(username);
        ResultSet resultSet = null;
        if (role != null) {
            switch (role) {
                case Manager:
                    resultSet = databaseConnection.getManagerData(username, password);
                    break;
                case Owner:
                    resultSet = databaseConnection.getOwnerData(username, password);
                    break;
                case Host:
                    resultSet = databaseConnection.getHostData(username, password);
                    break;
                case Tenant:
                    resultSet = databaseConnection.getTenantData(username, password);
                    break;
            }

            if (resultSet != null) {
                try {
                    if (resultSet.isBeforeFirst()) {
                        this.loginRole = role;
                        this.loginSuccess = true;
                        switch (role) {
                            case Manager:
                                if (resultSet.next())
                                {
                                    String id = resultSet.getString("userid");
                                    String fullname = resultSet.getString("fullname");
                                    String contact = resultSet.getString("contact");
                                    Date dob = resultSet.getDate("dob");
                                    this.manager = new Manager(id, fullname, username, password, role, dob, contact);
                                }
                                break;
                            case Owner:
                                //Load Owner
                                break;
                            case Tenant:
                                //Load Tenant
                                break;
                            case Host:
                                //Load Host
                                break;
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
