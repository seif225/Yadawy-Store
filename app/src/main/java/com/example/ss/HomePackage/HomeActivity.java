package com.example.ss.HomePackage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.ss.R;

public class HomeActivity extends Fragment {

    ImageButton imgButton;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);


        View.OnClickListener imgButtonHandler=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgButton.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
            }
        };




        return view;
    }



  void initializeFields(){
      imgButton=view.findViewById(R.id.like_image_button);
//      imgButton.setOnClickListener(imgButtonHandler);
  }

}
