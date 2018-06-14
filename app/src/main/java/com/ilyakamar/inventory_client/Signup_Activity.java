package com.ilyakamar.inventory_client;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Signup_Activity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView_closeSignup;

    EditText et_email;
    EditText et_password;
    EditText et_password_repeat;

    Button bt_signup_join;

    private static final String TAG = "Signup_Activity";

    // FIREBASE
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {// onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // firebase
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() { /// mAuthListener
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                /**
                 * bdika im hauzer mehubar
                 */
                if (user !=null){
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged: User is signed in");
                    Toast.makeText(Signup_Activity.this, "User is signed in",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    // User is sign out
                    Log.d(TAG, "onAuthStateChanged: User is sign out");
                    Toast.makeText(Signup_Activity.this, "User is sign out",
                            Toast.LENGTH_SHORT).show();

                }
            }
        };/////////// end mAuthListener

        initControl();


    }// end onCreate


    private void initControl() {// initControl



        et_email = (EditText)findViewById(R.id.et_activity_signup_email);
        et_password  = (EditText)findViewById(R.id.et_activity_signup_password);
        et_password_repeat  = (EditText)findViewById(R.id.et_activity_signup_password_repeat);
//        imageView_closeSignup = (ImageView) findViewById(R.id.iv_closeSignup);
        bt_signup_join = (Button)findViewById(R.id.bt_signup_join);

        imageView_closeSignup.setOnClickListener(this);
        bt_signup_join.setOnClickListener(this);






    }// end initControl


    @Override
    public void onClick(View view) {// onClick

        switch (view.getId()) {
            case R.id.bt_signup_join:
                Log.d(TAG, "onClick: bt_signup_join");
                fb_registration();  // et_email.getText().toString(),et_password.getText().toString()

                break;

//            case R.id.iv_closeSignup:
//                Log.d(TAG, "onClick: iv_closeSignup");
//                Signup_Activity.super.onBackPressed();
//                break;

        }
    }// end onClick





    public void fb_registration(){// fb_registration



        String  email = et_email.getText().toString().trim();
        String  password =  et_password.getText().toString().trim();


        if (email.isEmpty()){
            et_email.setError("Email is required");
            et_email.requestFocus();
            return;
        }
        // chek for korection of mail
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please enter a valid email");
            et_email.requestFocus();
            return;
        }


        if (password.isEmpty()){
            et_password.setError("Password is required");
            et_password.requestFocus();
            return;
        }


        if (password.length()<6){
            et_password.setError("Minimum lenght of password should be 6 ");
            et_password.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Signup_Activity.this, "createUserWithEmail:success",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Signup_Activity.this,Home.class);
                            // lehashmid kol activitis shehau lifnei
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(Signup_Activity.this, "Email already Registered",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // updateUI(null);
                        }

                        // ...
                    }
                });

    }// end fb_registration


}// END