package com.ilyakamar.inventory_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ilyakamar.inventory_client.Common.Common;
import com.ilyakamar.inventory_client.Interface.ItemClickListener;
import com.ilyakamar.inventory_client.Model.Food;
import com.ilyakamar.inventory_client.ViewHolder.FoodViewHolder;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;



    String categoryId = "";

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;


    // Search Functionality
    FirebaseRecyclerAdapter<Food,FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;






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




        // Get Intent here
        if (getIntent()!= null)
            categoryId = getIntent().getStringExtra(Common.CATEGORY_ID);   // change to  "CategoryId"


        if (!categoryId.isEmpty()&& categoryId != null)
        {
            loadListFood (categoryId);
        }


        // Search
        materialSearchBar = findViewById(R.id.searchBar);
        materialSearchBar.setHint("הזן מוצר לחיפוש");
     //    materialSearchBar.setSpeechMode(false); // no need , becus we already define it in xml
        loadSuggest(); // write function to load suggest from firebase

        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {// addTextChangeListener
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }// end beforeTextChanged

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // when user type their text, we will change suggest list

                List<String> suggest = new ArrayList<String>();
                for (String search:suggestList) // loop in suggest List
                {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);

            }// end onTextChanged

            @Override
            public void afterTextChanged(Editable editable) {

            }// end afterTextChanged
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

                // when Search Bar is close
                // Restore original  adapter
                if (!enabled)
                    recyclerView.setAdapter(adapter);


            }// end onSearchStateChanged

            @Override
            public void onSearchConfirmed(CharSequence text) {
                // When search finish
                // show result of search adapter
                startSearch(text);



            }// end onSearchConfirmed

            @Override
            public void onButtonClicked(int buttonCode) {

            }// end onButtonClicked
        });








    }// end onCreate

    private void startSearch(CharSequence text) {// startSearch

        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("name").equalTo(text.toString())   // Compare name  ////#########
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
//                        Toast.makeText(FoodList.this, ""+local.getName(),
//                                Toast.LENGTH_SHORT).show();

                        // Start new Activity
                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",searchAdapter.getRef(position).getKey()); // send food id to new activity
                        startActivity(foodDetail);
                    }
                });

            }// end populateViewHolder
        };

        recyclerView.setAdapter(searchAdapter); // set adapter for Recycler View is Search result




    }// end startSearch

    private void loadSuggest() {

        foodList.orderByChild("menuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Food item = postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName()); // add name of food to suggest list
                        }

                    }// end onDataChange

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }// end onCancelled
                });

    }

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

                        // Start new Activity
                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey()); // send food id to new activity
                        startActivity(foodDetail);
                    }
                });
            }
        };

        // Set Adapter
        recyclerView.setAdapter(adapter);

    }
}// END
