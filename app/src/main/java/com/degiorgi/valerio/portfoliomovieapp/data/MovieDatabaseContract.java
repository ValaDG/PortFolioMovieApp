package com.degiorgi.valerio.portfoliomovieapp.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Valerio on 25/02/2016.
 */

public interface MovieDatabaseContract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement

    public static final String _ID = "_id";

    @DataType(DataType.Type.INTEGER) @NotNull
    public static final String MovieId ="MovieId";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String PosterUrl = "PosterUrl";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String OriginalTitle ="OriginalTitle";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String Overview ="Overview";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String ReleaseDate ="ReleaseDate";

    @DataType(DataType.Type.REAL) @NotNull
    public static final String UserRating ="Rating";


}
