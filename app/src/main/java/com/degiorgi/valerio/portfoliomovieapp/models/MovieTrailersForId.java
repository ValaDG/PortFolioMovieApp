package com.degiorgi.valerio.portfoliomovieapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valerio on 29/02/2016.
 */

public class MovieTrailersForId {

  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("results")
  @Expose
  private List<SingleTrailerResult> results = new ArrayList<SingleTrailerResult>();

  /**
   * @return The id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id The id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return The results
   */
  public List<SingleTrailerResult> getResults() {
    return results;
  }

  /**
   * @param results The results
   */
  public void setResults(List<SingleTrailerResult> results) {
    this.results = results;
  }

}