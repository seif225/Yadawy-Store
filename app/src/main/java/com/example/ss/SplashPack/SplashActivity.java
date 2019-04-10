package com.example.ss.SplashPack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ss.R;

public class SplashActivity extends AppCompatActivity {

    Button mailLog,phoneLog,signUp,googleLog;
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intializeFields();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToSignUpActivity();


            }
        });

        mailLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splashPresenter.sendUserToMailLogin();
            }
        });



    }

    private void sendUserToSignUpActivity() {
        splashPresenter.sendUserToSingup();

    }

    private void intializeFields() {
        mailLog=findViewById(R.id.mailLogin);
        phoneLog=findViewById(R.id.phoneLogin);
        signUp=findViewById(R.id.signUp);
        splashPresenter=new SplashPresenter(this);

    }
}
