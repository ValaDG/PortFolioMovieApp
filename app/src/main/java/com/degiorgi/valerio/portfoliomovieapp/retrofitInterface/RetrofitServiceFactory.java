package com.degiorgi.valerio.portfoliomovieapp.retrofitInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valerio on 22/06/2016.
 */
public class RetrofitServiceFactory {

private static Retrofit retrofit;

  public static Retrofit Factory(){

    if(retrofit == null) {
      retrofit = new Retrofit.Builder().baseUrl("http://api.themoviedb.org/")
          .addConverterFactory(GsonConverterFactory.create()).build();
    }
    return retrofit;

  }

}
