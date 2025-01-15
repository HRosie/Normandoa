package com.example.normand.Controllers.SceneController.Others;

import com.example.normand.Models.Person;

public class Session {
    private static Session instance;

    private Person user;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public Person getUser() {
        return user;
    }
}
