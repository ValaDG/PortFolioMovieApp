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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.movie_fragment_layout, container, false);

        FetchMovieTask task = new FetchMovieTask();
        task.execute();

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview_list);

        gridview.setAdapter(mAdapater);


        return rootView;
    }


    public ArrayList<MovieObject> TurnJsonIntoMovies(String MovieJsonString) throws JSONException {

        ArrayList<MovieObject> MoviesList = new ArrayList<>();


        JSONObject obj = new JSONObject(MovieJsonString);


        JSONArray x = obj.getJSONArray("results");


        String url = x.getJSONObject(0).getString("poster_path");
        int id = x.getJSONObject(0).getInt("id");

        MovieObject movie = new MovieObject(url, id);

        MoviesList.add(movie);

        return MoviesList;
    }


    public class FetchMovieTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String MovieJsonString = null;

            try {
                URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=MyOwnApiKey");

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

            try {
                mAdapater = new MoviePosterAdapter(getContext(), TurnJsonIntoMovies(s));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
}
}



