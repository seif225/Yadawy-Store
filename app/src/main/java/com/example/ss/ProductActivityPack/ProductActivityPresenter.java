package com.example.ss.ProductActivityPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ss.EditProductPackage.EditProductActivity;
import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.example.ss.MainActivity;
import com.example.ss.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import flepsik.github.com.progress_ring.ProgressRingView;

class ProductActivityPresenter {

    private String productId;
    private DatabaseReference reference;
    private ProductModel productModel;
    private ArrayList<String> listOfPictureLinks;
    private Context context;
    private float rate = 0;
    private List<ReviewModel> listOfReviews;
    private ReviewsAdapter adapter;

    ProductActivityPresenter(Context context, String userId, String productId) {


        this.productId = productId;
        productModel = new ProductModel();
        this.context = context;
        reference = FirebaseDatabase.getInstance().getReference().child("products").child(userId).child(productId);
        listOfPictureLinks = new ArrayList<>();
        listOfReviews = new ArrayList<>();
        adapter = new ReviewsAdapter(listOfReviews, context);

    }

    public ProductModel getProductModel() {
        return productModel;
    }

    void getProductData(final ProgressDialog progressDialog, final SliderLayout sliderLayout, final TextView category,
                        final TextView price, final TextView describtion, final TextView userName, final TextView productIdTv,
                        final CircleImageView userPp, final ProgressRingView progress, final TextView accuRate, final TextView rateCounter, final Button removeProductBtn, final Button edit) {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("getting dataa ..");
        progressDialog.show();


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                listOfPictureLinks.clear();

                if (productModel.getImagesLinks() != null && productModel.getImagesLinks().size() > 0) {
                    productModel.getImagesLinks().clear();
                    listOfPictureLinks.clear();
                }

                if (dataSnapshot.hasChild("images")) {
                    //productModel.setImagesLinks(dataSnapshot.child("images").getValue().toString());

                    for (DataSnapshot imagesDataSnapShot : dataSnapshot.child("images").getChildren()) {

                        listOfPictureLinks.add(imagesDataSnapShot.getValue().toString());

                    }
                    productModel.setImagesLinks(listOfPictureLinks);
                }


                if (dataSnapshot.hasChild("category")) {
                    productModel.setCategory(dataSnapshot.child("category").getValue().toString());
                    // Log.e("category",dataSnapshot1.child("category").getValue().toString()+" msh null yasta wla eh?");
                    category.setText(productModel.getCategory());
                }
                if (dataSnapshot.hasChild("color")) {
                    productModel.setColor(dataSnapshot.child("color").getValue().toString());

                }
                if (dataSnapshot.hasChild("price_range")) {
                    productModel.setPriceRange(dataSnapshot.child("price_range").getValue().toString());

                }
                if (dataSnapshot.hasChild("product_describtion")) {
                    productModel.setProductDescribtion(dataSnapshot.child("product_describtion").getValue().toString());
                    describtion.setText(productModel.getProductDescribtion());
                }
                if (dataSnapshot.hasChild("product_id")) {
                    productModel.setProductId(dataSnapshot.child("product_id").getValue().toString());
                    productIdTv.setText(productModel.getProductId());
                }
                if (dataSnapshot.hasChild("product_name")) {
                    productModel.setProductName(dataSnapshot.child("product_name").getValue().toString());

                }
                if (dataSnapshot.hasChild("product_price")) {
                    productModel.setProdcutPrice(dataSnapshot.child("product_price").getValue().toString());
                    price.setText(productModel.getProdcutPrice());
                }


                if (dataSnapshot.hasChild("use_id")) {
                    productModel.setuId(dataSnapshot.child("use_id").getValue().toString());
                    FirebaseDatabase.getInstance().getReference().child("Users").
                            child(productModel.getuId()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            //  userPp.setImageURI(dataSnapshot.child("image").getValue().toString());
                            if (dataSnapshot.hasChild("image")) {
                                Picasso.get().load(dataSnapshot.child("image").getValue().toString()).
                                        placeholder(R.drawable.user).into(userPp);
                            }
                            userName.setText(dataSnapshot.child("userName").getValue().toString());

                            if (productId != null) {

                                if (productModel.getuId().equals(FirebaseAuth.getInstance().getUid())) {
                                    removeProductBtn.setVisibility(View.VISIBLE);
                                    edit.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


                setSliderViews(productModel.getImagesLinks(), sliderLayout);
                getStats(progress, accuRate, rateCounter);

                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();

            }
        });


    }

    void disLike() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Likes").child(productModel.getProductName()).removeValue();
        FirebaseDatabase.getInstance().getReference().child("products").child(productModel.getuId()).child(productId).child("Likers").
                child(FirebaseAuth.getInstance().getUid()).removeValue();


    }

    void like() {
        HashMap<String, String> likedItem = new HashMap<>();
        likedItem.put("productId", productId);
        likedItem.put("userId", productModel.getuId());

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("Likes").child(productModel.getProductName()).setValue(likedItem);

        HashMap<String, String> liker = new HashMap<>();
        liker.put("userId", FirebaseAuth.getInstance().getUid());
        FirebaseDatabase.getInstance().getReference().child("products").child(productModel.getuId()).child(productId)
                .child("Likers").child(FirebaseAuth.getInstance().getUid()).setValue(liker);


    }


