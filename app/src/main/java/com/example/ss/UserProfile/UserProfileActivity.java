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

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private CircleImageView userPp;
    private TextView numberOfProducts,followers,following,userName;
    private CheckBox followButton;
    private UserProfilePresenter presenter;
    private RecyclerView productsRecyclerView;
    private String uid;
    private boolean follow_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initalizeFields();
        presenter.getAndPreviewUserData(new ProgressDialog(this),userPp,numberOfProducts,userName,followers,following,followButton,productsRecyclerView,uid);

       presenter.checkButtonSettings(followButton);

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.checkButtonSettings(followButton);

                if(presenter.isFollowed(uid)){
                    presenter.unFollow();
                    presenter.checkButtonSettings(followButton);

                }
                else{ presenter.follow();
                    presenter.checkButtonSettings(followButton);

                };

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
    follow_state=false;


    }
}
