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
import com.degiorgi.valerio.portfoliomovieapp.retrofitInterface.RetrofitServiceFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Valerio on 01/03/2016.
 *
 */
public class ReviewsFragment extends Fragment {

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
        R.layout.reviews_listview_item,
        R.id.reviews_listview_item,
        new ArrayList<String>());


    reviewsListView = (ListView) rootview.findViewById(R.id.reviews_listview);

    getReviews(id);

    return rootview;
  }


  public void getReviews(int id) {

    //method to grab our reviews based on the movie id


    MovieService.FetchMovieInterface MovieInterface = RetrofitServiceFactory.Factory().create(MovieService.FetchMovieInterface.class);

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
