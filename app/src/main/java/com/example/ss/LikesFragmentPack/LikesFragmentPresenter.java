package com.example.ss.LikesFragmentPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.ss.HomeFragmentV2Package.NewsFeedRecyclerAdapter;
import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class LikesFragmentPresenter {

    private Context context;
    private DatabaseReference productRef;
    private DatabaseReference userLikesRef;
    private LikeModel model;
    private ArrayList<LikeModel> listOfModels;
    private ArrayList<ProductModel> listofProducts;
    private ProductModel productModel;
    private NewsFeedRecyclerAdapter adapter;
    private ArrayList<String> listOfPictureLinks;

    LikesFragmentPresenter(Context context) {

        this.context = context;
        userLikesRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
        productRef = FirebaseDatabase.getInstance().getReference().child("products");
        listOfModels = new ArrayList<>();
        listofProducts = new ArrayList<>();
        adapter = new NewsFeedRecyclerAdapter(context, listofProducts);


    }


    void getDataAndPreview(final RecyclerView likesRecycler, final ProgressDialog progressDialog) {
        progressDialog.setMessage("getting data..");
        progressDialog.show();

        userLikesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("Likes")) {

                    for (DataSnapshot d : dataSnapshot.child("Likes").getChildren()) {
                        model = new LikeModel();

                        model.setProductId(d.child("productId").getValue().toString());
                        model.setUserId(d.child("userId").getValue().toString());
                        Log.e(model.getProductId()+"hellloo",model.getUserId()+"hha");
                        listOfModels.add(model);

                    }

                    makeListOfProducts(likesRecycler, progressDialog);

                }

                else
                {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void makeListOfProducts(final RecyclerView likesRecycler, final ProgressDialog progressDialog) {



        for (int i = 0; i < listOfModels.size(); i++) {
            Log.e("makelistofp",listOfModels.size()+"heeey");
            final int finalI = i;
            Log.e("makelistofp",listOfModels.get(finalI).getUserId()+" hello from loop " +listOfModels.get(finalI).getProductId());


            productRef.child(listOfModels.get(i).getUserId()).child(listOfModels.get(i).getProductId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {


                    productModel = new ProductModel();

                    if(dataSnapshot1.hasChild("images")){
                        //productModel.setImagesLinks(dataSnapshot.child("images").getValue().toString());
                        int i=0;
                        listOfPictureLinks = new ArrayList<>();
                        for (DataSnapshot imagesDataSnapShot:dataSnapshot1.child("images").getChildren()){

                            listOfPictureLinks.add(imagesDataSnapShot.getValue().toString());

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
                        Log.e("profile Fragment","id check "+productModel.getuId());


                    }

                    if(dataSnapshot1.hasChild("Likers")){

                        productModel.setProductLikes(String.valueOf(dataSnapshot1.child("Likers").getChildrenCount()));
                    }

                    listofProducts.add(productModel);

                    previewDataOnHome(likesRecycler,progressDialog);

                    //adapter.notifyDataSetChanged();







                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });











        }


    }

    private void previewDataOnHome(RecyclerView recyclerView, ProgressDialog progressDialog) {

         Log.e ("PreviewDataOnhome"," LikesFragmentPresenter ");


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setAdapter(adapter);

        progressDialog.dismiss();

    }


}
