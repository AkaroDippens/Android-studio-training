package com.example.perfectmovie;

import com.example.perfectmovie.models.ActorsRegisers;
import com.example.perfectmovie.models.FilmInfo;
import com.example.perfectmovie.models.Root;
import com.example.perfectmovie.models.Trailer;
import com.example.perfectmovie.models.Video;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Apiinterface {
    @Headers({"X-API-KEY: 567215a5-c7ff-43ef-9738-9949bba05896", "Content-Type: application/json"})
    @GET("v2.2/films/top?type=TOP_AWAIT_FILMS&page=1")
    Call<Root> getPageId();

    @Headers({"X-API-KEY: 567215a5-c7ff-43ef-9738-9949bba05896", "Content-Type: application/json"})
    @GET("v2.2/films/top?type=TOP_250_BEST_FILMS&page=1")
    Call<Root> getPagesId();

    @Headers({"X-API-KEY: 567215a5-c7ff-43ef-9738-9949bba05896"})
    @GET("v2.2/films/{kinopiskId}")
    Call<FilmInfo> getFid(@Path("kinopiskId") int kinopoiskId);

    @Headers({"X-API-KEY: 3dce5dbd-5520-4447-b334-e9829d2317b9"})
    @GET("v2.2/films/{kinopiskId}/videos")
    Call<Trailer> getVId(@Path("kinopiskId") int kinopoiskId);

    @Headers({"X-API-KEY: 3dce5dbd-5520-4447-b334-e9829d2317b9"})
    @GET("v1/staff")
    Call<ArrayList<ActorsRegisers>> getActors(@Query("filmId") int filmId);
}
