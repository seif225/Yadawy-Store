package com.example.ss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class TestingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);



        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");
        Log.e("testing activity", "onCreate: "+ myList.get(0) );


    }
}
