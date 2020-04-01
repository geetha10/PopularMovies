package com.geetha.popularmoviess1.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.geetha.popularmoviess1.FetchMovieTask;
import com.geetha.popularmoviess1.Movie;
import com.geetha.popularmoviess1.MovieItem;
import com.geetha.popularmoviess1.MoviesAPICallback;
import com.geetha.popularmoviess1.MoviesAdapter;
import com.geetha.popularmoviess1.R;

import java.util.ArrayList;
import java.util.List;

import static com.geetha.popularmoviess1.utilities.NetworkUtils.GET_POPULAR_MOVIES;
import static com.geetha.popularmoviess1.utilities.NetworkUtils.GET_TOP_RATED_MOVIES;

public class WelcomeActivity extends AppCompatActivity implements MoviesAPICallback, MovieItem {

    private static final String TAG = "WelcomeActivity";
    RecyclerView mMoviesListRV;
    List<Movie> movieList = new ArrayList<> ();
    MoviesAdapter moviesAdapter;
    AsyncTask<Void, Void, List<Movie>> fetchMovieAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_welcome);
        mMoviesListRV = findViewById (R.id.rv_movieslist);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager (3,
                StaggeredGridLayoutManager.VERTICAL);
        mMoviesListRV.setLayoutManager (layoutManager);
        moviesAdapter = new MoviesAdapter (this, movieList, this);
        mMoviesListRV.setAdapter (moviesAdapter);
        if(isOnline ()){
            fetchMovieAsyncTask = new FetchMovieTask (this, GET_POPULAR_MOVIES).execute ();
        }
        else {
            Toast.makeText (this,"No network available", Toast.LENGTH_LONG).show ();
        }

    }

    @Override
    public void setData(List<Movie> resultFromAPI) {
        Log.d (TAG, "setData: size: " + resultFromAPI.size ());
        movieList.clear ();
        movieList.addAll (resultFromAPI);
        moviesAdapter.notifyDataSetChanged ();
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        Intent intent = new Intent (this, DetailActivity.class);
        intent.putExtra ("MOVIE_TITLE", movie.title);
        intent.putExtra ("POSTER_PATH", movie.posterPath);
        intent.putExtra ("USER_RATING", movie.userRating);
        intent.putExtra ("RELEASE_DATE", movie.releaseDate);
        intent.putExtra ("PLOT_SYNOPSIS", movie.plotSynopsis);
        startActivity (intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.popular_movies:
                fetchMovieAsyncTask = new FetchMovieTask (this, GET_POPULAR_MOVIES).execute ();
                return true;
            case R.id.top_rated:
                fetchMovieAsyncTask = new FetchMovieTask (this, GET_TOP_RATED_MOVIES).execute ();
                return true;
            default:
                return super.onOptionsItemSelected (item);

        }
    }

    @Override
    protected void onStop() {
        super.onStop ();
        if (fetchMovieAsyncTask != null) {
            fetchMovieAsyncTask.cancel (true);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
