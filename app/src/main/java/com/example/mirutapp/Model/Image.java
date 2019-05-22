package com.example.mirutapp.Model;

public class Image {
    public Image(){}
    public Image(String url) {
        this.url = url;
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
