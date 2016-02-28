package com.degiorgi.valerio.portfoliomovieapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Valerio on 28/02/2016.
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
    public void bindView(View view, Context context, Cursor cursor) {

        String baseUrl = "http://image.tmdb.org/t/p/w185/";

        ImageView img = (ImageView) view;

        int width = context.getResources().getDisplayMetrics().widthPixels / 2;
        int height = (int) ((int) width*1.5);


        Picasso.with(context)
                .load(baseUrl + cursor.getString(2))
                .resize(width,height)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(img);


    }
}