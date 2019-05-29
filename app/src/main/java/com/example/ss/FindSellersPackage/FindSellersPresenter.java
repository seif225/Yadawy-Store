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

    public ValueEventListener getUserListner() {
        return userListner;
    }

    public DatabaseReference getUserRef() {
        return userRef;
    }

    private ArrayList<UserModel> listOfUsers;

    private FindSellersRecyclerAdapter adapter;

    private UserModel userModel;

    private DatabaseReference userRef;
    private ValueEventListener userListner;
    private  ProgressDialog progressDialog;





    FindSellersPresenter(final Context context,final RecyclerView recyclerView) {

        listOfUsers = new ArrayList<>();
        progressDialog = new ProgressDialog(context);

        adapter = new FindSellersRecyclerAdapter(context, listOfUsers);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        userListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfUsers.clear();
                adapter.notifyDataSetChanged();
                for (DataSnapshot d:dataSnapshot.getChildren()) {

                    if(d.hasChild("account type")&&d.child("account type").getValue().equals("business account")){
                        userModel = new UserModel();
                        userModel.setuId(d.child("userID").getValue().toString());
                        if(d.hasChild("userName")){
                            userModel.setName(d.child("userName").getValue().toString());}
                        if(d.hasChild("bio")){

                        userModel.setBio(d.child("bio").getValue().toString());

                        }


                        if(d.hasChild("image")){
                            userModel.setProfilePicture(d.child("image").getValue().toString());}
                        if(userModel.getName()!= null && userModel.getuId()!=null )
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
        };
    }

    public void previewSellers(final ProgressDialog pr, final RecyclerView recyclerView) {

progressDialog.setCancelable(false);
                progressDialog.show();

        userRef.addValueEventListener(userListner);





    }











}
