package com.example.normand.Controllers.SceneController.Others;

/**
 * @author sg-random-tut3-group2
 */

import com.example.normand.Models.Person;
import com.example.normand.Models.Property;
import com.example.normand.Models.RentalAgreement;

public class Session {
    private static Session instance;

    private Person user;
    private Property property;
    private RentalAgreement rentalAgreement;

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

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public String getOwnerId() {
        return property.getOwnerId();
    }
}
