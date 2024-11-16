package com.example.perfectmovie.models;

import java.util.ArrayList;

public class Film {
    public int filmId;
    public String nameRu;
    public String nameEn;
    public String year;
    public String rating;
    public String posterUrl;
    public String posterUrlPreview;

    public Film(int filmId, String nameRu, String nameEn, String year, String rating, String posterUrl, String posterUrlPreview) {
        this.filmId = filmId;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
        this.year = year;
        this.rating = rating;
        this.posterUrl = posterUrl;
        this.posterUrlPreview = posterUrlPreview;
    }

    public int getFilmId() {
        return filmId;
    }

    public String getNameRu() {
        return nameRu;
    }
    public String getNameEn() {
        return nameEn;
    }
    public String getYear() {
        return year;
    }
    public String getRating() {
        return rating;
    }
    public String getPosterUrl() {
        return posterUrl;
    }
    public String getPosterUrlPreview() {
        return posterUrlPreview;
    }
}
