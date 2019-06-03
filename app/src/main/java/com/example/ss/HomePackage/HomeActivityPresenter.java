package com.example.ss.HomePackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivityPresenter {

    private Context context;
    private ValueEventListener productListner;
    private RecyclerView homeRecycler;

    public ValueEventListener getProductListner() {
        return productListner;
    }

    public DatabaseReference getProductsRef() {
        return productsRef;
    }

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference productsRef;
    private ProductModel productModel;
    private ArrayList<ProductModel> listOfProducts;
    private ArrayList<String> listOfPictureLinks;
    private NewsFeedRecyclerAdapter adapter;
    private ArrayList<String> followersList;
    private TextView messageText;
    private static final String TAG = "HomeActivityPresenter";
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private ValueEventListener likesListner, userListner;
    private DatabaseReference adapterrLikesRef, adapterUserRef;


    public ValueEventListener getLikesListner() {
        likesListner = adapter.getLikesListner();

        return likesListner;
    }

    public ValueEventListener getUserListner() {
        userListner = adapter.getUserListner();
        return userListner;
    }

    public DatabaseReference getAdapterrLikesRef() {
        adapterrLikesRef = adapter.getLikesRef();
        return adapterrLikesRef;
    }

    public DatabaseReference getAdapterUserRef() {
        adapterUserRef = adapter.getUserRef();
        return adapterUserRef;
    }

    public Context getContext() {

        return context;
    }

    HomeActivityPresenter(Context context, RecyclerView recyclerView, TextView messageText) {

        this.messageText = messageText;
        this.context = context;
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        followersList = new ArrayList<>();
        productsRef = database.getReference().child("products");
        listOfPictureLinks = new ArrayList<>();
        listOfProducts = new ArrayList<>();
        this.recyclerView = recyclerView;
        progressDialog = new ProgressDialog(context);
        adapter = new NewsFeedRecyclerAdapter(context, listOfProducts);


        productListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listOfProducts.clear();
                adapter.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Log.e("onDataChange", "heey");


                    //TODO : Bug
                    // Log.e("following",dataSnapshot1.child("use_id").getValue().toString()+" ya rb msh null");


                    productModel = new ProductModel();

                    if (dataSnapshot1.hasChild("images")) {
                        //productModel.setImagesLinks(dataSnapshot.child("images").getValue().toString());
                        int i = 0;
                        listOfPictureLinks = new ArrayList<>();
                        for (DataSnapshot imagesDataSnapShot : dataSnapshot1.child("images").getChildren()) {

                            listOfPictureLinks.add(imagesDataSnapShot.getValue().toString());

                            i++;
                        }
                        productModel.setImagesLinks(listOfPictureLinks);

                    }


                    if (dataSnapshot1.hasChild("category")) {
                        productModel.setCategory(dataSnapshot1.child("category").getValue().toString());
                        Log.e(TAG, "onDataChange");

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
                    //previewDataOnHome(homeRecycler, progressDialog);
                    // progressDialog.dismiss();
                    //adapter.notifyDataSetChanged();


                }

                progressDialog.dismiss();
                adapter.notifyDataSetChanged();
                previewDataOnHome(homeRecycler, progressDialog);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        };

        productsRef.addValueEventListener(productListner);


    }


    void getAndShowNewsFeedFromFirebase(final RecyclerView homeRecycler) {


        progressDialog.setMessage("getting data");
        progressDialog.setCancelable(false);


//         adapter.notifyDataSetChanged();

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //progressDialog.show();


                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    followersList.add(d.getValue().toString());
                    Log.e("first loop[ ", followersList.get(0));

                }


                if (followersList.size() == 0 || listOfProducts.size() == 0) {
                    progressDialog.dismiss();
                    messageText.setVisibility(View.VISIBLE);
                    messageText.setText("NO DATA :(");
                    Log.e("first loop[ ", followersList.size() + "");

                }


                for (int i = 0; i < followersList.size(); i++) {
                    //getting products
                    productsRef.child(followersList.get(i)).addValueEventListener(productListner);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });


    }


    private void previewDataOnHome(RecyclerView re, ProgressDialog progressDialog) {

        /*Log.e("preview home data",listOfProducts+" hah");
        Log.e("preview home data","without list" +"hah");
*/

        if (followersList.isEmpty() || listOfProducts.isEmpty()) {

            // Toast.makeText(context, "no followers yet", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            //recyclerView.setVisibility(View.GONE);
            Log.e("if", listOfProducts + " hah");
            // messageText.setVisibility(View.VISIBLE);

        } else {

            messageText.setVisibility(View.INVISIBLE);
            Log.e("else", listOfProducts + " haha ");
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            // Log.e("previewOnHome",listOfProducts.get(0).getCategory()+"inshallah msh null ");
            recyclerView.setAdapter(adapter);

            //recyclerView.getLayoutManager().scrollToPosition(adapter.getItemCount());
            progressDialog.dismiss();
        }

    }


    public NewsFeedRecyclerAdapter getAdapter() {
        return adapter;
    }
}
