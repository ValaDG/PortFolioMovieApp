package com.degiorgi.valerio.portfoliomovieapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
    }


    public static class MovieDetailFragment extends Fragment

    {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootview = inflater.inflate(R.layout.movie_detail_fragment, container, false);

            String baseurl = "http://image.tmdb.org/t/p/w185/";

            Intent intent = getActivity().getIntent();
            if (intent != null) {
                ImageView PosterImageView = (ImageView) rootview.findViewById(R.id.poster_imageView);
                Picasso.with(getActivity()).load(baseurl + intent.getStringExtra("url")).into(PosterImageView);

                TextView Title = (TextView) rootview.findViewById(R.id.title_view);
                Title.setText(intent.getStringExtra("title"));

                TextView releaseDate = (TextView) rootview.findViewById(R.id.release_date_view);
                releaseDate.setText(intent.getStringExtra("release"));

                TextView UserRating = (TextView) rootview.findViewById(R.id.users_rating_view);

                UserRating.setText(intent.getStringExtra("rating"));

                TextView OverView = (TextView) rootview.findViewById(R.id.synopsis_view);
                OverView.setText(intent.getStringExtra("synopsis"));
            }
            return rootview;
        }

    }
}
