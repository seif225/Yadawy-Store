package com.example.ss.CategoryPackage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ss.CategoryItems.CategoryItemsActivity;
import com.example.ss.R;
import com.smarteist.autoimageslider.SliderLayout;

public class CategoryFragment extends Fragment {
    private ImageView  accessoriesImageView,giftsImageView,krosheshImageView,notebooksImageView;
    private View view;
    private Intent i ;
    private SliderLayout sliderLayout;
    private CategoryPresenter presenter;

    public CategoryFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
                i.putExtra("category","Handmade gifts");
                startActivity(i);
            }
        });

    krosheshImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            i.putExtra("category","Krosheh");
            startActivity(i);
        }
    });


    notebooksImageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            i.putExtra("category","Notebooks");
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
    presenter=new CategoryPresenter(getContext());

    }


}