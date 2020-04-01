package com.geetha.popularmoviess1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geetha.popularmoviess1.R;

public class DetailActivity extends AppCompatActivity {

    ImageView mMoviePoster;
    TextView mTitle,mUserRatings,mReleaseDate,mPlotSynopsis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail);
        mMoviePoster=findViewById (R.id.iv_movieposter);
        mTitle=findViewById (R.id.tv_display_title);
        mUserRatings=findViewById (R.id.tv_display_ratings);
        mReleaseDate=findViewById (R.id.tv_display_releasedate);
        mPlotSynopsis=findViewById (R.id.tv_display_synopsis);
        if(getIntent ().getStringExtra ("MOVIE_TITLE") != null){
            mTitle.setText (getIntent ().getStringExtra ("MOVIE_TITLE"));
        }
        if(getIntent ().getStringExtra ("POSTER_PATH") != null){
            Glide.with (this)
                    .load ("http://image.tmdb.org/t/p/w185"+ getIntent ().getStringExtra ("POSTER_PATH"))
                    .into (this.mMoviePoster);
        }
        if(getIntent ().getStringExtra ("USER_RATING") != null){
            mUserRatings.setText (getIntent ().getStringExtra ("USER_RATING"));
        }
        if(getIntent ().getStringExtra ("RELEASE_DATE") != null){
            mReleaseDate.setText (getIntent ().getStringExtra ("RELEASE_DATE"));
        }
        if(getIntent ().getStringExtra ("PLOT_SYNOPSIS") != null){
            mPlotSynopsis.setText (getIntent ().getStringExtra ("PLOT_SYNOPSIS"));
        }

        /*intent.putExtra ("MOVIE_TITLE",movie.title);
        intent.putExtra ("POSTER_PATH",movie.posterPath);
        intent.putExtra ("USER_RATING",movie.userRating);
        intent.putExtra ("RELEASE_DATE",movie.releaseDate);
        intent.putExtra ("PLOT_SYNOPSIS",movie.plotSynopsis);*/
    }
}
