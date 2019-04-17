package com.example.ss.HomePackage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ss.AddProductPAckage.AddProductActivity;
import com.example.ss.R;

import java.util.List;

public class NewsFeedRecyclerAdapter extends RecyclerView.Adapter<NewsFeedRecyclerAdapter.ViewHolder> {
    List<AddProductActivity> list;

    public NewsFeedRecyclerAdapter(List<AddProductActivity> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage = itemView.findViewById(R.id.product_image_at_recyclerView);
            productTitle = itemView.findViewById(R.id.product_title_at_recyclerView);
            productDescription = itemView.findViewById(R.id.product_description_at_recyclerView);
            productPrice = itemView.findViewById(R.id.product_price_at_recyclerView);
        }
    }
}