package com.example.ss.financeForBusinessUserPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ss.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addFinancialInfoActivityPresenter {

    Context context;
    FirebaseDatabase database;
    DatabaseReference userRef;
    FirebaseAuth mAuth;

    addFinancialInfoActivityPresenter(Context context)
    {
        this.context=context;
        database= FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        userRef=database.getReference().child("Users").child(mAuth.getUid()).child("finance");
    }

    void uploadFinancialData(String cardNumber , String expairyDate , String cvv, final ProgressDialog progressDialog){
        progressDialog.setMessage("updating your financial data ..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userRef.child("card_number").setValue(cardNumber);
        userRef.child("expairy_date").setValue(expairyDate);
        userRef.child("cvv").setValue(cvv).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                sendUserToMain();
            }
        });



    }

    private void sendUserToMain() {

    Intent i = new Intent (context, MainActivity.class);
    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);


    }


    public void checkData(final EditText cardNumberEt, final EditText expairyMonthEt, final EditText expairyYearEt, final EditText cvvEt, final Button confirm) {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                if(dataSnapshot.hasChild("finance"))
                {


                   final  ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("please wait");
                    progressDialog.show();
                    final String cardNumber = dataSnapshot.child("finance").child("card_number").getValue().toString();
                    final String cvv =dataSnapshot.child("finance").child("cvv").getValue().toString();
                    final String expairyDateGet = dataSnapshot.child("finance").child("expairy_date").getValue().toString();

                    final  String expairyDate = expairyDateGet.replace("/","");
                    Log.e("Financial info",expairyDate);
                    Log.e("Financial info",expairyDateGet);

                    cardNumberEt.setText( cardNumber.replaceAll("\\w(?=\\w{4})", "*"));
                    cvvEt.setText(cvv);
                    progressDialog.dismiss();
                    String monthDate = expairyDate.charAt(0)+""+expairyDate.charAt(1)+"";
                    String yearDate = expairyDate.charAt(2)+""+expairyDate.charAt(3)+"";

                    expairyMonthEt.setText(monthDate);
                    expairyYearEt.setText(yearDate);
                    final String expairyDateFinal = monthDate+"/"+yearDate;
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(cardNumber.isEmpty() ||cardNumber.length()!=16){

                                cardNumberEt.setError("this card number is not correct");
                                // Log.e("card num",cardNumber.charAt(0) +".."+cardNumber.length()+"");

                            }
                            else if (expairyDateFinal.length()>5){
                                Toast.makeText(context, "your expairy date is'nt correct", Toast.LENGTH_SHORT).show();
                            }

                            else if(cvv.length()!=3){
                                cvvEt.setError("this cvv number isnt correct");
                            }


                            else {
                                if(cardNumber.charAt(0)=='5'){

                                    uploadFinancialData(cardNumber,expairyDateFinal,cvv,progressDialog);


                                }

                                else if (cardNumber.charAt(0)=='4'){
                                    uploadFinancialData(cardNumber,expairyDateFinal,cvv,progressDialog);


                                }
                                else{
                                    cardNumberEt.setError("this card number is not correct");


                                }

                            }

                        }
                    });





                }

















            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
