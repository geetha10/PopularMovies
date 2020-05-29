package com.geetha.popularmoviess1.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.geetha.popularmoviess1.models.MovieTrailers;
import com.geetha.popularmoviess1.models.Review;

import java.util.List;

@Dao
public interface MoviesDBApi {

    @Query("SELECT * FROM Popular_Movies")
    LiveData <List <PopularMoviesEntity>> loadPopularMovies();

    @Query("SELECT * FROM Top_Rated_Movies")
    LiveData <List <TopRatedMoviesEntity>> loadTopRatedMovies();

    @Query("SELECT * FROM Favorite_Movies")
    LiveData <List <FavoriteMoviesEntity>> loadFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updatePopularMovies(List <PopularMoviesEntity> popularMoviesEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateTopRatedMovies(List <TopRatedMoviesEntity> topRatedMoviesEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateFavoriteMovies(FavoriteMoviesEntity favoriteMoviesEntities);

    @Query("SELECT * FROM Favorite_Movies WHERE id=:id")
    FavoriteMoviesEntity loadFavById(int id);

    @Query("DELETE FROM Favorite_Movies WHERE id=:id")
    void DeleteFromFavorites(int id);

    //Detail Avtivity

    @Query("SELECT * FROM MovieTrailers where id=:id")
    LiveData <List <TrailersEntity>> getMovieTrailers(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateMovieTrailers(List <TrailersEntity> trailers);

    @Query("SELECT * FROM UserReviews where id=:id")
    LiveData <List <UserReviewsEntity>> getUserReviews(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateUserReviews(List <UserReviewsEntity> trailers);
}
