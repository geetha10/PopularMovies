package com.geetha.popularmoviess1.utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String API_BASE_URL="http://api.themoviedb.org/3/movie/";
    public static final String GET_POPULAR_MOVIES = "POPULAR_MOVIES";
    public static final String GET_TOP_RATED_MOVIES = "TOP_RATED_MOVIES";
    private static final String POPULAR_MOVIES="popular";
    private static final String TOP_RATED="top_rated";
    private  final static String QUERY_PARAM = "api_key";
    //Deleted the key as per guidelines
    private final static String API_KEY="";

    public static URL buildUrl(String query) {

        String query_uri = GET_POPULAR_MOVIES.equals (query)
                ? API_BASE_URL + POPULAR_MOVIES
                : API_BASE_URL + TOP_RATED;

        Uri builtUri = Uri.parse(query_uri).buildUpon()
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static JSONObject getPopularMovies(URL url) throws IOException {

        HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection ();
        try{
            InputStream in=urlConnection.getInputStream ();
            Scanner scanner=new Scanner (in);
            StringBuffer sb = new StringBuffer ();
            while(scanner.hasNext ()) {
                String response=scanner.next ();
                sb.append (response+" ");
            }
            return new JSONObject (sb.toString ());
        } catch (JSONException e) {
            e.printStackTrace ();
        } finally {
            urlConnection.disconnect ();
        }
        return null;
    }


}
