package com.example.ss.SplashPack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ss.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

public class SplashActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;

    Button mailLog;
    SignInButton googleLog;
    TextView signUp;
    SplashPresenter splashPresenter;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        intializeFields();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                splashPresenter.sendUserToSingup();



            }
        });

        mailLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                splashPresenter.sendUserToMailLogin();

            }
        });

        googleLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInWithGoogleAccount();

            }
        });

    }



    private void intializeFields() {

        mailLog = findViewById(R.id.mailLogin);
        signUp = findViewById(R.id.signUp);
        googleLog = findViewById(R.id.googleLogin);
        splashPresenter = new SplashPresenter(this);
            //gso is an object that takes the project id in order for the user to make a google account (FIREBASE)
        signUp.setPaintFlags(signUp.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



    }


    private void signInWithGoogleAccount() {

        mGoogleSignInClient = GoogleSignIn.getClient(SplashActivity.this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        splashPresenter.loginWithGoogleAccount(requestCode, RC_SIGN_IN, data);
    }
}
