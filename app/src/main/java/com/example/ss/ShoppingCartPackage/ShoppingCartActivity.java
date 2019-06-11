package com.example.ss.ShoppingCartPackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.ss.R;

public class ShoppingCartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button proceedBtn;
    private ShoppingCartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initializeFields();
        presenter.getDataAndPreview(recyclerView,proceedBtn);

    }

    private void initializeFields() {
    recyclerView=findViewById(R.id.cart_recycler_view);
    proceedBtn=findViewById(R.id.check_out_btn);
    presenter=new ShoppingCartPresenter(this);


    }
}
