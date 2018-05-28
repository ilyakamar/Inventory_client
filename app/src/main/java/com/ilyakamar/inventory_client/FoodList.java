package com.ilyakamar.inventory_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ilyakamar.inventory_client.Common.Common;
import com.ilyakamar.inventory_client.Interface.ItemClickListener;
import com.ilyakamar.inventory_client.Model.Food;
import com.ilyakamar.inventory_client.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;



    String categoryId = "";

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {// onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


        // Firebase

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recyclerView = findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


//        // Get Intent here
//        if (getIntent() != null){
//            categoryId = getIntent().getStringExtra(Common.CATEGORY_ID);
//        }
//
//        // if (!categoryId.isEmpty() && categoryId != null)
//        if (!categoryId.isEmpty() && categoryId != null){ /////////////////??????  categoryId != null = allways true   <<<<<<
//            loadListFood(categoryId);
//        }

        if (getIntent()!= null)
            categoryId = getIntent().getStringExtra(Common.CATEGORY_ID);   // change to  "CategoryId"


        if (!categoryId.isEmpty()&& categoryId != null)
            loadListFood (categoryId);










    }// end onCreate

    private void loadListFood(String categoryId) {


        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("menuId").equalTo(categoryId) // like : Select * from foods where MenuId =
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.food_image);


                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(FoodList.this, ""+local.getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };


        // Set Adapter
        recyclerView.setAdapter(adapter);

    }


}// END
