package com.mpay.wallet.View.Activity.Statement.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mpay.wallet.R;

public class StatementSendViaMailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement_send_via_mail);
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, StatementActivity.class);
        startActivity(in);
        finish();
    }
}