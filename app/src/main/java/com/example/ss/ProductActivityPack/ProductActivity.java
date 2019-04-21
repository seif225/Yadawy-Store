package com.example.ss.ProductActivityPack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ss.HomePackage.ProductModel;
import com.example.ss.R;
import com.smarteist.autoimageslider.SliderLayout;

public class ProductActivity extends AppCompatActivity {

    String prodcutId,userId;
    ProductActivityPresenter presenter;
    SliderLayout sliderLayout;;
    TextView price,category,describtion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        //Log.e("activity shit",productModel.getCategory()+"what so evaaaa ");
        intializeFields();
        presenter.getProductData(new ProgressDialog(this),sliderLayout,category,price,describtion);


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

    }


}
