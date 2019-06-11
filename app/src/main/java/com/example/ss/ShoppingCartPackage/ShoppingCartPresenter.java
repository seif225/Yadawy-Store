package com.example.ss.ShoppingCartPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartPresenter {
    private Context context;
    private DatabaseReference cartRef;
    private List<ProductModel> listOfProducts;
    private ShoppingCartRecyclerAdapter adapter;

    public ShoppingCartPresenter(Context context) {
        this.context = context;
        cartRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("cart");
        listOfProducts = new ArrayList<>();
        adapter = new ShoppingCartRecyclerAdapter(listOfProducts, context);
    }


    public void getDataAndPreview(final RecyclerView recyclerView, Button proceedBtn) {


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild("cart")) {

                            cartRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    listOfProducts.clear();
                                    adapter.notifyDataSetChanged();

                                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                                        Log.e("test" , d.toString());

                                        ProductModel productModel = new ProductModel();

                                        if (d.hasChild("imagesLinks")) {

                                            productModel.setThumbnail(d.child("imagesLinks").child("0").getValue().toString());

                                        }

                                        productModel.setProductName(d.child("productName").getValue().toString());
                                        productModel.setProductId(d.child("productId").getValue().toString());
                                        productModel.setPriceAsInt(Integer.parseInt(d.child("prodcutPrice").getValue().toString()));
                                        productModel.setuId(d.child("uId").getValue().toString());
                                        Log.e("shoppingCartPresenter",productModel.getProductName()+productModel.getPriceAsInt()+" test ! ");


                                        listOfProducts.add(productModel);

                                    }


                                    previewDataOnActivity(recyclerView);


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    private void previewDataOnActivity(RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(adapter);


    }
}
