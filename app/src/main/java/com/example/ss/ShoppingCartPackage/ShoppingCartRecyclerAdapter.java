package com.example.ss.ShoppingCartPackage;

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
import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.example.ss.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShoppingCartRecyclerAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerAdapter.ViewHolder> {
    List<ProductModel> listOfProducts;
    Context context;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;

    ShoppingCartRecyclerAdapter(List<ProductModel> listOfProducts, Context context) {
        this.listOfProducts = listOfProducts;
        this.context = context;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("are you sure ?");
        alertDialogBuilder.setMessage("this operation can't be undone.");

    }


    @NonNull
    @Override
    public ShoppingCartRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_cart_row_item, viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShoppingCartRecyclerAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.increament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.quantity = viewHolder.quantity + 1;
                viewHolder.quantityTv.setText(viewHolder.quantity+"");
                viewHolder.totalPriceTv.setText(viewHolder.quantity * listOfProducts.get(i).getPriceAsInt()+"");


            }
        });

        viewHolder.decreament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.quantity > 1) {
                    viewHolder.quantity = viewHolder.quantity - 1;
                    viewHolder.quantityTv.setText(viewHolder.quantity+"");
                    viewHolder.totalPriceTv.setText(viewHolder.quantity * listOfProducts.get(i).getPriceAsInt()+"");

                }
            }
        });

        viewHolder.quantityTv.setText(viewHolder.quantity+"");
        if (listOfProducts.get(i).getThumbnail() != null ) {
            Picasso.get().load(listOfProducts.get(i).getThumbnail()).into(viewHolder.productImageIv);

        }

        viewHolder.productNameTv.setText(listOfProducts.get(i).getProductName());
        viewHolder.productPriceTv.setText(listOfProducts.get(i).getPriceAsInt() + ".LE");
        viewHolder.totalPriceTv.setText(viewHolder.quantity * listOfProducts.get(i).getPriceAsInt()+"");

        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                .child("cart").child(listOfProducts.get(i).getProductId()).removeValue();



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

    @Override
    public int getItemCount() {
        if (listOfProducts == null) {
            return 0;
        }
        return listOfProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageIv;
        TextView productNameTv, productPriceTv, quantityTv, totalPriceTv;
        Button increament, decreament, remove;
        int quantity = 1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageIv = itemView.findViewById(R.id.product_image_cart);
            productNameTv = itemView.findViewById(R.id.product_name_cart);
            productPriceTv = itemView.findViewById(R.id.price_cart);
            quantityTv = itemView.findViewById(R.id.quantity);
            increament = itemView.findViewById(R.id.increament);
            decreament = itemView.findViewById(R.id.decreament);
            remove = itemView.findViewById(R.id.remove_button_cart);
            totalPriceTv = itemView.findViewById(R.id.total_price);

        }
    }
}
