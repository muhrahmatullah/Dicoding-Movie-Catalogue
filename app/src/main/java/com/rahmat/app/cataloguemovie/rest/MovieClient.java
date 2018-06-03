package com.rahmat.app.cataloguemovie.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.rahmat.app.cataloguemovie.utils.UtilsConstant.BASE_URL;

public class MovieClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
