package com.geetha.popularmoviess1.ui;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.geetha.popularmoviess1.Constants;
import com.geetha.popularmoviess1.database.AppConverters;
import com.geetha.popularmoviess1.database.AppDatabase;
import com.geetha.popularmoviess1.database.AppExecutors;
import com.geetha.popularmoviess1.database.FavoriteMoviesEntity;
import com.geetha.popularmoviess1.database.TrailersEntity;
import com.geetha.popularmoviess1.database.UserReviewsEntity;
import com.geetha.popularmoviess1.models.GetTrailersAPIResponse;
import com.geetha.popularmoviess1.models.Movie;
import com.geetha.popularmoviess1.models.MovieTrailers;
import com.geetha.popularmoviess1.models.Review;
import com.geetha.popularmoviess1.models.ReviewApiResponse;
import com.geetha.popularmoviess1.network.MoviesApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsActivityViewModel extends ViewModel {

    private static final String TAG = "DetailsRepo";
    private final Movie movie;
    private MoviesApi api;
    private AppDatabase database;

    private List <Review> mUserReviews = new ArrayList <> ();
    private List <MovieTrailers> mMovieTrailers = new ArrayList <> ();

    /*private MutableLiveData <List <MovieTrailers>> _trailersLiveData = new MutableLiveData <> ();
    LiveData <List <MovieTrailers>> trailersLiveData = _trailersLiveData;*/

    private MutableLiveData <List <TrailersEntity>> _trailersEntityLiveData = new MutableLiveData <List <TrailersEntity>> ();
    LiveData <List <TrailersEntity>> trailersEntityLiveData = _trailersEntityLiveData;

    private MutableLiveData <List <Review>> _reviewsLiveData = new MutableLiveData <> ();
    LiveData <List <Review>> reviewsLiveData = _reviewsLiveData;

    private MutableLiveData <List <UserReviewsEntity>> _userReviewsEntityLiveData = new MutableLiveData <> ();
    LiveData <List <UserReviewsEntity>> userReviewsEntityLiveData = _userReviewsEntityLiveData;

    public DetailsActivityViewModel(MoviesApi api, AppDatabase database, Movie movie) {
        this.api = api;
        this.database = database;
        this.movie = movie;
    }

    void getTrailersAsync(boolean isOnline) {
        if (isOnline) {
            api.getMovieTrailers (movie.getId (), Constants.MOVIE_DB_API_KEY)
                    .enqueue (new Callback <GetTrailersAPIResponse> () {
                        @Override
                        public void onResponse(Call <GetTrailersAPIResponse> call, Response <GetTrailersAPIResponse> response) {
                            List <MovieTrailers> results = response.body ().getResults ();
                            _trailersEntityLiveData.setValue (AppConverters.getTrailersEntityFromMovieTrailer (results, movie.getId ()));
                            AppExecutors.getInstance ().diskIO ().execute (new Runnable () {
                                @Override
                                public void run() {
                                    database.moviesDBApi ().updateMovieTrailers (AppConverters.getTrailersEntityFromMovieTrailer (mMovieTrailers, movie.getId ()));
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call <GetTrailersAPIResponse> call, Throwable t) {
                            Log.d (TAG, "GetTrailers onFailure: " + t.getMessage () + t.getCause ());
                        }
                    });
        } else {
            trailersEntityLiveData = database.moviesDBApi ().getMovieTrailers (movie.getId ());
        }
    }

    void getUserReviewsAsync(boolean isOnline) {
        if (isOnline) {
            api.getUserReviews (movie.getId (), Constants.MOVIE_DB_API_KEY).enqueue (new Callback <ReviewApiResponse> () {
                @Override
                public void onResponse(Call <ReviewApiResponse> call, Response <ReviewApiResponse> response) {
                    List <Review> results = response.body ().getReviews ();
                    _userReviewsEntityLiveData.setValue (AppConverters.getUserReviewsFromReview (results, movie.getId ()));
                    AppExecutors.getInstance ().diskIO ().execute (new Runnable () {
                        @Override
                        public void run() {
                            database.moviesDBApi ().updateUserReviews (AppConverters.getUserReviewsFromReview (mUserReviews, movie.getId ()));
                        }
                    });
                }

                @Override
                public void onFailure(Call <ReviewApiResponse> call, Throwable t) {

                }
            });
        } else {
            userReviewsEntityLiveData = database.moviesDBApi ().getUserReviews (movie.getId ());
        }
    }

    void updateFavorites() {
        final FavoriteMoviesEntity favEntity = AppConverters.getFavoriteMovie (movie);
        if (favEntity.isFavorite ())
            AppExecutors.getInstance ().diskIO ().execute (new Runnable () {
                @Override
                public void run() {
                    database.moviesDBApi ().updateFavoriteMovies (favEntity);
                    Log.d (TAG, "updateFavorites: Movie Added to Favorites");
                }
            });
    }

    void deleteFromFavorites(){
        AppExecutors.getInstance ().diskIO ().execute (new Runnable () {
            @Override
            public void run() {
                database.moviesDBApi ().DeleteFromFavorites (movie.getId ());
                Log.d (TAG, "updateFavorites: " + movie.getTitle () + " Movie Removed From Favorites");
            }
        });
    }

}
