package com.mpay.wallet.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.Home.HomeActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Cash_Withdraw_ReceiptActivity extends AppCompatActivity {
    TextView Tv_AmountINOUT, Tv_CashInRecptBankName, Tv_CashInRecptAmountReceived, Tv_CashInRecptFee, Tv_CashInRecptNewBalance, Tv_Receipt;
    String BANK_NAME, BANK_NO, AMOUNT, ACTIVITY_CLASS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_withdraw_receipt);
        Initilization();
    }
    private void Initilization() {
        Bundle extras = getIntent().getExtras();
        NumberFormat formatter = new DecimalFormat("#0.00");
        if (extras != null) {
            BANK_NAME = extras.getString("BANK_NAME");
            BANK_NO = extras.getString("BANK_NO");
            AMOUNT = extras.getString("AMOUNT");
            ACTIVITY_CLASS = extras.getString("ACTIVITY_CLASS");
        }
        Tv_Receipt          = findViewById(R.id.Tv_Receipt);
        Tv_AmountINOUT          = findViewById(R.id.Tv_AmountINOUT);
        Tv_CashInRecptBankName          = findViewById(R.id.Tv_CashInRecptBankName);
        Tv_CashInRecptAmountReceived    = findViewById(R.id.Tv_CashInRecptAmountReceived);
        Tv_CashInRecptFee               = findViewById(R.id.Tv_CashInRecptFee);
        Tv_CashInRecptNewBalance        = findViewById(R.id.Tv_CashInRecptNewBalance);

        Tv_CashInRecptBankName.setText(BANK_NAME);
        Tv_CashInRecptAmountReceived.setText("€"+formatter.format(Double.parseDouble(AMOUNT))+"");
        Tv_CashInRecptFee.setText("€0.50");


        if(ACTIVITY_CLASS.equals("WITHDRAW"))
        {
            Tv_Receipt.setText(getString(R.string.withdraw_receipt));
            Tv_AmountINOUT.setText(getString(R.string.amount_withdraw));
            double TotAmt_old = Double.parseDouble(SharedPreferenceAmount.getInstance(this).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
            double TotAmt_New = (TotAmt_old - (Double.parseDouble(AMOUNT)));
            SharedPreferenceAmount.getInstance(getApplicationContext()).setString_TotAmount(Constants.TOTAL_AMOUNT, String.valueOf(TotAmt_New));
            Tv_CashInRecptNewBalance.setText("€"+formatter.format(TotAmt_New)+"");
        }
        if(ACTIVITY_CLASS.equals("CASH_IN"))
        {
            Tv_Receipt.setText(getString(R.string.cash_in_receipt));
            Tv_AmountINOUT.setText(getString(R.string.amount_received));
            double TotAmt_old = Double.parseDouble(SharedPreferenceAmount.getInstance(this).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
            double TotAmt_New = (TotAmt_old+(Double.parseDouble(AMOUNT)));
            SharedPreferenceAmount.getInstance(getApplicationContext()).setString_TotAmount(Constants.TOTAL_AMOUNT, String.valueOf(TotAmt_New));
            Tv_CashInRecptNewBalance.setText("€"+formatter.format(TotAmt_New)+"");
        }
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, HomeActivity.class);
        startActivity(in);
        finish();
    }
}