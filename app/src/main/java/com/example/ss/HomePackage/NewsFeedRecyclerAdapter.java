package com.example.ss.HomePackage;

import android.support.annotation.NonNull;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ss.AddProductPAckage.AddProductActivity;
import com.example.ss.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsFeedRecyclerAdapter extends RecyclerView.Adapter<NewsFeedRecyclerAdapter.ViewHolder> {
    List<ProductModel> list;

    public NewsFeedRecyclerAdapter(List<ProductModel> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //this picasso code shows only the first image of image collection in firebase
        //this happens only if images exist
        if(list.get(i).getImagesLinks()!=null){
        Picasso.get().load(list.get(i).getImagesLinks().get(0)).placeholder(R.drawable.user).into(viewHolder.ProductImage);
        Log.e("image in adapter",list.get(i).getImagesLinks().get(0)+"i =" +i + "size= "+list.get(i).getImagesLinks().size());
        }

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage = itemView.findViewById(R.id.product_image_at_recyclerView);
            productTitle = itemView.findViewById(R.id.product_title_at_recyclerView);
            productDescription = itemView.findViewById(R.id.product_description_at_recyclerView);
            productPrice = itemView.findViewById(R.id.product_price_at_recyclerView);
        }
    }
}