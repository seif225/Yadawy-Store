package com.example.ss.UserProfile;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ss.HomePackage.NewsFeedRecyclerAdapter;
import com.example.ss.HomePackage.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
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
    private boolean check;
    private ArrayList<ProductModel> listOfProducts;
    private ArrayList<String> listOfPictureLinks;
    private NewsFeedRecyclerAdapter adapter;
    private String profileId;
    private boolean follow_state;

    UserProfilePresenter(Context context, String profileId, Button followButton) {
        this.profileId = profileId;
        this.context = context;
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        productRef = FirebaseDatabase.getInstance().getReference().child("products");
        listOfPictureLinks = new ArrayList<>();
        listOfProducts = new ArrayList<>();
        adapter = new NewsFeedRecyclerAdapter(context, listOfProducts);


    }


    void getAndPreviewUserData(final ProgressDialog progressDialog, final CircleImageView userPp, final TextView numberOfProducts, final TextView userName, TextView followers, TextView following, Button followButton, final RecyclerView productsRecyclerView, final String uid) {

        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userName.setText(dataSnapshot.child("userName").getValue().toString());

                if (dataSnapshot.hasChild("image")) {
                    Picasso.get().load(dataSnapshot.child("image").getValue().toString()).into(userPp);
                }

                productRef.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        numberOfProducts.setText(dataSnapshot.getChildrenCount() + "");

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            productModel = new ProductModel();

                            if (dataSnapshot1.hasChild("images")) {
                                //productModel.setImagesLinks(dataSnapshot.child("images").getValue().toString());
                                int i = 0;
                                listOfPictureLinks = new ArrayList<>();
                                for (DataSnapshot imagesDataSnapShot : dataSnapshot1.child("images").getChildren()) {

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


                            if (dataSnapshot1.hasChild("category")) {
                                productModel.setCategory(dataSnapshot1.child("category").getValue().toString());
                                // Log.e("category",dataSnapshot1.child("category").getValue().toString()+" msh null yasta wla eh?");

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

                            if (dataSnapshot1.hasChild("Likers")) {

                                productModel.setProductLikes(dataSnapshot1.child("Likers").getChildrenCount() + "");

                            }


                            listOfProducts.add(productModel);
                            previewDataOnHome(productsRecyclerView, progressDialog);

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

    private void previewDataOnHome(RecyclerView recyclerView, ProgressDialog progressDialog) {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(adapter);


        progressDialog.dismiss();

    }


    boolean isFollowed(final String uid) {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild("following")) {

                            check = dataSnapshot.child("following").hasChild(uid);
                            Log.e("if", check + "");

                        } else check = false;
                        Log.e("else 1", check + "");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        check = false;
                        Log.e("else 2", check + "");

                    }



                });


        return check;
    }

    void checkButtonSettings(Button followButton) {


        if(profileId.equals(FirebaseAuth.getInstance().getUid())){

            followButton.setVisibility(View.GONE);

        }


        if (isFollowed(profileId)) {

            followButton.setText("follow");
            followButton.setBackgroundColor(Color.GREEN);


        } else {
            followButton.setText("unfollow");
            followButton.setBackgroundColor(Color.RED);
        }
    }

    void follow() {

        userRef.child(FirebaseAuth.getInstance().getUid()).child("following").child(profileId).setValue(profileId);
        userRef.child(profileId).child("followers").child(FirebaseAuth.getInstance().getUid()).setValue(FirebaseAuth.getInstance().getUid());

    }

    void unFollow() {
        userRef.child(FirebaseAuth.getInstance().getUid()).child("following").child(profileId).removeValue();
        userRef.child(profileId).child("followers").child(FirebaseAuth.getInstance().getUid()).removeValue();

    }


}
