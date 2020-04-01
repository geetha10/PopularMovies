package com.geetha.popularmoviess1.utilities;

import android.util.Log;

import com.geetha.popularmoviess1.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static String TAG="Movie Object";

    public static List<Movie> parseJsonData(JSONObject json) {
        List<Movie> movies=new ArrayList<> ();
        try {
            JSONArray moviesArray = json.getJSONArray ("results");
            for (int i = 0; i < moviesArray.length (); i++) {
                JSONObject movieDetails=moviesArray.getJSONObject (i);
                Movie movie=new Movie ();
                movie.setPosterPath (movieDetails.getString ("poster_path"));
                movie.setUserRating (movieDetails.getString ("vote_average"));
                movie.setReleaseDate (movieDetails.getString ("release_date"));
                movie.setSynopsis (movieDetails.getString ("overview"));
                movie.setTitle (movieDetails.getString ("title"));
                movies.add (movie);
                Log.d (TAG, "parseJsonData: "+movie);
            }

        }
        catch(JSONException e){
                e.printStackTrace ();
            }
        return movies;
        }

}
