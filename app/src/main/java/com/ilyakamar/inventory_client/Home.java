package com.ilyakamar.inventory_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ilyakamar.inventory_client.Common.Common;
import com.ilyakamar.inventory_client.Interface.ItemClickListener;
import com.ilyakamar.inventory_client.Model.Category;
import com.ilyakamar.inventory_client.Service.ListenOrder;
import com.ilyakamar.inventory_client.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {/// START

    FirebaseDatabase database;
    DatabaseReference category;

     FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    RecyclerView recyler_menu;
    RecyclerView.LayoutManager layoutManager;



    TextView txtFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {// onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("תפריט");
        setSupportActionBar(toolbar);


        // Init Firebase
        database = FirebaseDatabase.getInstance();
        category= database.getReference("Category");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        // Set name for user
        View headerView = navigationView.getHeaderView(0);
//        txtFullName = findViewById(R.id.txtFullName);
        //txtFullName.setText(Common.currentUser.getName);

        // Load menu
        recyler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recyler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyler_menu.setLayoutManager(layoutManager);

        loadMenu();


        // Register Service
        Intent service = new Intent(Home.this, ListenOrder.class);
        startService(service);




    }//*************** end onCreate

    private void loadMenu() {// loadMenu

         adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(
                Category.class,
                R.layout.menu_item,
                MenuViewHolder.class,
                category
        ) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {// populateViewHolder



                viewHolder.txtMenuName.setText(model.getName());

                Picasso.with(getBaseContext())
                        .load(model
                        .getImage())
                        .into(viewHolder.imageView);

                final Category clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {// onClick
//                        Toast.makeText(Home.this, ""+clickItem.getName(),
//                                Toast.LENGTH_SHORT).show();

                        // get category and send to new activity
                        Intent foodList = new Intent(Home.this,FoodList.class);
                        // because categoryId is key , so we just get key of this item
                         foodList.putExtra(Common.CATEGORY_ID,adapter.getRef(position).getKey() );// adapter.getRef(position).getKey()
                        startActivity(foodList);

                    }// end onClick
                });




            }// end populateViewHolder
        };

        recyler_menu.setAdapter(adapter);



    }// end loadMenu

    @Override
    public void onBackPressed() {// onBackPressed
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }// end onBackPressed

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {// onCreateOptionsMenu
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }// end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {// onOptionsItemSelected


        return super.onOptionsItemSelected(item);
    }// end onOptionsItemSelected

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {// onNavigationItemSelected
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(Home.this,Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent(Home.this,OrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.nav_log_out) {

            // Logout
            Intent signIn = new Intent(Home.this,Signin.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(signIn);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }// end onNavigationItemSelected
}// END
