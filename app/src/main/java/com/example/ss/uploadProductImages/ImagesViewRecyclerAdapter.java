package com.example.ss.uploadProductImages;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ss.R;

import java.util.ArrayList;

public class ImagesViewRecyclerAdapter extends RecyclerView.Adapter<ImagesViewRecyclerAdapter.ViewHolder> {

    ArrayList<Uri> listOfImagesUri;
    Context context;

    ImagesViewRecyclerAdapter(ArrayList<Uri> listOfImagesUri , Context context){
        this.listOfImagesUri=listOfImagesUri;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_preview_rec_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.imageView.setImageURI(listOfImagesUri.get(i));

    }

    @Override
    public int getItemCount() {
        if(listOfImagesUri==null){
            return  0;
        }

        return listOfImagesUri.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        imageView=itemView.findViewById(R.id.image_view_holder_recycler_of_image);

        }
    }
}
