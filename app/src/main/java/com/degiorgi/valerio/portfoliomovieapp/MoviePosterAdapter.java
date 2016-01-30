package com.degiorgi.valerio.portfoliomovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class MoviePosterAdapter extends ArrayAdapter<MovieObject> {


    public MoviePosterAdapter(Context context, List<MovieObject> objects) {
        super(context, R.layout.gridview_item, objects);


    }


    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieObject object = getItem(position);
        String baseUrl = "http://image.tmdb.org/t/p/w185/";


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_item, null);

        }
        //Get screen size, and divide it by the number of columns of your grid view.
        int width = getContext().getResources().getDisplayMetrics().widthPixels / 2;

        ImageView Poster = (ImageView) convertView.findViewById(R.id.gridview_item_image);
        Picasso.with(getContext())
                .load(baseUrl + object.PosterUrl)
                .resize(width, width)
                .centerCrop()
                .into(Poster);

        return convertView;
    }
}