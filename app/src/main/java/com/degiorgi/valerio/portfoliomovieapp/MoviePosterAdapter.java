package com.degiorgi.valerio.portfoliomovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Valerio on 24/01/2016.
 */
public class MoviePosterAdapter extends ArrayAdapter<String> {

    List<String> PosterUrls;


    public MoviePosterAdapter(Context context, List<String> objects) {
        super(context, 0, objects);

        PosterUrls = objects;

    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_item, parent, false);
        }

        ImageView Poster = (ImageView) convertView.findViewById(R.id.gridview_item_image);
        Picasso.with(getContext()).load(getItem(position)).fit().into(Poster);
        return convertView;
    }
}