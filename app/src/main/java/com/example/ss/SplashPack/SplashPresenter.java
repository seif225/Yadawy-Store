package com.example.ss.SplashPack;

import android.content.Context;
import android.content.Intent;

import com.example.ss.MailLoginPack.MailLoginActivity;
import com.example.ss.signUpPack.SingUpActivity;

public class SplashPresenter {

    Context context;
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



}
