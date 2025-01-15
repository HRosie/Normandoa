package com.example.normand.Models;

public enum Role {
    Manager, Host, Owner, Tenant, Visitor;

    public String toLowerCase() {return this.name().toLowerCase();}
}
