package com.degiorgi.valerio.portfoliomovieapp.UI;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.degiorgi.valerio.portfoliomovieapp.MovieService;
import com.degiorgi.valerio.portfoliomovieapp.R;
import com.degiorgi.valerio.portfoliomovieapp.models.MovieReviewsForId;
import com.degiorgi.valerio.portfoliomovieapp.models.MovieTrailersForId;
import com.degiorgi.valerio.portfoliomovieapp.models.SingleReviewResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valerio on 28/02/2016.
 */
public class MovieDetailFragment extends Fragment

{

    public static final String API_BASE_URL = "http://api.themoviedb.org/";
    String api_key = "241141bc665e9b2d0fb9ac4759497786";
    ArrayAdapter<String> mReviewsAdapter;

    String url;
    String title;
    String release;
    String synopsis;
    double userRating;
    int id;

    //Single detail activity for single movies, we receive the intents from the movie fragment and update each view with the correct values

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


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

        mReviewsAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.single_list_item_textview,
                R.id.single_item_textview_id,
                new ArrayList<String>());

        getReviews(id);

        ListView reviewsListView = (ListView) rootview.findViewById(R.id.reviews_listview);
        reviewsListView.setAdapter(mReviewsAdapter);

        return rootview;
    }


    public ArrayList<String> getTrailers(int id){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        MovieService.FetchMovieInterface MovieInterface = retrofit.create(MovieService.FetchMovieInterface.class);

        Call<MovieTrailersForId> callMovies = MovieInterface.getMovieTrailers(id,api_key);

        return new ArrayList<>();
    }

    public void getReviews(int id){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        MovieService.FetchMovieInterface MovieInterface = retrofit.create(MovieService.FetchMovieInterface.class);

        Call<MovieReviewsForId> callReviews = MovieInterface.getMovieReviews(id,api_key);

        callReviews.enqueue(new Callback<MovieReviewsForId>() {
            @Override
            public void onResponse(Call<MovieReviewsForId> call, Response<MovieReviewsForId> response) {

                List<SingleReviewResult> singleList = response.body().getResults();



                for(SingleReviewResult result : singleList)
                {
                    mReviewsAdapter.add(result.getContent());

                }



            }

            @Override
            public void onFailure(Call<MovieReviewsForId> call, Throwable t) {

            }
        });

    }

}