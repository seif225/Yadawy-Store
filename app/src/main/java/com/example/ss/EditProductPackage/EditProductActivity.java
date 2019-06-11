package com.example.ss.EditProductPackage;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ss.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;

import java.net.URI;
import java.util.ArrayList;

public class EditProductActivity extends AppCompatActivity {

    private String uId, productId;
    private EditProductPresenter presenter;
    private RecyclerView recycler;
    private EditText nameEt, priceEt, descriptionEt;
    private Button save, addImages;
    private static final int REQUEST_CODE = 123;
    private ArrayList<String> mResults ;
    private ImageView test;
    private ArrayList<Uri> arrayOfPicsUri;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        initializeFields();

        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });


        presenter.getProductData(recycler, nameEt, priceEt, descriptionEt, save, addImages);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, price, des;
                name = nameEt.getText().toString();
                price = priceEt.getText().toString();
                des = descriptionEt.getText().toString();

                if (name.isEmpty()) {

                    nameEt.setError("you can't leave this empty");
                } else if (price.isEmpty()) {

                    priceEt.setError("you can't leave this empty");
                } else if (des.isEmpty()) {

                    descriptionEt.setError("you can't leave this empty");
                } else {
                    FirebaseDatabase.getInstance().getReference().child("products").child(uId).child(productId).child("product_name").setValue(name);
                    FirebaseDatabase.getInstance().getReference().child("products").child(uId).child(productId).child("product_describtion").setValue(des);
                    FirebaseDatabase.getInstance().getReference().child("products").child(uId).child(productId).child("product_price").setValue(price).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            finish();
                        }
                    });


                }


            }
        });


    }

    private void initializeFields() {
        Intent i = getIntent();
        uId = i.getStringExtra("uId");
        productId = i.getStringExtra("productId");
        Log.e("EditProductActivity", uId + productId + "");
        recycler = findViewById(R.id.recycler_of_edit_product);
        nameEt = findViewById(R.id.product_name_et_edit_product);
        priceEt = findViewById(R.id.price_et_edit_product);
        descriptionEt = findViewById(R.id.description_et_edit_product);
        save = findViewById(R.id.save_data_btn_edit_product);
        addImages = findViewById(R.id.add_images_btn_edit_product);
        test = findViewById(R.id.test);
        arrayOfPicsUri = new ArrayList<>();
        presenter = new EditProductPresenter(this, uId, productId);
       mResults = new ArrayList<>();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                presenter.uploadImagesToFirebase(resultUri);


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
