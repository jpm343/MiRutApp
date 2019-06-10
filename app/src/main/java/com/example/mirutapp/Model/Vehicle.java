package com.example.mirutapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "patente", unique = true)})
public class Vehicle {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "patente")
    private String patente;
    private String alias;
    private boolean notificating;

    //constructor
    public Vehicle(String patente, String alias) {
        this.patente = patente;
        this.alias = alias;
        this.notificating = true;
    }

    //getters and setters
    public int getId() { return id; }
    public String getPatente() { return patente; }
    public String getAlias() { return alias; }
    public boolean isNotificating() { return notificating; }

    public void setId(int id) { this.id = id; }
    public void setPatente(String patente) { this.patente = patente; }
    public void setAlias(String alias) { this.alias = alias; }
    public void setNotificating(boolean value) { this.notificating = value; }
}
