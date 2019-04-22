package com.example.ss.HomePackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivityPresenter {

    private Context context ;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference productsRef;
    private ProductModel productModel;
    private ArrayList<ProductModel> listOfProducts;
    private ArrayList<String> listOfPictureLinks;
    private NewsFeedRecyclerAdapter adapter;


     HomeActivityPresenter(Context context) {

         this.context=context;
         database = FirebaseDatabase.getInstance();
         mAuth=FirebaseAuth.getInstance();
        productsRef = database.getReference().child("products");
         listOfPictureLinks = new ArrayList<>();
         listOfProducts=new ArrayList<>();

         adapter=new NewsFeedRecyclerAdapter(context,listOfProducts);


     }


     void getAndShowNewsFeedFromFirebase(final ProgressDialog progressDialog, final RecyclerView homeRecycler) {

            progressDialog.setMessage("getting data");
            progressDialog.setCancelable(false);
            progressDialog.show();

            productsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    adapter.notifyDataSetChanged();

                    for (DataSnapshot d: dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1: d.getChildren()) {
                            productModel = new ProductModel();

                            if(dataSnapshot1.hasChild("images")){
                                //productModel.setImagesLinks(dataSnapshot.child("images").getValue().toString());
                                int i=0;
                                listOfPictureLinks = new ArrayList<>();
                                for (DataSnapshot imagesDataSnapShot:dataSnapshot1.child("images").getChildren()){

                                    listOfPictureLinks.add(imagesDataSnapShot.getValue().toString());
                                    //Log.e("images",imagesDataSnapShot.getValue().toString()+" msh null yasta wla eh?");
                                   /* Log.e("datapshot fl presenter",dataSnapshot.toString());


                                    // da esm el product ==>

                                    Log.e("datapshot1 fl presenter",dataSnapshot1.getKey().toString());*/


                                    //Log.e("images",listOfPictureLinks.get(i)+" msh null yasta wla eh?");
                                    //this counter is just for testing purposes
                                    i++;
                                }
                                productModel.setImagesLinks(listOfPictureLinks);

                            }


                            if(dataSnapshot1.hasChild("category")){
                                productModel.setCategory(dataSnapshot1.child("category").getValue().toString());
                               // Log.e("category",dataSnapshot1.child("category").getValue().toString()+" msh null yasta wla eh?");

                            }
                            if(dataSnapshot1.hasChild("color")){
                                productModel.setColor(dataSnapshot1.child("color").getValue().toString());

                            }
                            if(dataSnapshot1.hasChild("price_range")){
                                productModel.setPriceRange(dataSnapshot1.child("price_range").getValue().toString());

                            }
                            if(dataSnapshot1.hasChild("product_describtion")){
                                productModel.setProductDescribtion(dataSnapshot1.child("product_describtion").getValue().toString());

                            }
                            if(dataSnapshot1.hasChild("product_id")){
                                productModel.setProductId(dataSnapshot1.child("product_id").getValue().toString());

                            }
                            if(dataSnapshot1.hasChild("product_name")){
                                productModel.setProductName(dataSnapshot1.child("product_name").getValue().toString());

                            }
                            if(dataSnapshot1.hasChild("product_price")){
                                productModel.setProdcutPrice(dataSnapshot1.child("product_price").getValue().toString());

                            }
                            if(dataSnapshot1.hasChild("use_id")){
                                productModel.setuId(dataSnapshot1.child("use_id").getValue().toString());

                            }
                            listOfProducts.add(productModel);
                            previewDataOnHome(homeRecycler,progressDialog);

                            adapter.notifyDataSetChanged();


                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



    }

    private void previewDataOnHome(RecyclerView recyclerView,ProgressDialog progressDialog) {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
       // Log.e("previewOnHome",listOfProducts.get(0).getCategory()+"inshallah msh null ");
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();

     }


}
