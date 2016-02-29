package com.degiorgi.valerio.portfoliomovieapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Valerio on 28/02/2016.
 */
public class MovieDetailFragment extends Fragment

{


    //Single detail activity for single movies, we receive the intents from the movie fragment and update each view with the correct values

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String url;
        String title;
        String release;
        String synopsis;
        double userRating;
        int id;

        View rootview = inflater.inflate(R.layout.movie_detail_fragment, container, false);

        String baseurl = "http://image.tmdb.org/t/p/w185/";

        Intent intent = getActivity().getIntent();
        if (intent != null) {

            id = Integer.valueOf(intent.getStringExtra("id"));
            url = intent.getStringExtra("url");
            ImageView PosterImageView = (ImageView) rootview.findViewById(R.id.poster_imageView);
            Picasso.with(getActivity()).load(baseurl + url).into(PosterImageView);

            TextView Title = (TextView) rootview.findViewById(R.id.title_view);
            Title.setText(intent.getStringExtra("title"));

            title = intent.getStringExtra("release");
            TextView releaseDate = (TextView) rootview.findViewById(R.id.release_date_view);
            releaseDate.setText(title);

            userRating = Double.parseDouble(intent.getStringExtra("rating"));
            TextView UserRating = (TextView) rootview.findViewById(R.id.users_rating_view);
            UserRating.setText(intent.getStringExtra("rating"));

            synopsis = intent.getStringExtra("synopsis");
            TextView OverView = (TextView) rootview.findViewById(R.id.synopsis_view);
            OverView.setText(synopsis);
        }


        return rootview;
    }

}