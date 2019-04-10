package com.example.ss.MailLoginPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.ss.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MailLoginPresenter {
    Context context;
    FirebaseAuth auth;
    MailLoginPresenter(Context context){
        this.context=context;
        auth=FirebaseAuth.getInstance();
    }

    void signIn(String mail, String password , final ProgressDialog progressDialog){
        progressDialog.setMessage("login in..");
        progressDialog.show();
        auth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){
               progressDialog.dismiss();
               sendUserToMain();

           }
           else{
               progressDialog.dismiss();


           }

            }

            private void sendUserToMain() {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);


            }
        });

    }


}
