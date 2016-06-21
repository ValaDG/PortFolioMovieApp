package com.degiorgi.valerio.portfoliomovieapp.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Valerio on 25/02/2016.
 */
@ContentProvider(authority = MovieContentProvider.AUTHORITY, database = MovieLocalDatabase.class)
public final class MovieContentProvider {

  public static final String AUTHORITY = "com.degiorgi.valerio.portfoliomovieapp.data.MovieContentProvider";

  static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  private static Uri buildUri(String... paths) {

    Uri.Builder builder = BASE_CONTENT_URI.buildUpon();

    for (String Path : paths) {
      builder.appendPath(Path);

    }
    return builder.build();
  }

  interface Path {

    String Local_Movies = "Local_Movies";
    String Favourite_Movies = "Favourite_Movies";

  }

  @TableEndpoint(table = MovieLocalDatabase.LOCAL_MOVIES)
  public static class Local_Movies {

    @ContentUri(
        path = Path.Local_Movies,
        type = "vnd.android.cursor.dir/Local_Movies"
    )

    public static final Uri CONTENT_URI = buildUri(Path.Local_Movies);


    @InexactContentUri(

        name = "MOVIE_ID",
        path = Path.Local_Movies + "/#",
        type = "vnd.android.cursor.item/Local_Movie",
        whereColumn = MovieDatabaseContract.MovieId,
        pathSegment = 1)

    public static Uri withId(int id) {

      return buildUri(Path.Local_Movies, String.valueOf(id));
    }


  }


  @TableEndpoint(table = MovieLocalDatabase.FAVOURITE_MOVIES)
  public static class Favourite_Movies {

    @ContentUri(
        path = Path.Favourite_Movies,
        type = "vnd.android.cursor.dir/Favourite_Movies"

    )

    public static final Uri CONTENT_URI = buildUri(Path.Favourite_Movies);

    @InexactContentUri(

        name = "FAVOURITE_ID",
        path = Path.Favourite_Movies + "/#",
        type = "vnd.android.cursor.item/Favourite_Movie",
        whereColumn = MovieDatabaseContract._ID,
        pathSegment = 1)

    public static Uri withId(long id) {

      return buildUri(Path.Favourite_Movies, String.valueOf(id));
    }
  }

}