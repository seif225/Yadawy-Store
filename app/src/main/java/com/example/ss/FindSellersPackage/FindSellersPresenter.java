package com.example.ss.FindSellersPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class FindSellersPresenter {

    private ArrayList<UserModel> listOfUsers;
    private Context context;
    private FindSellersRecyclerAdapter adapter;
    private ArrayList<String> listOfFollowers;
    private UserModel userModel;
    private int businessAcoountsCounter;
    private DatabaseReference userRef;

    FindSellersPresenter(Context context) {
        this.context = context;
        listOfUsers = new ArrayList<>();
        listOfFollowers = new ArrayList<>();
        adapter = new FindSellersRecyclerAdapter(context, listOfUsers);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        businessAcoountsCounter = 0;
    }

    public void previewSellers(final ProgressDialog progressDialog, final RecyclerView recyclerView) {

progressDialog.setCancelable(false);
                progressDialog.show();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listOfUsers.clear();
                    adapter.notifyDataSetChanged();
                for (DataSnapshot d:dataSnapshot.getChildren()) {

                    if(d.child("account type").getValue().equals("business account")){
                        userModel = new UserModel();
                        userModel.setuId(d.child("userID").getValue().toString());
                        userModel.setName(d.child("userName").getValue().toString());
                        if(d.hasChild("image")){
                        userModel.setProfilePicture(d.child("image").getValue().toString());}
                        listOfUsers.add(userModel);
                    }


                }


                preview(progressDialog,recyclerView);



            }

            private void preview(ProgressDialog progressDialog, RecyclerView recyclerView) {

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            progressDialog.dismiss();





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }











}
