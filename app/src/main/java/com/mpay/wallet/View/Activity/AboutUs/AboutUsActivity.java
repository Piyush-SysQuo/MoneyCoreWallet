package com.mpay.wallet.View.Activity.AboutUs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.More.MoreActivity;
import com.mpay.wallet.View.Activity.Setting.SettingActivity;

public class AboutUsActivity extends AppCompatActivity {
    TextView txt_about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        txt_about = findViewById(R.id.txt_about);

        txt_about.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n\n\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry. took a galley of type and scrambled it to make a type specimen book. desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, SettingActivity.class);
        startActivity(in);
        finish();
    }
}