package com.degiorgi.valerio.portfoliomovieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.degiorgi.valerio.portfoliomovieapp.R;
import com.degiorgi.valerio.portfoliomovieapp.models.SingleTrailerResult;

import java.util.ArrayList;

/**
 * Created by Valerio on 29/02/2016.
 * Handles entries for the Trailers Listview in the detail view
 */

public class MovieTrailersAdapter extends ArrayAdapter<SingleTrailerResult> {

    private Context mContext;

    public MovieTrailersAdapter(Context context, ArrayList<SingleTrailerResult> objects) {
        super(context, R.layout.single_list_item_textview, objects);

        mContext = context;

    }


    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        SingleTrailerResult object = getItem(position);


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_list_item_textview, null);

        }

        TextView text = (TextView) convertView.findViewById(R.id.single_item_textview_id);
        text.setText(object.getName());

        return convertView;
    }
}