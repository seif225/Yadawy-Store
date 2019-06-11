package com.example.ss.BillingDisclaimerPackage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.ss.financeForBusinessUserPack.addFinancialInfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BillingDisclaimerPresenter {
    private Calendar currentMonth ;
    private SimpleDateFormat dateFormat ;
    private DatabaseReference userRef;
    private Context context;

    BillingDisclaimerPresenter(Context context){
        this.context=context;
        currentMonth = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        userRef= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());

    }


    void upLoadBillingDateToFirebaseAndBeyond() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            userRef.child("billing_date").setValue(getNextMonthFormat()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserToAddFinancialInfoActivity();
                    }
                }
            });

        }
    }



    String getCurrentDateFormat() {


        return dateFormat.format(currentMonth.getTime());

    }


    String getNextMonthFormat() {

        currentMonth.add(Calendar.MONTH, 1);
        return dateFormat.format(currentMonth.getTime());
    }

    private void sendUserToAddFinancialInfoActivity(){

        Intent i = new Intent(context, addFinancialInfoActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }


}
