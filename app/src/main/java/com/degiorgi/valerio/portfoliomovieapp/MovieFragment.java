package com.degiorgi.valerio.portfoliomovieapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Valerio on 23/01/2016.
 */
public class MovieFragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.movie_fragment_layout, container, false);


        GridView gridView =(GridView) rootView.findViewById(R.id.gridview_list);



        return rootView;
    }


}
