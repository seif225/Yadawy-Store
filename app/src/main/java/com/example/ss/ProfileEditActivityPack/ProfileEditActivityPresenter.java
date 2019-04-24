package com.example.ss.ProfileEditActivityPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ss.MainActivity;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class ProfileEditActivityPresenter {


    private Context context;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference usersRef;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    ProfileEditActivityPresenter(Context context) {

        this.context = context;
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        usersRef = database.getReference().child("Users");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }


    void uploadDataWithoutPicture(HashMap<String, Object> info, final ProgressDialog progressDialog) {
        progressDialog.setMessage("updating your data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        usersRef.child(auth.getUid()).updateChildren(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    sendUsertoMain();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, task.getException() + "", Toast.LENGTH_SHORT).show();
                    Log.e("error", task.getException() + "");
                }
            }


        });


    }

    void uploadData(HashMap<String, Object> info, final Uri resultUri, final ProgressDialog progressDialog) {
        progressDialog.setMessage("updating your data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        usersRef.child(auth.getUid()).updateChildren(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    uploadPictureToStorageAndFirebase(resultUri, progressDialog);

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, task.getException() + "", Toast.LENGTH_SHORT).show();
                    Log.e("error", task.getException() + "");
                }
            }

            private void sendUsertoMain() {
                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });


    }

    private void uploadPictureToStorageAndFirebase(Uri resultUri, final ProgressDialog progressDialog) {


        storageRef.child(auth.getUid() + ".jpg").putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                storageRef.child(auth.getUid() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

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
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }

    public void fillData(final ProgressDialog progressDialog, final EditText userNameEt, final EditText bioEt, final EditText phoneEt
            , final EditText mailEt, final EditText adressEt, final CircleImageView circleImageView) {


        progressDialog.setMessage("please wait ..");
        progressDialog.setTitle("getting your data");
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("userName")) {
                userNameEt.setText(dataSnapshot.child("userName").getValue().toString());
                }
                if (dataSnapshot.hasChild("bio")) {
                    bioEt.setText(dataSnapshot.child("bio").getValue().toString());

                }
                if (dataSnapshot.hasChild("phone")) {
                    phoneEt.setText(dataSnapshot.child("phone").getValue().toString());

                }
                if (dataSnapshot.hasChild("mail")) {
                    mailEt.setText(dataSnapshot.child("mail").getValue().toString());

                }
                if (dataSnapshot.hasChild("address")) {
                    adressEt.setText(dataSnapshot.child("address").getValue().toString());

                }
                if (dataSnapshot.hasChild("image")) {
                    //adressEt.setText(dataSnapshot.child("image").getValue().toString());
                    Picasso.get().load(dataSnapshot.child("image").getValue().toString()).into(circleImageView);
                }


                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
        progressDialog.dismiss();


    }



}
