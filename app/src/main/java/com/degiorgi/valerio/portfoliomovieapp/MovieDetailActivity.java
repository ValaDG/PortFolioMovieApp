package com.degiorgi.valerio.portfoliomovieapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.degiorgi.valerio.portfoliomovieapp.data.FavouriteMoviesColumns;
import com.degiorgi.valerio.portfoliomovieapp.data.MovieContentProvider;
import com.degiorgi.valerio.portfoliomovieapp.data.MovieDatabaseContract;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }

    public void favButtonClick(View view){

        TextView TitleText = (TextView) findViewById(R.id.title_view);

        String[] args = {TitleText.getText().toString()};

        ContentResolver resolver = getContentResolver();

        Cursor cur  = resolver.query(MovieContentProvider.Local_Movies.CONTENT_URI, null,
                MovieDatabaseContract.OriginalTitle + "=?", args, null);

        if(cur.moveToFirst()) {

            String[] idArgs ={cur.getString(1)};
            Cursor cursorCheck = resolver.query(MovieContentProvider.Favourite_Movies.CONTENT_URI,
                    null,
                    FavouriteMoviesColumns.MovieId + "=?",
                    idArgs,
                    null);

            if (!cursorCheck.moveToFirst())  {


                ContentValues values = new ContentValues();

            values.put("MovieId",cur.getString(1));
            values.put("PosterUrl",cur.getString(2));
            values.put("OriginalTitle",cur.getString(3));
            values.put("Overview",cur.getString(4));
            values.put("ReleaseDate",cur.getString(5));
            values.put("Rating",cur.getDouble(6));
                values.put("Popularity",cur.getString(7));

                resolver.insert(MovieContentProvider.Favourite_Movies.CONTENT_URI, values);

            }

        }
    }
   }
