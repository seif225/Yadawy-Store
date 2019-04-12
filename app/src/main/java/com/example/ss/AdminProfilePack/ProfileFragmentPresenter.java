package com.example.ss.AdminProfilePack;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragmentPresenter {

    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private String userName, bio, image;

    ProfileFragmentPresenter(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userRef = database.getReference().child("Users").child(mAuth.getUid());

    }


    void retriveUserData(final ProgressDialog progressDialog, final TextView tv1, final CircleImageView pp, final TextView tv2) {
        progressDialog.setMessage("getting your data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userName = dataSnapshot.child("userName").getValue().toString();
                Log.e("username", userName + "");
                if (dataSnapshot.hasChild("image")) {
                    image = dataSnapshot.child("image").getValue().toString();
                    Log.e("image", image + "");

                }

                if (dataSnapshot.hasChild("bio")) {

                    bio = dataSnapshot.child("bio").getValue().toString();
                    Log.e("bio", bio + "");

                }


                fetchData(progressDialog,tv1, pp, tv2);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    String getUserName() {
        return userName;
    }

    String getImage() {


        return image;
    }

    String getBio() {
        if (bio == null) {

            return "no bio";
        }
        return bio;
    }

    private void fetchData(ProgressDialog progressDialog, TextView v1, CircleImageView v2, TextView v3) {
        Log.e("username Frag", this.getUserName() + "");
        Log.e("image Frag", this.getImage() + "");
        Log.e("bio Frag ", this.getBio() + "");

        v3.setText(this.getBio());
        Picasso.get().load(this.getImage()).placeholder(R.drawable.user).into(v2);
        v1.setText(this.getUserName());
        progressDialog.dismiss();


    }

}
