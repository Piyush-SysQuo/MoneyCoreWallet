package com.mpay.wallet.View.Activity.Welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.Login.VIEW.LoginActivity;

import maes.tech.intentanim.CustomIntent;

public class WelcomeActivitySecond extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_second);
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