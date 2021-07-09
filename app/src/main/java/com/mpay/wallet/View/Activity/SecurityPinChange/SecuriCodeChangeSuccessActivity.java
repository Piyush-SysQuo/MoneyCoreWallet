package com.mpay.wallet.View.Activity.SecurityPinChange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.Login.LoginActivity;

public class SecuriCodeChangeSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_securi_code_change_success);
    }

    public void continueLogin(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, LoginActivity.class);
        startActivity(in);
        finish();
    }

    public void backPressed(View view) {
        onBackPressed();
    }
}