package com.rahmat.app.cataloguemovie.rest;

import com.rahmat.app.cataloguemovie.model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieInterface {
    
    @GET("movie/now_playing")
    Call<Movie> getNowPlayingMovie(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<Movie> getUpcomingMovie(@Query("api_key") String apiKey);


    @GET("search/movie/")
    Call<Movie> getMovieBySearch(@Query("query") String q, @Query("api_key") String apiKey);

}
