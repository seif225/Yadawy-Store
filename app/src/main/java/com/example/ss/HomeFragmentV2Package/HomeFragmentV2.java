package com.example.ss.HomeFragmentV2Package;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmentV2 extends Fragment {


    private View view;
    private RecyclerView homeRecycler;
    private TextView homeTextView;
    private HomeFragmentV2Presenter presenter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeFields(inflater,container);
        if(FirebaseAuth.getInstance().getUid() != null) {
            FirebaseDatabase.getInstance().
                    getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (FirebaseAuth.getInstance().getUid() != null && !dataSnapshot.hasChild("anonymous"))
                                presenter.getDataForHomeActivity(homeRecycler, homeTextView);

                            else if (dataSnapshot.hasChild("anonymous")) presenter.handleAnonymous(homeRecycler, homeTextView);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }




        return view;
    }

    private void initializeFields(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_home_fragment_v2, container, false);
        homeRecycler = view.findViewById(R.id.homeRecyclerV2);


        homeTextView=view.findViewById(R.id.home_text_messageV2);
        presenter = new HomeFragmentV2Presenter(getActivity());



    }

}
