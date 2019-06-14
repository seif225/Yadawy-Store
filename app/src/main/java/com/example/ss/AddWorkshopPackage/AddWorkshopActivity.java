package com.example.ss.AddWorkshopPackage;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ss.R;
import com.theartofdev.edmodo.cropper.CropImage;

public class AddWorkshopActivity extends AppCompatActivity {

    private EditText workShopTitleEt,formLinkEt;
    private ImageView workshopImage;
    private Button uploadImageBtn, confirmBtn;
    private AddWorkshopPresenter presenter;
    private String workshopTitle;
    private Uri resultUri;
    String formLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workshop);
        initializeFields();
       uploadImageBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               presenter.sendImageToOnActivityResult(AddWorkshopActivity.this);
           }
       });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.uploadDataToFirebase(resultUri,workShopTitleEt,formLinkEt);
            }
        });



    }

    private void initializeFields() {

        workShopTitleEt = findViewById(R.id.add_workshop_name);
        workshopImage = findViewById(R.id.add_workshop_image);
        uploadImageBtn = findViewById(R.id.upload_workshop_image);
        confirmBtn = findViewById(R.id.confirm_workshop);
        presenter = new AddWorkshopPresenter(this);
        //workshopTitle = workShopTitleEt.getText().toString();
        formLinkEt = findViewById(R.id.add_form_link);
        formLink=formLinkEt.getText().toString();


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                workshopImage.setImageURI(resultUri);
                workshopImage.setVisibility(View.VISIBLE);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error+"", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void mfinish() {
    finish();
    }
}
