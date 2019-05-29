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

public class ProductActivity extends AppCompatActivity {

    private String prodcutId,userId;
    private ProductActivityPresenter presenter;
    private SliderLayout sliderLayout;;
    private TextView price,category,describtion,userName,productIdTv;
    private CircleImageView userPp;
    private ShineButton likeButton;
    private boolean likeState;
    private RatingBar ratingBar;
    private TextView textView;

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
                   textView.setVisibility(View.VISIBLE);
               }


           }
       });
        presenter.getProductData(new ProgressDialog(this),sliderLayout,category,price,describtion,userName,productIdTv,userPp);
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

        category=findViewById(R.id.product_category_tv);
        price=findViewById(R.id.product_price_tv);
        describtion=findViewById(R.id.product_describtion_tv);
        userName=findViewById(R.id.user_name_in_product_activity);
        productIdTv=findViewById(R.id.product_code_tv);
        userPp=findViewById(R.id.profile_picture_in_product_activity);
        likeButton=findViewById(R.id.like_image_button_in_product_activity);
        ratingBar = findViewById(R.id.rate);
        ratingBar.setMax(5);
        textView= findViewById(R.id.write_review);

    }


}