    private void setSliderViews(ArrayList<String> imagesLinks, SliderLayout sliderLayout) {


        if (imagesLinks == null || imagesLinks.size() == 0) {

            SliderView sliderView = new SliderView(context);
            sliderView.setImageDrawable(R.drawable.user);
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(productModel.getProductName());
            sliderView.setDescriptionTextSize(18);
            //            Log.e("product name ",productModel.getProductName());
            sliderLayout.addSliderView(sliderView);
        } else {
            for (int i = 0; i < imagesLinks.size(); i++) {

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


    String getProducName() {

        return productModel.getProductName();
    }

    void updateRating(float rating) {


        updateRatingForUser(rating);
        updateRatingForProduct(rating);


    }

    private void updateRatingForUser(float rating) {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("ratings").child(productModel.getProductId()).child("rate").setValue("" + rating);
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("ratings").child(productModel.getProductId()).child("productId").setValue(productModel.getProductId());


    }

    private void updateRatingForProduct(float rating) {

        FirebaseDatabase.getInstance().getReference().child("products").child(productModel.getuId()).child(productModel.getProductId()).child("ratings").child(FirebaseAuth.getInstance().getUid()).child("rate").setValue("" + rating);
        FirebaseDatabase.getInstance().getReference().child("products").child(productModel.getuId()).child(productModel.getProductId()).child("ratings").child(FirebaseAuth.getInstance().getUid()).child("userId").setValue(productModel.getuId());

    }

    void previewUserRate(final RatingBar ratingBar) {


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("ratings"))

                    if (dataSnapshot.child("ratings").hasChild(productModel.getProductId()))

                        ratingBar.setRating(Float.parseFloat(dataSnapshot.child("ratings").child(productModel.getProductId()).child("rate").getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    void getStats(final ProgressRingView progress, final TextView accuRate, final TextView rateCounter) {
        Log.e("get stats", productModel.getuId() + " " + productModel.getProductId());
        FirebaseDatabase.getInstance().getReference().child("products")
                .child(productModel.getuId()).child(productModel.getProductId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rate = 0;

                if (dataSnapshot.hasChild("ratings")) {

                    for (DataSnapshot d : dataSnapshot.child("ratings").getChildren()) {

                        rate = rate + Float.parseFloat(d.child("rate").getValue().toString()) / dataSnapshot.child("ratings").getChildrenCount();


                    }


                }


                progress.setProgress(rate * 2 / 10);
                // method Math.floor decreases the precision of float
                accuRate.setText(String.valueOf(Math.floor(rate * 100) / 100));
                progress.setAnimated(true);
                progress.setAnimationDuration(500);

                Log.e("productActPresenter", "the rate is :" + rate);

                if ((int) rate == 0) {

                    progress.setProgressColor(Color.RED);

                } else if ((int) rate == 1) {
                    progress.setProgressColor(context.getResources().getColor(R.color.orange));

                } else if ((int) rate == 2) {
                    progress.setProgressColor(context.getResources().getColor(R.color.yellow));


                } else if ((int) rate == 3) {
                    progress.setProgressColor(context.getResources().getColor(R.color.lightGreen));


                } else if ((int) rate == 4) {

                    progress.setProgressColor(Color.GREEN);

                }

                rateCounter.setText(dataSnapshot.child("ratings").getChildrenCount() + " rated this product");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }

    public void removeProduct() {

        FirebaseDatabase.getInstance().getReference().child("products").child(FirebaseAuth.getInstance().getUid()).child(productModel.getProductId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    sendUserToMainActivity();

                }
            }
        });


    }

    private void sendUserToMainActivity() {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void sendUserToEditProductActivity() {

        Intent i = new Intent(context, EditProductActivity.class);
        i.putExtra("uId", productModel.getuId());
        i.putExtra("productId", productModel.getProductId());

        context.startActivity(i);

    }

    public void uploadReviewToFirebase(String review, String userIdOfUploader, String prodcutId, String uid, final DialogInterface dialog) {
        String reviewId = UUID.randomUUID().toString();


        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setReviewId(reviewId);
        reviewModel.setUserId(FirebaseAuth.getInstance().getUid());
        Calendar currentMonth;
        SimpleDateFormat dateFormat;
        currentMonth = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        reviewModel.setUserReview(review);
        reviewModel.setReviewDate(dateFormat.format(currentMonth.getTime()));


        FirebaseDatabase.getInstance().getReference().child("products").child(userIdOfUploader)
                .child(prodcutId).child("reviews").child(reviewId).setValue(reviewModel);

        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(uid).child("reviews").child(prodcutId).child(reviewId).setValue(review).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
            }
        });


    }

    public void getAndPreviewReviews(final RecyclerView reviewRecyclerView, String id, String productId, final Button seeMoreReviews) {

        FirebaseDatabase.getInstance().getReference().child("products").child(id).child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfReviews.clear();

                if (dataSnapshot.hasChild("reviews")) {
                    for (DataSnapshot d : dataSnapshot.child("reviews").getChildren()) {

                        ReviewModel reviewModel = new ReviewModel();
                        reviewModel.setReviewDate(d.child("reviewDate").getValue().toString());
                        reviewModel.setUserId(d.child("userId").getValue().toString());
                        reviewModel.setUserReview(d.child("userReview").getValue().toString());
                        Log.e("getReviews", reviewModel.getReviewDate() + reviewModel.getUserReview() + " (Y)");
                        listOfReviews.add(reviewModel);
                    }


                }
                if (listOfReviews.size() > 5) {
                    seeMoreReviews.setVisibility(View.VISIBLE);
                }
                previewData(reviewRecyclerView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void previewData(RecyclerView reviewRecyclerView) {

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        reviewRecyclerView.addItemDecoration(new DividerItemDecoration(reviewRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        reviewRecyclerView.setAdapter(adapter);


    }


    void addProductToCart(String userId, String prodcutId){

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("cart")
                .child(prodcutId)
                .setValue(productModel);

        Toast.makeText(context, "this product has been added to your cart", Toast.LENGTH_SHORT).show();
    }


}



