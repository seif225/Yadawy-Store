package com.example.ss.CategoryPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.example.ss.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class CategoryPresenter {

   private  DatabaseReference adsRef;
    private ArrayList<String> images;
    private Context context;
    CategoryPresenter(Context context){
        adsRef= FirebaseDatabase.getInstance().getReference().child("Ads");
        images=new ArrayList<>();
        this.context=context;
    }

    public void fillSlider(final SliderLayout sliderLayout) {

        adsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot d:dataSnapshot.getChildren()) {

                    images.add(d.getValue().toString());

                }


                setSliderViews(images,sliderLayout);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void setSliderViews(ArrayList<String> imagesLinks, SliderLayout sliderLayout) {


        if(imagesLinks==null || imagesLinks.size()==0){

            SliderView sliderView = new SliderView(context);
            sliderView.setImageDrawable(R.drawable.user);
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("");
            sliderView.setDescriptionTextSize(18);
            //            Log.e("product name ",productModel.getProductName());
            sliderLayout.addSliderView(sliderView);
        }

        else{
            for ( int i = 0; i < imagesLinks.size(); i++) {

                SliderView sliderView = new SliderView(context);
                sliderView.setImageUrl(imagesLinks.get(i));
                sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                //sliderView.setDescription(productModel.getProductName());
                sliderView.setDescriptionTextSize(18);
                //            Log.e("product name ",productModel.getProductName());
                sliderLayout.addSliderView(sliderView);
            }

        }
    }
}
