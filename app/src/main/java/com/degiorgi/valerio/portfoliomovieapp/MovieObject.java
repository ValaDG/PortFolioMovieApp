package com.degiorgi.valerio.portfoliomovieapp;


/**
 * Created by Valerio on 30/01/2016.
 */
public class MovieObject {

    String PosterUrl;
    int id;
    String  originalTitle;
    String overview;
    String release;
    double userRating;




    public MovieObject (String Url, String title, String synopsis, String releaseDate, double rating,int id)
    {
        this.id = id;
        this.PosterUrl = Url;
        this.originalTitle = title;
        this.overview = synopsis;
        this.release = releaseDate;
        this.userRating = rating;

    }

}
