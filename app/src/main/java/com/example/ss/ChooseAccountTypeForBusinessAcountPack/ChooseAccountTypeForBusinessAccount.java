package com.example.ss.ChooseAccountTypeForBusinessAcountPack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ss.MainActivity;
import com.example.ss.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChooseAccountTypeForBusinessAccount extends AppCompatActivity {


    private Spinner spinner;
    private Button done;
    private ArrayList<String> accountTypes;
    private String accountType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account_type_for_business_account);
        intializeFields();
        customiseSpinner();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountType=spinner.getSelectedItem().toString();

                if(spinner.getSelectedItem().toString()==null){

                    Toast.makeText(getBaseContext(), "you have to fill the spinner", Toast.LENGTH_SHORT).show();

                }



                else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users").
                            child(FirebaseAuth.getInstance().getUid()).child("account type").setValue(accountType)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            sendUserToMain();

                        }
                    });
                }
            }
        });



    }

    private void sendUserToMain() {

    Intent i = new Intent(this, MainActivity.class) ;
    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(i);


    }

    private void customiseSpinner() {
        accountTypes = new ArrayList<>();
        accountTypes.add("user account");
        accountTypes.add("business account");
        spinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,accountTypes));


    }

    private void intializeFields() {

    spinner=findViewById(R.id.account_type_spinner_2);
    done = findViewById(R.id.done_account_type);

    }

}
