package com.mpay.wallet.View.Activity.SecurityPinChange;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.Login.LoginActivity;
import com.mpay.wallet.View.Activity.More.MoreActivity;

public class SecurityPinChangeActivity extends AppCompatActivity {
    PinEntryEditText PinEntryEditText_OLD, PinEntryEditText_NEW,PinEntryEditText_ConfirmNew;
    LinearLayout Layout_ChangeSecurityCodeOld, Layout_ChangeSecurityCodeNew, Layout_ChangeSecurityCodeConfirmNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_pin_change);

        PinEntryEditText_OLD = findViewById(R.id.PinEntryEditText_OLD);
        PinEntryEditText_NEW = findViewById(R.id.PinEntryEditText_NEW);
        PinEntryEditText_ConfirmNew = findViewById(R.id.PinEntryEditText_ConfirmNew);

        Layout_ChangeSecurityCodeOld = findViewById(R.id.Layout_ChangeSecurityCodeOld);
        Layout_ChangeSecurityCodeNew = findViewById(R.id.Layout_ChangeSecurityCodeNew);
        Layout_ChangeSecurityCodeConfirmNew = findViewById(R.id.Layout_ChangeSecurityCodeConfirmNew);

        Layout_ChangeSecurityCodeOld.setVisibility(View.VISIBLE);
        Layout_ChangeSecurityCodeNew.setVisibility(View.GONE);
        Layout_ChangeSecurityCodeConfirmNew.setVisibility(View.GONE);


        if (PinEntryEditText_OLD != null) {
            PinEntryEditText_OLD.setAnimateText(true);
            PinEntryEditText_OLD.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals("1234")) {
                        Toast.makeText(SecurityPinChangeActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        Layout_ChangeSecurityCodeOld.setVisibility(View.GONE);
                        Layout_ChangeSecurityCodeNew.setVisibility(View.VISIBLE);
                        Layout_ChangeSecurityCodeConfirmNew.setVisibility(View.GONE);
                        try {
                            PinEntryEditText_NEW.setSelected(true);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                PinEntryEditText_NEW.setFocusable(1);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        PinEntryEditText_OLD.setError(true);
                        Toast.makeText(SecurityPinChangeActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                        PinEntryEditText_OLD.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PinEntryEditText_OLD.setText(null);
                            }
                        }, 1000);
                    }
                }
            });
        }

        if (PinEntryEditText_NEW != null) {
            PinEntryEditText_NEW.setAnimateText(true);
            PinEntryEditText_NEW.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals("9876")) {
                        Toast.makeText(SecurityPinChangeActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        Layout_ChangeSecurityCodeOld.setVisibility(View.GONE);
                        Layout_ChangeSecurityCodeNew.setVisibility(View.GONE);
                        Layout_ChangeSecurityCodeConfirmNew.setVisibility(View.VISIBLE);
                    } else {
                        PinEntryEditText_NEW.setError(true);
                        Toast.makeText(SecurityPinChangeActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                        PinEntryEditText_NEW.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PinEntryEditText_NEW.setText(null);
                            }
                        }, 1000);
                    }
                }
            });
        }

        if (PinEntryEditText_ConfirmNew != null) {
            PinEntryEditText_ConfirmNew.setAnimateText(true);
            PinEntryEditText_ConfirmNew.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals("9876")) {
                        Intent in = new Intent(SecurityPinChangeActivity.this, SecuriCodeChangeSuccessActivity.class);
                        startActivity(in);
                        finish();
                    } else {
                        PinEntryEditText_ConfirmNew.setError(true);
                        Toast.makeText(SecurityPinChangeActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                        PinEntryEditText_ConfirmNew.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                PinEntryEditText_ConfirmNew.setText(null);
                            }
                        }, 1000);
                    }
                }
            });
        }
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, MoreActivity.class);
        startActivity(in);
        finish();
    }
}