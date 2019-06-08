package com.example.ss.EditProductPackage;

import android.content.Context;

import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProductPresenter {

    private ProductModel productModel;
    private Context context;
    private String uId,productId;
    private DatabaseReference productRef;
    EditProductPresenter(Context context , String uId , String productId){
        this.context=context;
        productModel=new ProductModel();
        this.uId=uId;
        this.productId=productId;
        productRef= FirebaseDatabase.getInstance().getReference()
                .child("products").child(uId).child(productId);


    }







}
