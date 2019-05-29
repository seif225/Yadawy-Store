package com.example.ss.FindSellersPackage;

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

import com.example.ss.R;

public class FindSellersActivity extends Fragment {

    private RecyclerView recyclerView;
    private FindSellersPresenter presenter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_find_sellers, container, false);
        intializeFields();
        presenter.previewSellers(new ProgressDialog(getContext()),recyclerView);
        return view;
    }

    //-------------------------------------------------------------------------


    private void intializeFields() {
    recyclerView=view.findViewById(R.id.find_sellers_recycler_view);
    presenter= new FindSellersPresenter(getActivity(),recyclerView);


    }


    @Override
    public void onPause() {
        super.onPause();
        presenter.getUserRef().removeEventListener(presenter.getUserListner());

        presenter.getUserRef().removeEventListener(presenter.getUserListner());
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.getUserRef().removeEventListener(presenter.getUserListner());

        presenter.getUserRef().removeEventListener(presenter.getUserListner());
    }





}
