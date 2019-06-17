package com.example.ss.MoreReviewsPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.ss.R;

public class MoreReviewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoreReviewsPresenter presenter;
    private String prodcutId,userId;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_reviews);
        initializeFields();

        presenter.showReviews(recyclerView,prodcutId,userId);

    }

    private void initializeFields() {

    recyclerView = findViewById(R.id.more_reviews_recycler);
    presenter = new MoreReviewsPresenter(this);
        i = getIntent();
        prodcutId = i.getStringExtra("productId");
        userId = i.getStringExtra("uid");

    }
}
