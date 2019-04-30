package com.example.ss.HomePackage;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ss.R;

public class HomeActivity extends Fragment {


    View view;
    RecyclerView homeRecycler;
    HomeActivityPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
            initializeFields();
            presenter.getAndShowNewsFeedFromFirebase(new ProgressDialog(getActivity()),homeRecycler);


        return view;
    }



  void initializeFields(){

      presenter = new HomeActivityPresenter(getActivity(),(TextView) view.findViewById(R.id.home_text_message));
      homeRecycler = view.findViewById(R.id.homeRecycler);


  }

}
