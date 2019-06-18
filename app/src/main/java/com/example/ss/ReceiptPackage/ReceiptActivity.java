package com.example.ss.ReceiptPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ss.R;

public class ReceiptActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReceiptPresenter presenter;
    private TextView totalTv;
    private String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        initializeFields();
        presenter.finalizeOrderAndCheckOut(recyclerView,orderId);
    }

    private void initializeFields() {

        Intent i = getIntent();
         orderId = i.getStringExtra("orderId");
        Log.e("receipt activity", "initializeFields: " + orderId);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.receipt_recycler);
        totalTv = findViewById(R.id.total_receipt);
        presenter = new ReceiptPresenter(this,totalTv);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);

    }

}
