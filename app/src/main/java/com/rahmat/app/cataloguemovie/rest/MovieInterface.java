package com.rahmat.app.cataloguemovie.rest;

import com.rahmat.app.cataloguemovie.model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieInterface {

    @GET("movie/popular")
    Call<Movie> getPopularMovie(@Query("api_key") String apiKey);

    @GET("search/movie/")
    Call<Movie> getMovieBySearch(@Query("query") String q, @Query("api_key") String apiKey);

}