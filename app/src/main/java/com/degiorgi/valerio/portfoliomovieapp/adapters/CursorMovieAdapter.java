package com.degiorgi.valerio.portfoliomovieapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.degiorgi.valerio.portfoliomovieapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Valerio on 28/02/2016.
 * <p>
 * handles the movie url loading and disk caching
 */
public class CursorMovieAdapter extends CursorAdapter {
  public CursorMovieAdapter(Context context, Cursor c, int flags) {
    super(context, c, flags);
  }


  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    View view = LayoutInflater.from(context).inflate(R.layout.gridview_item, parent, false);

    return view;
  }


  @Override
  public void bindView(View view, final Context context, final Cursor cursor) {

    final String baseUrl = "http://image.tmdb.org/t/p/w185/";

    final ImageView img = (ImageView) view;

    final int width = context.getResources().getDisplayMetrics().widthPixels / 2;
    final int height = (int) ((int) width * 1.5);
    final String url = baseUrl + cursor.getString(2);

    //Uses Picasso to load images into the Gridview, uses the disk cache first and when it fails it goes online.


    Picasso.with(context)
        .load(url)
        .networkPolicy(NetworkPolicy.OFFLINE)
        .resize(width, height)
        .centerCrop()
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .into(img, new Callback() {
          @Override
          public void onSuccess() {

          }

          @Override
          public void onError() {

            Picasso.with(context)
                .load(url)
                .error(R.drawable.error)
                .resize(width, height)
                .centerCrop()
                .into(img, new Callback() {
                  @Override
                  public void onSuccess() {

                  }

                  @Override
                  public void onError() {
                    Log.v("Picasso", "Could not fetch image");
                  }
                });


          }
        });


  }
}