package com.example.normand.Controllers.UsersController;

import com.example.normand.Models.Manager;
import javafx.scene.chart.PieChart;

import java.sql.Connection;

public class ManagerController {
    private final Manager manager;
    private Connection connection;

    public ManagerController(Manager manager, Connection connection) {
        this.manager = manager;
        this.connection = connection;
    }

    public String getInfo() {
        return "ID:" + manager.getId();
    }


}
