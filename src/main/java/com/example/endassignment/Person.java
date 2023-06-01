package com.example.endassignment;

import java.io.Serializable;

//The Person class is the base class for the User class and Member class.
//The fields in this class are protected so that only the User and Member class can access them.
//The Person class implements Serializable so that objects of the class Member can be serialized.
public class Person implements Serializable {
    protected String firstName;
    protected String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}