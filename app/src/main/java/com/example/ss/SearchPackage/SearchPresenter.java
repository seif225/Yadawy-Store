package com.example.ss.SearchPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ss.HomeFragmentV2Package.NewsFeedRecyclerAdapter;
import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

class SearchPresenter {
    private Context context;
    private ProductModel productModel;
    private ArrayList<String> listOfPictureLinks;
    private NewsFeedRecyclerAdapter adapter;
    private List<ProductModel> listOfProducts;


    SearchPresenter(Context context) {

        this.context = context;
        listOfProducts=new ArrayList<>();
        adapter = new NewsFeedRecyclerAdapter(context,listOfProducts);


    }


    public void search(final String s, final RecyclerView recyclerView) {


        FirebaseDatabase.getInstance().getReference().child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    for (DataSnapshot d : dataSnapshot1.getChildren()) {


                        Log.e("anon datasnap", d + " this is d");

                        productModel = new ProductModel();

                        if (d.hasChild("images")) {
                            //productModel.setImagesLinks(dataSnapshot.child("images").getValue().toString());

                            listOfPictureLinks = new ArrayList<>();
                            for (DataSnapshot imagesDataSnapShot : d.child("images").getChildren()) {

                                listOfPictureLinks.add(imagesDataSnapShot.getValue().toString());


                            }
                            productModel.setImagesLinks(listOfPictureLinks);

                        }


                        if (d.hasChild("category")) {
                            productModel.setCategory(d.child("category").getValue().toString());
                            Log.e("searchPresenter", "onDataChange");

                        }
                        if (d.hasChild("color")) {
                            productModel.setColor(d.child("color").getValue().toString());

                        }
                        if (d.hasChild("price_range")) {
                            productModel.setPriceRange(d.child("price_range").getValue().toString());

                        }
                        if (d.hasChild("product_describtion")) {
                            productModel.setProductDescribtion(d.child("product_describtion").getValue().toString());

                        }
                        if (d.hasChild("product_id")) {
                            productModel.setProductId(d.child("product_id").getValue().toString());

                        }
                        if (d.hasChild("product_name")) {
                            productModel.setProductName(d.child("product_name").getValue().toString());
                        }

                        if (d.hasChild("product_price")) {
                            productModel.setProdcutPrice(d.child("product_price").getValue().toString());

                        }
                        if (d.hasChild("use_id")) {
                            productModel.setuId(d.child("use_id").getValue().toString());

                            FirebaseDatabase.getInstance().getReference().child("Users").child(productModel.getuId())
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            productModel.setUserName(dataSnapshot.child("userName").getValue().toString());
                                            Log.e("username", productModel.getUserName());
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                        }

                        if (d.hasChild("Likers")) {

                            productModel.setProductLikes(d.child("Likers").getChildrenCount() + "");

                        }
                        if (d.hasChild("product_number")) {

                            productModel.setProductNumberAsInt(Integer.parseInt(d.child("product_number").getValue().toString()));
                            Log.e("productNumber", d.child("product_number").getValue().toString() + " test");
                        }


                        if (productModel.getProductName().toLowerCase().contains(s.toLowerCase())) {

                            listOfProducts.add(productModel);

                        }


                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public List<ProductModel> getListOfProducts() {
        return listOfProducts;
    }
}
