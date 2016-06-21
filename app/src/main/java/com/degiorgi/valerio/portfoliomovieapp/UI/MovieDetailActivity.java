package com.degiorgi.valerio.portfoliomovieapp.UI;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.degiorgi.valerio.portfoliomovieapp.R;
import com.degiorgi.valerio.portfoliomovieapp.Utility;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailFragment.reviewsCallBack {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    if (savedInstanceState == null) {
      // Create the detail fragment and add it to the activity
      // using a fragment transaction.

      Bundle arguments = new Bundle();
      Uri uri = getIntent().getData();
      if (uri != null) {
        arguments.putParcelable(MovieDetailFragment.MOVIE_ARG, getIntent().getData());

        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
            .add(R.id.movie_detail_container, fragment)
            .commit();
      }

    }
  }

  @Override
  public void onReviewsButtonClicked(String id) {

    //Launches a new review fragment to replace the detail view fragment

    Bundle bundle = new Bundle();

    bundle.putString("ReviewsMovieId", id);

    ReviewsActivity reviewsFragment = new ReviewsActivity();
    reviewsFragment.setArguments(bundle);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.movie_detail_container, reviewsFragment)
        .commit();
  }

  public void favButtonClick(View view) {

    // on button Click, finds the MovieID by looking for the Database ROW with that movie name, and sends all the data
    // into the favourite dabatase
    TextView titleTextView = (TextView) findViewById(R.id.title_view);
    ContentResolver resolver = getContentResolver();
    Context context = getApplicationContext();
    Utility.addFavoriteToDatabase(titleTextView, resolver, context);
  }
}





