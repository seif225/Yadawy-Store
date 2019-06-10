package com.example.ss.EditProductPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

public class EditProductPresenter {

    private ProductModel productModel;
    private Context context;
    private String uId, productId;
    private DatabaseReference productRef;
    private ArrayList<ImageModel> list;
    private ImagesRecyclerAdapter adapter;
    private static final int REQUEST_CODE = 123;
    //private ArrayList<String> mResults = new ArrayList<>();
    private ArrayList<String> listOfPictureLinks;
    private StorageReference storage ;



    EditProductPresenter(Context context, String uId, String productId) {
        this.context = context;
        productModel = new ProductModel();
        this.uId = uId;
        this.productId = productId;
        productRef = FirebaseDatabase.getInstance().getReference()
                .child("products").child(uId).child(productId);
        list = new ArrayList<>();
        adapter = new ImagesRecyclerAdapter(list, context, uId, productId);
        listOfPictureLinks = new ArrayList<>();
        storage = FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child(productId);


    }


    void getProductData(final RecyclerView recycler, final EditText nameEt, final EditText priceEt, final EditText descriptionEt, final Button save, final Button addImages) {
        FirebaseDatabase.getInstance().getReference().child("products").child(uId).child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                adapter.notifyDataSetChanged();
                if (dataSnapshot.hasChild("images")) {

                    for (DataSnapshot d : dataSnapshot.child("images").getChildren()) {
                        ImageModel model = new ImageModel();
                        model.setImage(d.getValue().toString());
                        model.setImageName(d.getKey());
                        list.add(model);
                    }

                    if(dataSnapshot.hasChild("product_name")){
                        nameEt.setText(dataSnapshot.child("product_name").getValue().toString());
                    }
                    if(dataSnapshot.hasChild("product_price")){
                        priceEt.setText(dataSnapshot.child("product_price").getValue().toString());

                    }
                    if(dataSnapshot.hasChild("product_describtion")){
                        descriptionEt.setText(dataSnapshot.child("product_describtion").getValue().toString());

                    }

    
                }


                previewDataOnActivity(recycler);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void previewDataOnActivity(RecyclerView recycler) {

        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.setAdapter(adapter);


    }


    public void uploadImagesToFirebase(final Uri uri) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("uploading your images");
        progressDialog.setTitle("please wait ..");
        progressDialog.show();


        if(uri==null){
            Toast.makeText(context, "please add an image :)", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

        else {


             /*   Log.e("pic size",mResults.size()+"");
                Log.e("item",mResults.get(i)+"");

*/

                final String fileName= UUID.randomUUID().toString() + ".jpg";
                storage.child(fileName).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storage.child(fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.e("", uri + "");
                                String link = uri.toString();
                                listOfPictureLinks.add(link);
                                FirebaseDatabase.getInstance().getReference()
                                        .child("products").child(uId).child(productId)
                                        .child("images").child(UUID.randomUUID().toString()).setValue(link);


                            }
                        });


                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        // Log.e("", listOfPictureLinks.get(finalI) + "");

                        //productRef.child("images").setValue(listOfPictureLinks);
                        progressDialog.dismiss();
                        Toast.makeText(context, "your picture has been uploaded ", Toast.LENGTH_SHORT).show();
                        //sendUserToProfileActivity();


                    }
                });




            }


        }








    }

