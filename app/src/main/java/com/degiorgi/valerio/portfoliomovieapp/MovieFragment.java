package com.degiorgi.valerio.portfoliomovieapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Valerio on 23/01/2016.
 */
public class MovieFragment extends android.support.v4.app.Fragment {

    MoviePosterAdapter mAdapater;
    ArrayList<MovieObject> Movies;

    private void UpdateMovies()
    {
        FetchMovieTask task = new FetchMovieTask();
        task.execute();    }

    @Override
    public void onStart() {
        super.onStart();

        UpdateMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.movie_fragment_layout, container, false);

        mAdapater = new MoviePosterAdapter(getContext(), new ArrayList<MovieObject>());


        GridView gridview = (GridView) rootView.findViewById(R.id.gridview_list);

        gridview.setAdapter(mAdapater);


        return rootView;
    }


    public ArrayList<MovieObject> TurnJsonIntoMovies(String MovieJsonString) throws JSONException {

        ArrayList<MovieObject> MoviesList = new ArrayList<MovieObject>();


        JSONObject obj = new JSONObject(MovieJsonString);


        JSONArray x = obj.getJSONArray("results");

for(int i = 0; i<x.length(); i++) {
    String url = x.getJSONObject(i).getString("poster_path");
    int id = x.getJSONObject(i).getInt("id");

    MovieObject movie = new MovieObject(url, id);

    MoviesList.add(movie);
}

        return MoviesList;
    }


    public class FetchMovieTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String MovieJsonString = null;

            try {
                URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=241141bc665e9b2d0fb9ac4759497786");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    MovieJsonString = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    MovieJsonString = null;
                }
                MovieJsonString = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                MovieJsonString = null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            return MovieJsonString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
if(s!= null) {
    mAdapater.clear();
    try {
        Movies = TurnJsonIntoMovies(s);
    } catch (JSONException e) {
        e.printStackTrace();
    }
    for (MovieObject movie : Movies) {
        mAdapater.add(movie);

    }
}

        }
}
}



