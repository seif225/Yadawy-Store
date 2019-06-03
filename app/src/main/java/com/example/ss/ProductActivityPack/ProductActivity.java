package com.example.ss.ProductActivityPack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ss.HomePackage.ProductModel;
import com.example.ss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.smarteist.autoimageslider.SliderLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import flepsik.github.com.progress_ring.ProgressRingView;

public class ProductActivity extends AppCompatActivity {

    private String prodcutId,userId;
    private ProductActivityPresenter presenter;
    private SliderLayout sliderLayout;;
    private TextView price,category,describtion,userName,productIdTv;
    private CircleImageView userPp;
    private ShineButton likeButton;
    private boolean likeState;
    private RatingBar ratingBar;
    private TextView textView,accuRate,rateCounter;
    private ProgressRingView progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        //Log.e("activity shit",productModel.getCategory()+"what so evaaaa ");
        intializeFields();


       ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               if(ratingBar.getRating()!=0){
                   presenter.updateRating(rating);
                   textView.setVisibility(View.VISIBLE);
               }
               if(rating==0){
                   textView.setVisibility(View.GONE);
                   presenter.updateRating(0);

               }


           }
       });




        presenter.getProductData(new ProgressDialog(this),sliderLayout,category,price,describtion,userName,productIdTv,userPp,progress,accuRate,rateCounter);
        DatabaseReference likRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        likRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("Likes")){
                    if (dataSnapshot.child("Likes").hasChild(presenter.getProducName())){


                        likeState = true;
                        likeButton.setChecked(true);

                    }
                    else {

                        likeButton.setChecked(false);
                        likeState = false;

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!likeState){
                    presenter.like();
                    likeButton.setChecked(true);

                }
                else if (likeState){
                    presenter.disLike();
                    likeButton.setChecked(false);

                }
            }
        });

       presenter.previewUserRate(ratingBar);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProductActivity.this, "Working on it :D , sry :( ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void intializeFields() {
        Intent i = getIntent();
        prodcutId=i.getStringExtra("productId");
        userId=i.getStringExtra("uid");
        Log.e("haahaha",prodcutId+"yid"+userId+"heyyou");
        presenter=new ProductActivityPresenter(this,userId,prodcutId);
        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.WORM);
        sliderLayout.animate();
        sliderLayout.setScrollTimeInSec(2);
        progress = findViewById(R.id.progressRing);
        accuRate = findViewById(R.id.accumlated_rate);
        rateCounter = findViewById(R.id.ratings_counter);
        category=findViewById(R.id.product_category_tv);
        price=findViewById(R.id.product_price_tv);
        describtion=findViewById(R.id.product_describtion_tv);
        userName=findViewById(R.id.user_name_in_product_activity);
        productIdTv=findViewById(R.id.product_code_tv);
        userPp=findViewById(R.id.profile_picture_in_product_activity);
        likeButton=findViewById(R.id.like_image_button_in_product_activity);
        ratingBar = findViewById(R.id.rate);
        ratingBar.setMax(5);
        /*ratingBar.setStepSize(0.01f);*/
        textView= findViewById(R.id.write_review);

    }


}
