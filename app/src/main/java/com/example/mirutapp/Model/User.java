package com.example.mirutapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//declare your model using Room library
@Entity
public class User {
    @PrimaryKey
    private int id;
    private String name;
    private String lastName;

    //get, set
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
