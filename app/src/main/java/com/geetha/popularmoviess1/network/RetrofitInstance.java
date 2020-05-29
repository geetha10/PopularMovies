package com.geetha.popularmoviess1.network;

import com.geetha.popularmoviess1.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static MoviesApi get(){
        Retrofit retrofit=new Retrofit.Builder ()
                .baseUrl (Constants.MOVIE_DB_BASE_URL)
                .addConverterFactory (GsonConverterFactory.create ())
                .build ();
        return retrofit.create (MoviesApi.class);
    }
}
