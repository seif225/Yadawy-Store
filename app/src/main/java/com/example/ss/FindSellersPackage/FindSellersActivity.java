package com.example.ss.FindSellersPackage;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.ss.R;

public class FindSellersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FindSellersPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_sellers);
        intializeFields();
        presenter.previewSellers(new ProgressDialog(this),recyclerView);





    }

    private void intializeFields() {
    recyclerView=findViewById(R.id.find_sellers_recycler_view);
    presenter= new FindSellersPresenter(this);


    }
}
