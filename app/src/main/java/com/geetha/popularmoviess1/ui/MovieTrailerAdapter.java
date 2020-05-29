package com.geetha.popularmoviess1.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geetha.popularmoviess1.LaunchYoutube;
import com.geetha.popularmoviess1.R;
import com.geetha.popularmoviess1.models.MovieTrailers;

import java.util.List;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerViewHolder> implements View.OnClickListener {

    private List<MovieTrailers> mMovieTrailers;
    private LaunchYoutube launchYoutube;
    private String id;

    public MovieTrailerAdapter(List<MovieTrailers> mMovieTrailers,LaunchYoutube youtube) {
        this.mMovieTrailers = mMovieTrailers;
        this.launchYoutube=youtube;
    }



    @NonNull
    @Override
    public MovieTrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.item_movie_trailer, parent, false);
        return new MovieTrailerViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieTrailerViewHolder holder, final int position) {
        holder.mTrailerTitle.setText (mMovieTrailers.get (position).getName ());
        //holder.mTrailerVideoView.setVideoURI ();
        holder.mTrailerVideoBtn.setText ("Trailer "+ (int)(position+1));
        holder.mTrailerVideoBtn.setOnClickListener (this);
        id=mMovieTrailers.get (position).getKey ();
    }



    @Override
    public int getItemCount() {
        return mMovieTrailers.size ();
    }

    @Override
    public void onClick(View v) {
        if(v.getId ()==R.id.btn_trailer){
            launchYoutube.watchYoutubeVideo (id);
        }

    }

    static class MovieTrailerViewHolder extends RecyclerView.ViewHolder{

        Button mTrailerVideoBtn;
        TextView mTrailerTitle;

        public MovieTrailerViewHolder(@NonNull View itemView) {
            super (itemView);
            mTrailerVideoBtn =itemView.findViewById (R.id.btn_trailer);
            mTrailerTitle=itemView.findViewById (R.id.tv_trailer_title);
        }

    }
}
