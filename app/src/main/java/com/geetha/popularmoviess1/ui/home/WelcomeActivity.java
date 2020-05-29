package com.geetha.popularmoviess1.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.geetha.popularmoviess1.R;
import com.geetha.popularmoviess1.database.AppConverters;
import com.geetha.popularmoviess1.database.AppDatabase;
import com.geetha.popularmoviess1.database.FavoriteMoviesEntity;
import com.geetha.popularmoviess1.database.PopularMoviesEntity;
import com.geetha.popularmoviess1.database.TopRatedMoviesEntity;
import com.geetha.popularmoviess1.models.Movie;
import com.geetha.popularmoviess1.network.RetrofitInstance;
import com.geetha.popularmoviess1.ui.DetailActivity;
import org.parceler.Parcels;
import java.util.ArrayList;
import java.util.List;


public class WelcomeActivity extends AppCompatActivity implements MoviesAdapter.MovieItemClickCallback {

    private static final String TAG = "WelcomeActivity";
    Toolbar mTitleToolbar;
    RecyclerView mMoviesListRV;
    List<Movie> movieList = new ArrayList<> ();
    MoviesAdapter moviesAdapter;
    WelcomeActivityViewModel viewModel;

    Observer<List<Movie>> moviesChangeObserver = new Observer<List<Movie>> () {
        @Override
        public void onChanged(List<Movie> movies) {
            onMoviesFetched (movies);
        }
    };

    Observer<List<FavoriteMoviesEntity>> favoriteMoviesObserver = new Observer<List<FavoriteMoviesEntity>> () {
        @Override
        public void onChanged(List<FavoriteMoviesEntity> f) {
            viewModel.updateMoviesOnUi (AppConverters.getMoviesFromFavoriteMoviesEntity(f));
        }
    };
    Observer<List<TopRatedMoviesEntity>> topRatedMoviesObserver = new Observer<List<TopRatedMoviesEntity>> () {
        @Override
        public void onChanged(List<TopRatedMoviesEntity> f) {
            viewModel.updateMoviesOnUi (AppConverters.getMoviesFromTopRatedMoviesEntity (f));
        }
    };

    Observer<List<PopularMoviesEntity>> popularMoviesMoviesObserver = new Observer<List<PopularMoviesEntity>> () {
        @Override
        public void onChanged(List<PopularMoviesEntity> f) {
            viewModel.updateMoviesOnUi (AppConverters.getMoviesFromPopularMoviesEntity (f));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_welcome);
        mTitleToolbar=findViewById (R.id.toolbar_title);
        setSupportActionBar (mTitleToolbar);
        getSupportActionBar ().setTitle (getString (R.string.popular_movies));
        mMoviesListRV = findViewById (R.id.rv_movieslist);

        moviesAdapter = new MoviesAdapter (this, movieList, this);
        mMoviesListRV.setAdapter (moviesAdapter);

        WelcomeViewModelFactory factory = new WelcomeViewModelFactory (RetrofitInstance.get (), AppDatabase.getInstance (this));
        viewModel = ViewModelProviders.of (this, factory).get (WelcomeActivityViewModel.class);

        viewModel.getPopularMoviesAsync (isOnline ());
        viewModel.moviesLiveData.observe (this, moviesChangeObserver);
        viewModel.popularMoviesLiveData.observe (this,popularMoviesMoviesObserver);
    }

    @Override
    public void onMovieItemClicked(Movie movie) {
        Intent intent = new Intent (this, DetailActivity.class);
        intent.putExtra ("EXTRA_MOVIE", Parcels.wrap (movie));
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
                viewModel.getPopularMoviesAsync (isOnline ());
                viewModel.popularMoviesLiveData.observe (this,popularMoviesMoviesObserver);
                getSupportActionBar ().setTitle (getString (R.string.popular_movies));
                return true;
            case R.id.top_rated:
                viewModel.getTopRatedMoviesAsync (isOnline ());
                viewModel.topRatedMoviesLiveData.observe (this,topRatedMoviesObserver);
                getSupportActionBar ().setTitle (getString (R.string.top_rated_movies));
                return true;
            case R.id.fav:
                viewModel.getFavoriteMoviesAsync ();
                viewModel.favoritesLiveData.observe (this, favoriteMoviesObserver);
                getSupportActionBar ().setTitle (getString (R.string.favorite_movies));
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo ();
        return netInfo != null && netInfo.isConnectedOrConnecting ();
    }

    public void onMoviesFetched(List<Movie> movies) {
        movieList.clear ();
        movieList.addAll (movies);
        moviesAdapter.notifyDataSetChanged ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        viewModel.moviesLiveData.removeObservers (this);
        viewModel.favoritesLiveData.removeObservers (this);
        viewModel.popularMoviesLiveData.removeObservers (this);
        viewModel.topRatedMoviesLiveData.removeObservers (this);
    }
}
