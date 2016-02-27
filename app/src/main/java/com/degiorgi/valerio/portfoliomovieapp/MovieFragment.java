package com.degiorgi.valerio.portfoliomovieapp;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.degiorgi.valerio.portfoliomovieapp.models.MovieApiRequest;
import com.degiorgi.valerio.portfoliomovieapp.models.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valerio on 23/01/2016.
 */
public class MovieFragment extends Fragment {

    public static final String API_BASE_URL = "http://api.themoviedb.org/";
    MoviePosterAdapter mAdapater;
    List<Result> Movies = new ArrayList<>();
    Call<MovieApiRequest> CallMovies;
    String api_key = "241141bc665e9b2d0fb9ac4759497786";


    private void UpdateMovies() { //Executes the background Network Call

        mAdapater = new MoviePosterAdapter(getActivity(), new ArrayList<Result>());

        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        MovieService.FetchMovieInterface MovieInterface = retrofit.create(MovieService.FetchMovieInterface.class);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy =preferences.getString(getString(R.string.sort_by_key), getString(R.string.sort_by_default));


        CallMovies = MovieInterface.getMovies(sortBy,api_key);

        CallMovies.enqueue(new Callback<MovieApiRequest>() {
            @Override
            public void onResponse(Call<MovieApiRequest> call, Response<MovieApiRequest> response) {

                if (response != null) {

                    MovieApiRequest request = response.body();

                    mAdapater.clear();
                    Movies = request.getResults();
                    mAdapater.addAll(Movies);
                }
            }

            @Override
            public void onFailure(Call<MovieApiRequest> call, Throwable t) {

            }
        });

    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

// Create a gridview reference, initialize the adapater, and assign it to the gridview
        View rootView = inflater.inflate(R.layout.movie_fragment_layout, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview_list);
        UpdateMovies();
        gridview.setAdapter(mAdapater);



        return rootView;

    }

    @Override
    public void onStop() {
        super.onStop();
        CallMovies.cancel();
    }

}



