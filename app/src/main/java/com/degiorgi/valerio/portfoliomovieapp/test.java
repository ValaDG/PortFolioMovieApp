package com.degiorgi.valerio.portfoliomovieapp;

import com.degiorgi.valerio.portfoliomovieapp.models.MovieApiRequest;
import com.degiorgi.valerio.portfoliomovieapp.models.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Valerio on 26/02/2016.
 */
class test {

    ArrayList<Result> result = new ArrayList<>();

    public final String API_BASE_URL = "http://api.themoviedb.org/";


    Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    Service.FetchMovieInterface task = retrofit.create(Service.FetchMovieInterface.class);

    Call<MovieApiRequest> call = task.MovieApiRequest("popularity.desc", "241141bc665e9b2d0fb9ac4759497786");


}