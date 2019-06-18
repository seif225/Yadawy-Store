package com.example.ss.ShoppingCartPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ss.HomeFragmentV2Package.NewsFeedRecyclerAdapter;
import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.example.ss.R;
import com.example.ss.ReceiptPackage.ReceiptActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.support.constraint.Constraints.TAG;

public class ShoppingCartRecyclerAdapter extends RecyclerView.Adapter<ShoppingCartRecyclerAdapter.ViewHolder> {
    private List<ProductModel> listOfProducts;
    private Context context;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private Button proceedBtn;

    ShoppingCartRecyclerAdapter(final List<ProductModel> listOfProducts, final Context context, Button proceedBtn) {
        this.listOfProducts = listOfProducts;
        this.context = context;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("are you sure ?");
        alertDialogBuilder.setMessage("this operation can't be undone.");
        this.proceedBtn=proceedBtn;
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listOfProducts.size() > 0) {

                    final String orderNumber = UUID.randomUUID().toString();
                    final ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("just a glance..");
                    progressDialog.setMessage("submitting your order ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    FirebaseDatabase.getInstance().getReference().child("orders").child(FirebaseAuth.getInstance().getUid())
                            .child(orderNumber).setValue(listOfProducts).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(FirebaseAuth.getInstance().getUid()).child("cart").removeValue();
                                Intent i = new Intent(context, ReceiptActivity.class);
                                progressDialog.dismiss();
                                i.putExtra("orderId", orderNumber);


                                context.startActivity(i);
                            } else {
                                Toast.makeText(context, "" + task.getResult(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        }
                    });


                }
            }
        });

    }

    public List<ProductModel> getListOfProducts() {
        return listOfProducts;
    }

    @NonNull
    @Override
    public ShoppingCartRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_cart_row_item, viewGroup, false);


        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final ShoppingCartRecyclerAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.quantity=1;
        listOfProducts.get(i).setQuantity(viewHolder.quantity);

        viewHolder.increament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.quantity = viewHolder.quantity + 1;
                viewHolder.quantityTv.setText(viewHolder.quantity+"");
                viewHolder.totalPriceTv.setText(viewHolder.quantity * listOfProducts.get(i).getPriceAsInt()+"");
                listOfProducts.get(i).setQuantity(viewHolder.quantity);


            }
        });

        viewHolder.decreament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.quantity > 1) {
                    viewHolder.quantity = viewHolder.quantity - 1;
                    viewHolder.quantityTv.setText(viewHolder.quantity+"");
                    viewHolder.totalPriceTv.setText(viewHolder.quantity * listOfProducts.get(i).getPriceAsInt()+"");
                    listOfProducts.get(i).setQuantity(viewHolder.quantity);


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

        viewHolder.customisationEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listOfProducts.get(i).setCustomisation(viewHolder.customisationEt.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

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
         EditText customisationEt;


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
            customisationEt = itemView.findViewById(R.id.customisation_et);

        }
    }
}
