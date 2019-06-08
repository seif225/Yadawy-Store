package com.example.ss.HomeFragmentV2Package;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ss.ProductSort;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

class HomeFragmentV2Presenter {

    private DatabaseReference currentUserRef, productRef;
    private List<String> followersList;
    private ProductModel productModel;
    private static final String TAG = "HomeActivityPresenter";
    private ArrayList<String> listOfPictureLinks ;
    private ArrayList<ProductModel>  ListOfProducts;
    private NewsFeedRecyclerAdapter adapter;
    private  FragmentActivity activity;
    private ProgressDialog progressDialog;


    //Constructor
    HomeFragmentV2Presenter(FragmentActivity activity) {
        //to make sure that the user is logged in as an extra check
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("please wait ..");
        progressDialog.setMessage("getting data..");
        progressDialog.show();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            currentUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
            productRef = FirebaseDatabase.getInstance().getReference().child("products");
            followersList = new ArrayList<>();
            ListOfProducts=new ArrayList<>();


            adapter = new NewsFeedRecyclerAdapter(activity,ListOfProducts);


            //the upcoming operations in the class will be done in this snippt , where the user is 100% logged in


        } else

            Toast.makeText(activity, "you've got problem with Authentication ", Toast.LENGTH_SHORT).show();


    }




    void getDataForHomeActivity(RecyclerView homeRecycler, TextView homeTextView){
        //TODO : Check current user
        Log.e("HomeFragPresenter","v2 : getDataFrom Activity method :(");
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        getFollowersList(homeRecycler,homeTextView);

    }



    private void getFollowersList(final RecyclerView homeRecycler, final TextView homeTextView) {
        currentUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followersList.clear();
                adapter.notifyDataSetChanged();
                if (dataSnapshot.hasChild("following")) {

                    for (DataSnapshot d : dataSnapshot.child("following").getChildren()) {

                        followersList.add(d.getValue().toString());


                    }

                    //after filling the list with the people you follow
                    //the app will search for their products in the next snippt

                    getProductsOfFollowers(homeRecycler,homeTextView);


                } else {

                    previewDataOnView(homeRecycler,homeTextView);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getProductsOfFollowers(final RecyclerView homeRecycler, final TextView homeTextView) {

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ListOfProducts.clear();






                for (int i = 0; i < followersList.size(); i++) {

                    for (DataSnapshot d : dataSnapshot.child(followersList.get(i)).getChildren()) {

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
                            Log.e(TAG, "onDataChange");

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

                        }

                        if (d.hasChild("Likers")) {

                            productModel.setProductLikes(d.child("Likers").getChildrenCount() + "");

                        }
                        if(d.hasChild("product_number")){

                            productModel.setProductNumberAsInt(Integer.parseInt(d.child("product_number").getValue().toString()));
                            Log.e("productNumber",d.child("product_number").getValue().toString()+" test");
                        }

                        adapter.notifyDataSetChanged();
                       ListOfProducts.add(productModel);
                    }




                }

               adapter.notifyDataSetChanged();
                previewDataOnView(homeRecycler,homeTextView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void previewDataOnView(RecyclerView homeRecycler, TextView homeTextView) {

        if(!ListOfProducts.isEmpty()){
            //in this case, list of products has already products
            //we will send these products to the adapter to view it
           // ListOfProducts=new ProductSort().fillTemp(ListOfProducts,ListOfProducts.size());

            homeRecycler.setLayoutManager(new LinearLayoutManager(activity));
            homeRecycler.setAdapter(adapter);
            progressDialog.dismiss();


        }
        else {
            // if the list of products is empty , we will show a message to the user in the text view

            homeTextView.setVisibility(View.VISIBLE);
            homeTextView.setText("no products to show , follow more sellers ;)");
            progressDialog.dismiss();
        }




    }

    ;


}
