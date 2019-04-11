package com.example.ss.financeForBusinessUserPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.ss.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


}
