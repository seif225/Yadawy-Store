package com.example.ss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ss.AddWorkshopPackage.AddWorkshopActivity;
import com.example.ss.AdminProfilePack.ProfileFragment;
import com.example.ss.BillingDisclaimerPackage.BillingDisclaimerActivity;
import com.example.ss.BillingDisclaimerPackage.BillingDisclaimerPresenter;
import com.example.ss.CategoryPackage.CategoryFragment;
import com.example.ss.ChooseAccountTypeForBusinessAcountPack.ChooseAccountTypeForBusinessAccount;
import com.example.ss.FindSellersPackage.FindSellersActivity;
import com.example.ss.HomeFragmentV2Package.HomeFragmentV2;
import com.example.ss.LikesFragmentPack.LikesFragment;
import com.example.ss.ProfileEditActivityPack.ProfileEditActivity;
import com.example.ss.ShoppingCartPackage.ShoppingCartActivity;
import com.example.ss.SplashPack.SplashActivity;
import com.example.ss.WorkShopsPackage.WorkShopsFragment;
import com.example.ss.financeForBusinessUserPack.addFinancialInfoActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;
    private Fragment selectedFragment;
    private View navigationHeader;
    private CircleImageView navuseRImage;
    private TextView navUserName, navUserMail;

    public static Context MAIN_CONTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        intializeFields();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        navigationHeader = navigationView.getHeaderView(0);
        navuseRImage = navigationHeader.findViewById(R.id.userImageView_nav);
        navUserName = navigationHeader.findViewById(R.id.userName_nav);
        navUserMail = navigationHeader.findViewById(R.id.userMail_nav);
        getSupportActionBar().setIcon(R.drawable.yaaadaw);
        updateUserInfo();


    }

    private void updateUserInfo() {

        FirebaseDatabase.getInstance().getReference().child("Users").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid())) {
                                if (dataSnapshot.child(FirebaseAuth.getInstance().getUid()).hasChild("image")) {
                                    Picasso.get().load(dataSnapshot.child(FirebaseAuth.getInstance().getUid()).child("image").getValue().toString()).into(navuseRImage);
                                }
                                if (dataSnapshot.child(FirebaseAuth.getInstance().getUid()).hasChild("userName")) {
                                    navUserName.setText(dataSnapshot.child(FirebaseAuth.getInstance().getUid()).child("userName").getValue().toString() + "");
                                }
                                if (dataSnapshot.child(FirebaseAuth.getInstance().getUid()).hasChild("mail")) {
                                    navUserMail.setText(dataSnapshot.child(FirebaseAuth.getInstance().getUid()).child("mail").getValue().toString() + "");
                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            sendUserToSplash();
        } else {
            userRef.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.hasChild("userID")) {

                        sendUserToSplash();
                    } else if (!dataSnapshot.hasChild("account type") && dataSnapshot.hasChild("mail") && !dataSnapshot.hasChild("anonymous")) {
                        sendUserToChooseAccountType();

                    } else if (!dataSnapshot.hasChild("userName") || !dataSnapshot.hasChild("phone") || !dataSnapshot.hasChild("address")) {

                        if (!dataSnapshot.hasChild("anonymous")) {
                            sendUserToProfileEditActivity();
                        }


                    } else if (dataSnapshot.hasChild("userName") && dataSnapshot.hasChild("phone") && dataSnapshot.hasChild("address")
                            && dataSnapshot.hasChild("account type")) {

                        if (dataSnapshot.child("account type").getValue().equals("business account") &&
                                !dataSnapshot.hasChild("billing_date") && !dataSnapshot.hasChild("anonymous")) {


                            sendUserToDisclaimerActivity();


                        }


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


    }

    private void sendUserToChooseAccountType() {
        Intent i = new Intent(this, ChooseAccountTypeForBusinessAccount.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

    private void sendUsertoAddFinancialinfoActivity() {
        Intent i = new Intent(this, addFinancialInfoActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }

    private void sendUserToDisclaimerActivity() {

        Intent i = new Intent(this, BillingDisclaimerActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }


    private void sendUsertoAddFinancialinfoActivityWithoutFlag() {
        Intent i = new Intent(this, addFinancialInfoActivity.class);
        startActivity(i);

    }

    private void sendUserToProfileEditActivity() {

        Intent i = new Intent(this, ProfileEditActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        //Log.e("here","onw");


    }


    private void sendUserToSplash() {

        Intent i = new Intent(this, SplashActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }


    private void intializeFields() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MAIN_CONTEXT = this.getApplicationContext();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference().child("Users");
        selectedFragment = new HomeFragmentV2();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart_button) {

            FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild("anonymous"))
                        Toast.makeText(MainActivity.this, " you have to login first", Toast.LENGTH_SHORT).show();
                    else
                        startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void sendUserToFindSellersActivity() {
        Intent i = new Intent(this, FindSellersActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            selectedFragment = new HomeFragmentV2();


        } else if (id == R.id.nav_categroies) {
            selectedFragment = new CategoryFragment();

        } else if (id == R.id.nav_save) {
            selectedFragment = new LikesFragment();

        } else if (id == R.id.nav_profile) {

            FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.hasChild("anonymous")){
                        selectedFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }

                    else
                    Toast.makeText(MainActivity.this, " you have to login first", Toast.LENGTH_SHORT).show();



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else if (id == R.id.nav_share) {

        }  else if (id == R.id.nav_contct_us) {

        } else if (id == R.id.nav_logOut) {
            mAuth.signOut();
            sendUserToSplash();

        } else if (id == R.id.find_sellers_nav_menu) {
            selectedFragment = new FindSellersActivity();



        } else if (id == R.id.workshops_nav_menu) {

            FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.hasChild("anonymous")) {
                        selectedFragment = new WorkShopsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }
                    else
                    Toast.makeText(MainActivity.this, " you have to login first", Toast.LENGTH_SHORT).show();




                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        //return true;


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
