package com.example.ss.UserProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.ss.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private CircleImageView userPp;
    private TextView numberOfProducts,followers,following,userName;
    private BootstrapButton followButton;
    private UserProfilePresenter presenter;
    private RecyclerView productsRecyclerView;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initalizeFields();
        presenter.getAndPreviewUserData(new ProgressDialog(this),userPp,numberOfProducts,userName,followers,following,followButton,productsRecyclerView,uid);
        presenter.handleFollowButton(followButton);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(presenter.isFollowed()) presenter.unFollow();
                else presenter.follow();
            }
        });
    }

    private void initalizeFields() {

    userPp = findViewById(R.id.user_profile_picture_user_profile);
    numberOfProducts=findViewById(R.id.products_number);
    followers=findViewById(R.id.follower_number);
    following=findViewById(R.id.following_number);
    followButton = findViewById(R.id.follow_button);
    productsRecyclerView=findViewById(R.id.recyclerView_in_user_profile);
        Intent i = getIntent();
        userName=findViewById(R.id.user_name_user_profile);
        uid = i.getStringExtra("uid");
        Log.e("uid in userProfile",""+ uid+" hell yeaaaah ! ");
    presenter=new UserProfilePresenter(this,uid,followButton);




    }
}
