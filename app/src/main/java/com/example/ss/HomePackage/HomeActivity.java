package com.example.ss.HomePackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ss.MainActivity;
import com.example.ss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends Fragment {

    Context context;
    View view;
    RecyclerView homeRecycler;
    HomeActivityPresenter presenter;
    ProgressDialog progressDialog;
    boolean activityState;
    ValueEventListener myListner;
    DatabaseReference myRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
            initializeFields();




        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

      myRef.addValueEventListener(myListner);



    }

    @Override
    public void onPause() {
        super.onPause();
        activityState = false;
        if(myListner!=null)  myRef.removeEventListener(myListner);

        if(presenter.getProductListner()!=null) presenter.getProductsRef().removeEventListener(presenter.getProductListner());
        if(presenter.getLikesListner()!=null) presenter.getAdapterrLikesRef().removeEventListener(presenter.getLikesListner());
        if(presenter.getUserListner()!=null) presenter.getAdapterUserRef().removeEventListener(presenter.getUserListner());
    }

    @Override
    public void onStop() {
        super.onStop();
        activityState = false;
        if(myListner!=null)  myRef.removeEventListener(myListner);

        if(presenter.getProductListner()!=null) presenter.getProductsRef().removeEventListener(presenter.getProductListner());
        if(presenter.getLikesListner()!=null) presenter.getAdapterrLikesRef().removeEventListener(presenter.getLikesListner());
        if(presenter.getUserListner()!=null) presenter.getAdapterUserRef().removeEventListener(presenter.getUserListner());

    }

    void initializeFields(){
    progressDialog=new ProgressDialog(getActivity());
        homeRecycler = view.findViewById(R.id.homeRecycler);

        presenter = new HomeActivityPresenter(getActivity(),homeRecycler,(TextView) view.findViewById(R.id.home_text_message));
        context=getActivity();
        activityState = false;
        myListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                    if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid()+"")) {
                        // progressDialog.show();
                        presenter.getAndShowNewsFeedFromFirebase(homeRecycler);
                        if(homeRecycler.isAttachedToWindow()){
                            progressDialog.dismiss();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        myRef =  FirebaseDatabase.getInstance().getReference().child("Users");

  }

}
