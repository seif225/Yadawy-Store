package com.example.ss.WorkShopsPackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ss.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class WorkshopRecyclerAdapter extends RecyclerView.Adapter<WorkshopRecyclerAdapter.ViewHolder> {


    private Context context;
    private ArrayList<WorkshopModel> listOfModels;

    WorkshopRecyclerAdapter(ArrayList<WorkshopModel> listOfModels, Context context) {
        this.context = context;
        this.listOfModels = listOfModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.work_shops_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.workshopTitle.setText(listOfModels.get(i).getWorkShopName());
        viewHolder.userName.setText(listOfModels.get(i).getUserName());
        if(listOfModels.get(i).getWorkshopImage()!=null)
        Picasso.get().load(listOfModels.get(i).getWorkshopImage()).resize(600,300).into(viewHolder.workShopImage);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listOfModels.get(i).getFormLink()!=null) {
                    Uri url = Uri.parse(listOfModels.get(i).getFormLink());
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(url);
                    context.startActivity(i);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
      if (listOfModels==null){return 0;}
        return listOfModels.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName,workshopTitle;
        ImageView workShopImage;
         ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.user_name_workshop_row_item);
            workshopTitle=itemView.findViewById(R.id.workshop_name);
            workShopImage = itemView.findViewById(R.id.workshop_imageVew);
        }
    }
}
