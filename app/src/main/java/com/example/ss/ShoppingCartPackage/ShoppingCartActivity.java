package com.example.ss.ShoppingCartPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.example.ss.ProductActivityPack.ProductActivity;
import com.example.ss.R;
import com.example.ss.TestingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class ShoppingCartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button proceedBtn;
    private ShoppingCartPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initializeFields();
        presenter.getDataAndPreview(recyclerView,proceedBtn);

    }

    private void initializeFields() {
    recyclerView=findViewById(R.id.cart_recycler_view);
    proceedBtn=findViewById(R.id.check_out_btn);
    presenter=new ShoppingCartPresenter(this,proceedBtn);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    }

