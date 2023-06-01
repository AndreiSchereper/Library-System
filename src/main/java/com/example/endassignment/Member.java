package com.example.endassignment;

import java.io.Serializable;
import java.time.LocalDate;

//The Member class is final so that nothing can extend on it.
//The Member class extends the Person class so that it can use the firstName and lastName fields.
//The Member class implements Serializable so that objects of this class can be serialized.
//The Member class represents people who can lend books from the library.
public final class Member extends Person implements Serializable {
    private int identifier;
    private LocalDate birthDate;

    public Member(String firstName, String lastName, LocalDate birthDate, int identifier) {
        super(firstName, lastName);
        this.birthDate = birthDate;
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {this.identifier = identifier;}

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
