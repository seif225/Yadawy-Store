package com.example.ss.EditProductPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ss.R;

public class EditProductActivity extends AppCompatActivity {

    private String uId,productId;
    private EditProductPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        initializeFields();



    }

    private void initializeFields() {
    Intent i = getIntent();
    uId = i.getStringExtra("uId");
    productId=i.getStringExtra("productId");
    Log.e("EditProductActivity", uId+productId+"");



    }
}
