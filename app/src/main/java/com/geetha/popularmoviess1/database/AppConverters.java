package com.geetha.popularmoviess1.database;

import com.geetha.popularmoviess1.models.Movie;
import com.geetha.popularmoviess1.models.MovieTrailers;
import com.geetha.popularmoviess1.models.Review;

import java.util.ArrayList;
import java.util.List;

public class AppConverters {

    public static List<TopRatedMoviesEntity> getTopRatedMovieFromMovieApi(List<Movie> moviesList) {
        List<TopRatedMoviesEntity> entityList = new ArrayList<> ();
        if (moviesList != null) {
            for (Movie movie : moviesList) {
                TopRatedMoviesEntity entity = new TopRatedMoviesEntity ();
                entity.setPopularity (movie.getPopularity ());
                entity.setVideo (movie.getVideo ());
                entity.setId (movie.getId ());
                entity.setPosterPath (movie.getPosterPath ());
                entity.setOriginalLanguage (movie.getOriginalLanguage ());
                entity.setOriginalTitle (movie.getOriginalTitle ());
                entity.setTitle (movie.getTitle ());
                entity.setVoteAverage (movie.getVoteAverage ());
                entity.setOverview (movie.getOverview ());
                entity.setReleaseDate (movie.getReleaseDate ());
                entityList.add (entity);
            }
        }
        return entityList;
    }

    public static List<Movie> getMoviesFromTopRatedMoviesEntity(List<TopRatedMoviesEntity> entitiesList) {
        List<Movie> movieList = new ArrayList<> ();
        if (entitiesList != null) {
            for (TopRatedMoviesEntity entity : entitiesList) {
                Movie m = new Movie ();
                m.setId (entity.getId ());
                m.setVideo (entity.getVideo ());
                m.setPopularity (entity.getPopularity ());
                m.setPosterPath (entity.getPosterPath ());
                m.setOriginalLanguage (entity.getOriginalLanguage ());
                m.setOriginalTitle (entity.getOriginalTitle ());
                m.setTitle (entity.getTitle ());
                m.setVoteAverage (entity.getVoteAverage ());
                m.setOverview (entity.getOverview ());
                m.setReleaseDate (entity.getReleaseDate ());
                movieList.add (m);
            }
        }
        return movieList;
    }

    public static List<PopularMoviesEntity> getPopularMovieFromMovieApi(List<Movie> moviesList) {
        List<PopularMoviesEntity> entityList = new ArrayList<> ();
        if (moviesList != null) {
            for (Movie movie : moviesList) {
                PopularMoviesEntity entity = new PopularMoviesEntity ();
                entity.setPopularity (movie.getPopularity ());
                entity.setVideo (movie.getVideo ());
                entity.setId (movie.getId ());
                entity.setPosterPath (movie.getPosterPath ());
                entity.setOriginalLanguage (movie.getOriginalLanguage ());
                entity.setOriginalTitle (movie.getOriginalTitle ());
                entity.setTitle (movie.getTitle ());
                entity.setVoteAverage (movie.getVoteAverage ());
                entity.setOverview (movie.getOverview ());
                entity.setReleaseDate (movie.getReleaseDate ());
                entityList.add (entity);
            }
        }
        return entityList;
    }

    public static List<Movie> getMoviesFromPopularMoviesEntity(List<PopularMoviesEntity> entitiesList) {
        List<Movie> movieList = new ArrayList<> ();
        if (entitiesList != null) {
            for (PopularMoviesEntity entity : entitiesList) {
                Movie m = new Movie ();
                m.setId (entity.getId ());
                m.setVideo (entity.getVideo ());
                m.setPopularity (entity.getPopularity ());
                m.setPosterPath (entity.getPosterPath ());
                m.setOriginalLanguage (entity.getOriginalLanguage ());
                m.setOriginalTitle (entity.getOriginalTitle ());
                m.setTitle (entity.getTitle ());
                m.setVoteAverage (entity.getVoteAverage ());
                m.setOverview (entity.getOverview ());
                m.setReleaseDate (entity.getReleaseDate ());
                movieList.add (m);
            }
        }
        return movieList;
    }

    public static List<FavoriteMoviesEntity> getFavoriteMoviesFromMovieApi(List<Movie> moviesList) {
        List<FavoriteMoviesEntity> entityList = new ArrayList<> ();
        if (moviesList != null) {
            for (Movie movie : moviesList) {
                FavoriteMoviesEntity entity = AppConverters.getFavoriteMovie (movie);
                entityList.add (entity);
            }
        }
        return entityList;
    }

