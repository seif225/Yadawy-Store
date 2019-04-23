package com.example.ss.AdminProfilePack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ss.HomePackage.NewsFeedRecyclerAdapter;
import com.example.ss.HomePackage.ProductModel;
import com.example.ss.MainActivity;
import com.example.ss.ProductActivityPack.ProductActivity;
import com.example.ss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFeedRecyclerAdapterForCurrentUser extends RecyclerView.Adapter<NewsFeedRecyclerAdapterForCurrentUser.ViewHolder> {

    private List<ProductModel> list;
    private Context context;

    NewsFeedRecyclerAdapterForCurrentUser(Context context, List<ProductModel> list) {
        this.list = list;
        this.context=context;
    }


    @NonNull
    @Override
    public NewsFeedRecyclerAdapterForCurrentUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_profiel, viewGroup, false);
        return new NewsFeedRecyclerAdapterForCurrentUser.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsFeedRecyclerAdapterForCurrentUser.ViewHolder viewHolder, final int i) {




        if(list.get(i).getImagesLinks()!=null){
            //Picasso.get().load(list.get(i).getImagesLinks().get(0)).placeholder(R.drawable.gifts).into(viewHolder.productPic);
            //loadImageInBackground(viewHolder.productPic,list.get(i).getImagesLinks().get(0));
            //notifyDataSetChanged();
            Picasso.get()
                    .load(list.get(i).getImagesLinks().get(0))
                    .resize(600, 200) // resizes the image to these dimensions (in pixel)
                    .centerCrop()
                    .into(viewHolder.productPic);
            Log.e("image in adapter",list.get(i).getImagesLinks().get(0)+"i =" +i + "size= "+list.get(i).getImagesLinks().size());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context,ProductActivity.class);
                j.putExtra("uid",list.get(i).getuId());
                Log.e("uid in adapter",list.get(i).getuId()+"  helafhasjf");
                j.putExtra("productId",list.get(i).getProductId());

                context.startActivity(j);

            }
        });


        FirebaseDatabase.getInstance().getReference().child("Users").child(list.get(i).getuId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("image")){
                    Picasso.get().load(dataSnapshot.child("image").getValue().toString()).placeholder(R.drawable.user).into(viewHolder.profilePic);}
                if(dataSnapshot.hasChild("userName")){
                    viewHolder.userName.setText(dataSnapshot.child("userName").getValue().toString());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        viewHolder.productName.setText(list.get(i).getProductName());




    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePic;
        TextView userName,productName;
        ImageView productPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        profilePic=itemView.findViewById(R.id.profile_picture_row_item_profile);
            userName=itemView.findViewById(R.id.user_name_profile_row_item);
            productPic=itemView.findViewById(R.id.product_picture_row_item_profile);
            productName = itemView.findViewById(R.id.product_name_row_item_profile);

        }

    }
    public void loadImageInBackground(final ImageView imageView, String s) {


        Target target = new Target() {

            @Override
            public void onPrepareLoad(Drawable arg0) {

            }

            @Override
            public void onBitmapLoaded(Bitmap arg0, Picasso.LoadedFrom arg1) {
                imageView.setImageBitmap(arg0);

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {


            }


        };
        Log.e("load image in back",s+" yeah man");

        Picasso.get()
                .load(s)
                .resize(600, 200) // resizes the image to these dimensions (in pixel)
                .centerCrop()
                .into(target);
    }


}
