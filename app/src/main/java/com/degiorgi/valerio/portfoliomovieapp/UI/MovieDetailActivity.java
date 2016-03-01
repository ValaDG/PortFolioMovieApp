package com.degiorgi.valerio.portfoliomovieapp.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.degiorgi.valerio.portfoliomovieapp.R;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null) {
                // Create the detail fragment and add it to the activity
                // using a fragment transaction.

                Bundle arguments = new Bundle();
                arguments.putParcelable(MovieDetailFragment.MOVIE_ARG, getIntent().getData());

                MovieDetailFragment fragment = new MovieDetailFragment();
                fragment.setArguments(arguments);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_detail_container, fragment)
                        .commit();
            }
        }




}
