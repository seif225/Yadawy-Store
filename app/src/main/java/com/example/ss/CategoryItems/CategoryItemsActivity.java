package com.example.ss.CategoryItems;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ss.R;

public class CategoryItemsActivity extends AppCompatActivity {

    private String category;
    private TextView categoryNameTv;
    private RecyclerView recyclerView;
    private CategoryItemPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);


        intializeFields();


        presenter.getandPreviewData(new ProgressDialog(this), recyclerView);


    }

    private void intializeFields() {
        category = getIntent().getStringExtra("category");
        Log.e("category item activity", category + "test");
        categoryNameTv = findViewById(R.id.category_name_category_items_tv);
        categoryNameTv.setText(category);
        recyclerView = findViewById(R.id.category_items_recycler_view);
        presenter = new CategoryItemPresenter(this, category);

    }
}
