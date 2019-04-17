package com.example.ss.uploadProductImages;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.ss.AdminProfilePack.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class UploadProductImagePresenter {

    private String productId;
    private Context context;
    private ArrayList<Uri> pictures;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference productRef;
    private ArrayList<String> listOfPictureLinks;
    private StorageReference storage ;

    UploadProductImagePresenter(Context context,String productId){

        this.context=context;
        pictures = new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        Log.e("mauthasj",mAuth.getUid());

        Log.e("iddddd",productId);
        productRef=database.getReference().child("products").child(mAuth.getCurrentUser().getUid()).child(productId);
        storage = FirebaseStorage.getInstance().getReference().child(mAuth.getUid()).child(productId);
        listOfPictureLinks=new ArrayList<>();

    }



     void addPictureToArray(Uri resultUri) {

        pictures.add(resultUri);

    }

    public ArrayList<Uri> getPictures() {
        return pictures;
    }

    public void uploadPicturesToStorage(String productId, final ProgressDialog progressDialog, Context context) {

        this.productId=productId;
        progressDialog.setMessage("uploading your pictures");
        progressDialog.setCancelable(false);
        progressDialog.show();


        if(pictures==null||pictures.size()==0){
            Toast.makeText(context, "please add an image :)", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

        else {

            for (int i = 0; i < pictures.size() ; i++) {
                Log.e("pic size",pictures.size()+"");
                Log.e("item",pictures.get(i)+"");


                final int finalI = i;
                final String fileName=UUID.randomUUID().toString() + ".jpg";
                storage.child(fileName).putFile(pictures.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storage.child(fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.e("", uri + "");
                                String link = uri.toString();
                                listOfPictureLinks.add(link);
                                productRef.child("images").child(UUID.randomUUID().toString()).setValue(link);


                            }
                        });


                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       // Log.e("", listOfPictureLinks.get(finalI) + "");

                        //productRef.child("images").setValue(listOfPictureLinks);
                        progressDialog.dismiss();
                        //sendUserToProfileActivity();


                    }
                });




            }


        }


    }

    private void sendUserToProfileActivity() {
    Intent i = new Intent (context, ProfileFragment.class);
    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);


    }


}