    public static FavoriteMoviesEntity getFavoriteMovie(Movie movie) {
        FavoriteMoviesEntity entity = new FavoriteMoviesEntity ();
        entity.setFavorite (true);
        entity.setPopularity (movie.getPopularity ());
        entity.setVideo (movie.getVideo ());
        entity.setId (movie.getId ());
        entity.setPosterPath (movie.getPosterPath ());
        entity.setOriginalLanguage (movie.getOriginalLanguage ());
        entity.setOriginalTitle (movie.getOriginalTitle ());
        entity.setTitle (movie.getTitle ());
        entity.setVoteAverage (movie.getVoteAverage ());
        entity.setOverview (movie.getOverview ());
        entity.setReleaseDate (movie.getReleaseDate ());

        return entity;
    }

    public static List<Movie> getMoviesFromFavoriteMoviesEntity(List<FavoriteMoviesEntity> entitiesList) {
        List<Movie> movieList = new ArrayList<> ();
        if (entitiesList != null) {
            for (FavoriteMoviesEntity entity : entitiesList) {
                Movie m = new Movie ();
                m.setId (entity.getId ());
                m.setVideo (entity.getVideo ());
                m.setPopularity (entity.getPopularity ());
                m.setPosterPath (entity.getPosterPath ());
                m.setOriginalLanguage (entity.getOriginalLanguage ());
                m.setOriginalTitle (entity.getOriginalTitle ());
                m.setTitle (entity.getTitle ());
                m.setVoteAverage (entity.getVoteAverage ());
                m.setOverview (entity.getOverview ());
                m.setReleaseDate (entity.getReleaseDate ());
                movieList.add (m);
            }
        }
        return movieList;
    }

    public static List<TrailersEntity> getTrailersEntityFromMovieTrailer(List<MovieTrailers> mTrailers, int id){
        List<TrailersEntity> trailersList= new ArrayList <> ();
        if(mTrailers != null){
            for(MovieTrailers movieTrailer : mTrailers){
                TrailersEntity trailer= new TrailersEntity ();
                trailer.setMovieId (id);
                trailer.setId (movieTrailer.getId ());
                trailer.setIso6391 (movieTrailer.getIso6391 ());
                trailer.setIso31661 (movieTrailer.getIso31661 ());
                trailer.setKey (movieTrailer.getKey ());
                trailer.setName (movieTrailer.getName ());
                trailer.setSite (movieTrailer.getSite ());
                trailer.setSize (movieTrailer.getSize ());
                trailer.setType (movieTrailer.getType ());
                trailersList.add (trailer);
            }
        }
        return trailersList;
    }

    public static List<MovieTrailers> getMovieTrailersFromTrailersEntity(List<TrailersEntity> trailersEntityList){

        List<MovieTrailers> trailersList= new ArrayList <> ();
        if(trailersEntityList != null){
            for(TrailersEntity movieTrailer : trailersEntityList){
                MovieTrailers trailer= new MovieTrailers ();
                //trailer.setMovieId (id);
                trailer.setId (movieTrailer.getId ());
                trailer.setIso6391 (movieTrailer.getIso6391 ());
                trailer.setIso31661 (movieTrailer.getIso31661 ());
                trailer.setKey (movieTrailer.getKey ());
                trailer.setName (movieTrailer.getName ());
                trailer.setSite (movieTrailer.getSite ());
                trailer.setSize (movieTrailer.getSize ());
                trailer.setType (movieTrailer.getType ());
                trailersList.add (trailer);
            }
        }
        return trailersList;
    }


    /*private int movieId;

    private String id;
    private String author;
    private String content;
    private String url;*/

    public static List<UserReviewsEntity> getUserReviewsFromReview(List<Review> reviews, int id){
        List<UserReviewsEntity>  userReviews=new ArrayList <> ();
        for(Review r:reviews){
            UserReviewsEntity u=new UserReviewsEntity ();
            u.setMovieId (id);
            u.setId (r.getId ());
            u.setAuthor (r.getAuthor ());
            u.setContent (r.getContent ());
            u.setUrl (r.getUrl ());
            userReviews.add (u);
        }
        return userReviews;
    }

    public static List<Review> getReviewsFromUserReviewEntity(List<UserReviewsEntity> reviews){

        List<Review>  userReviews=new ArrayList <> ();
        for(UserReviewsEntity r:reviews){
            Review u=new Review ();
            u.setId (r.getId ());
            u.setAuthor (r.getAuthor ());
            u.setContent (r.getContent ());
            u.setUrl (r.getUrl ());
            userReviews.add (u);
        }
        return userReviews;
    }
}
