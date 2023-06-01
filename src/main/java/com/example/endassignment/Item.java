package com.example.endassignment;

import java.io.Serializable;
import java.time.LocalDate;

//The Item class represents the books that can be lent to Members.
//The Item class implements Serializable so that objects of this class can be serialized.
public class Item implements Serializable {
    private String title;
    private String author;
    private String isAvailable;
    private int code;
    private LocalDate lendingDate;

    public Item(String title, String author, String isAvailable, int code) {
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
        this.code = code;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}

    public void setAuthor(String author) {this.author = author;}

    public String getIsAvailable() {return isAvailable;}

    public void setIsAvailable(String isAvailable) {this.isAvailable = isAvailable;}

    public int getCode() {return code;}

    public void setCode(int code) {this.code = code;}

    public LocalDate getLendingDate() {return lendingDate;}

    public void setLendingDate(LocalDate lendingDate) {this.lendingDate = lendingDate;}
}
