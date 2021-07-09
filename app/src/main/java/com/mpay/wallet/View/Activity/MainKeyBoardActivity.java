package com.mpay.wallet.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.MyKeyboard;
import com.mpay.wallet.View.Activity.SecurityPinChange.SecurityPinChangeActivity;

public class MainKeyBoardActivity extends AppCompatActivity {
    PinEntryEditText PinEntryEditText_OLD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_key_board);
        PinEntryEditText_OLD = findViewById(R.id.PinEntryEditText_OLD);

        MyKeyboard keyboard = (MyKeyboard) findViewById(R.id.keyboard);
        PinEntryEditText_OLD.setRawInputType(InputType.TYPE_CLASS_TEXT);
        PinEntryEditText_OLD.setTextIsSelectable(true);

        InputConnection ic = PinEntryEditText_OLD.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);


        if (PinEntryEditText_OLD != null) {
            PinEntryEditText_OLD.setAnimateText(true);
            PinEntryEditText_OLD.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals("1234")) {
                        try {
                            Toast.makeText(MainKeyBoardActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        PinEntryEditText_OLD.setError(true);

                        try {
                            Toast.makeText(MainKeyBoardActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
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
    }
}