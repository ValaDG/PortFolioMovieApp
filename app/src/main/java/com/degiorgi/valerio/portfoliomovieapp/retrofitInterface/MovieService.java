package com.degiorgi.valerio.portfoliomovieapp.retrofitInterface;

import com.degiorgi.valerio.portfoliomovieapp.models.MovieApiRequest;
import com.degiorgi.valerio.portfoliomovieapp.models.MovieReviewsForId;
import com.degiorgi.valerio.portfoliomovieapp.models.MovieTrailersForId;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Valerio on 26/02/2016.
 */


public class MovieService {

    public interface FetchMovieInterface {

        @GET("3/discover/movie")
        Call<MovieApiRequest> getMovies(@Query("sort_by") String sort, @Query("api_key") String key);

        @GET("3/movie/{id}/videos")
        Call<MovieTrailersForId> getMovieTrailers(@Path("id") int id, @Query("api_key") String key);

        @GET("3/movie/{id}/reviews")
        Call<MovieReviewsForId> getMovieReviews(@Path("id") int id, @Query("api_key") String key);
    }
}