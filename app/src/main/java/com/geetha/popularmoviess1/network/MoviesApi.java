package com.geetha.popularmoviess1.network;

import com.geetha.popularmoviess1.models.GetTrailersAPIResponse;
import com.geetha.popularmoviess1.models.MovieApiResponse;
import com.geetha.popularmoviess1.models.ReviewApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {

    @GET("popular")
    Call<MovieApiResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MovieApiResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("{id}/videos")
    Call<GetTrailersAPIResponse> getMovieTrailers(@Path ("id") Integer id, @Query ("api_key") String apiKey);

    @GET("{id}/reviews")
    Call<ReviewApiResponse> getUserReviews(@Path ("id") Integer id,@Query ("api_key") String apiKey);

}
