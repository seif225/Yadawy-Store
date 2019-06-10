package com.example.ss.ProductActivityPack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ss.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {


    private List<ReviewModel> reviewsList;
    private Context context;

    public ReviewsAdapter(List<ReviewModel> reviewsList, Context context) {
        this.reviewsList = reviewsList;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_recycler_row, viewGroup, false);


        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {


        final ReviewModel singleReview = reviewsList.get(i);

        if (singleReview.getReviewDate() != null)
            viewHolder.dateTv.setText(singleReview.getReviewDate());
        if (singleReview.getUserReview() != null)
            viewHolder.userReview.setText(singleReview.getUserReview());


        FirebaseDatabase.getInstance().getReference().child("Users").child(singleReview.userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("userName"))
                viewHolder.userName.setText(dataSnapshot.child("userName").getValue().toString());
                if(dataSnapshot.hasChild("image"))
                Picasso.get().load(dataSnapshot.child("image").getValue().toString()).into(viewHolder.userImage);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public int getItemCount() {
        if (reviewsList == null) return 0;
        else if (reviewsList.size() > 5) return 5;
        else return reviewsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userImage;
        TextView userName, userReview, dateTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.user_profile_picture_review_recycler_row);
            userName = itemView.findViewById(R.id.user_name_review_recycler_row);
            userReview = itemView.findViewById(R.id.review_recycler_row_review);
            dateTv = itemView.findViewById(R.id.review_date);

        }
    }


}
