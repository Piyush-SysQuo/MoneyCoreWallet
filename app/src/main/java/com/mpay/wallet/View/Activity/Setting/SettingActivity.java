package com.mpay.wallet.View.Activity.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.AboutUs.AboutUsActivity;
import com.mpay.wallet.View.Activity.FAQ.FAQActivity;
import com.mpay.wallet.View.Activity.ManageAccount.ManageAccountActivity;
import com.mpay.wallet.View.Activity.More.MoreActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SettingActivity extends AppCompatActivity {
    Switch Switch_SettingNotification, Switch_SettingBio;
    TextView Tv_SettingVersion;
    int notificationCounter = 0, biometricCounter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Switch_SettingNotification = findViewById(R.id.Switch_SettingNotification);
        Switch_SettingBio = findViewById(R.id.Switch_SettingBio);
        Tv_SettingVersion = findViewById(R.id.Tv_SettingVersion);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;
            Tv_SettingVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    public void notificationOnOff(View view) {
        if(notificationCounter == 0)
        {
            notificationCounter = 1;
            Switch_SettingNotification.setChecked(true);
        }
        else if(notificationCounter == 1)
        {
            notificationCounter = 0;
            Switch_SettingNotification.setChecked(false);
        }
    }

    public void biometricOnOff(View view) {
        if(biometricCounter == 0)
        {
            biometricCounter = 1;
            Switch_SettingBio.setChecked(true);
        }
        else if(biometricCounter == 1)
        {
            biometricCounter = 0;
            Switch_SettingBio.setChecked(false);
        }
    }

    public void aboutUs(View view) {
        Intent in = new Intent(this, AboutUsActivity.class);
        in.putExtra("ACTIVITY_CLASS", "MORE");
        startActivity(in);
        finish();
    }

    public void faqActivityCall(View view) {
        Intent in = new Intent(this, FAQActivity.class);
        in.putExtra("ACTIVITY_CLASS", "MORE");
        startActivity(in);
        finish();
    }

    public void changeLanguage(View view) {
        boolean isWithIcons = true;
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(this, R.style.MyPopupOtherStyle);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, view);

        /*  The below code in try catch is responsible to display icons*/
        if (isWithIcons) {
            try {
                Field[] fields = popup.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popup);
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //inflate menu
        popup.getMenuInflater().inflate(R.menu.lamguage_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                /*if(menuItem.getTitle().equals("English")) {
                    TV_Login_select_Lng.setText("EN");
                }
                if(menuItem.getTitle().equals("French")) {
                    TV_Login_select_Lng.setText("FR");
                }*/
                return true;
            }
        });
        popup.show();

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, MoreActivity.class);
        startActivity(in);
        finish();
    }
}