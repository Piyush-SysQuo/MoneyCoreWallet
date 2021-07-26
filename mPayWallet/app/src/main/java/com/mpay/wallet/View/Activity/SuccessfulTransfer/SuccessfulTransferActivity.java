package com.mpay.wallet.View.Activity.SuccessfulTransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.Transfer_Receipt.TransferReceiptActivity;

public class SuccessfulTransferActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Tv_Success_Msg, Tv_Transfer_SucesName, Tv_Transfer_Amount, Tv_Transfer_OrderID, Tv_Transfer_OrderDT;
    ImageView Iv_Successful, Iv_Icon;
    String TRANSFER_AMOUNT, MOBILE_NO, TRANSACTION_TYPE, NAME, EMAIL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_transfer);
        Initilization();
    }
    private void Initilization() {
        Bundle extras = getIntent().getExtras();
        String userName;

        if (extras != null) {
            TRANSFER_AMOUNT = extras.getString("TRANSFER_AMOUNT");
            MOBILE_NO = extras.getString("MOBILE_NO");
            TRANSACTION_TYPE = extras.getString("TRANSACTION_TYPE");
            NAME = extras.getString("NAME");
            EMAIL = extras.getString("EMAIL");
        }
        Tv_Success_Msg = findViewById(R.id.Tv_Success_Msg);
        Tv_Transfer_SucesName = findViewById(R.id.Tv_Transfer_SucesName);
        Tv_Transfer_Amount = findViewById(R.id.Tv_Transfer_Amount);
        Tv_Transfer_OrderID = findViewById(R.id.Tv_Transfer_OrderID);
        Tv_Transfer_OrderDT = findViewById(R.id.Tv_Transfer_OrderDT);


        Iv_Icon = findViewById(R.id.Iv_Icon);
        Iv_Successful = findViewById(R.id.Iv_Successful);
        Iv_Successful.setOnClickListener(this);

        Tv_Transfer_Amount.setText("€"+TRANSFER_AMOUNT);
        Tv_Transfer_SucesName.setText(NAME);
        if(TRANSACTION_TYPE.equals("PAY"))
        {
            Tv_Success_Msg.setText(R.string.paid_successfully_to);
            Iv_Icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_paid));
        }
        else if(TRANSACTION_TYPE.equals("TRANSFER"))
        {
            Tv_Success_Msg.setText(R.string.successfully_transfered_to);
        }

        DateFormat df = new SimpleDateFormat("dd MMM, yyyy, hh:mm a");
        String date = df.format(Calendar.getInstance().getTime());
        Tv_Transfer_OrderDT.setText(date);
    }
    public void backPressed(View view) {
    }

    @Override
    public void onClick(View v) {
        if(v == Iv_Successful){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = null;
        i = new Intent(SuccessfulTransferActivity.this, TransferReceiptActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("TRANSFER_AMOUNT", TRANSFER_AMOUNT);
        i.putExtra("MOBILE_NO", MOBILE_NO);
        i.putExtra("NAME", NAME);
        i.putExtra("EMAIL", EMAIL);
        i.putExtra("TRANSACTION_TYPE", TRANSACTION_TYPE);
        startActivity(i);
        finish();
    }
}