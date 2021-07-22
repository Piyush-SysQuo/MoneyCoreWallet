package com.mpay.wallet.View.Activity.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mpay.wallet.R;
import com.mpay.wallet.Utils.AlertPopup;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.More.MoreActivity;
import com.mpay.wallet.View.Fragment.More.MoreFragment;
import com.mpay.wallet.View.Fragment.Scan_QR.ScanQRFragment;
import com.mpay.wallet.View.Fragment.Space.View.SpaceFragment;
import com.mpay.wallet.View.Fragment.home.view.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    ImageView   homeImageView, walletImageView, spaceImageView, scanQRImageView, moreImageView;
    TextView    homeTextView, walletTextView, spaceTextView, scanQRTextView, moreTextView;
    View        homeView, walletView, spaceView, scanQRView, moreView;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Initialization();
    }

    public void Initialization()
    {
        homeImageView   = findViewById(R.id.homeImageView);
        walletImageView = findViewById(R.id.walletImageView);
        spaceImageView  = findViewById(R.id.spaceImageView);
        scanQRImageView = findViewById(R.id.scanQRImageView);
        moreImageView   = findViewById(R.id.moreImageView);

        homeTextView    = findViewById(R.id.homeTextView);
        walletTextView  = findViewById(R.id.walletTextView);
        spaceTextView   = findViewById(R.id.spaceTextView);
        scanQRTextView  = findViewById(R.id.scanQRTextView);
        moreTextView    = findViewById(R.id.moreTextView);

        homeView        = findViewById(R.id.homeView);
        walletView      = findViewById(R.id.walletView);
        spaceView       = findViewById(R.id.spaceView);
        scanQRView      = findViewById(R.id.scanQRView);
        moreView        = findViewById(R.id.moreView);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new HomeFragment()).commit();
    }

    public void homeClick(View view) {
        homeImageView.setColorFilter(getResources().getColor(R.color.button_color));
        homeTextView.setTextColor(getResources().getColor(R.color.button_color));
        homeView.setBackgroundColor(getResources().getColor(R.color.button_color));

        walletImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        walletTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        walletView.setBackgroundColor(0);

        spaceImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        spaceTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        spaceView.setBackgroundColor(0);

        scanQRImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        scanQRTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        scanQRView.setBackgroundColor(0);

        moreImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        moreTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        moreView.setBackgroundColor(0);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new HomeFragment()).addToBackStack(null).commit();
    }

    public void walletsClick(View view) {
        homeImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        homeTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        homeView.setBackgroundColor(0);

        walletImageView.setColorFilter(getResources().getColor(R.color.button_color));
        walletTextView.setTextColor(getResources().getColor(R.color.button_color));
        walletView.setBackgroundColor(getResources().getColor(R.color.button_color));

        spaceImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        spaceTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        spaceView.setBackgroundColor(0);

        scanQRImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        scanQRTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        scanQRView.setBackgroundColor(0);

        moreImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        moreTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        moreView.setBackgroundColor(0);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new HomeFragment()).addToBackStack(null).commit();
    }

    public void spaceClick(View view) {
        homeImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        homeTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        homeView.setBackgroundColor(0);

        walletImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        walletTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        walletView.setBackgroundColor(0);

        spaceImageView.setColorFilter(getResources().getColor(R.color.button_color));
        spaceTextView.setTextColor(getResources().getColor(R.color.button_color));
        spaceView.setBackgroundColor(getResources().getColor(R.color.button_color));

        scanQRImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        scanQRTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        scanQRView.setBackgroundColor(0);

        moreImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        moreTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        moreView.setBackgroundColor(0);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new SpaceFragment()).addToBackStack(null).commit();
    }

    public void scanQrClick(View view) {
        homeImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        homeTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        homeView.setBackgroundColor(0);

        walletImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        walletTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        walletView.setBackgroundColor(0);

        spaceImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        spaceTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        spaceView.setBackgroundColor(0);

        scanQRImageView.setColorFilter(getResources().getColor(R.color.button_color));
        scanQRTextView.setTextColor(getResources().getColor(R.color.button_color));
        scanQRView.setBackgroundColor(getResources().getColor(R.color.button_color));

        moreImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        moreTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        moreView.setBackgroundColor(0);
        ScanQRFragment scanQRFragment = new ScanQRFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CLASS", "PAY");
        bundle.putString("TRANSACTION_TYPE", "TRANSFER");
        scanQRFragment.setArguments(bundle);

//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, scanQRFragment).addToBackStack("Home").commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, scanQRFragment).addToBackStack(null).commit();
    }

    public void moreClick(View view) {
        homeImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        homeTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        homeView.setBackgroundColor(0);

        walletImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        walletTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        walletView.setBackgroundColor(0);

        spaceImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        spaceTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        spaceView.setBackgroundColor(0);

        scanQRImageView.setColorFilter(getResources().getColor(R.color.ensan_gray));
        scanQRTextView.setTextColor(getResources().getColor(R.color.ensan_gray));
        scanQRView.setBackgroundColor(0);

        moreImageView.setColorFilter(getResources().getColor(R.color.button_color));
        moreTextView.setTextColor(getResources().getColor(R.color.button_color));
        moreView.setBackgroundColor(getResources().getColor(R.color.button_color));

        Intent in = new Intent(this, MoreActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            SharedPreferenceAmount.getInstance(getApplicationContext()).setString("LOGIN", "false");
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    private void openDialog(String msg){

        AlertPopup mAlert = new AlertPopup(this);
        mAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlert.setMessage(msg);
        mAlert.setOkButton(view -> {
            SharedPreferenceAmount.getInstance(getApplicationContext()).setString("LOGIN", "false");
            mAlert.dismiss();
            finish();
            //Do want you want
        });
        mAlert.show();
    }
}