package com.example.endassignment;

import java.util.ArrayList;
import java.util.List;

//This class is a mock-up database.
//The Database class contains a list of Member objects and a list of Item objects.
//The Database class is final because nothing extends on it.
public final class Database {
    private List<Item> items = new ArrayList<>();
    private List<Member> members = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
