package com.ilyakamar.inventory_client;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartScreen_Activity extends AppCompatActivity implements View.OnClickListener {

    Button bt_startScreen_join;
    Button bt_startScreen_logIn ;
    TextView tv_startScreen_welcome;
    // TAG
    private static final String TAG = "StartScreen_Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {// onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);


        initControl();


    }// end onCreate


    private void initControl() {// initControl


        bt_startScreen_join =  findViewById(R.id.bt_startScreen_join);
        bt_startScreen_logIn =  findViewById(R.id.bt_startScreen_logIn);
        tv_startScreen_welcome =  findViewById(R.id.tv_startScreen_welcome);

        bt_startScreen_join.setOnClickListener(this);
        bt_startScreen_logIn.setOnClickListener(this);

        changeFont();

    }// end initControl

    private void changeFont() {
        Typeface face  = Typeface.createFromAsset(getAssets(),"fonts/NABILA.TTF");
        tv_startScreen_welcome.setTypeface(face);
    }


    @Override
    public void onClick(View view) {// onClick

        switch (view.getId()) {
            case R.id.bt_startScreen_join:
                Intent signupIntent = new Intent(StartScreen_Activity.this,SignUp.class);
                startActivity(signupIntent);
                Log.d(TAG, "onClick: bt_startScreen_join");

                break;

            case R.id.bt_startScreen_logIn:
                Intent loginIntent = new Intent(StartScreen_Activity.this,Signin.class);
                startActivity(loginIntent);
                Log.d(TAG, "onClick: bt_startScreen_logIn");
                break;
        }// end switch
    }// end onClick


}// END
