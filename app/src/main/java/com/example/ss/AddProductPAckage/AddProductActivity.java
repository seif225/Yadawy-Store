package com.example.ss.AddProductPAckage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ss.R;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity {

    private ImageButton sendToUploadImageButton;
    private EditText productName , productPrice,productDescribtion;
    Spinner colorSpinner,categorySpinner,priceRangeSpinner;
    Button uploadButton;
    AddProductPresenter presenter;
    String productNameString,productPriceString,productDescribtionString,colorString,categoryString,
            priceRangeString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
            intializeFields();
            uploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    productNameString=productName.getText().toString();
                    productPriceString=productPrice.getText().toString();
                    productDescribtionString=productDescribtion.getText().toString();
                    colorString=colorSpinner.getSelectedItem().toString();
                    categoryString=categorySpinner.getSelectedItem().toString();
                    priceRangeString=priceRangeSpinner.getSelectedItem().toString();
                    validateAndSend();



                }
            });





    }

    private void validateAndSend() {

    if(productNameString.isEmpty()){
        productName.setError("you cant leave the name empty");
        productName.requestFocus();
    }
       else if(productPriceString.isEmpty()){
            productName.setError("you cant leave the price empty");
            productName.requestFocus();
        }
        else if(productDescribtionString.isEmpty()){
            productName.setError("you cant leave the describtion empty");
            productName.requestFocus();
        }

       else  if(colorString.isEmpty() || categoryString.isEmpty() || priceRangeString.isEmpty()){
            Toast.makeText(this, "you should fill all the info", Toast.LENGTH_SHORT).show();
        }

        else {
            presenter.uploadProductDataToFirebase(productNameString,productPriceString,productDescribtionString,colorString,categoryString,
                    priceRangeString);
//            startActivity(new Intent(this, UploadProudctImagesActivity.class));

    }

    }

    private void intializeFields() {


    productName = findViewById(R.id.product_name_et_edit_product);
    productPrice=findViewById(R.id.price_et_edit_product);
    productDescribtion=findViewById(R.id.description_et_edit_product);
    colorSpinner = findViewById(R.id.color_spinner);
    categorySpinner=findViewById(R.id.category_spinner);
    priceRangeSpinner=findViewById(R.id.price_range_spinner);
    uploadButton=findViewById(R.id.upload_product_data_btn_edit_product);
    presenter=new AddProductPresenter(this);
    callSpinnerLists();
    }

    private void callSpinnerLists() {


        final ArrayList<String> colorList=new ArrayList<>();
        colorList.add("white");
        colorList.add("red");
        colorList.add("yellow");
        colorList.add("blue");
        colorList.add("black");
        colorList.add("green");
        colorList.add("orange");
        colorList.add("aqua");
        colorList.add("indigo");
        colorList.add("violet");
        colorList.add("gray");
        colorList.add("beige");
        colorList.add("other");
        colorSpinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,colorList));


        final ArrayList<String>priceRange=new ArrayList<>();
        priceRange.add("50-100");
        priceRange.add("100-200");
        priceRange.add("200-500");
        priceRange.add("500-1000");
        priceRange.add("more than 1000");

        priceRangeSpinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,priceRange));

        final ArrayList<String>category=new ArrayList<>();
        category.add("Accessories");
        category.add("Handmade gifts");
        category.add("Krosheh");
        category.add("Notebooks");

        //category.add("DIY");


        categorySpinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,category));



    }


}
