package com.example.ss.SearchPackage;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ss.HomeFragmentV2Package.NewsFeedRecyclerAdapter;
import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.example.ss.R;
import com.example.ss.SplashPack.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView;
    ImageView imageView;
    SearchPresenter presenter;
    Button logIn;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeFields();
        imageView.setImageResource(R.drawable.yaaadaw);
       // Picasso.get().load(R.drawable.logowithblackslogan).into(imageView);
        presenter.handleAnonymousUser();
        presenter.handleLoginButton(logIn,imageView,searchView,recyclerView);

    }


    @Override
    protected void onStart() {
        super.onStart();

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchView.setFocusable(true);
                searchView.setIconified(false);

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                presenter.getListOfProducts().clear();
                presenter.search(s, recyclerView);

                return false;
            }
        });


    }


    private void initializeFields() {


        recyclerView = findViewById(R.id.search_recyclerView);
        searchView = findViewById(R.id.search_view);
        imageView=findViewById(R.id.logo_search);
        presenter = new SearchPresenter(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        logIn = findViewById(R.id.login_button_search);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // add back arrow to toolbar
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChild("anonymous")) {

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                        getSupportActionBar().setTitle("Yadawy");


                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToSplash();


            }
        });



    }

    private void sendUserToSplash() {


    startActivity(new Intent(this, SplashActivity.class));






    }
}
