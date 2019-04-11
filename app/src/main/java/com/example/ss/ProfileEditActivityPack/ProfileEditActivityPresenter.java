package com.example.ss.ProfileEditActivityPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.ss.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class ProfileEditActivityPresenter {


   private  Context context;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference usersRef;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    ProfileEditActivityPresenter(Context context) {

        this.context = context;
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        usersRef=database.getReference().child("Users");
        storage=FirebaseStorage.getInstance();
        storageRef=storage.getReference();
    }


     void uploadDataWithoutPicture(HashMap<String, Object> info, final ProgressDialog progressDialog) {
        progressDialog.setMessage("updating your data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        usersRef.child(auth.getUid()).updateChildren(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                progressDialog.dismiss();
                sendUsertoMain();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(context, task.getException()+"", Toast.LENGTH_SHORT).show();
                    Log.e("error",task.getException()+"");
                }
            }


        });



    }

     void uploadData(HashMap<String, Object>info, final Uri resultUri, final ProgressDialog progressDialog) {
        progressDialog.setMessage("updating your data...");
         progressDialog.setCancelable(false);
         progressDialog.show();

         usersRef.child(auth.getUid()).updateChildren(info).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful()){

                     uploadPictureToStorageAndFirebase(resultUri,progressDialog);

                 }
                 else{
                     progressDialog.dismiss();
                     Toast.makeText(context, task.getException()+"", Toast.LENGTH_SHORT).show();
                     Log.e("error",task.getException()+"");
                 }
             }

             private void sendUsertoMain() {
                 Intent i = new Intent(context, MainActivity.class);
                 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(i);

             }
         });



    }

    private void uploadPictureToStorageAndFirebase(Uri resultUri, final ProgressDialog progressDialog) {


        storageRef.child(auth.getUid()+".jpg").putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                storageRef.child(auth.getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("", uri + "");
                        String link = uri.toString();
                        uploadLinkToFirebase(link, progressDialog);


                    }


                });

            }
        });



    }

    private void uploadLinkToFirebase(String link, final ProgressDialog progressDialog) {

        usersRef.child(auth.getUid()).child("image").setValue(link).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                sendUsertoMain();

            }
        });


    }
    private void sendUsertoMain() {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
