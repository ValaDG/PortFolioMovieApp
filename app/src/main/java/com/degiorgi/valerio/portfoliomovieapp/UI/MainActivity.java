package com.degiorgi.valerio.portfoliomovieapp.UI;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.degiorgi.valerio.portfoliomovieapp.R;
import com.degiorgi.valerio.portfoliomovieapp.data.FavouriteMoviesColumns;
import com.degiorgi.valerio.portfoliomovieapp.data.MovieContentProvider;
import com.degiorgi.valerio.portfoliomovieapp.data.MovieDatabaseContract;
import com.degiorgi.valerio.portfoliomovieapp.settings.SettingsActivity;

import static com.degiorgi.valerio.portfoliomovieapp.R.layout;

public class MainActivity extends AppCompatActivity implements MovieFragment.backCall {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";

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

        if (sortBy!= null && !sortBy.equals(mSortBy)) {
            MovieFragment ff = (MovieFragment)getSupportFragmentManager().findFragmentById(R.id.movie_fragment);
            if ( null != ff ) {
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


    public void favButtonClick(View view) {

        TextView TitleText = (TextView) findViewById(R.id.title_view);

        String[] args = {TitleText.getText().toString()};

        ContentResolver resolver = getContentResolver();

        Cursor cur = resolver.query(MovieContentProvider.Local_Movies.CONTENT_URI, null,
                MovieDatabaseContract.OriginalTitle + "=?", args, null);

        if (cur.moveToFirst()) {

            String[] idArgs = {cur.getString(1)};
            Cursor cursorCheck = resolver.query(MovieContentProvider.Favourite_Movies.CONTENT_URI,
                    null,
                    FavouriteMoviesColumns.MovieId + "=?",
                    idArgs,
                    null);

            if (!cursorCheck.moveToFirst()) {


                ContentValues values = new ContentValues();

                values.put("MovieId", cur.getString(1));
                values.put("PosterUrl", cur.getString(2));
                values.put("OriginalTitle", cur.getString(3));
                values.put("Overview", cur.getString(4));
                values.put("ReleaseDate", cur.getString(5));
                values.put("Rating", cur.getDouble(6));
                values.put("Popularity", cur.getString(7));

                resolver.insert(MovieContentProvider.Favourite_Movies.CONTENT_URI, values);

                Toast toast = Toast.makeText(getApplicationContext(), "Movie saved in your Favorites!", Toast.LENGTH_SHORT);
                toast.show();

            }
            cursorCheck.close();
        }

        cur.close();
    }
}

