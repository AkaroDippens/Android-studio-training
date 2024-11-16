package com.example.perfectmovie.models;

import java.util.ArrayList;

public class FilmInfo {
    public int kinopoiskId;
    public String imdbId;
    public String nameRu;
    public Object nameEn;
    public String posterUrl;
    public String posterUrlPreview;
    public double ratingImdb;
    public int year;
    public String description;
    public String ratingMpaa;

    public int getKinopoiskId() {
        return kinopoiskId;
    }
    public String getImdbId() {
        return imdbId;
    }
    public String getNameRu() {
        return nameRu;
    }
    public Object getNameEn() {
        return nameEn;
    }
    public String getPosterUrl() {
        return posterUrl;
    }
    public String getPosterUrlPreview() {
        return posterUrlPreview;
    }
    public double getRatingImdb() {
        return ratingImdb;
    }
    public int getYear() {
        return year;
    }
    public String getDescription() {
        return description;
    }

    public String getRatingMpaa() {
        return ratingMpaa;
    }

}
