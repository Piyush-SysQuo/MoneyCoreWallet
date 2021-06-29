package com.mpay.wallet.View.Activity.Withdraw;

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

public class ConfirmWithdrawActivity extends AppCompatActivity {
    TextView Tv_CnfrmWithdAmt, Tv_CnfrmWithdBank;
    EditText etfirstWithdraw, etsecandWithdraw, etthirdWithdraw, etfourthWithdraw;
    NumberFormat formatter;
    String BANK_NAME = null, BANK_NO = null, AMOUNT = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_withdraw);
        Initilization();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void Initilization() {
        formatter = new DecimalFormat("#0.00");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            BANK_NAME = extras.getString("BANK_NAME");
            BANK_NO = extras.getString("BANK_NO");
            AMOUNT = extras.getString("AMOUNT");
        }
        if(BANK_NAME == null)
        {
            BANK_NAME = "Bank of America";
            BANK_NO = "**** 2893";
        }
//        TextView
        Tv_CnfrmWithdAmt = findViewById(R.id.Tv_CnfrmWithdAmt);
        Tv_CnfrmWithdBank = findViewById(R.id.Tv_CnfrmWithdBank);

        etfirstWithdraw = findViewById(R.id.etfirstWithdraw);
        etsecandWithdraw = findViewById(R.id.etsecandWithdraw);
        etthirdWithdraw = findViewById(R.id.etthirdWithdraw);
        etfourthWithdraw = findViewById(R.id.etfourthWithdraw);

        Tv_CnfrmWithdAmt.setText("€"+formatter.format(Double.parseDouble(AMOUNT))+"");
        Tv_CnfrmWithdBank.setText(BANK_NAME+"  "+BANK_NO);
        TextChangeListener();

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void TextChangeListener()
    {
        etfirstWithdraw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (etfirstWithdraw.getText().toString().length() == 1) {
                    etsecandWithdraw.requestFocus();
                } else {
                    etfirstWithdraw.requestFocus();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etfirstWithdraw.getText().toString().length() == 1) {
                    etsecandWithdraw.requestFocus();
                } else {
                    etfirstWithdraw.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etsecandWithdraw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etsecandWithdraw.getText().toString().length() == 1) {
                    etthirdWithdraw.requestFocus();
                } else {
                    etfirstWithdraw.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etthirdWithdraw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etthirdWithdraw.getText().toString().length() == 1) {
                    etfourthWithdraw.requestFocus();
                } else {
                    etsecandWithdraw.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etfourthWithdraw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etfourthWithdraw.getText().toString().length() == 1) {
                    //                    fou.continueTv.requestFocus();
                    try {
//                        hideSoftKeyboard(requireActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    etthirdWithdraw.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void confirmWithdraw(View view) {
        if (etfirstWithdraw.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            return;
        } else if (etsecandWithdraw.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            return;
        } else if (etthirdWithdraw.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            return;
        } else if (etfourthWithdraw.getText().toString().isEmpty()) {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String firstBox = etfirstWithdraw.getText().toString().trim();
            String secBox = etsecandWithdraw.getText().toString().trim();
            String thirdBox = etthirdWithdraw.getText().toString().trim();
            String fourthBox = etfourthWithdraw.getText().toString().trim();
            String fOtp = firstBox + secBox + thirdBox + fourthBox;
            if (fOtp.equals("1234")) {
                Intent i = null;
                i = new Intent(this, Cash_Withdraw_ReceiptActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("BANK_NAME", BANK_NAME);
                i.putExtra("BANK_NO", BANK_NO);
                i.putExtra("AMOUNT", Tv_CnfrmWithdAmt.getText().toString().replace("€", ""));
                i.putExtra("ACTIVITY_CLASS", "WITHDRAW");
                startActivity(i);
                this.finish();
            } else {
                Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void cancelWithdraw(View view) {
        onBackPressed();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void backPressed(View view) {
        onBackPressed();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onBackPressed() {
        Intent in =  new Intent(this, WithdrawActivity.class);
        startActivity(in);
        finish();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}