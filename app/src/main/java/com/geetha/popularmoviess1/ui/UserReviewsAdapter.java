package com.geetha.popularmoviess1.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geetha.popularmoviess1.R;

import com.geetha.popularmoviess1.models.Review;


import java.util.List;

public class UserReviewsAdapter extends RecyclerView.Adapter<UserReviewsAdapter.UserReviewsViewHolder> {

    private List<Review> userReviews;

    UserReviewsAdapter(List<Review> userReviews){
        this.userReviews=userReviews;
    }


    @NonNull
    @Override
    public UserReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.item_user_review, parent, false);
        return  new UserReviewsAdapter.UserReviewsViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReviewsViewHolder holder, int position) {
        holder.mUserName.setText (userReviews.get (position).getAuthor ());
        holder.mUserReview.setText (userReviews.get (position).getContent ());
    }

    @Override
    public int getItemCount() {
        return userReviews.size ();
    }

    static class UserReviewsViewHolder extends RecyclerView.ViewHolder{

        //ImageView mUserImg;
        TextView mUserName,mUserReview;

        public UserReviewsViewHolder(@NonNull View itemView) {
            super (itemView);
            //mUserImg = itemView.findViewById (R.id.user_img);
            mUserName = itemView.findViewById (R.id.tv_user_name);
            mUserReview = itemView.findViewById (R.id.tv_user_review);
        }
    }
}
