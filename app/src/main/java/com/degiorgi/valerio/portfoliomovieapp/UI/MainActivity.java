package com.degiorgi.valerio.portfoliomovieapp.UI;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.degiorgi.valerio.portfoliomovieapp.R;
import com.degiorgi.valerio.portfoliomovieapp.Utility;
import com.degiorgi.valerio.portfoliomovieapp.settings.SettingsActivity;

import static com.degiorgi.valerio.portfoliomovieapp.R.layout;

public class MainActivity extends AppCompatActivity implements MovieFragment.backCall, MovieDetailFragment.reviewsCallBack {

  private static final String DETAILFRAGMENT_TAG = "DFTAG";
  private static final String REVIEWSFRAGMENT_TAG = "RWTAG";

  private boolean mTwoPane;
  private String mSortBy;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(layout.activity_main);

    if (findViewById(R.id.movie_detail_container) != null) {
      // The detail container view will be present only in the large-screen layouts
      // (res/layout-sw600dp). If this view is present, then the activity should be
      // in two-pane mode.
      mTwoPane = true;
      // In two-pane mode, show the detail view in this activity by
      // adding or replacing the detail fragment using a
      // fragment transaction.
      if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.movie_detail_container, new MovieDetailFragment(), DETAILFRAGMENT_TAG)
            .commit();
      }
    } else {
      mTwoPane = false;
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {


    int id = item.getItemId();

    if (id == R.id.action_settings) {
      startActivity(new Intent(this, SettingsActivity.class));
    }
    return super.onOptionsItemSelected(item);
  }


  @Override
  protected void onResume() {
    super.onResume();

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    String sortBy = preferences.getString(getString(R.string.sort_by_key), getString(R.string.sort_by_default));
    // update the location in our second pane using the fragment manager

    if (!sortBy.equals(mSortBy)) {
      MovieFragment ff = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.movie_fragment);
      if (null != ff) {
        ff.onSortChanged();
      }
    }
  }

  @Override
  public void onGridItemSelected(Uri contentUri) {
    if (mTwoPane) {
      // In two-pane mode, show the detail view in this activity by
      // adding or replacing the detail fragment using a
      // fragment transaction.
      Bundle args = new Bundle();
      args.putParcelable(MovieDetailFragment.MOVIE_ARG, contentUri);

      MovieDetailFragment fragment = new MovieDetailFragment();
      fragment.setArguments(args);

      getSupportFragmentManager().beginTransaction()
          .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
          .commit();
    } else {
      Intent intent = new Intent(this, MovieDetailActivity.class)
          .setData(contentUri);
      startActivity(intent);
    }
  }

  @Override
  public void onReviewsButtonClicked(String id) {
    if (mTwoPane) {
      // In two-pane mode, show the reviews Listview in this activity by
      // adding or replacing the detail fragment using a
      // fragment transaction.
      Bundle args = new Bundle();
      args.putString("ReviewsMovieId", id);

      ReviewsFragment fragment = new ReviewsFragment();
      fragment.setArguments(args);

      getSupportFragmentManager().beginTransaction()
          .replace(R.id.movie_detail_container, fragment, REVIEWSFRAGMENT_TAG)
          .commit();

    }
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
