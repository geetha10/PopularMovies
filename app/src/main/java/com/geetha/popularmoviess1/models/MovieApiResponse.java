package com.geetha.popularmoviess1.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class MovieApiResponse {

    @SerializedName("results")
    @Expose
    private List<Movie> movies = null;

    public List<Movie> getMovies() {
        return movies;
    }

    public String getMovieTrailer(){ return "Trailers";}

    public String getUserReviews(){return  "User Reviews";}

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
