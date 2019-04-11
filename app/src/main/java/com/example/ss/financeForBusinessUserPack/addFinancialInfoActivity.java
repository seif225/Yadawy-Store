package com.example.ss.financeForBusinessUserPack;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ss.R;

public class addFinancialInfoActivity extends AppCompatActivity {
    EditText cardNumberEt,expairyMonthEt,expairyYearEt,cvvEt;
    String cardNumber,expairyDate,cvv;
    Button confirm;
    addFinancialInfoActivityPresenter presenter;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_financial_info);
        intializeFields();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNumber=cardNumberEt.getText().toString();
                expairyDate = expairyMonthEt.getText()+"/"+expairyYearEt.getText();
                cvv=cvvEt.getText().toString();

                if(cardNumber.isEmpty() ||cardNumber.length()!=14){

                    cardNumberEt.setError("this card number is not correct");
                   // Log.e("card num",cardNumber.charAt(0) +".."+cardNumber.length()+"");

                }
                else if (expairyDate.length()<5){
                    Toast.makeText(addFinancialInfoActivity.this, "your expairy date isnt correct", Toast.LENGTH_SHORT).show();
                }

                else if(cvv.length()!=3){
                    cvvEt.setError("this cvv number isnt correct");
                }


                else {
                    if(cardNumber.charAt(0)=='5'){

                        presenter.uploadFinancialData(cardNumber,expairyDate,cvv,progressDialog);


                    }

                    else if (cardNumber.charAt(0)=='4'){
                        presenter.uploadFinancialData(cardNumber,expairyDate,cvv,progressDialog);


                    }
else{
                        cardNumberEt.setError("this card number is not correct");


                    }

                }

            }
        });


    }

    private void intializeFields() {
        cardNumberEt=findViewById(R.id.card_number);
        expairyMonthEt=findViewById(R.id.expiary_month);
        expairyYearEt=findViewById(R.id.expairy_year);
        cvvEt=findViewById(R.id.cvv);
        confirm=findViewById(R.id.confirm);
        presenter=new addFinancialInfoActivityPresenter(this);
        progressDialog=new ProgressDialog(this);
    }
}
