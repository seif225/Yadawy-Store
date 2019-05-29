package com.example.ss.UserProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.ss.R;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private CircleImageView userPp;
    private TextView numberOfProducts, followers, following, userName;
    private CheckBox followButton;
    private UserProfilePresenter presenter;
    private RecyclerView productsRecyclerView;
    private String uid;
    private static final String TAG = "UserProfileActivity";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
       /* Log.e(TAG,"first Log : " + getIntent().getStringExtra("uid"));
        Log.e(TAG,"uid: " + uid);
        Log.e(TAG,"uid: " + savedInstanceState.getString("Uid"));*/

        initalizeFields();


        presenter.checkButtonSettings(followButton,uid);

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.checkButtonSettings(followButton,uid);

                if (presenter.isFollowed(uid)) {
                    presenter.unFollow();
                    presenter.checkButtonSettings(followButton,uid);


                } else {
                    presenter.follow();
                    presenter.checkButtonSettings(followButton,uid);


                }
                ;

            }

        });


    }




    private void initalizeFields() {

        userPp = findViewById(R.id.user_profile_picture_user_profile);
        numberOfProducts = findViewById(R.id.products_number);
        followers = findViewById(R.id.follower_number);
        following = findViewById(R.id.following_number);
        followButton = findViewById(R.id.follow_button);
        uid = getIntent().getStringExtra("uid");
        productsRecyclerView = findViewById(R.id.recyclerView_in_user_profile);
        userName = findViewById(R.id.user_name_user_profile);
        presenter = new UserProfilePresenter(this, uid, followButton);
        presenter.getAndPreviewUserData(new ProgressDialog(this), userPp, numberOfProducts, userName, followers, following, followButton, productsRecyclerView,uid);

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.checkButtonSettings(followButton,uid);

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.checkButtonSettings(followButton,uid);


    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.checkButtonSettings(followButton,uid);

    }
}
