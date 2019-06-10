package com.example.ss.AddProductPAckage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.ss.uploadProductImages.UploadProudctImagesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;

public class AddProductPresenter {

    Context context;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference productsRef;
   private long size=0;

    AddProductPresenter(Context context){

        this.context=context;
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        productsRef=firebaseDatabase.getReference().child("products");



    }
        private void sendUserToUploadProductImagesActivity(){

        Intent i = new Intent(context, UploadProudctImagesActivity.class);
        Log.e("intent i ","de7k"+context);
        context.startActivity(i);

    }



    void uploadProductDataToFirebase(String productNameString,String productPriceString,String productDescribtionString,String colorString
            ,String categoryString, String priceRangeString){


        HashMap <String, String>attributes=new HashMap();
        final String randomProductId = UUID.randomUUID().toString();

        attributes.put("product_name",productNameString);
        attributes.put("product_price",productPriceString);
        attributes.put("product_describtion",productDescribtionString);
        attributes.put("color",colorString);
        attributes.put("category",categoryString);
        attributes.put("price_range",priceRangeString);
        attributes.put("use_id",mAuth.getUid());
        attributes.put("product_id",randomProductId);
        attributes.put("product_number",getProductsNumber()+"");




        productsRef.child(mAuth.getUid()).child(randomProductId).setValue(attributes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Intent i = new Intent (context,UploadProudctImagesActivity.class);
                    i.putExtra("productId",randomProductId);
                   context.startActivity(i);
                    // sendUserToUploadProductImagesActivity();
                }

                else {

                    Toast.makeText(context, "lalala"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private long getProductsNumber(){


        FirebaseDatabase.getInstance().getReference().child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot d:dataSnapshot.getChildren()) {
                    size=d.getChildrenCount();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return size;
    }

}
