package com.example.ss.ProductActivityPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ss.HomePackage.ProductModel;
import com.example.ss.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

class ProductActivityPresenter {

    private String userId,productId;
    private DatabaseReference reference;
    private ProductModel productModel;
    private ArrayList<String>listOfPictureLinks;
    private Context context;
    ProductActivityPresenter(Context context, String userId, String productId){

        this.userId=userId;
        this.productId=productId;
        productModel=new ProductModel();
        this.context=context;
    reference= FirebaseDatabase.getInstance().getReference().child("products").child(userId).child(productId);

    }

     void getProductData(final ProgressDialog progressDialog, final SliderLayout sliderLayout, final TextView category, final TextView price, final TextView describtion) {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("getting dataa ..");
        progressDialog.show();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("images")){
                    //productModel.setImagesLinks(dataSnapshot.child("images").getValue().toString());
                    int i=0;
                    listOfPictureLinks = new ArrayList<>();
                    for (DataSnapshot imagesDataSnapShot:dataSnapshot.child("images").getChildren()){

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


                if(dataSnapshot.hasChild("category")){
                    productModel.setCategory(dataSnapshot.child("category").getValue().toString());
                    // Log.e("category",dataSnapshot1.child("category").getValue().toString()+" msh null yasta wla eh?");
                    category.setText(productModel.getCategory());
                }
                if(dataSnapshot.hasChild("color")){
                    productModel.setColor(dataSnapshot.child("color").getValue().toString());

                }
                if(dataSnapshot.hasChild("price_range")){
                    productModel.setPriceRange(dataSnapshot.child("price_range").getValue().toString());

                }
                if(dataSnapshot.hasChild("product_describtion")){
                    productModel.setProductDescribtion(dataSnapshot.child("product_describtion").getValue().toString());
                    describtion.setText(productModel.getProductDescribtion());
                }
                if(dataSnapshot.hasChild("product_id")){
                    productModel.setProductId(dataSnapshot.child("product_id").getValue().toString());

                }
                if(dataSnapshot.hasChild("product_name")){
                    productModel.setProductName(dataSnapshot.child("product_name").getValue().toString());

                }
                if(dataSnapshot.hasChild("product_price")){
                    productModel.setProdcutPrice(dataSnapshot.child("product_price").getValue().toString());
                    price.setText(productModel.getProdcutPrice());
                }
                if(dataSnapshot.hasChild("use_id")){
                    productModel.setuId(dataSnapshot.child("use_id").getValue().toString());

                }
                setSliderViews(productModel.getImagesLinks(),sliderLayout);

              progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });


    }

    private void setSliderViews(ArrayList<String> imagesLinks, SliderLayout sliderLayout) {


        if(imagesLinks==null || imagesLinks.size()==0){

            SliderView sliderView = new SliderView(context);
            sliderView.setImageDrawable(R.drawable.user);
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(productModel.getProductName());
            sliderView.setDescriptionTextSize(18);
            //            Log.e("product name ",productModel.getProductName());
            sliderLayout.addSliderView(sliderView);
        }

        else{
        for ( int i = 0; i < imagesLinks.size(); i++) {

            SliderView sliderView = new SliderView(context);
            sliderView.setImageUrl(listOfPictureLinks.get(i));
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(productModel.getProductName());
            sliderView.setDescriptionTextSize(18);
            //            Log.e("product name ",productModel.getProductName());
            sliderLayout.addSliderView(sliderView);
        }

    }
    }

}



