package com.example.ss.AddWorkshopPackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import com.example.ss.MainActivity;
import com.example.ss.WorkShopsPackage.WorkShopsFragment;
import com.example.ss.WorkShopsPackage.WorkshopModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.UUID;

class AddWorkshopPresenter {

    private Context context;

    AddWorkshopPresenter(Context context) {

        this.context = context;


    }


     void sendImageToOnActivityResult(Activity activity) {

         CropImage.activity().start(activity);




    }

     void uploadDataToFirebase(final Uri resultUri, EditText workshopTitleEt, EditText formLinkEt) {
        final String workshopTitle,formLink;
        workshopTitle = workshopTitleEt.getText().toString();
        formLink=formLinkEt.getText().toString();
        if(workshopTitle.equals("") || workshopTitle.isEmpty()) workshopTitleEt.setError("you must write a title");
        else if (formLink.isEmpty() ){formLinkEt.setError("you should add a form link ");}
        else {

            final WorkshopModel workshopModel = new WorkshopModel();
            final String uuid = UUID.randomUUID().toString();

            workshopModel.setWorkShopId(uuid);
            workshopModel.setWorkShopName(workshopTitle);
            workshopModel.setFormLink(formLink);
            FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    workshopModel.setUserId(FirebaseAuth.getInstance().getUid());
                    workshopModel.setUserName(dataSnapshot.child("userName").getValue().toString());


                    final ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("please wait..");
                    progressDialog.setMessage("uploading your data");
                    progressDialog.show();
                    if (resultUri != null) {
                        FirebaseStorage.getInstance().getReference().child(uuid).putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                FirebaseStorage.getInstance().getReference().child(uuid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.e("", uri + "");
                                        String link = uri.toString();
                                        workshopModel.setWorkshopImage(link);


                                        FirebaseDatabase.getInstance()
                                                .getReference().child("workshops").child(uuid).setValue(workshopModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                                            .child("workshops").child(workshopTitle).setValue(uuid);
                                                    progressDialog.dismiss();
                                                    sendUserToWorkshopFragment();
                                                } else progressDialog.dismiss();


                                            }
                                        });

                                    }
                                });

                            }
                        });
                    } else {

                        FirebaseDatabase.getInstance()
                                .getReference().child("workshops").
                                child(uuid).setValue(workshopModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                            .child("workshops").child(workshopTitle).setValue(uuid);
                                    progressDialog.dismiss();
                                    sendUserToWorkshopFragment();

                                } else progressDialog.dismiss();


                            }
                        });


                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





        }
    }

    private void sendUserToWorkshopFragment() {

        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}



