package com.degiorgi.valerio.portfoliomovieapp;

import com.degiorgi.valerio.portfoliomovieapp.models.MovieApiRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Valerio on 26/02/2016.
 */

public final class Service {

public interface FetchMovieInterface {

    @GET("3/discover/movie")
    Call<MovieApiRequest> MovieApiRequest(@Query("sort_by") String sort, @Query("api_key") String apikey);


}}
