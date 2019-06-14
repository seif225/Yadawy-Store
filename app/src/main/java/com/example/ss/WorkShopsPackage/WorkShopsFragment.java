package com.example.ss.WorkShopsPackage;


import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ss.R;
import com.google.firebase.database.collection.LLRBNode;


public class WorkShopsFragment extends Fragment {
    View view;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    ImageView linearLayout;
    WorkShopPresenter presenter;

    public WorkShopsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initializeFields(inflater, container);
        presenter.checkFabState(fab);
        presenter.PreviewData(recyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendUserToAddWorkShopActivity();
            }
        });


        return view;
    }

    private void initializeFields(LayoutInflater inflater, ViewGroup container) {

        view = inflater.inflate(R.layout.fragment_work_shops, container, false);
        fab = view.findViewById(R.id.add_workshop_fab);
        recyclerView = view.findViewById(R.id.workshops_recyclerView);
        linearLayout = view.findViewById(R.id.workShop_linear_container);

        presenter = new WorkShopPresenter(getActivity());
        fab.hide();
    }


}
