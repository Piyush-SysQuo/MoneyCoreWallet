package com.mpay.wallet.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpay.wallet.R;

public class MyKeyboard  extends LinearLayout implements View.OnClickListener {

    private TextView button1, button2, button3, button4,
            button5, button6, button7, button8,
            button9, button0, buttonDelete, buttonEnter;

    private SparseArray<String> keyValues = new SparseArray<>();
    private InputConnection inputConnection;

    public MyKeyboard(Context context) {
        this(context, null, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.keyboard_number, this, true);
        button1 = (TextView) findViewById(R.id.TvKeyPad_1);
        button1.setOnClickListener(this);
        button2 = (TextView) findViewById(R.id.TvKeyPad_2);
        button2.setOnClickListener(this);
        button3 = (TextView) findViewById(R.id.TvKeyPad_3);
        button3.setOnClickListener(this);
        button4 = (TextView) findViewById(R.id.TvKeyPad_4);
        button4.setOnClickListener(this);
        button5 = (TextView) findViewById(R.id.TvKeyPad_5);
        button5.setOnClickListener(this);
        button6 = (TextView) findViewById(R.id.TvKeyPad_6);
        button6.setOnClickListener(this);
        button7 = (TextView) findViewById(R.id.TvKeyPad_7);
        button7.setOnClickListener(this);
        button8 = (TextView) findViewById(R.id.TvKeyPad_8);
        button8.setOnClickListener(this);
        button9 = (TextView) findViewById(R.id.TvKeyPad_9);
        button9.setOnClickListener(this);
        button0 = (TextView) findViewById(R.id.TvKeyPad_0);
        button0.setOnClickListener(this);
        buttonDelete = (TextView) findViewById(R.id.TvKeyPad_Delete);
        buttonDelete.setOnClickListener(this);
        buttonEnter = (TextView) findViewById(R.id.TvKeyPad_Complete);
        buttonEnter.setOnClickListener(this);

        keyValues.put(R.id.TvKeyPad_1, "1");
        keyValues.put(R.id.TvKeyPad_2, "2");
        keyValues.put(R.id.TvKeyPad_3, "3");
        keyValues.put(R.id.TvKeyPad_4, "4");
        keyValues.put(R.id.TvKeyPad_5, "5");
        keyValues.put(R.id.TvKeyPad_6, "6");
        keyValues.put(R.id.TvKeyPad_7, "7");
        keyValues.put(R.id.TvKeyPad_8, "8");
        keyValues.put(R.id.TvKeyPad_9, "9");
        keyValues.put(R.id.TvKeyPad_0, "0");
        keyValues.put(R.id.TvKeyPad_Comma, ",");
//        keyValues.put(R.id.TvKeyPad_Comma, "\n");
    }

    @Override
    public void onClick(View view) {
        if (inputConnection == null)
            return;

        if (view.getId() == R.id.TvKeyPad_Delete) {
            CharSequence selectedText = inputConnection.getSelectedText(0);

            if (TextUtils.isEmpty(selectedText)) {
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                inputConnection.commitText("", 1);
            }
        } else {
            String value = keyValues.get(view.getId());
            inputConnection.commitText(value, 1);
        }
    }

    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;
    }
}