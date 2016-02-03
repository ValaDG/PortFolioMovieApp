package com.degiorgi.valerio.portfoliomovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MoviePosterAdapter extends ArrayAdapter<MovieObject> {

   private Context mContext;

    public MoviePosterAdapter(Context context, ArrayList<MovieObject> objects) {
        super(context, R.layout.gridview_item, objects);

mContext =context;

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
        //Get screen size, and divide it by the number of columns of the grid view.
        int width = getContext().getResources().getDisplayMetrics().widthPixels / 2;
        int height = (int) ((int) width*1.5);


        ImageView Poster = (ImageView) convertView.findViewById(R.id.gridview_item_image);

        Picasso.with(mContext)
                .load(baseUrl + object.PosterUrl)
                .resize(width, height)
                .centerCrop()
                .into(Poster);

        return convertView;
    }
}