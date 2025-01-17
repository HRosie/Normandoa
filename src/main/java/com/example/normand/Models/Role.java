package com.example.normand.Models;

/**
 * @author sg-random-tut3-group2
 */

public enum Role {
    Manager, Host, Owner, Tenant, Visitor;

    public String toLowerCase() {return this.name().toLowerCase();}
}
