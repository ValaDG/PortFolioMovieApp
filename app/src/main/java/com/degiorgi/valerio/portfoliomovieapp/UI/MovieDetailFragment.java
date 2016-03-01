package com.degiorgi.valerio.portfoliomovieapp.UI;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.degiorgi.valerio.portfoliomovieapp.R;
import com.degiorgi.valerio.portfoliomovieapp.adapters.MovieTrailersAdapter;
import com.degiorgi.valerio.portfoliomovieapp.models.MovieReviewsForId;
import com.degiorgi.valerio.portfoliomovieapp.models.MovieTrailersForId;
import com.degiorgi.valerio.portfoliomovieapp.models.SingleReviewResult;
import com.degiorgi.valerio.portfoliomovieapp.models.SingleTrailerResult;
import com.degiorgi.valerio.portfoliomovieapp.retrofitInterface.MovieService;
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
public class MovieDetailFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor>

{

    public static final String API_BASE_URL = "http://api.themoviedb.org/";
    String api_key = "";
    ArrayAdapter<String> mReviewsAdapter;
    MovieTrailersAdapter mTrailerAdapter;
    Call<MovieTrailersForId> callMovies;
    Call<MovieReviewsForId> callReviews;
    static final String MOVIE_ARG ="movie_id";
    String Imagebaseurl = "http://image.tmdb.org/t/p/w185/";
    private Uri mUri;

    private static final int DETAIL_LOADER = 0;

    ImageView PosterImageView;
    TextView Title;
    TextView releaseDate;
    TextView UserRating;
    TextView OverView;
    ListView reviewsListView;
    ListView trailersListView;
    //Single detail activity for single movies, we receive the intents from the movie fragment and update each view with the correct values

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(MovieDetailFragment.MOVIE_ARG);
        }

        View rootview = inflater.inflate(R.layout.movie_detail_fragment, container, false);

        PosterImageView = (ImageView) rootview.findViewById(R.id.poster_imageView);
        Title = (TextView) rootview.findViewById(R.id.title_view);
        releaseDate = (TextView) rootview.findViewById(R.id.release_date_view);
        UserRating = (TextView) rootview.findViewById(R.id.users_rating_view);
        OverView = (TextView) rootview.findViewById(R.id.synopsis_view);

        mReviewsAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.single_list_item_textview,
                R.id.single_item_textview_id,
                new ArrayList<String>());

        reviewsListView = (ListView) rootview.findViewById(R.id.reviews_listview);
        mTrailerAdapter = new MovieTrailersAdapter(getActivity(), new ArrayList<SingleTrailerResult>());

        trailersListView = (ListView) rootview.findViewById(R.id.trailers_listview);

        trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SingleTrailerResult result = (SingleTrailerResult) parent.getItemAtPosition(position);

                String MovieId = result.getKey();

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + MovieId));
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + MovieId));
                    startActivity(intent);
                }
            }
        });
        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    public void getTrailers(int id) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        MovieService.FetchMovieInterface MovieInterface = retrofit.create(MovieService.FetchMovieInterface.class);

        callMovies = MovieInterface.getMovieTrailers(id, api_key);

        callMovies.enqueue(new Callback<MovieTrailersForId>() {
            @Override
            public void onResponse(Call<MovieTrailersForId> call, Response<MovieTrailersForId> response) {

                List<SingleTrailerResult> singleTrailerList = response.body().getResults();

                for (SingleTrailerResult result : singleTrailerList) {

                    mTrailerAdapter.add(result);
                }
            }

            @Override
            public void onFailure(Call<MovieTrailersForId> call, Throwable t) {

            }
        });
    }

    public void getReviews(int id) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        MovieService.FetchMovieInterface MovieInterface = retrofit.create(MovieService.FetchMovieInterface.class);

        callReviews = MovieInterface.getMovieReviews(id, api_key);

        callReviews.enqueue(new Callback<MovieReviewsForId>() {
            @Override
            public void onResponse(Call<MovieReviewsForId> call, Response<MovieReviewsForId> response) {

                List<SingleReviewResult> singleList = response.body().getResults();


                for (SingleReviewResult result : singleList) {
                    mReviewsAdapter.add(result.getContent());

                }


            }

            @Override
            public void onFailure(Call<MovieReviewsForId> call, Throwable t) {

            }
        });

    }



    @Override
    public void onStop() {
        super.onStop();
        callMovies.cancel();
        callReviews.cancel();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    null,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(data!=null && data.moveToFirst())
        {
            Picasso.with(getContext()).load(Imagebaseurl + data.getString(2)).into(PosterImageView);
            Title.setText(data.getString(3));
            OverView.setText(data.getString(4));
            releaseDate.setText(data.getString(5));
            UserRating.setText(String.valueOf(data.getString(6)));

            getTrailers(data.getInt(1));
            getReviews(data.getInt(1));

            reviewsListView.setAdapter(mReviewsAdapter);
            trailersListView.setAdapter(mTrailerAdapter);
        }



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}