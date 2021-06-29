package com.mpay.wallet.View.Activity.CashIn.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.Cash_Withdraw_ReceiptActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ConfirmCashinActivity extends AppCompatActivity {

    TextView Tv_CashInCnfrmBankName, Tv_CashInCnfrmBankNo, Tv_CashInCnfrmBankAmt; 
    EditText etfirst, etsecand, etthird, etfourth;
    String BANK_NAME, BANK_NO, AMOUNT;
    NumberFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_cashin);
        Initilization();
    }

    public void Initilization()
    {
        formatter = new DecimalFormat("#0.00");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            BANK_NAME = extras.getString("BANK_NAME");
            BANK_NO = extras.getString("BANK_NO");
            AMOUNT = extras.getString("AMOUNT");
        }

        Tv_CashInCnfrmBankName = findViewById(R.id.Tv_CashInCnfrmBankName);
        Tv_CashInCnfrmBankNo = findViewById(R.id.Tv_CashInCnfrmBankNo);
        Tv_CashInCnfrmBankAmt = findViewById(R.id.Tv_CashInCnfrmBankAmt);

        Tv_CashInCnfrmBankName.setText(BANK_NAME);
        Tv_CashInCnfrmBankNo.setText(BANK_NO);
        double newAmt = Double.parseDouble(AMOUNT)+0.50;
        Tv_CashInCnfrmBankAmt.setText("€"+formatter.format(newAmt)+"");


        etfirst = findViewById(R.id.etfirst);
        etsecand = findViewById(R.id.etsecand);
        etthird = findViewById(R.id.etthird);
        etfourth = findViewById(R.id.etfourth);

        TextChangeListener();
    }
    public void cancelCashIn(View view) {
        onBackPressed();
    }

    public void confirmCashIn(View view) {
        if (etfirst.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            return;
        } else if (etsecand.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            return;
        } else if (etthird.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            return;
        } else if (etfourth.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String firstBox = etfirst.getText().toString().trim();
            String secBox = etsecand.getText().toString().trim();
            String thirdBox = etthird.getText().toString().trim();
            String fourthBox = etfourth.getText().toString().trim();
            String fOtp = firstBox + secBox + thirdBox + fourthBox;
            if (fOtp.equals("1234")) {
                Intent i = null;
                i = new Intent(this, Cash_Withdraw_ReceiptActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("BANK_NAME", BANK_NAME);
                i.putExtra("BANK_NO", BANK_NO);
                i.putExtra("AMOUNT", Tv_CashInCnfrmBankAmt.getText().toString().replace("€", ""));
                i.putExtra("ACTIVITY_CLASS", "CASH_IN");
                startActivity(i);
                this.finish();
            } else {
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
    public void TextChangeListener()
    {
        etfirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (etfirst.getText().toString().length() == 1) {
                    etsecand.requestFocus();
                } else {
                    etfirst.requestFocus();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etfirst.getText().toString().length() == 1) {
                    etsecand.requestFocus();
                } else {
                    etfirst.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etsecand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etsecand.getText().toString().length() == 1) {
                    etthird.requestFocus();
                } else {
                    etfirst.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etthird.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etthird.getText().toString().length() == 1) {
                    etfourth.requestFocus();
                } else {
                    etsecand.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etfourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etfourth.getText().toString().length() == 1) {
                    //                    fou.continueTv.requestFocus();
                    try {
//                        hideSoftKeyboard(requireActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    etthird.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, CashInActivity.class);
        startActivity(in);
        finish();
    }

    public void backPressed(View view) {
        onBackPressed();
    }
}