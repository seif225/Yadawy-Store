package com.example.ss.uploadProductImages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ss.R;
import com.theartofdev.edmodo.cropper.CropImage;

public class UploadProudctImagesActivity extends AppCompatActivity {

    private String productId;
    private UploadProductImagePresenter presenter;
    private Button uploadExtraImage,uploadImages;
    private RecyclerView recyclerView;
    private Uri resultUri;
    ImagesViewRecyclerAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_proudct_images);
    initializeFields();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridItemDecoration(1, 2, true));
        recyclerView.setAdapter(adapter);



        uploadExtraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();

            }
        });

        uploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.uploadPicturesToStorage(productId,progressDialog,UploadProudctImagesActivity.this);

                //Snackbar.make().show();
            }
        });

    }

    private void initializeFields() {

        Intent i = getIntent();
    productId=i.getExtras().getString("productId");
    //Log.e("iddd",productId);
        Log.e("product id in upload",productId);
    presenter=new UploadProductImagePresenter(this,productId);

    uploadExtraImage=findViewById(R.id.add_pic);
    uploadImages=findViewById(R.id.upload_pictures);
    recyclerView = findViewById(R.id.pictures_recycler_view);
        adapter = new ImagesViewRecyclerAdapter(presenter.getPictures(),this);
        progressDialog=new ProgressDialog(this);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                presenter.addPictureToArray(resultUri);
                adapter.notifyDataSetChanged();

               // profilePicture.setImageURI(resultUri);
                Log.e("here",resultUri+"");


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, error+"", Toast.LENGTH_SHORT).show();
            }
        }
    }


    void pickImage() {
        CropImage.activity().start(this);
    }


}
