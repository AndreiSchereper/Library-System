package com.example.endassignment;

//The User class is final so that nothing can extend on it.
//The User class extends the Person class so that it can use the firstName and lastName fields.
//The User class represents the people who can log into the application.
public final class User extends Person {
    private String username;
    private String password;

    public User(String username, String password, String firstName, String lastName) {
        super(firstName, lastName);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
