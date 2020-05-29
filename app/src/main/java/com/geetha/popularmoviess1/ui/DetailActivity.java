package com.geetha.popularmoviess1.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geetha.popularmoviess1.LaunchYoutube;
import com.geetha.popularmoviess1.R;
import com.geetha.popularmoviess1.database.AppConverters;
import com.geetha.popularmoviess1.database.AppDatabase;
import com.geetha.popularmoviess1.database.AppExecutors;
import com.geetha.popularmoviess1.database.FavoriteMoviesEntity;
import com.geetha.popularmoviess1.database.TrailersEntity;
import com.geetha.popularmoviess1.database.UserReviewsEntity;
import com.geetha.popularmoviess1.models.Movie;
import com.geetha.popularmoviess1.models.MovieTrailers;
import com.geetha.popularmoviess1.models.Review;
import com.geetha.popularmoviess1.network.RetrofitInstance;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, LaunchYoutube {

    List<Review> mUserReviews = new ArrayList<> ();
    List<MovieTrailers> mMovieTrailers = new ArrayList<> ();
    Toolbar mTitleToolbar;

    ImageView mMoviePoster;
    TextView mReleaseDate, mPlotSynopsis, mUserReviewTv;
    ImageButton mAddToFav;
    RatingBar mVoteAvg;
    RecyclerView mUserReviewsRV, mTrailersRv;
    DetailsActivityViewModel detailsActivityViewModel;
    Movie movie;
    AppDatabase mDb;
    private String TAG = DetailActivity.this.getClass ().getSimpleName ();
    MovieTrailerAdapter movieTrailerAdapter;
    UserReviewsAdapter userReviewsAdapter;

    Observer <List<TrailersEntity>> trailersObserver = new Observer<List<TrailersEntity>> () {
        @Override
        public void onChanged(List<TrailersEntity> f) {
            onTrailersFetched (f);
        }
    };

    Observer <List<UserReviewsEntity>> reviewsObserver = new Observer<List<UserReviewsEntity>> () {
        @Override
        public void onChanged(List<UserReviewsEntity> f) {
           onReviewsFetched (f);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail);
        movie = Parcels.unwrap (getIntent ().getParcelableExtra ("EXTRA_MOVIE"));
        mDb = AppDatabase.getInstance (this);

        mTitleToolbar=findViewById (R.id.toolbar_title);
        mMoviePoster = findViewById (R.id.img_movie_poster);
        mReleaseDate = findViewById (R.id.tv_releasedate);
        mPlotSynopsis = findViewById (R.id.tv_synopsis);
        mUserReviewsRV = findViewById (R.id.rv_user_reviews);
        mUserReviewTv = findViewById (R.id.tv_user_reviews_header);
        mVoteAvg = findViewById (R.id.rtg_br_vote_avg);
        mTrailersRv = findViewById (R.id.rv_trailers);
        mAddToFav = findViewById (R.id.btn_add_to_favirites);

        DetailActivityViewModelFactory factory = new DetailActivityViewModelFactory (RetrofitInstance.get (), mDb, movie);
        detailsActivityViewModel = ViewModelProviders.of (this, factory).get (DetailsActivityViewModel.class);

        mAddToFav.setOnClickListener (this);

        RecyclerView.LayoutManager trailerManager = new LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false);
        mTrailersRv.setLayoutManager (trailerManager);

        movieTrailerAdapter = new MovieTrailerAdapter (mMovieTrailers,this);
        mTrailersRv.setAdapter (movieTrailerAdapter);

        AppExecutors.getInstance ().diskIO ().execute (new Runnable () {
            @Override
            public void run() {
                final FavoriteMoviesEntity fav=mDb.moviesDBApi ().loadFavById (movie.getId ());
                runOnUiThread (new Runnable () {
                    @Override
                    public void run() {
                        if(fav != null){
                            mAddToFav.setImageDrawable (getDrawable (R.drawable.ic_favorite_red_24dp));
                            mAddToFav.setTag ("isFav");
                        }
                    }
                });
            }
        });

        detailsActivityViewModel.getTrailersAsync (isOnline ());
        detailsActivityViewModel.trailersEntityLiveData.observe (this,trailersObserver);
        RecyclerView.LayoutManager reviewsManager = new LinearLayoutManager (this);
        mUserReviewsRV.setLayoutManager (reviewsManager);
        userReviewsAdapter = new UserReviewsAdapter (mUserReviews);
        mUserReviewsRV.setAdapter (userReviewsAdapter);

       detailsActivityViewModel.getUserReviewsAsync (isOnline ());
       detailsActivityViewModel.userReviewsEntityLiveData.observe (this,reviewsObserver );

        if (movie.getTitle () != null) {
            mTitleToolbar.setTitle (movie.getTitle ());
        }
        if (movie.getPosterPath () != null) {
            Glide.with (this)
                    .load ("http://image.tmdb.org/t/p/w185" + movie.getPosterPath ())
                    .into (this.mMoviePoster);
        }
        Double rating=movie.getVoteAverage ();
        mVoteAvg.setRating (rating.floatValue ()/2);

        if (movie.getReleaseDate () != null) {
            mReleaseDate.setText (movie.getReleaseDate ());
        }
        if (movie.getOverview () != null) {
            mPlotSynopsis.setText (movie.getOverview ());
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId ()==R.id.btn_add_to_favirites){
            if("notFav".equals (mAddToFav.getTag ())) {
                detailsActivityViewModel.updateFavorites ();
                mAddToFav.setTag ("isFav");
                Toast.makeText (this, "Movie Added to Favorites", Toast.LENGTH_SHORT).show ();
                mAddToFav.setImageDrawable (getDrawable (R.drawable.ic_favorite_red_24dp));
            } else{
                detailsActivityViewModel.deleteFromFavorites ();
                mAddToFav.setTag ("notFav");
                Toast.makeText (this, "Movie Deleted From Favorites",Toast.LENGTH_SHORT).show ();
                mAddToFav.setImageDrawable (getDrawable (R.drawable.ic_favorite_border_black_24dp));
            }
        }
    }

    @Override
    public void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        Log.d (TAG, "watchYoutubeVideo: "+Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    void onTrailersFetched(List<TrailersEntity> t){
        mMovieTrailers.clear ();
        mMovieTrailers.addAll (AppConverters.getMovieTrailersFromTrailersEntity (t));
        movieTrailerAdapter.notifyDataSetChanged ();
    }

    void onReviewsFetched(List<UserReviewsEntity> t){
        mUserReviews.clear ();
        mUserReviews.addAll (AppConverters.getReviewsFromUserReviewEntity (t));
        userReviewsAdapter.notifyDataSetChanged ();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo ();
        return netInfo != null && netInfo.isConnectedOrConnecting ();
    }
}

