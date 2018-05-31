package com.ilyakamar.inventory_client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ilyakamar.inventory_client.Common.Common;
import com.ilyakamar.inventory_client.Model.User;

public class Signin extends AppCompatActivity {// START


    EditText edtPhone,edtPassword;
    Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {// onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edtPassword = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);
        btnSignIn = findViewById(R.id.btnSignIn);

       // Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // ProgressDialog
                final ProgressDialog mDialog = new ProgressDialog(Signin.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // Check if user not exist in database
                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()){

                            // Get user information

                            mDialog.dismiss();

                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                            user.setPhone(edtPhone.getText().toString());// set Phone
                            if (user.getPassword().equals(edtPassword.getText().toString())){
                                Toast.makeText(Signin.this, "Sign in successfully !",
                                        Toast.LENGTH_SHORT).show();

                                Intent homeIntent= new Intent(Signin.this,Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();
                            }else {
                                Toast.makeText(Signin.this, "Wrong Password !",
                                        Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            mDialog.dismiss();
                            Toast.makeText(Signin.this, "User not exist in Database",
                                    Toast.LENGTH_SHORT).show();
                        }




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }// end onCreate
}// END
