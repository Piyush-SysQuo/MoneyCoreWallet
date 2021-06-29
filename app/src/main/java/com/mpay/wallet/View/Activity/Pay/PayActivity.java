package com.mpay.wallet.View.Activity.Pay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.AlertPopup;
import com.mpay.wallet.View.Activity.Home.HomeActivity;
import com.mpay.wallet.View.Fragment.Confirm_Transfer.ConfirmTransferFragment;

public class PayActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Tv_Pay_Tot_Amt, Tv_Pay_Name, Tv_Pay_Addredd, Tv_Pay_100, Tv_Pay_200,
            Tv_Pay_300, Tv_Pay_400, Tv_Pay_Fee;
    LinearLayout Layout_Pay_Topup;
    ImageView Iv_Pay_UserPic;
    TextInputLayout MTIL_Pay_SendAmount, MTIL_Pay_DescrpLayout;
    TextInputEditText EditText_Pay_SendAmount, EditText_Pay_Description;
    TextView Button_Pay_Payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Initilization();
    }
    private void Initilization() {
        Tv_Pay_Tot_Amt              = findViewById(R.id.Tv_Pay_Tot_Amt);
        Tv_Pay_Name                 = findViewById(R.id.Tv_Pay_Name);
        Tv_Pay_Addredd              = findViewById(R.id.Tv_Pay_Addredd);
        Tv_Pay_100                  = findViewById(R.id.Tv_Pay_100);
        Tv_Pay_200                  = findViewById(R.id.Tv_Pay_200);
        Tv_Pay_300                  = findViewById(R.id.Tv_Pay_300);
        Tv_Pay_400                  = findViewById(R.id.Tv_Pay_400);
        Tv_Pay_Fee                  = findViewById(R.id.Tv_Pay_Fee);
        Button_Pay_Payment          = findViewById(R.id.Button_Pay_Payment);


        Layout_Pay_Topup            = findViewById(R.id.Layout_Pay_Topup);

        Iv_Pay_UserPic              = findViewById(R.id.Iv_Pay_UserPic);

        MTIL_Pay_SendAmount       = findViewById(R.id.MTIL_Pay_SendAmount);
        EditText_Pay_SendAmount    = findViewById(R.id.EditText_Pay_SendAmount);
        MTIL_Pay_DescrpLayout       = findViewById(R.id.MTIL_Pay_DescrpLayout);
        EditText_Pay_Description    = findViewById(R.id.EditText_Pay_Description);

        Tv_Pay_100.setOnClickListener(this);
        Tv_Pay_200.setOnClickListener(this);
        Tv_Pay_300.setOnClickListener(this);
        Tv_Pay_400.setOnClickListener(this);
    }
    public void backPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if( v == Tv_Pay_100)
        {
            Tv_Pay_100.setBackgroundResource(R.drawable.button_background);
            Tv_Pay_100.setTextColor(getResources().getColor(R.color.white));

            Tv_Pay_200.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_200.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            Tv_Pay_300.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_300.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            Tv_Pay_400.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_400.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            EditText_Pay_SendAmount.setText(Tv_Pay_100.getText().toString().replaceAll("€", ""));
        }
        else if( v == Tv_Pay_200)
        {
            Tv_Pay_200.setBackgroundResource(R.drawable.button_background);
            Tv_Pay_200.setTextColor(getResources().getColor(R.color.white));

            Tv_Pay_100.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_100.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            Tv_Pay_300.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_300.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            Tv_Pay_400.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_400.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            EditText_Pay_SendAmount.setText(Tv_Pay_200.getText().toString().replaceAll("€", ""));
        }
        else if( v == Tv_Pay_300)
        {
            Tv_Pay_300.setBackgroundResource(R.drawable.button_background);
            Tv_Pay_300.setTextColor(getResources().getColor(R.color.white));

            Tv_Pay_200.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_200.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            Tv_Pay_100.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_100.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            Tv_Pay_400.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_400.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            EditText_Pay_SendAmount.setText(Tv_Pay_300.getText().toString().replaceAll("€", ""));
        }
        else if( v == Tv_Pay_400)
        {
            Tv_Pay_400.setBackgroundResource(R.drawable.button_background);
            Tv_Pay_400.setTextColor(getResources().getColor(R.color.white));

            Tv_Pay_200.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_200.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            Tv_Pay_300.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_300.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            Tv_Pay_100.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
            Tv_Pay_100.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            EditText_Pay_SendAmount.setText(Tv_Pay_400.getText().toString().replaceAll("€", ""));
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = null;
        i = new Intent(PayActivity.this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    public void DoPayment(View view) {
        double Tot_Amount = Double.parseDouble(Tv_Pay_Tot_Amt.getText().toString().replaceAll("€", ""));
        if (EditText_Pay_SendAmount.getText().toString().isEmpty()) {
            openDialog("Please enter Amount");
        } else if ( Double.parseDouble(EditText_Pay_SendAmount.getText().toString()) > Tot_Amount) {
            openDialog("Enter Amount should not greate then Total Wallet Amount");
        } else {
            Toast.makeText(PayActivity.this, "Money Transfered", Toast.LENGTH_SHORT).show();
            try {
                double Send_Amount = Double.parseDouble(EditText_Pay_SendAmount.getText().toString());
                ConfirmTransferFragment confirmTransferFragment = new ConfirmTransferFragment();
                Bundle bundle = new Bundle();
                bundle.putString("MOBILE_NO", EditText_Pay_SendAmount.getText().toString());
                bundle.putString("AMOUNT", String.valueOf(Send_Amount));
                confirmTransferFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, confirmTransferFragment).addToBackStack("Transfer").commit();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // open dialog
    private void openDialog(String msg){

        AlertPopup mAlert = new AlertPopup(this);
        mAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlert.setMessage(msg);
        mAlert.setOkButton(view -> {
            mAlert.dismiss();
            //Do want you want
        });
        mAlert.show();
    }
}