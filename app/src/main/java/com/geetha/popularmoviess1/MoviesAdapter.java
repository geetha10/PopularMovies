package com.geetha.popularmoviess1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geetha.popularmoviess1.R;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>{

    private List<Movie> moviesList;
    private Context context;
    private ConstraintLayout mMovieTile;
    private MovieItem movieItem;

    public MoviesAdapter(Context context, List<Movie> moviesList,MovieItem movieItem) {
        this.context = context;
        this.moviesList = moviesList;
        this.movieItem=movieItem;
    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from (parent.getContext ()).inflate (R.layout.movie, parent, false);
        return new MoviesViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, final int position) {
        holder.mMovieNameTV.setText (moviesList.get (position).title);
        Glide.with (context)
                .load ("http://image.tmdb.org/t/p/w185"+moviesList.get (position).posterPath)
                .into (holder.mMoviePosterIV);
        mMovieTile.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                movieItem.onMovieItemClicked (moviesList.get (position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size ();
    }



    class MoviesViewHolder extends RecyclerView.ViewHolder {

        ImageView mMoviePosterIV;
        TextView mMovieNameTV;
        public MoviesViewHolder(@NonNull View itemView) {
            super (itemView);
            mMoviePosterIV=itemView.findViewById (R.id.iv_movieposter);
            mMovieNameTV=itemView.findViewById (R.id.tv_moviename);
            mMovieTile=itemView.findViewById (R.id.cl_movietile);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
