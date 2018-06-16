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
import com.ilyakamar.inventory_client.Model.User;

public class SignUp extends AppCompatActivity {// START


    EditText edtPhone,edtPassword,edtName;
    Button btnSignUp;
    boolean ok = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {// onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        edtPhone = findViewById(R.id.edtPhone);

        btnSignUp = findViewById(R.id.btnSignUp);

        // Init Firebase
               final FirebaseDatabase database = FirebaseDatabase.getInstance();
               final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ProgressDialog
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("אנא המתן...");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String noPhone,noName,noPassword,corectNumber;


                        noPhone = edtPhone.getText().toString().trim();
                        noName = edtName.getText().toString().trim();
                        noPassword = edtPassword.getText().toString().trim();
                        corectNumber = edtPhone.getText().toString().trim();




                        // check if NO PARMS
                        if (noPhone.isEmpty()){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "חובה להזין מספר טלפון",
                                    Toast.LENGTH_SHORT).show();
                        }else
                        if (edtPhone.getText().toString().trim().length()<10){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "המספר אינו תקין",
                                    Toast.LENGTH_SHORT).show();
                        }else
                            // Check if already user phone
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()){
                                mDialog.dismiss();

                                if (ok)
                                Toast.makeText(SignUp.this, "המספר כבר קיים במערכת",Toast.LENGTH_SHORT).show();

                            }else

                            /// try
                        if (!(corectNumber.charAt(0)=='0')|| !(corectNumber.charAt(1)=='5')
                                 ){   /// !corectNumber.contains("054")||
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "קידומת של המספר אינה תקינה",
                                    Toast.LENGTH_SHORT).show();
                        }else
                            // end try
                            if (noName.isEmpty()){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "חובה להזין שם משתמש",
                                    Toast.LENGTH_SHORT).show();
                        }else
                            if (edtName.getText().toString().trim().length()<2){
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "שם משתמש קצר מידי",
                                        Toast.LENGTH_SHORT).show();
                            }else
                        if (noPassword.isEmpty()){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "חובה להזין סיסמה",
                                    Toast.LENGTH_SHORT).show();
                        }else
                        if (edtPassword.getText().toString().trim().length()<6){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "חובה להזין לפחות 6 תווים בסיסמה",
                                    Toast.LENGTH_SHORT).show();
                        }else

                             {
                            mDialog.dismiss();
                            // try to fix i add Phone
                            User user = new User(edtName.getText().toString(),edtPassword.getText().toString(),edtPhone.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "ההרשמה בוצעה בהצלחה ! ",
                                    Toast.LENGTH_SHORT).show();
                            Intent homeIntent= new Intent(SignUp.this,Home.class);
                            startActivity(homeIntent);
                            finish();
                                 ok= false;
                        }


                    }// end onDataChange

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }// end onCreate
}// END
