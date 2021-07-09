package com.mpay.wallet.View.Activity.Support;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.View.Activity.More.MoreActivity;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, MoreActivity.class);
        startActivity(in);
        finish();
    }

    public void callUsForSupport(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", Constants.SUPPORT_PHONE, null));
        startActivity(intent);
    }

    public void mailUsForSupport(View view) {
        String[] addresses = {"support@moneycorepay.com"};
        composeEmail(addresses, "Need Support in Money Core Wallet");
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}