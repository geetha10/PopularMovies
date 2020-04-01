package com.geetha.popularmoviess1;

import android.os.AsyncTask;
import android.util.Log;

import com.geetha.popularmoviess1.utilities.JsonUtils;
import com.geetha.popularmoviess1.utilities.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.List;

public class FetchMovieTask extends AsyncTask<Void, Void,List<Movie>> {

    private final static String TAG="FetchMovieTask";
    private MoviesAPICallback callBack;
    private String query;

    public FetchMovieTask(MoviesAPICallback callBack, String query) {
        this.callBack = callBack;
        this.query=query;
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {

        URL url= NetworkUtils.buildUrl (query);
        try {
            JSONObject movieAPIResponse = NetworkUtils.getPopularMovies (url);
            List<Movie> movies= JsonUtils.parseJsonData (movieAPIResponse);

            return movies;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> s) {
        super.onPostExecute (s);
        Log.d (TAG, "onPostExecute: "+s);
        callBack.setData (s);
    }
}
