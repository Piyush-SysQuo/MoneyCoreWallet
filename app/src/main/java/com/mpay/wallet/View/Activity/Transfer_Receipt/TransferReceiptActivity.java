package com.mpay.wallet.View.Activity.Transfer_Receipt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.Home.HomeActivity;
import com.mpay.wallet.View.Activity.SuccessfulTransfer.SuccessfulTransferActivity;

public class TransferReceiptActivity extends AppCompatActivity {
    TextView Tv_Receipt, Tv_TransRecptName, Tv_TransRecptAmount, Tv_TransRecptFee, Tv_TransRecptNewBalance;
    String TRANSFER_AMOUNT, MOBILE_NO, TRANSACTION_TYPE, NAME, EMAIL;
    double TOT_AMOUNT = 0.00, FEE = 0.00, NEW_AMOUNT= 0.00, LAST_SPENT = 0.00, NEW_SPENT = 0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_receipt);
        Initilization();
    }
    private void Initilization() {
        Bundle extras = getIntent().getExtras();
        String userName;

        if (extras != null) {
            TRANSFER_AMOUNT = extras.getString("TRANSFER_AMOUNT");
            MOBILE_NO = extras.getString("MOBILE_NO");
            NAME = extras.getString("NAME");
            EMAIL = extras.getString("EMAIL");
            TRANSACTION_TYPE = extras.getString("TRANSACTION_TYPE");
        }
        Tv_TransRecptName = findViewById(R.id.Tv_TransRecptName);
        Tv_TransRecptName.setText(NAME);
        Tv_Receipt = findViewById(R.id.Tv_Receipt);
        Tv_TransRecptAmount = findViewById(R.id.Tv_TransRecptAmount);
        Tv_TransRecptAmount.setText(TRANSFER_AMOUNT);
        Tv_TransRecptFee = findViewById(R.id.Tv_TransRecptFee);
        Tv_TransRecptNewBalance = findViewById(R.id.Tv_TransRecptNewBalance);
        TOT_AMOUNT = Double.parseDouble(SharedPreferenceAmount.getInstance(getApplicationContext()).getString_TotAmount(Constants.TOTAL_AMOUNT));
        LAST_SPENT = Double.parseDouble(SharedPreferenceAmount.getInstance(getApplicationContext()).getString_TotAmount(Constants.SPENT_AMOUNT));

        NEW_AMOUNT = TOT_AMOUNT - (Double.parseDouble(TRANSFER_AMOUNT)+Double.parseDouble(Tv_TransRecptFee.getText().toString().replaceAll("€", "")));
        Tv_TransRecptNewBalance.setText("€"+NEW_AMOUNT+"");


        NEW_SPENT = LAST_SPENT + Double.parseDouble(TRANSFER_AMOUNT);
        SharedPreferenceAmount.getInstance(getApplicationContext()).setString_TotAmount(Constants.TOTAL_AMOUNT, NEW_AMOUNT+"");
        SharedPreferenceAmount.getInstance(getApplicationContext()).setString_SendAmount(Constants.SPENT_AMOUNT, NEW_SPENT+"");

        if(TRANSACTION_TYPE.equals("PAY"))
        {
            Tv_Receipt.setText(R.string.pay_receipt);
        }
        else if(TRANSACTION_TYPE.equals("TRANSFER"))
        {
            Tv_Receipt.setText(R.string.transfer_receipt);
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = null;
        i = new Intent(TransferReceiptActivity.this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    public void backPressed(View view) {
        onBackPressed();
    }
}