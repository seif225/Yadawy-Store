package com.example.ss.HomePackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.StringJoiner;

public class HomeActivityPresenter {

    private Context context ;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference productsRef;
    private ProductModel productModel;
    private ArrayList<ProductModel> listOfProducts;
    private ArrayList<String> listOfPictureLinks;
    private NewsFeedRecyclerAdapter adapter;
    private ArrayList<String> followersList;
    private TextView messageText;

private ProgressDialog progressDialog;
    HomeActivityPresenter(Context context, TextView messageText) {

        this.messageText=messageText;
         this.context=context;
         database = FirebaseDatabase.getInstance();
         mAuth=FirebaseAuth.getInstance();
         followersList=new ArrayList<>();
        productsRef = database.getReference().child("products");
         listOfPictureLinks = new ArrayList<>();
         listOfProducts=new ArrayList<>();
         progressDialog= new ProgressDialog(context);
         adapter=new NewsFeedRecyclerAdapter(context,listOfProducts);


     }


     void getAndShowNewsFeedFromFirebase( final RecyclerView homeRecycler) {

            progressDialog.setMessage("getting data");
            progressDialog.setCancelable(false);


//         adapter.notifyDataSetChanged();

         FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("following").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 //progressDialog.show();



                 for (DataSnapshot d: dataSnapshot.getChildren()) {
                     followersList.add(d.getValue().toString());
                        Log.e("first loop[ ", followersList.get(0));

                 }


                 if (followersList.size()==0 || listOfProducts.size()==0    ){
                     progressDialog.dismiss();
                     messageText.setVisibility(View.VISIBLE);
                     messageText.setText("you have no followers");
                     Log.e("first loop[ ", followersList.size()+"");

                 }


                 for (int i = 0; i <followersList.size() ; i++) {
                    //getting products
                     productsRef.child(followersList.get(i)).addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             listOfProducts.clear();
                             adapter.notifyDataSetChanged();

                             for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                                    Log.e("onDataChange","heey");


                                         productModel = new ProductModel();
                                         //TODO : Bug
                                        // Log.e("following",dataSnapshot1.child("use_id").getValue().toString()+" ya rb msh null");

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
                                              Log.e("category",dataSnapshot1.child("category").getValue().toString()+" msh null yasta wla eh?");

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
                                        // previewDataOnHome(homeRecycler, progressDialog);
                                            progressDialog.dismiss();
                                         adapter.notifyDataSetChanged();


                                     }
                             progressDialog.dismiss();
                             previewDataOnHome(homeRecycler, progressDialog);


                         }







                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                             progressDialog.dismiss();

                         }
                     });




                 }


             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

                 progressDialog.dismiss();

             }
         });





    }


    private void previewDataOnHome(RecyclerView recyclerView,ProgressDialog progressDialog) {

        /*Log.e("preview home data",listOfProducts+" hah");
        Log.e("preview home data","without list" +"hah");
*/

        if(followersList.isEmpty() || listOfProducts.isEmpty()){

            Toast.makeText(context, "no followers yet", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            //recyclerView.setVisibility(View.GONE);
            Log.e("if",listOfProducts+" hah");
           // messageText.setVisibility(View.VISIBLE);

        }
        else
        {
            messageText.setVisibility(View.INVISIBLE);
            Log.e("else",listOfProducts+" haha ");
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            // Log.e("previewOnHome",listOfProducts.get(0).getCategory()+"inshallah msh null ");
            recyclerView.setAdapter(adapter);

            //recyclerView.getLayoutManager().scrollToPosition(adapter.getItemCount());
            progressDialog.dismiss();
        }

     }


}
