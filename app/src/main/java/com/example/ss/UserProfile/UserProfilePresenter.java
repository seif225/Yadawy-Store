package com.example.ss.UserProfile;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.ss.HomePackage.NewsFeedRecyclerAdapter;
import com.example.ss.HomePackage.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class UserProfilePresenter {

    private Context context;
    private DatabaseReference userRef, productRef;
    private ProductModel productModel;

    private ArrayList<ProductModel> listOfProducts;
    private ArrayList<String> listOfPictureLinks;
    private NewsFeedRecyclerAdapter adapter;

    UserProfilePresenter(Context context) {

        this.context=context;
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        productRef=FirebaseDatabase.getInstance().getReference().child("products");
        listOfPictureLinks = new ArrayList<>();
        listOfProducts=new ArrayList<>();

        adapter=new NewsFeedRecyclerAdapter(context,listOfProducts);

    }


      void getAndPreviewUserData(final ProgressDialog progressDialog, final CircleImageView userPp, final TextView numberOfProducts, final TextView userName, TextView followers, TextView following, BootstrapButton followButton, final RecyclerView productsRecyclerView, final String uid) {

        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            userName.setText(dataSnapshot.child("userName").getValue().toString());

            if(dataSnapshot.hasChild("image")) {
                Picasso.get().load(dataSnapshot.child("image").getValue().toString()).into(userPp);
            }

                productRef.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        numberOfProducts.setText(dataSnapshot.getChildrenCount()+"");

                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
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

                            if(dataSnapshot1.hasChild("Likers")){

                                productModel.setProductLikes(dataSnapshot1.child("Likers").getChildrenCount()+"");

                            }


                            listOfProducts.add(productModel);
                            previewDataOnHome(productsRecyclerView,progressDialog);

                            adapter.notifyDataSetChanged();


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



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

        //recyclerView.getLayoutManager().scrollToPosition(adapter.getItemCount());
        progressDialog.dismiss();

    }



}
