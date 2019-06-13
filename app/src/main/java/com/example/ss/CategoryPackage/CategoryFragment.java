package com.example.ss.CategoryPackage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ss.CategoryItems.CategoryItemsActivity;
import com.example.ss.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.smarteist.autoimageslider.SliderLayout;

public class CategoryFragment extends Fragment {
    private ImageView  accessoriesImageView,giftsImageView,krosheshImageView,notebooksImageView;
    private View view;
    private Intent i ;
    private SliderLayout sliderLayout;
    private CategoryPresenter presenter;
    private AdView mAdView;
    PublisherAdView adView;
    private PublisherAdView mPublisherAdView;


    public CategoryFragment() {

    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        intializeFields();
        presenter.fillSlider(sliderLayout);

        accessoriesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i.putExtra("category","Accessories");
                startActivity(i);

            }
        });

        giftsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("category","Customised crafts");
                startActivity(i);
            }
        });

    krosheshImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            i.putExtra("category","Crochet");
            startActivity(i);
        }
    });


    notebooksImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            i.putExtra("category","Tableau gallery");
            startActivity(i);
        }
    });


        return view;
    }

    private void intializeFields() {

    accessoriesImageView = view.findViewById(R.id.accessories_image_view);
    giftsImageView=view.findViewById(R.id.hand_made_gifts);
    krosheshImageView=view.findViewById(R.id.krosheh);
    notebooksImageView=view.findViewById(R.id.note_books);
    i = new Intent (getActivity(), CategoryItemsActivity.class);
        sliderLayout = view.findViewById(R.id.imageSlider_in_cayegory_fragment);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.WORM);
        sliderLayout.animate();
        sliderLayout.setScrollTimeInSec(2);
         adView = new PublisherAdView(getActivity());


        mPublisherAdView = view.findViewById(R.id.adView);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mPublisherAdView.loadAd(adRequest);

    presenter=new CategoryPresenter(getContext());

    }


}
