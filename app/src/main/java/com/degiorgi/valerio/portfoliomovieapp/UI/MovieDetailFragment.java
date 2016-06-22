package com.degiorgi.valerio.portfoliomovieapp.UI;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.degiorgi.valerio.portfoliomovieapp.R;
import com.degiorgi.valerio.portfoliomovieapp.adapters.MovieTrailersAdapter;
import com.degiorgi.valerio.portfoliomovieapp.data.MovieContentProvider;
import com.degiorgi.valerio.portfoliomovieapp.data.MovieDatabaseContract;
import com.degiorgi.valerio.portfoliomovieapp.models.MovieTrailersForId;
import com.degiorgi.valerio.portfoliomovieapp.models.SingleTrailerResult;
import com.degiorgi.valerio.portfoliomovieapp.retrofitInterface.MovieService;
import com.degiorgi.valerio.portfoliomovieapp.retrofitInterface.RetrofitServiceFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Valerio on 28/02/2016.
 */
public class MovieDetailFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor>

{
  static final String MOVIE_ARG = "movie_id";
  private static final int DETAIL_LOADER = 0;
  MovieTrailersAdapter mTrailerAdapter;
  Call<MovieTrailersForId> callMovies;
  String Imagebaseurl = "http://image.tmdb.org/t/p/w185/";
  ImageView PosterImageView;
  TextView Title;
  TextView releaseDate;
  TextView UserRating;
  TextView OverView;
  ListView trailersListView;
  String mYoutubeKey;
  private reviewsCallBack revCallBack;
  private Uri mUri;
  private ShareActionProvider mShareActionProvider;

  //Single DetailViewFragment, receives all info in a bundle from the Main Activity or Detail activity, and acts accordingly

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    setHasOptionsMenu(true);

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


    mTrailerAdapter = new MovieTrailersAdapter(getActivity(), new ArrayList<SingleTrailerResult>());

    trailersListView = (ListView) rootview.findViewById(R.id.trailers_listview);

    trailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        // Handles the youtube link visualizion on trailer click, checks if there's an activity
        //That can handle it, otherwise, launches in browser
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

    Button reviewsButton = (Button) rootview.findViewById(R.id.reviews_button);

    reviewsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        //handles the reviews fragment main activity or detail activity callBack

        String[] args = {Title.getText().toString()};

        ContentResolver resolver = getActivity().getContentResolver();

        Cursor cur = resolver.query(MovieContentProvider.Local_Movies.CONTENT_URI, null,
            MovieDatabaseContract.OriginalTitle + "=?", args, null);

        if (cur.moveToFirst()) {

          String MovieReviewsId = String.valueOf(cur.getInt(1));

          revCallBack.onReviewsButtonClicked(MovieReviewsId);
        }

        cur.close();
      }
    });

    return rootview;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // Inflate the menu; this adds items to the action bar if it is present.
    inflater.inflate(R.menu.movie_detail_menu, menu);

    // Retrieve the share menu item
    MenuItem menuItem = menu.findItem(R.id.menu_item_share);

    // Get the provider and hold onto it to set/change the share intent.
    mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

    if (mYoutubeKey != null) {
      mShareActionProvider.setShareIntent(createMovieIntent());
    }


  }

  private Intent createMovieIntent() {

    // handles the share intent content for the shareactionprovider
    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + mYoutubeKey);
    sendIntent.setType("text/plain");

    return sendIntent;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    getLoaderManager().initLoader(DETAIL_LOADER, null, this);
    super.onActivityCreated(savedInstanceState);
  }

  public void getTrailers(int id) {

    //gets our trailers by using its ID and feeds the adapter with the trailer name

    MovieService.FetchMovieInterface MovieInterface = RetrofitServiceFactory.Factory().create(MovieService.FetchMovieInterface.class);

    callMovies = MovieInterface.getMovieTrailers(id, getString(R.string.api_key));

    callMovies.enqueue(new Callback<MovieTrailersForId>() {
      @Override
      public void onResponse(Call<MovieTrailersForId> call, Response<MovieTrailersForId> response) {

        List<SingleTrailerResult> singleTrailerList = response.body().getResults();

        for (SingleTrailerResult result : singleTrailerList) {

          mTrailerAdapter.add(result);
        }
        try {
          mYoutubeKey = singleTrailerList.get(0).getKey();
          if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createMovieIntent());
          }
        } catch (Exception e) {
        }
      }

      @Override
      public void onFailure(Call<MovieTrailersForId> call, Throwable t) {

      }
    });
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    if (null != mUri) {
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

    if (data != null && data.moveToFirst()) {
      Picasso.with(getContext()).load(Imagebaseurl + data.getString(2)).into(PosterImageView);
      Title.setText(data.getString(3));
      OverView.setText(data.getString(4));
      releaseDate.setText(data.getString(5));
      UserRating.setText(String.valueOf(data.getString(6)));

      getTrailers(data.getInt(1));

      trailersListView.setAdapter(mTrailerAdapter);
    }


  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {

  }

  @Override
  public void onDetach() {
    super.onDetach();
    revCallBack = null;
  }

  // we check if the activities launching this fragment implement our interface to handle the reviews fragment,
  // otherwise, launches an exception
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      revCallBack =
          (reviewsCallBack) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString()
          + " must implement reviewsCallback");
    }
  }

  // The reviews interface our activities have to implement and its method to implement
  public interface reviewsCallBack {

    public void onReviewsButtonClicked(String id);

  }
}