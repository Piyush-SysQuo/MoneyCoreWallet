package com.mpay.wallet.View.Activity.More;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.mpay.wallet.R;
import com.mpay.wallet.Utils.AlertPopup;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.Home.HomeActivity;
import com.mpay.wallet.View.Activity.Login.LoginActivity;
import com.mpay.wallet.View.Activity.ManageAccount.ManageAccountActivity;
import com.mpay.wallet.View.Activity.MyProfile.MyProfileActivity;
import com.mpay.wallet.View.Activity.MyQRCodeActivity;
import com.mpay.wallet.View.Activity.SecurityPinChange.SecurityPinChangeActivity;
import com.mpay.wallet.View.Activity.Setting.SettingActivity;
import com.mpay.wallet.View.Activity.Support.SupportActivity;

public class MoreActivity extends AppCompatActivity {
    Switch SwitchBtn_MoreLockUnLock;
    int counterSwitch = 0;
    ImageView Iv_MoreUserPic;
    TextView Tv_MoreUserName, TextView_MoreUserMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        SwitchBtn_MoreLockUnLock = findViewById(R.id.SwitchBtn_MoreLockUnLock);
        Iv_MoreUserPic = findViewById(R.id.Iv_MoreUserPic);
        Tv_MoreUserName = findViewById(R.id.Tv_MoreUserName);
        TextView_MoreUserMail = findViewById(R.id.TextView_MoreUserMail);

        TextView_MoreUserMail.setText(SharedPreferenceAmount.getInstance(this).getString_Mail(Constants.EMAIL));

        if(SharedPreferenceAmount.getInstance(this).getString_Mail(Constants.EMAIL).equals("daviddorvik@gmail.com"))
        {
            Tv_MoreUserName.setText("David Dorvik");
            Iv_MoreUserPic.setImageDrawable(getResources().getDrawable(R.drawable.man_1));
        }
        else if(SharedPreferenceAmount.getInstance(this).getString_Mail(Constants.EMAIL).equals("william@gmail.com"))
        {
            Tv_MoreUserName.setText("William Rae");
            Iv_MoreUserPic.setImageDrawable(getResources().getDrawable(R.drawable.man_2));

        }
    }

    public void onClickProfile(View view) {
        Intent in = new Intent(this, MyProfileActivity.class);
        in.putExtra("ACTIVITY_CLASS", "MORE");
        startActivity(in);
        finish();
    }

    public void onClickMyQRCode(View view) {
        Intent in = new Intent(this, MyQRCodeActivity.class);
        in.putExtra("ACTIVITY_CLASS", "MORE");
        startActivity(in);
        finish();
    }

    public void onClickChangeSecurityCode(View view) {
        Intent in = new Intent(this, SecurityPinChangeActivity.class);
        in.putExtra("ACTIVITY_CLASS", "MORE");
        startActivity(in);
        finish();
    }

    public void onClickLockUnlock(View view) {
        if(counterSwitch == 0)
        {
            counterSwitch = 1;
            SwitchBtn_MoreLockUnLock.setChecked(true);
        }
        else if(counterSwitch == 1)
        {
            counterSwitch = 0;
            SwitchBtn_MoreLockUnLock.setChecked(false);
        }
    }

    public void onClickManageAccount(View view) {
        Intent in = new Intent(this, ManageAccountActivity.class);
        in.putExtra("ACTIVITY_CLASS", "MORE");
        startActivity(in);
        finish();
    }

    public void onClickSupport(View view) {
        Intent in = new Intent(this, SupportActivity.class);
        startActivity(in);
        finish();
    }

    public void onClickSetting(View view) {
        Intent in = new Intent(this, SettingActivity.class);
        in.putExtra("ACTIVITY_CLASS", "MORE");
        startActivity(in);
        finish();
    }

    public void onClickLogOut(View view) {
        openDialog("Do you want Logout!");
    }
    private void openDialog(String msg){

        AlertPopup mAlert = new AlertPopup(this);
        mAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlert.setMessage(msg);
        mAlert.setOkButton(view -> {
            mAlert.dismiss();
            Intent in = new Intent(this, LoginActivity.class);
            in.putExtra("ACTIVITY_CLASS", "MORE");
            startActivity(in);
            finish();
            //Do want you want
        });
        mAlert.show();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, HomeActivity.class);
        in.putExtra("ACTIVITY_CLASS", "MORE");
        startActivity(in);
        finish();
    }
}