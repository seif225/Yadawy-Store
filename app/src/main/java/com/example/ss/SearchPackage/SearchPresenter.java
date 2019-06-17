package com.example.ss.SearchPackage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ss.HomeFragmentV2Package.NewsFeedRecyclerAdapter;
import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

class SearchPresenter {
    private Context context;
    private ProductModel productModel;
    private ArrayList<String> listOfPictureLinks;
    private NewsFeedRecyclerAdapter adapter;
    private List<ProductModel> listOfProducts;


    SearchPresenter(Context context) {

        this.context = context;
        listOfProducts = new ArrayList<>();
        adapter = new NewsFeedRecyclerAdapter(context, listOfProducts);


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

    List<ProductModel> getListOfProducts() {
        return listOfProducts;
    }

    void handleAnonymousUser() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild("anonymous")) {

                            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("you are logged in as a guest , and this app has amazing features too ," +
                                    " so it's highly recommended to login from the login button above to get benefits from these features ^_^");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    void handleLoginButton(final Button logIn, final ImageView imageView, final SearchView searchView, final RecyclerView recyclerView) {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        Log.e(TAG, "onDataChange: " + FirebaseAuth.getInstance().getUid());


                        if (dataSnapshot.hasChild("anonymous")) {
                            Log.e(TAG, "onDataChange: " + "anon");
                            logIn.setVisibility(View.VISIBLE);
                    /*searchView.setFocusable(true);
                    searchView.setIconified(false);*/
                            search(" ", recyclerView);


                        } else {
                            Log.e(TAG, "onDataChange: " + "unanon");
                            logIn.setVisibility(View.GONE);
                            imageView.setVisibility(View.INVISIBLE);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }
}
