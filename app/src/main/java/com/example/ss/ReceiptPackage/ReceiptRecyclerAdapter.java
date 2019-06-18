package com.example.ss.ReceiptPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ss.HomeFragmentV2Package.ProductModel;
import com.example.ss.R;

import java.util.List;

public class ReceiptRecyclerAdapter extends RecyclerView.Adapter<ReceiptRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> listOfOrders;
    private TextView totalTv;
    private int totalPrice=0;

    ReceiptRecyclerAdapter(Context context, List<ProductModel> listOfOrders, TextView totalTv) {
        this.context = context;
        this.listOfOrders = listOfOrders;
        this.totalTv = totalTv;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.receipt_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.quantityTv.setText(listOfOrders.get(i).getQuantity()+"X");
        viewHolder.nameTv.setText(listOfOrders.get(i).getProductName());
        viewHolder.priceTv.setText(listOfOrders.get(i).getPriceAsInt()+"L.E");
        viewHolder.totalTv.setText(listOfOrders.get(i).getQuantity()*listOfOrders.get(i).getPriceAsInt()+"L.E");
        totalPrice = totalPrice +listOfOrders.get(i).getQuantity()*listOfOrders.get(i).getPriceAsInt();
        totalTv.setText("Total : "+totalPrice+" L.E");
        totalTv.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        if (listOfOrders == null) {
            return 0;
        }
        return listOfOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView quantityTv, nameTv, priceTv, totalTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quantityTv = itemView.findViewById(R.id.quantity_recepit_row);
            nameTv = itemView.findViewById(R.id.name_recepit_row);
            priceTv = itemView.findViewById(R.id.price_recepit_row);
            totalTv = itemView.findViewById(R.id.total_recepit_row);

        }
    }
}
