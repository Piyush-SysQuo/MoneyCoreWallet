package com.mpay.wallet.View.Activity.Splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.ChangeLanguage.ChangeLanguageActivity;
import com.mpay.wallet.View.Activity.Welcome.WelcomeActivityFirst;

import java.util.Locale;

import maes.tech.intentanim.CustomIntent;


public class SplashActivity extends AppCompatActivity {

//    private LoginResponse loginResponse;
    private FirebaseAnalytics mFirebaseAnalytics;
    private boolean isSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView IV_Splash1 = (ImageView) findViewById(R.id.IV_Splash1);
        ImageView IV_Splash2 = (ImageView) findViewById(R.id.IV_Splash2);
        IV_Splash1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in));
        IV_Splash2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_animation));

        // get data from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_SPLASH_NAME,MODE_PRIVATE);
        isSkip = sharedPreferences.getBoolean(Constants.KEY_SKIP_SPLASH,false);
        setLocale("en");
        new Handler().postDelayed(() -> {
                    if (SharedPreferenceAmount.getInstance(this).getString_Language(Constants.KEY_LANGUAGE) != null) {
                        Intent i = null;
                        String lngCode = SharedPreferenceAmount.getInstance(this).getString_Language(Constants.KEY_LANGUAGE);
                        i = new Intent(SplashActivity.this, WelcomeActivityFirst.class);
                        i.putExtra(Constants.KEY_LANGUAGE, lngCode);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        CustomIntent.customType(this, "left-to-right");
                    }
                    else
                    {
                        Intent i = null;
                        i = new Intent(SplashActivity.this, ChangeLanguageActivity.class);
                        i.putExtra(Constants.KEY_LANGUAGE, "");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        CustomIntent.customType(this, "left-to-right");
                    }
                    /*
                Intent Animation Type

                left-to-right
                right-to-left
                bottom-to-up
                up-to-bottom
                fadein-to-fadeout
                rotateout-to-rotatein
                */
            finish();
        }, 4000);
    }


    public  void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}