package com.example.ss.SplashPack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.ss.ChooseAccountTypeForBusinessAcountPack.ChooseAccountTypeForBusinessAccount;
import com.example.ss.MailLoginPack.MailLoginActivity;
import com.example.ss.MainActivity;
import com.example.ss.signUpPack.SingUpActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashPresenter {

    static FirebaseAuth mAuth=FirebaseAuth.getInstance();
    static Context context;
    Intent i;
    SplashPresenter(Context context){
        this.context=context;
    }



    public void sendUserToSingup(){




        Intent i = new Intent(context, SingUpActivity.class);

        context.startActivity(i);




    }







    public void sendUserToMailLogin(){
        Intent i = new Intent(context, MailLoginActivity.class);
        context.startActivity(i);
    }
    public void sendUserToPhoneLogin(){
        Intent i = new Intent();
    }
    public void sendUserToGmailLogin(){
        Intent i = new Intent();
    }


    public void loginWithGoogleAccount(int requestCode,int RC_SIGN_IN,Intent data){
        if (requestCode == RC_SIGN_IN && data != null) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {

                    firebaseAuthWithGoogle(account);
                }

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e("Splash", "Google sign in failedddddd", e);
                // ...
            }
        }
    }

    private static void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("Splash Presenter", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("Splash Presenter", "signInWithCredential:success");
                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("userID").setValue(FirebaseAuth.getInstance().getUid());
                            FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("mail").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                            enterToHome();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Splash Presenter", "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }

    public static void enterToHome(){


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("account type")){


                    Intent i=new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(i);
                }

                else{
                    Intent i=new Intent(context, ChooseAccountTypeForBusinessAccount.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        Log.e("testttt","succccessss");
        //Toast.makeText(context, "Succccccc", Toast.LENGTH_SHORT).show();
    }

     void anonymousLogin() {


        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading");
        progressDialog.show();
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("anonymous",mAuth.getUid()+" uid");
                            FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid().toString())
                                    .child("userID").setValue(mAuth.getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .child("anonymous").setValue("anonymous").addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Intent i = new Intent (context,MainActivity.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                context.startActivity(i);
                                            }
                                        });





                                    }
                                    progressDialog.dismiss();

                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user
                            Toast.makeText(context, ""+task.getException() , Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                        }

                        // ...
                    }
                });
    }
}
