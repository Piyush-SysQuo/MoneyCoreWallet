package com.mpay.wallet.View.Activity.Welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.mpay.wallet.R;
import com.mpay.wallet.Utils.OnSwipeTouchListener;
import com.mpay.wallet.View.Activity.Login.LoginActivity;

import maes.tech.intentanim.CustomIntent;

public class WelcomeActivitySecond extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_second);


        RelativeLayout Relative_Main_Second = findViewById(R.id.Relative_Main_Second);
        Relative_Main_Second.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeLeft() {
                Intent i = null;
                i = new Intent(WelcomeActivitySecond.this, WelcomeActivityThird.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                CustomIntent.customType(WelcomeActivitySecond.this, "left-to-right");
            }
            /*public void onSwipeTop() {
                Toast.makeText(getApplicationContext(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(getApplicationContext(), "bottom", Toast.LENGTH_SHORT).show();
            }*/

        });

        /*new Handler().postDelayed(() -> {
            Intent i = null;
            i = new Intent(WelcomeActivitySecond.this, WelcomeActivityThird.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            CustomIntent.customType(this, "left-to-right");
            finish();
        }, 1000);*/
    }

    public void Skip(View view) {
        Intent i = null;
        i = new Intent(WelcomeActivitySecond.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
        CustomIntent.customType(this, "left-to-right");
    }

    public void Next(View view) {
        Intent i = null;
        i = new Intent(WelcomeActivitySecond.this, WelcomeActivityThird.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
        CustomIntent.customType(this, "left-to-right");
    }
}