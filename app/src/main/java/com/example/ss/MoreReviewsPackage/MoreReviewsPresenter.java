package com.example.ss.MoreReviewsPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ss.ProductActivityPack.ReviewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

class MoreReviewsPresenter {
   private  Context context;
    private ReviewModel reviewModel;
    private List<ReviewModel> listOfReviews;
private MoreReviewsAdapter adapter;
    MoreReviewsPresenter(Context context) {
        this.context = context;
        listOfReviews = new ArrayList();
        adapter = new MoreReviewsAdapter(listOfReviews,context);
    }

     public void showReviews(final RecyclerView recyclerView, String prodcutId, String userId) {




         FirebaseDatabase.getInstance().getReference().child("products").child(userId).child(prodcutId).child("reviews").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 for (DataSnapshot d:dataSnapshot.getChildren()) {


                     reviewModel = new ReviewModel();
                     reviewModel.setReviewDate(d.child("reviewDate").getValue().toString());
                     reviewModel.setUserId(d.child("userId").getValue().toString());
                     reviewModel.setUserReview(d.child("userReview").getValue().toString());
                     Log.e("getReviews", reviewModel.getReviewDate() + reviewModel.getUserReview() + " (Y)");
                     listOfReviews.add(reviewModel);


                 }

                preview(recyclerView);


             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });





     }

    private void preview(RecyclerView recyclerView) {

    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    recyclerView.setAdapter(adapter);
    }
}
