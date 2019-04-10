package com.example.ss.MailLoginPack;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ss.R;

public class MailLoginActivity extends AppCompatActivity {
    private EditText mailEt,passwordEt;
    private String password,mail;
    private Button loginButton;
    MailLoginPresenter mailLoginPresenter;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_login);

        intializeFields();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail=mailEt.getText().toString();
                password=passwordEt.getText().toString();
                if(mail.isEmpty()){
                    mailEt.setError("you have to enter your mail");

                }
                else if(password.isEmpty()){
                    passwordEt.setError("you have to enter your password");
                }
                else{
                    mailLoginPresenter.signIn(mail,password,progressDialog);

                }


            }
        });


    }

    private void intializeFields() {
    mailEt=findViewById(R.id.mail_et_login);
    passwordEt=findViewById(R.id.password_et_login);
    loginButton=findViewById(R.id.sign_in_login_button);
    progressDialog=new ProgressDialog(this);
    mailLoginPresenter=new MailLoginPresenter(this);

    }

}
