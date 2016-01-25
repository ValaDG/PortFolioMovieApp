package com.degiorgi.valerio.portfoliomovieapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Valerio on 23/01/2016.
 */
public class MovieFragment extends android.support.v4.app.Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.movie_fragment_layout, container, false);

        String[] data = {"https://i.imgur.com/Ua5sT1K.jpg", "http://i.imgur.com/w99mFqa.jpg"};

        List<String> ImageUrls = new ArrayList<String>(Arrays.asList(data));


        GridView gridView =(GridView) rootView.findViewById(R.id.gridview_list);


        MoviePosterAdapter mPosterAdapter = new MoviePosterAdapter(getActivity(), ImageUrls);

        gridView.setAdapter(mPosterAdapter);


        return rootView;
    }


}
