package com.ilyakamar.inventory_client.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilyakamar.inventory_client.Interface.ItemClickListener;
import com.ilyakamar.inventory_client.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;


    public MenuViewHolder(View itemView) {// cons
        super(itemView);

        txtMenuName = itemView.findViewById(R.id.menu_name);
        imageView = itemView.findViewById(R.id.menu_image);


        itemView.setOnClickListener(this);


    }// end cons


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {// onClick
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }// end onClick
}
