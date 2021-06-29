package com.mpay.wallet.View.Activity.Welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.OnSwipeTouchListener;
import com.mpay.wallet.View.Activity.Login.LoginActivity;
import com.mpay.wallet.View.Activity.Splash.SplashActivity;

import maes.tech.intentanim.CustomIntent;

public class WelcomeActivityFirst extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_first);

        /*new Handler().postDelayed(() -> {
            Intent i = null;
            i = new Intent(WelcomeActivityFirst.this, WelcomeActivitySecond.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            CustomIntent.customType(this, "left-to-right");
            finish();
        }, 1000);*/
    }

    public void Skip(View view) {
        Intent i = null;
        i = new Intent(WelcomeActivityFirst.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
        CustomIntent.customType(this, "left-to-right");
    }

    public void Next(View view) {
        Intent i = null;
        i = new Intent(WelcomeActivityFirst.this, WelcomeActivitySecond.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
        CustomIntent.customType(this, "left-to-right");
    }
}