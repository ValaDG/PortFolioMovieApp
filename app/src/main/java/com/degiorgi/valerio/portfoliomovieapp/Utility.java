package com.degiorgi.valerio.portfoliomovieapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;
import android.widget.Toast;

import com.degiorgi.valerio.portfoliomovieapp.data.MovieContentProvider;
import com.degiorgi.valerio.portfoliomovieapp.data.MovieDatabaseContract;

/**
 * Created by Valerio on 21/06/2016.
 */
public class Utility {

  public static void addFavoriteToDatabase(TextView title, ContentResolver resolver, Context context) {


    String[] args = {title.getText().toString()};


    Cursor cur = resolver.query(MovieContentProvider.Local_Movies.CONTENT_URI, null,
        MovieDatabaseContract.OriginalTitle + "=?", args, null);
    if (cur != null) {
      if (cur.moveToFirst()) {

        String[] idArgs = {cur.getString(1)};
        Cursor cursorCheck = resolver.query(MovieContentProvider.Favourite_Movies.CONTENT_URI,
            null,
            MovieDatabaseContract.MovieId + "=?",
            idArgs,
            null);

        if (cursorCheck != null) {
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

            Toast toast = Toast.makeText(context, "Movie saved in your Favorites!", Toast.LENGTH_SHORT);
            toast.show();

          }
          cursorCheck.close();
        }

      }
      cur.close();
    }


  }


}

