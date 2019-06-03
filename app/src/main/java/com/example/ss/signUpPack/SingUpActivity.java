package com.example.ss.signUpPack;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ss.R;

public class SingUpActivity extends AppCompatActivity {

    private Spinner typeSpinner;
    private String accountType, mail, password;
    private EditText mailEt, passwordEt;
    private SingUpPresnenter singUpPresenter;
    private Button registerButton;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        intializeFields();



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accountType = typeSpinner.getSelectedItem().toString();
                mail = mailEt.getText().toString();
                password = passwordEt.getText().toString();
                if (mail.isEmpty()) {
                    mailEt.setError("you cant leave this empty");
                } else if (password.isEmpty()) {

                    passwordEt.setError("you have to create a password");
                } else {
                    singUpPresenter.singUp(mail, password, accountType, progressDialog);

                }
            }


        });


    }

    private void intializeFields() {

        singUpPresenter = new SingUpPresnenter(this);




        typeSpinner = findViewById(R.id.account_type_spinner);

        typeSpinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, singUpPresenter.getAccountTypes()));
        mailEt = findViewById(R.id.mail_et_sign_up);
        passwordEt = findViewById(R.id.password_et_sign_up);
        registerButton = findViewById(R.id.sign_in_register_button);
        progressDialog = new ProgressDialog(this);

    }
}
