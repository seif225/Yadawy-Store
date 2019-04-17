package com.example.ss.HomePackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.ss.R;

public class HomeActivity extends AppCompatActivity {

    ImageButton imgButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imgButton=findViewById(R.id.like_image_button);
        imgButton.setOnClickListener(imgButtonHandler);
    }
    View.OnClickListener imgButtonHandler=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imgButton.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        }
    };
}
