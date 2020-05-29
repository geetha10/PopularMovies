package com.geetha.popularmoviess1.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.geetha.popularmoviess1.Constants;
import com.geetha.popularmoviess1.database.AppConverters;
import com.geetha.popularmoviess1.database.AppDatabase;
import com.geetha.popularmoviess1.database.AppExecutors;
import com.geetha.popularmoviess1.database.FavoriteMoviesEntity;
import com.geetha.popularmoviess1.database.PopularMoviesEntity;
import com.geetha.popularmoviess1.database.TopRatedMoviesEntity;
import com.geetha.popularmoviess1.models.Movie;
import com.geetha.popularmoviess1.models.MovieApiResponse;
import com.geetha.popularmoviess1.network.MoviesApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivityViewModel extends ViewModel {

    private static final String TAG = "Repository";

    private MoviesApi api;
    private AppDatabase database;
    private List<Movie> movies = new ArrayList<> ();

    private MutableLiveData<List<Movie>> mutableMoviesData = new MutableLiveData<> ();
    LiveData<List<Movie>> moviesLiveData = mutableMoviesData;

    private MutableLiveData<List<PopularMoviesEntity>> _popularMoviesLiveData = new MutableLiveData<> ();
    LiveData<List<PopularMoviesEntity>> popularMoviesLiveData = _popularMoviesLiveData;

    private MutableLiveData<List<TopRatedMoviesEntity>> _topRatedMoviesLiveData = new MutableLiveData<> ();
    LiveData<List<TopRatedMoviesEntity>> topRatedMoviesLiveData = _topRatedMoviesLiveData;

    private MutableLiveData<List<FavoriteMoviesEntity>> _favoritesLiveData = new MutableLiveData<> ();
    LiveData<List<FavoriteMoviesEntity>> favoritesLiveData = _favoritesLiveData;

    WelcomeActivityViewModel(MoviesApi api, AppDatabase database) {
        this.api = api;
        this.database = database;
    }

    void getTopRatedMoviesAsync(boolean isOnline) {
        if(isOnline){
            api.getTopRatedMovies (Constants.MOVIE_DB_API_KEY)
                    .enqueue (new Callback<MovieApiResponse> () {
                        @Override
                        public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                            updateMoviesOnUi (response.body ().getMovies ());
                            final List<TopRatedMoviesEntity> topRatedMovieFromMovieApi = AppConverters.getTopRatedMovieFromMovieApi (movies);
                            AppExecutors .getInstance ().diskIO ().execute (new Runnable () {
                                @Override
                                public void run() {
                                    database.moviesDBApi ().updateTopRatedMovies (topRatedMovieFromMovieApi);
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                            Log.d (TAG, "onFailure: "+t.getCause ()+" "+t.getMessage () );
                        }
                    });
        } else {
            topRatedMoviesLiveData = database.moviesDBApi ().loadTopRatedMovies ();
        }
    }

    void getPopularMoviesAsync(boolean isOnline) {
        if(isOnline){
            api.getPopularMovies (Constants.MOVIE_DB_API_KEY)
                    .enqueue (new Callback<MovieApiResponse> () {
                        @Override
                        public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                            updateMoviesOnUi (response.body ().getMovies ());
                            AppExecutors .getInstance ().diskIO ().execute (new Runnable () {
                                @Override
                                public void run() {
                                    database.moviesDBApi ().updatePopularMovies (AppConverters.getPopularMovieFromMovieApi (movies));
                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<MovieApiResponse> call, Throwable t) {

                        }
                    });
        } else {
            popularMoviesLiveData = database.moviesDBApi ().loadPopularMovies ();
        }
    }

    void getFavoriteMoviesAsync() {
        favoritesLiveData = database.moviesDBApi ().loadFavoriteMovies ();
    }

    void updateMoviesOnUi(List<Movie> m){
        movies.clear ();
        movies.addAll (m);
        mutableMoviesData.setValue (m);
    }
}
