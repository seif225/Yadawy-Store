package com.example.ss.WorkShopsPackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ss.AddWorkshopPackage.AddWorkshopActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class WorkShopPresenter {
    private Context context;
    private DatabaseReference userRef, workshopRef;
    private ArrayList<WorkshopModel> listOfModels;
    private WorkshopRecyclerAdapter adapter;

    WorkShopPresenter(Context context) {

        this.context = context;
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        workshopRef = FirebaseDatabase.getInstance().getReference().child("workshops");
        listOfModels = new ArrayList<>();
        adapter = new WorkshopRecyclerAdapter(listOfModels, context);
    }


    void checkFabState(final FloatingActionButton fab) {


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("account type").getValue().toString().equals("business account"))
                    fab.show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    void sendUserToAddWorkShopActivity() {
        context.startActivity(new Intent(context, AddWorkshopActivity.class));


    }

    private void previewAtActivity(RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);


    }


    void PreviewData(final RecyclerView recyclerView) {


        workshopRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    WorkshopModel workshopModel;
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        workshopModel = new WorkshopModel();

                        if (d.hasChild("formLink"))
                            workshopModel.setFormLink(d.child("formLink").getValue().toString());
                        if (d.hasChild("userName"))
                            workshopModel.setUserName(d.child("userName").getValue().toString());
                        if (d.hasChild("workshopImage"))
                            workshopModel.setWorkshopImage(d.child("workshopImage").getValue().toString());
                        if (d.hasChild("workShopName"))
                            workshopModel.setWorkShopName(d.child("workShopName").getValue().toString());
                        if (d.hasChild("workShopId"))
                            workshopModel.setWorkShopId(d.child("workShopId").getValue().toString());
                        if (d.hasChild("userId"))
                            workshopModel.setUserId(d.child("userId").getValue().toString());

                        listOfModels.add(workshopModel);


                    }


                    previewAtActivity(recyclerView);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
