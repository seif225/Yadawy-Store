package com.example.ss.CategoryItems;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ss.HomePackage.NewsFeedRecyclerAdapter;
import com.example.ss.HomePackage.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryItemPresenter {

    Context context;
    DatabaseReference productsReference;
    private ProductModel productModel;
    private ArrayList<ProductModel> listOfProducts;
    private ArrayList<String> listOfPictureLinks;
    private NewsFeedRecyclerAdapter adapter;
    private String category;
    public CategoryItemPresenter(Context context,String category) {
    Log.e("categoryItemPresenter",category+"ssamo aleko");
    this.context=context;
    productsReference= FirebaseDatabase.getInstance().getReference().child("products");
        listOfProducts=new ArrayList<>();
        this.category=category;
        listOfPictureLinks = new ArrayList<>();
        adapter=new NewsFeedRecyclerAdapter(context,listOfProducts);



    }

    public void getandPreviewData(final ProgressDialog progressDialog, final RecyclerView recyclerView) {

    progressDialog.setCancelable(false);
    progressDialog.setTitle("getting data ..");
    progressDialog.setMessage("please wait ..");
    progressDialog.show();


        productsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfProducts.clear();
                adapter.notifyDataSetChanged();
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1: d.getChildren()) {
                        productModel = new ProductModel();

                        if (dataSnapshot1.child("category").getValue().equals(category)) {

                            if (dataSnapshot1.hasChild("images")) {
                                //productModel.setImagesLinks(dataSnapshot.child("images").getValue().toString());
                                int i = 0;
                                listOfPictureLinks = new ArrayList<>();
                                for (DataSnapshot imagesDataSnapShot : dataSnapshot1.child("images").getChildren()) {

                                    listOfPictureLinks.add(imagesDataSnapShot.getValue().toString());

                                }
                                productModel.setImagesLinks(listOfPictureLinks);

                            }


                            if (dataSnapshot1.hasChild("category")) {
                                productModel.setCategory(dataSnapshot1.child("category").getValue().toString());
                                Log.e("category",dataSnapshot1.child("category").getValue().toString()+" msh null yasta wla eh?");

                            }
                            if (dataSnapshot1.hasChild("color")) {
                                productModel.setColor(dataSnapshot1.child("color").getValue().toString());

                            }
                            if (dataSnapshot1.hasChild("price_range")) {
                                productModel.setPriceRange(dataSnapshot1.child("price_range").getValue().toString());

                            }
                            if (dataSnapshot1.hasChild("product_describtion")) {
                                productModel.setProductDescribtion(dataSnapshot1.child("product_describtion").getValue().toString());

                            }
                            if (dataSnapshot1.hasChild("product_id")) {
                                productModel.setProductId(dataSnapshot1.child("product_id").getValue().toString());

                            }
                            if (dataSnapshot1.hasChild("product_name")) {
                                productModel.setProductName(dataSnapshot1.child("product_name").getValue().toString());

                            }
                            if (dataSnapshot1.hasChild("product_price")) {
                                productModel.setProdcutPrice(dataSnapshot1.child("product_price").getValue().toString());

                            }
                            if (dataSnapshot1.hasChild("use_id")) {
                                productModel.setuId(dataSnapshot1.child("use_id").getValue().toString());

                            }
                            listOfProducts.add(productModel);
                            previewDataOnHome(recyclerView, progressDialog);

                            adapter.notifyDataSetChanged();


                        }

                    }
                }
                    progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();



            }
        });





    }

    private void previewDataOnHome(RecyclerView recyclerView,ProgressDialog progressDialog) {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // Log.e("previewOnHome",listOfProducts.get(0).getCategory()+"inshallah msh null ");
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();



}}
