package com.example.ss.LikesFragmentPack;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikesFragment extends Fragment {

    private RecyclerView likesRecycler;
    private LikesFragmentPresenter presenter;
    private View view;
    public LikesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_likes, container, false);

        initializeFields();
        presenter.getDataAndPreview(likesRecycler,new ProgressDialog(getActivity()));



        return view;
    }

    private void initializeFields() {

        likesRecycler = view.findViewById(R.id.like_item_recycler_view);
        presenter = new LikesFragmentPresenter(getActivity());


    }


}
