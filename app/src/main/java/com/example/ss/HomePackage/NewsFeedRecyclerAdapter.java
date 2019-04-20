package com.example.ss.HomePackage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ss.AddProductPAckage.AddProductActivity;
import com.example.ss.ProductActivityPack.ProductActivity;
import com.example.ss.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFeedRecyclerAdapter extends RecyclerView.Adapter<NewsFeedRecyclerAdapter.ViewHolder> {
    private List<ProductModel> list;
    private Context context;

    public NewsFeedRecyclerAdapter(Context context, List<ProductModel> list) {
        this.list = list;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        //this picasso code shows only the first image of image collection in firebase
        //this happens only if images exist
        if(list.get(i).getImagesLinks()!=null){
        Picasso.get().load(list.get(i).getImagesLinks().get(0)).placeholder(R.drawable.user).into(viewHolder.ProductImage);
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
                Picasso.get().load(dataSnapshot.child("image").getValue().toString()).placeholder(R.drawable.user).into(viewHolder.userPp);}
                if(dataSnapshot.hasChild("userName")){
                viewHolder.userName.setText(dataSnapshot.child("userName").getValue().toString());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        viewHolder.productTitle.setText(list.get(i).getProductName());
       // Log.e("title fl adapter",list.get(i).getProductName() + " ya rab msh null :''D");
        viewHolder.productDescription.setText(list.get(i).getProductDescribtion());
        viewHolder.productPrice.setText(list.get(i).getProdcutPrice());
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage;
        TextView productTitle;
        TextView productDescription;
        TextView productPrice;
        CircleImageView userPp;
        TextView userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage = itemView.findViewById(R.id.product_image_at_recyclerView);
            productTitle = itemView.findViewById(R.id.product_title_at_recyclerView);
            productDescription = itemView.findViewById(R.id.product_description_at_recyclerView);
            productPrice = itemView.findViewById(R.id.product_price_at_recyclerView);
            userPp = itemView.findViewById(R.id.user_picture_in_layout_row);
            userName = itemView.findViewById(R.id.user_name_tv);

        }
    }
}