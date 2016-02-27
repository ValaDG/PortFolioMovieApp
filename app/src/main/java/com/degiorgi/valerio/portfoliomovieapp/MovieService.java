package com.degiorgi.valerio.portfoliomovieapp;

import com.degiorgi.valerio.portfoliomovieapp.models.MovieApiRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Valerio on 26/02/2016.
 */


public class MovieService {

    public interface FetchMovieInterface {

        @GET("3/discover/movie")
        Call<MovieApiRequest> getMovies(@Query("sort_by")String sort , @Query("api_key") String key);


    }
}