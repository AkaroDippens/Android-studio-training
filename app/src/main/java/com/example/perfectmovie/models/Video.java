package com.example.perfectmovie.models;

public class Video {
    public String url;
    public String name;
    public String site;

    public Video(String url, String name, String site) {
        this.url = url;
        this.name = name;
        this.site = site;
    }

    public String getUrl() {
        return url;
    }
    public String getName() {
        return name;
    }
    public String getSite() {
        return site;
    }
}
