package com.example.ss.EditProductPackage;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ss.HomeFragmentV2Package.NewsFeedRecyclerAdapter;
import com.example.ss.R;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ImagesRecyclerAdapter extends RecyclerView.Adapter<ImagesRecyclerAdapter.ViewHolder> {

    ArrayList<ImageModel> list;
    Context context;
    String uid, productId;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    ImagesRecyclerAdapter(ArrayList<ImageModel> list, Context context, String uid, String productId) {

        this.list = list;
        this.context = context;
        this.uid = uid;
        this.productId = productId;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("are you sure ?");
        alertDialogBuilder.setMessage("this operation can't be undone.");


    }

    @NonNull
    @Override
    public ImagesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.edit_product_images_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesRecyclerAdapter.ViewHolder viewHolder, final int i) {
        if (list.get(i).getImage() != null) {
            Picasso.get().load(list.get(i).getImage()).into(viewHolder.image);
        }
        viewHolder.imageName.setText(list.get(i).getImageName());
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteImage(i);

                    }
                });
                alertDialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });

                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


    }

    private void deleteImage(int i) {
        FirebaseDatabase.getInstance().getReference().child("products").child(uid).child(productId).child("images").child(list.get(i).getImageName()).removeValue();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView imageName;
        Button remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.product_image_row);
            imageName = itemView.findViewById(R.id.image_name_row);
            remove = itemView.findViewById(R.id.remove_pic_row);

        }
    }
}
