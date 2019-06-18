package com.example.ss.ReceiptPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

class ReceiptPresenter {

private ReceiptRecyclerAdapter adapter;
private Context context;
private List<ProductModel> listOfOrders;
    ReceiptPresenter(Context context, TextView totalTv) {
    this.context=context;
    listOfOrders=new ArrayList<>();
adapter=new ReceiptRecyclerAdapter(context,listOfOrders,totalTv);
    }


     void finalizeOrderAndCheckOut(final RecyclerView recyclerView, String orderId) {


         FirebaseDatabase.getInstance().getReference().child("orders").child(FirebaseAuth.getInstance().getUid()).child(orderId).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 for (DataSnapshot d :dataSnapshot.getChildren()) {


                     ProductModel productModel=d.getValue(ProductModel.class);
                     listOfOrders.add(productModel);

                 }

                 preview(recyclerView);

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });






    }


    void preview(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

    };



}
