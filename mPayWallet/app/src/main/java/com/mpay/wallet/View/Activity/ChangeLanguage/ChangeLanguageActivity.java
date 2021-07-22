package com.mpay.wallet.View.Activity.ChangeLanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.Welcome.WelcomeActivityFirst;
import com.mpay.wallet.View.Fragment.home.adapter.RecentTransactionAdapter;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import maes.tech.intentanim.CustomIntent;

public class ChangeLanguageActivity extends AppCompatActivity {

    LinearLayout LinearLayout_English, LinearLayout_France;
    TextView Tv_SaveLanguage;
    String languageCode = "er";
    String caller = null;
    String packageNm = null;
    Class callerClass = null;
    CircleImageView Img_English, Img_France;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        LinearLayout_English= findViewById(R.id.LinearLayout_English);
        LinearLayout_France = findViewById(R.id.LinearLayout_France);
        Tv_SaveLanguage     = findViewById(R.id.Tv_SaveLanguage);
        Img_English         = findViewById(R.id.Img_English);
        Img_France          = findViewById(R.id.Img_France);

        Glide.with(getApplicationContext()).load(getApplicationContext().getResources().getDrawable(R.drawable.flag_united_kingdom)).into(Img_English);
        Glide.with(getApplicationContext()).load(getApplicationContext().getResources().getDrawable(R.drawable.flag_france)).into(Img_France);
        /*Img_English.setImageDrawable(this.getResources().getDrawable(R.drawable.flag_united_kingdom));
        Img_France.setImageDrawable(this.getResources().getDrawable(R.drawable.flag_united_kingdom));*/


        try {
            caller     = getIntent().getStringExtra("caller");
            packageNm     = getIntent().getStringExtra("packageNm");
            if(caller != null) {
                callerClass = Class.forName("com.mpay.wallet.View.Activity.Login."+caller);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void englisgSelect(View view) {
        LinearLayout_English.setBackgroundResource(R.drawable.background_round);
        LinearLayout_English.setSelected(true);
        LinearLayout_France.setSelected(false);
        LinearLayout_France.setBackgroundResource(R.drawable.background_square_gray);

        languageCode = "er";
        SharedPreferenceAmount.getInstance(getApplicationContext()).setString_Language(Constants.KEY_LANGUAGE, languageCode);
        setLocale(languageCode);
        if(caller != null)
        {

            Intent i = null;
            i = new Intent(ChangeLanguageActivity.this, callerClass);
            i.putExtra(Constants.KEY_LANGUAGE,languageCode);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            CustomIntent.customType(this, "left-to-right");
            finish();
        }
        else {
            Intent i = null;
            i = new Intent(ChangeLanguageActivity.this, WelcomeActivityFirst.class);
            i.putExtra(Constants.KEY_LANGUAGE, languageCode);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            CustomIntent.customType(this, "left-to-right");
            finish();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void frenchSelect(View view) {
        LinearLayout_English.setBackgroundResource(R.drawable.background_square_gray);
        LinearLayout_English.setSelected(false);
        LinearLayout_France.setSelected(true);
        LinearLayout_France.setBackgroundResource(R.drawable.background_round);

        languageCode = "fr";
        SharedPreferenceAmount.getInstance(getApplicationContext()).setString_Language(Constants.KEY_LANGUAGE, languageCode);
        setLocale(languageCode);
        if(caller != null)
        {

            Intent i = null;
            i = new Intent(ChangeLanguageActivity.this, callerClass);
            i.putExtra(Constants.KEY_LANGUAGE,languageCode);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            CustomIntent.customType(this, "left-to-right");
            finish();
        }
        else {
            Intent i = null;
            i = new Intent(ChangeLanguageActivity.this, WelcomeActivityFirst.class);
            i.putExtra(Constants.KEY_LANGUAGE, languageCode);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            CustomIntent.customType(this, "left-to-right");
            finish();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void SaveLanguage(View view) {
        if(LinearLayout_English.isSelected() == false && LinearLayout_France.isSelected() == false)
        {
            Toast.makeText(this, "Please Select Application Language", Toast.LENGTH_SHORT).show();
        }
        else {
            setLocale(languageCode);
            if(caller != null)
            {

                Intent i = null;
                String lngCode = SharedPreferenceAmount.getInstance(this).getString_Language(Constants.KEY_LANGUAGE);
                i = new Intent(ChangeLanguageActivity.this, callerClass);
                i.putExtra(Constants.KEY_LANGUAGE, lngCode);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                CustomIntent.customType(this, "left-to-right");
                finish();
            }
            else {
                Intent i = null;
                String lngCode = SharedPreferenceAmount.getInstance(this).getString_Language(Constants.KEY_LANGUAGE);
                i = new Intent(ChangeLanguageActivity.this, WelcomeActivityFirst.class);
                i.putExtra(Constants.KEY_LANGUAGE, lngCode);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                CustomIntent.customType(this, "left-to-right");
                finish();
            }
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public  void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}