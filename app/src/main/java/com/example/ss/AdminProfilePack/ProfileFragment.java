package com.example.ss.AdminProfilePack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ss.ProfileEditActivityPack.ProfileEditActivity;
import com.example.ss.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    private View view;
    private ProfileFragmentPresenter presenter;
    private TextView userNameTv,bioTv;
    private CircleImageView profilePicture;
    private Button editProfileButton;
    private ProgressDialog progressDialog;

    public ProfileFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        intialiaizeFields();
        presenter.retriveUserData(progressDialog,userNameTv,profilePicture,bioTv);


        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToEditProfileActivity();
            }
        });

        return view;
    }



    private void sendUserToEditProfileActivity() {
        Intent i = new Intent(getActivity(), ProfileEditActivity.class);
        startActivity(i);
    }

    private void intialiaizeFields() {
        presenter = new ProfileFragmentPresenter(getActivity());
        userNameTv=view.findViewById(R.id.userName_tv_profile);
        bioTv=view.findViewById(R.id.bio_tv_profile_fragment);
        profilePicture=view.findViewById(R.id.proile_picture_admin_profile);
        editProfileButton=view.findViewById(R.id.edit_profile_btn);
        progressDialog=new ProgressDialog(getActivity());
    }


}
