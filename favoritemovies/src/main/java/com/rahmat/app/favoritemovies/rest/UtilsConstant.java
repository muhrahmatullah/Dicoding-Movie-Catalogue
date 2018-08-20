package com.rahmat.app.favoritemovies.rest;

import com.rahmat.app.favoritemovies.BuildConfig;

public class UtilsConstant {

    public final static String DATE_FORMAT = "dd MMMM yyyy";
    public final static String DATE_FORMAT_DAY = "EEEE, MMM d, yyyy";
    public final static String BASE_URL = "http://api.themoviedb.org/3/";
    public final static String API_KEY = BuildConfig.ApiKey;
    public final static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";
    public final static String BASE_BACKDROP_URL = "http://image.tmdb.org/t/p/w780";
    public final static String MOVIE_LIST_INSTANCE = "MOVIE_LIST";
    public final static String MOVIE_LIST_QUERY = "MOVIE_LIST_QUERY";
    public final static String MOVIE_LIST_TOTAL = "MOVIE_LIST_TOTAL";
    public final static String MOVIE_POPULAR_BOOL = "movie_popular_bool";
    public final static String MOVIE_DETAIL = "movie_detail";
    public static final String INTENT_SEARCH = "intent_search";
    public static final String INTENT_TAG = "tag";
    public static final String INTENT_DETAIL = "detail";

}
