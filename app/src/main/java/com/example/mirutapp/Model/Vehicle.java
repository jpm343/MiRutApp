package com.example.mirutapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(indices = {@Index(value = "patente", unique = true)})
public class Vehicle {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "patente")
    private String patente;
    private String alias;
    private boolean selloVerde;
    private boolean notificating;

    @TypeConverters(CarTypeConverter.class)
    private CarType type;

    //constructor
    public Vehicle(String patente, String alias, CarType type, boolean selloVerde) {
        this.patente = patente;
        this.alias = alias;
        this.type = type;
        this.selloVerde = selloVerde;
        this.notificating = true;
    }

    //getters and setters
    public int getId() { return id; }
    public String getPatente() { return patente; }
    public String getAlias() { return alias; }
    public CarType getType() { return type; }
    public boolean hasSelloVerde() { return selloVerde; }
    public boolean isNotificating() { return notificating; }

    public void setId(int id) { this.id = id; }
    public void setPatente(String patente) { this.patente = patente; }
    public void setAlias(String alias) { this.alias = alias; }
    public void setType(CarType type) { this.type = type; }
    public void setSelloVerde(boolean selloVerde) { this.selloVerde = selloVerde; }
    public void setNotificating(boolean value) { this.notificating = value; }


    //enum for carType
    public enum CarType {
        AUTO(0),
        CAMION(1),
        MOTO(2);
        private int code;

        CarType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
