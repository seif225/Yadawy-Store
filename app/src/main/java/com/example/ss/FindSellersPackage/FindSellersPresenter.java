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
        countAccounts();
    }


    private void countAccounts() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                businessAcoountsCounter=0;
                listOfUsers.clear();


                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    if (d.child("account type").getValue().equals("business account")) {

                        businessAcoountsCounter++;
                        Log.e("business",""+businessAcoountsCounter);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    void previewSellers(final ProgressDialog progressDialog, final RecyclerView recyclerView) {

        progressDialog.setTitle("loading..");
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfUsers.clear();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if (d.child("account type").getValue().toString().equals("business account")) {
                        listOfFollowers.add(d.child("userID").getValue().toString());
                        // businessAcoountsCounter++;
                    }
                }

                getUsers(progressDialog, recyclerView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void getUsers(final ProgressDialog progressDialog, final RecyclerView recyclerView) {

        for (int i = 0; i < businessAcoountsCounter; i++) {


            final int finalI = i;

            FirebaseDatabase.getInstance().getReference().child("Users").child(listOfFollowers.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    adapter.notifyDataSetChanged();
                    userModel = new UserModel();
                    userModel.setName(dataSnapshot.child("userName").getValue().toString());
                    if (dataSnapshot.hasChild("image")) {
                        userModel.setProfilePicture(dataSnapshot.child("image").getValue().toString());
                    }
                    userModel.setuId(listOfFollowers.get(finalI));
                    listOfUsers.add(userModel);
                    previewDataOnActivity(recyclerView, progressDialog);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


    }

    private void previewDataOnActivity(RecyclerView recyclerView, ProgressDialog progressDialog) {


        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(adapter);


        progressDialog.dismiss();


    }
}
