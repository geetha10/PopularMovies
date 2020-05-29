package com.geetha.popularmoviess1.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTrailersAPIResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieTrailers> MovieTrailers = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieTrailers> getResults() {
        return MovieTrailers;
    }

    public void setResults(List<MovieTrailers> MovieTrailers) {
        this.MovieTrailers = MovieTrailers;
    }

}

