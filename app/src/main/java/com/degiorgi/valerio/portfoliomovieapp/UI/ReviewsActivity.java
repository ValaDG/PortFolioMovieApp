package com.degiorgi.valerio.portfoliomovieapp.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.degiorgi.valerio.portfoliomovieapp.R;
import com.degiorgi.valerio.portfoliomovieapp.models.MovieReviewsForId;
import com.degiorgi.valerio.portfoliomovieapp.models.SingleReviewResult;
import com.degiorgi.valerio.portfoliomovieapp.retrofitInterface.MovieService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valerio on 01/03/2016.
 * Its named activity but its in truth a fragment
 *
 */
public class ReviewsActivity extends Fragment {

    ListView reviewsListView;
    ArrayAdapter<String> mReviewsAdapter;
    Call<MovieReviewsForId> callReviews;
    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.revies_fragment, container, false);

        Bundle args = getArguments();

        if (args != null) {

            id = Integer.valueOf(args.getString("ReviewsMovieId"));
        }

        mReviewsAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.single_list_item_textview,
                R.id.single_item_textview_id,
                new ArrayList<String>());


        reviewsListView = (ListView) rootview.findViewById(R.id.reviews_listview);

        getReviews(id);

        return rootview;
    }


    public void getReviews(int id) {

        //method to grab our reviews based on the movie id

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.API_BASE_URL)).addConverterFactory(GsonConverterFactory.create()).build();

        MovieService.FetchMovieInterface MovieInterface = retrofit.create(MovieService.FetchMovieInterface.class);

        callReviews = MovieInterface.getMovieReviews(id, getString(R.string.api_key));

        callReviews.enqueue(new Callback<MovieReviewsForId>() {
            @Override

            public void onResponse(Call<MovieReviewsForId> call, Response<MovieReviewsForId> response) {

                List<SingleReviewResult> singleList = response.body().getResults();


                for (SingleReviewResult result : singleList) {
                    mReviewsAdapter.add(result.getContent());

                }

                reviewsListView.setAdapter(mReviewsAdapter);


            }

            @Override
            public void onFailure(Call<MovieReviewsForId> call, Throwable t) {

            }
        });

    }
}
