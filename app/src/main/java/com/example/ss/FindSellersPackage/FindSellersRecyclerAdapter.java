package com.example.ss.FindSellersPackage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ss.R;
import com.example.ss.UserProfile.UserProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindSellersRecyclerAdapter extends RecyclerView.Adapter<FindSellersRecyclerAdapter.ViewHolder> {
    Context context; ArrayList<UserModel> listOfUsers;
    public FindSellersRecyclerAdapter(Context context, ArrayList<UserModel> listOfUsers) {
        this.context=context;
        this.listOfUsers=listOfUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.find_followers_row_item,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

    if(listOfUsers.size()>=i) {

        viewHolder.userName.setText(listOfUsers.get(i).getName());
        if (listOfUsers.get(i).getProfilePicture() != null) {
            Picasso.get().load(listOfUsers.get(i).getProfilePicture()).into(viewHolder.profilePic);
        } else {
            viewHolder.profilePic.setImageResource(R.drawable.user);
        }

        viewHolder.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToProfile(listOfUsers.get(i).getuId());

            }
        });


        viewHolder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToProfile(listOfUsers.get(i).getuId());

            }
        });

    }

    }




    private void sendUserToProfile(String uid) {



        Intent i = new Intent (context, UserProfileActivity.class);
        i.putExtra("uid",uid);
        context.startActivity(i);

    }

    @Override
    public int getItemCount() {
        if(listOfUsers==null){

            return 0;

        }

        return listOfUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePic;
        TextView userName;
        Button followButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        profilePic=itemView.findViewById(R.id.profle_picture_find_seller);
        userName=itemView.findViewById(R.id.user_name_find_seller);
        followButton=itemView.findViewById(R.id.follow_button_find_followers);


        }
    }
}
