package com.example.ss.UserProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.ss.R;
import com.example.ss.signUpPack.SingUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

                FirebaseDatabase.getInstance().getReference().child("Users")
                        .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        if(dataSnapshot.hasChild("anonymous")){
                            startActivity(new Intent(getApplicationContext(), SingUpActivity.class));

                        }
                        else {

                            if (presenter.isFollowed(uid)) {
                                presenter.unFollow();
                                presenter.checkButtonSettings(followButton,uid);


                            } else {
                                presenter.follow();
                                presenter.checkButtonSettings(followButton,uid);


                            }
                            ;
                        }








                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



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

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);

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
