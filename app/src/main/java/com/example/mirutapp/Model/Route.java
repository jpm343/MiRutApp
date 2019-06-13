package com.example.mirutapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Set;

@Entity
public class Route {
    //calendar: day = 7, hour = 11, minute = 12
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String url;
    private String routeName;
    private Boolean notificating;
    private int alarmHour;
    private int alarmMinute;

    @TypeConverters(SetOfDaysConverter.class)
    private Set<Integer> days;

    public Route(String url, String routeName, int alarmHour, int alarmMinute, Set<Integer> days) {
        this.url = url;
        this.routeName = routeName;
        this.notificating = true;
        this.alarmHour = alarmHour;
        this.alarmMinute = alarmMinute;
        this.days = days;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Boolean getNotificating() {
        return notificating;
    }

    public void setNotificating(Boolean notificating) {
        this.notificating = notificating;
    }

    public int getAlarmHour() {
        return alarmHour;
    }

    public void setAlarmHour(int alarmHour) {
        this.alarmHour = alarmHour;
    }

    public int getAlarmMinute() {
        return alarmMinute;
    }

    public void setAlarmMinute(int alarmMinute) {
        this.alarmMinute = alarmMinute;
    }

    public Set<Integer> getDays() {
        return days;
    }

    public void setDays(Set<Integer> days) {
        this.days = days;
    }
}
