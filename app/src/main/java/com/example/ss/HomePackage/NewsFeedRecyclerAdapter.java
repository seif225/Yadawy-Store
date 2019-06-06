package com.example.ss.HomePackage;

//ackage com.example.ss.UserProfile;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ss.ProductActivityPack.ProductActivity;
import com.example.ss.R;
import com.example.ss.UserProfile.UserProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsFeedRecyclerAdapter extends RecyclerView.Adapter<NewsFeedRecyclerAdapter.ViewHolder> {
    private List<ProductModel> list;
    private Context context;
    private ValueEventListener likesListner,userListner;
    private DatabaseReference userRef,likesRef;

    public NewsFeedRecyclerAdapter(Context context, List<ProductModel> list) {
        this.list = list;
        this.context=context;

        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            likesRef = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid());
            userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        }else{

            notifyDataSetChanged();
        }

    }

    public DatabaseReference getUserRef() {
        return userRef;
    }

    public ValueEventListener getUserListner() {
        return userListner;
    }

    public DatabaseReference getLikesRef() {
        return likesRef;
    }

    public ValueEventListener getLikesListner() {
        return likesListner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        //this picasso code shows only the first image of image collection in firebase
        //this happens only if images exist


       startListeningOnLikes(i,viewHolder);

        likesRef.addValueEventListener(likesListner);
        startListeningOnUsers(viewHolder);



        Log.e("NewsAdapter",list.get(i).getuId());
        userRef.child(list.get(i).getuId()).addValueEventListener(userListner);



        if(list.get(i).getImagesLinks()!=null){
            Picasso.get()
                    .load(list.get(i).getImagesLinks().get(0))
                    .resize(600, 200) // resizes the image to these dimensions (in pixel)
                    .centerCrop()
                    .into(viewHolder.ProductImage);
            Log.e("image in adapter",list.get(i).getImagesLinks().get(0)+"i =" +i + "size= "+list.get(i).getImagesLinks().size());
        }
        else if(list.get(i).getImagesLinks()==null ){

            viewHolder.ProductImage.setImageResource(R.drawable.user);

        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(context, ProductActivity.class);
                j.putExtra("uid",list.get(i).getuId());
                Log.e("uid in adapter",list.get(i).getuId()+"  helafhasjf");
                j.putExtra("productId",list.get(i).getProductId());

                context.startActivity(j);

            }
        });



        Log.e("newfeedadapter","id check "+list.get(i).getuId());



        viewHolder.productTitle.setText(list.get(i).getProductName());
        // Log.e("title fl adapter",list.get(i).getProductName() + " ya rab msh null :''D");
        viewHolder.productDescription.setText(list.get(i).getProductDescribtion());
        viewHolder.productPrice.setText(list.get(i).getProdcutPrice()+" L.E");


        if(list.get(i).getProductLikes()!=null){
            viewHolder.likesTv.setText(list.get(i).getProductLikes());

        }
        else{


            viewHolder.likesTv.setText("0");
        }



        viewHolder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendUserToProfile(list.get(i));

            }
        });

        viewHolder.userPp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToProfile(list.get(i));
            }
        });


/// on click
        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!viewHolder.likeState){
                    like(list.get(i).getProductId(),list.get(i).getuId(),list.get(i).getProductName());
                    viewHolder.likeButton.setChecked(true);

                }

                else if (viewHolder.likeState){
                    disLike(list.get(i).getProductId(),list.get(i).getuId(),list.get(i).getProductName());
                    viewHolder.likeButton.setChecked(false);

                }


            }
        });


    }

    private void startListeningOnUsers(final ViewHolder viewHolder) {

        userListner=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("image")){
                    Picasso.get().load(dataSnapshot.child("image").getValue().toString()).resize(100,100).placeholder(R.drawable.user).into(viewHolder.userPp);}
                if(dataSnapshot.hasChild("userName")){
                    viewHolder.userName.setText(dataSnapshot.child("userName").getValue().toString());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

    }

    private void sendUserToProfile(ProductModel productModel) {
        Intent i = new Intent (context, UserProfileActivity.class);
        i.putExtra("uid",productModel.getuId());
        context.startActivity(i);




    }

    void disLike(String productId, String getuId, String productName) {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Likes").child(productName).removeValue();
        FirebaseDatabase.getInstance().getReference().child("products").child(getuId).child(productId).child("Likers").
                child(FirebaseAuth.getInstance().getUid()).removeValue();


    }

    void like(String productId, String getuId,String productName) {
        HashMap<String,String> likedItem = new HashMap<>();
        likedItem.put("productId",productId);
        likedItem.put("userId",getuId);

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("Likes").child(productName).setValue(likedItem);

        HashMap<String,String>liker=new HashMap<>();
        liker.put("userId",FirebaseAuth.getInstance().getUid());
        FirebaseDatabase.getInstance().getReference().child("products").child(getuId).child(productId)
                .child("Likers").child(FirebaseAuth.getInstance().getUid()).setValue(liker);


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
        TextView likesTv;
        ShineButton likeButton;
        private boolean likeState;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ProductImage = itemView.findViewById(R.id.product_image_at_recyclerView);
            productTitle = itemView.findViewById(R.id.product_title_at_recyclerView);
            productDescription = itemView.findViewById(R.id.product_description_at_recyclerView);
            productPrice = itemView.findViewById(R.id.product_price_at_recyclerView);
            userPp = itemView.findViewById(R.id.user_picture_in_layout_row);
            userName = itemView.findViewById(R.id.user_name_tv);
            likeButton=itemView.findViewById(R.id.like_image_button);
            //likeButton.setChecked(true);
            likeState=false;
            likesTv=itemView.findViewById(R.id.number_of_likes);
        }
    }


    private void startListeningOnLikes(final int i, final ViewHolder viewHolder){



        likesListner=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("Likes")) {

                    if (i <= list.size()  ){

                        Log.e("startListeningLikes","adapter , numbers of likes: "+ list.size());

                        if (dataSnapshot.child("Likes").hasChild(list.get(i).getProductName())) {


                            viewHolder.likeState = true;
                            viewHolder.likeButton.setChecked(true);

                        } else {

                            viewHolder.likeButton.setChecked(false);
                            viewHolder.likeState = false;

                        }

                    }
                    else {
                        notifyDataSetChanged();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };




    }


}