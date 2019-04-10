package com.example.ss.signUpPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.example.ss.SplashPack.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SingUpPresnenter {

    private Context context;
    private ArrayList<String> accountTypes;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    SingUpPresnenter(Context context){

        this.context=context;
        accountTypes = new ArrayList<>();
        accountTypes.add("user account");
        accountTypes.add("business account");
        mAuth =FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    void singUp(final String mail, final String password, final String accountType, final ProgressDialog progressDialog){
        progressDialog.setMessage("creating your account");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Log.e("here",mail+password+accountType+FirebaseAuth.getInstance().getCurrentUser().getUid());
                  databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("mail").setValue(mail);
                  databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("password").setValue(password);
                  databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("account type").setValue(accountType);
                  databaseReference.child("Users").child(mAuth.getCurrentUser().getUid()).child("userID").setValue(mAuth.getUid());
                  sendUserToSplash();

                }


                else{

                    progressDialog.dismiss();
                    Toast.makeText(context,  task.getException().toString(), Toast.LENGTH_SHORT).show();

                }
            }

            private void sendUserToSplash() {

            Intent i = new Intent(context, SplashActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

            }


        });


    }





    public ArrayList<String> getAccountTypes() {
        return accountTypes;
    }
}
