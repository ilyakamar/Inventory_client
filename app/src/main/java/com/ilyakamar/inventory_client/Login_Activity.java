//package com.ilyakamar.inventory_client;
//
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class Login_Activity extends AppCompatActivity implements View.OnClickListener{
//
//    // FIREBASE
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//
//    ImageView iv_closeLogin;
//
//    EditText et_activity_signup_password;
//    EditText et_activity_signup_email;
//
//    ProgressBar progressbar;
//
//    Button bt_login_activity_login;
//
//    private static final String TAG = "Login_Activity";
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {// onCreate
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//
//        initControl();
//
//
//
//
//
//    }// end
//
//
//    private void initControl() {// initControl
//
//
//        // Firebase
//        mAuth = FirebaseAuth.getInstance();
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference table_user = database.getReference("User");
//
//
////        iv_closeLogin = (ImageView) findViewById(R.id.iv_closeLogin);
//        et_activity_signup_email = (EditText) findViewById(R.id.et_activity_signup_email);
//        et_activity_signup_password = (EditText) findViewById(R.id.et_activity_signup_password);
//        bt_login_activity_login = (Button)findViewById(R.id.bt_login_activity_login);
//
//        progressbar = (ProgressBar)findViewById(R.id.progressbar_login);
//
//
//        //OnClick
//        bt_login_activity_login.setOnClickListener(this);
//        iv_closeLogin.setOnClickListener(this);
//
//
//
//
//    }// end initControl
//
//
//
//
//    /**
//     * Login
//     */
//
//
//
//    private void fb_userLogin(){// fb_userLogin
//
//        String  email = et_activity_signup_email.getText().toString().trim();
//        String  password =  et_activity_signup_password.getText().toString().trim();
//
//        if (email.isEmpty()){
//            et_activity_signup_email.setError("Email is required");
//            et_activity_signup_email.requestFocus();
//            return;
//        }
//        // chek for korection of mail
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            et_activity_signup_email.setError("Please enter a valid email");
//            et_activity_signup_email.requestFocus();
//            return;
//        }
//
//
//        if (password.isEmpty()){
//            et_activity_signup_password.setError("Password is required");
//            et_activity_signup_password.requestFocus();
//            return;
//        }
//
//
//        if (password.length()<6){
//            et_activity_signup_password.setError("Minimum lenght of password should be 6 ");
//            et_activity_signup_password.requestFocus();
//            return;
//        }
//
//        //progressbar
//        progressbar.setVisibility(View.VISIBLE);
//
//        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    // end progressbar
//                    progressbar.setVisibility(View.GONE);
//
//                    Intent intent = new Intent(Login_Activity.this,Home.class);
//                    // lehashmid kol activitis shehau lifnei
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }// end fb_userLogin
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bt_login_activity_login:
//                Log.d(TAG, "onClick: bt_login_activity_login");
//                fb_userLogin();
//                break;
//
////            case R.id.iv_closeLogin:
////                Login_Activity.super.onBackPressed();
////
////                break;
//
//
//
//
//        }// end switch
//    }
//}//  END
