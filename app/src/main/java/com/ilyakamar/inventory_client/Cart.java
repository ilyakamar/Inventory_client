package com.ilyakamar.inventory_client;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ilyakamar.inventory_client.Common.Common;
import com.ilyakamar.inventory_client.Database.Database;
import com.ilyakamar.inventory_client.Model.Order;
import com.ilyakamar.inventory_client.Model.Request;
import com.ilyakamar.inventory_client.ViewHolder.CartAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {// START

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace,btnCancelOrder;


    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {// onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        // Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");


        // Init
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        txtTotalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.btnPlaceOrder);
        btnCancelOrder = findViewById(R.id.btnCancelOrder);


        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtTotalPrice.getText().toString().trim().equals("0")){
                    Toast.makeText(Cart.this, "ההזמנה ריקה",
                            Toast.LENGTH_SHORT).show();
                }else {
                    showAlertDialog();
                }


            }
        });

        LoadListFood();

        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).cleanCart();
                finish();
            }
        });



    }// end onCreate

    private void showAlertDialog() {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("עוד צעד אחרון!");
        alertDialog.setMessage("הכנס את הכתובת למשלוח: ");

        LayoutInflater inflater = this.getLayoutInflater();
        View order_address_comment = inflater.inflate(R.layout.order_address_comment,null);

        // init
        final EditText edtAddress = order_address_comment.findViewById(R.id.edtAddress);
        final EditText edtComment = order_address_comment.findViewById(R.id.edtComment);

        alertDialog.setView(order_address_comment);
        alertDialog.setIcon(R.drawable.ic_cart_black_24dp);

        alertDialog.setPositiveButton("כן", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(edtAddress.getText().toString().trim().equals("")){
                    Toast.makeText(Cart.this, "חובה להזין כתובת",
                            Toast.LENGTH_SHORT).show();
                }else {
                    // create new request
                    Request request = new Request(
                            Common.currentUser.getPhone(),
                            Common.currentUser.getName(),
                            edtAddress.getText().toString(),
                            txtTotalPrice.getText().toString(),
                            "0", // status
                            edtComment.getText().toString(),
                            cart
                    );




                    // submit to firebase
                    // we will using system.currentMilli to key
                    requests.child(String.valueOf(System.currentTimeMillis()))
                            .setValue(request);
                    // Delete cart   *********************************************************************
                    new Database(getBaseContext()).cleanCart();
                    Toast.makeText(Cart.this, "תודה על ההזמנה",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });

        alertDialog.setNegativeButton("לא", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();

    }

    private void LoadListFood() {// LoadListFood

        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //calculate total price
        int total = 0;



        for (Order order:cart){

                total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));




            // tsarih livdok im nahuts
//            Locale locale = new Locale("en","US");
//            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);


            // try

            // Get locale's currency.
             NumberFormat mCurrencyFormat = NumberFormat.getCurrencyInstance();

            txtTotalPrice.setText(mCurrencyFormat.format(total));
        }

    }// end LoadListFood


}// END
