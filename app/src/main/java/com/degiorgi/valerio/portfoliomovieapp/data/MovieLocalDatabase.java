package com.degiorgi.valerio.portfoliomovieapp.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Valerio on 25/02/2016.
 */

@Database(version = MovieLocalDatabase.VERSION)
public final class MovieLocalDatabase {

    private MovieLocalDatabase(){}

    public static final int VERSION = 1;

    @Table(MovieDatabaseContract.class) public static final String LOCAL_MOVIES = "Local_Movies";

    @Table(MovieDatabaseContract.class) public static final String FAVOURITE_MOVIES ="Favourite_Movies";

}
