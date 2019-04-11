package com.example.ss.ProfileEditActivityPack;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ss.R;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.security.Key;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class ProfileEditActivity extends AppCompatActivity {


    EditText userNameEt,bioEt,phoneEt,mailEt,adressEt;
    CircleImageView profilePicture;
    FloatingActionButton floatingActionButton;
    Button updateData;
    ProfileEditActivityPresenter profileEditActivityPresenter;
    Uri resultUri;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_activity);

        intializeFields();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName,bio,phone,mail,adress;
                HashMap<String,Object > info=new HashMap<>();

                userName=userNameEt.getText().toString();
                adress=adressEt.getText().toString();
                phone=phoneEt.getText().toString();
                bio=bioEt.getText().toString();
                mail=mailEt.getText().toString();


                if (userName.isEmpty()){
                userNameEt.setError("you have to write a name");

                }
                else if(adress.isEmpty()){
                    adressEt.setError("you have to write your address");

                }
                else if (phone.isEmpty()){

                phoneEt.setError("you have to write your phone number");

                }

                else{
                    info.put("userName",userName);
                    info.put("phone",phone);
                    info.put("address",adress);
                    if(!bio.isEmpty()){

                        info.put("bio",bio);
                    }
                    if(!mail.isEmpty()){

                        info.put("mail",mail);
                    }

               if(resultUri==null){
                   profileEditActivityPresenter.uploadDataWithoutPicture(info,progressDialog);

               }

                else{
                    profileEditActivityPresenter.uploadData(info,resultUri,progressDialog);


               }

                }

            }
        });




    }

    private void intializeFields() {
    userNameEt=findViewById(R.id.user_name_et);
    bioEt=findViewById(R.id.bio_et);
    phoneEt=findViewById(R.id.phone_et);
    mailEt=findViewById(R.id.mail_address_et);
    adressEt=findViewById(R.id.adress_et);
    profilePicture=findViewById(R.id.profilePicture);
    floatingActionButton=findViewById(R.id.upload_pic_fab);
    updateData=findViewById(R.id.update_data_button);
    profileEditActivityPresenter  = new ProfileEditActivityPresenter(this);
    progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                profilePicture.setImageURI(resultUri);
                Log.e("here",resultUri+"");

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error+"", Toast.LENGTH_SHORT).show();
            }
        }
    }


    void pickImage() {
        CropImage.activity().setAspectRatio(1,1).start(this);
    }




}
