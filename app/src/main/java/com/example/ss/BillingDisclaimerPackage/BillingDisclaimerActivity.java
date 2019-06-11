package com.example.ss.BillingDisclaimerPackage;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ss.R;
import com.google.firebase.auth.FirebaseAuth;

public class BillingDisclaimerActivity extends AppCompatActivity {

    private TextView disclaimer, currentDate, nextDate, termsAndConditions;
    private Button agree;
    private BillingDisclaimerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_disclaimer);
        initializeFields();

        fillFields();

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.upLoadBillingDateToFirebaseAndBeyond();
            }
        });
    }

    private void fillFields() {
    disclaimer.setText("DISCLAIMER: you're on a free trail which means that you won't be charged any fees for this month , however, starting from the next billing date you will be charged 50 L.E " +
            ", the charged amount will be collected automatically , in case of having insufficient cash to precede , your plan will be changed to a user account , which means that you won't be able to upload any product" +
            ", to know more read  terms and conditions  ");

if(FirebaseAuth.getInstance().getUid()!=null) {
    currentDate.setText(presenter.getCurrentDateFormat());
    nextDate.setText(presenter.getNextMonthFormat());
}

    }

    private void initializeFields() {

        disclaimer = findViewById(R.id.disclaimer);

        currentDate = findViewById(R.id.current_date);
        nextDate = findViewById(R.id.billing_date);
        termsAndConditions = findViewById(R.id.textView3);
        agree = findViewById(R.id.agree_disclaimer);
        termsAndConditions.setPaintFlags(termsAndConditions.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        presenter = new BillingDisclaimerPresenter(this);


    }
}
