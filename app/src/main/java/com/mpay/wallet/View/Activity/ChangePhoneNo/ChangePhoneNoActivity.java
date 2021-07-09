package com.mpay.wallet.View.Activity.ChangePhoneNo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.MyProfile.MyProfileActivity;

public class ChangePhoneNoActivity extends AppCompatActivity {
    TextInputLayout MTIL_ChangePhone_PhoneNoLayout, MTIL_ChangePhone_SecurityPinLayout;
    TextInputEditText ChangePhone_EditText_PhoneNo, ChangePhone_EditText_SecurityPin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_no);

        MTIL_ChangePhone_PhoneNoLayout = findViewById(R.id.MTIL_ChangePhone_PhoneNoLayout);
        MTIL_ChangePhone_SecurityPinLayout = findViewById(R.id.MTIL_ChangePhone_SecurityPinLayout);

        ChangePhone_EditText_PhoneNo = findViewById(R.id.ChangePhone_EditText_PhoneNo);
        ChangePhone_EditText_SecurityPin = findViewById(R.id.ChangePhone_EditText_SecurityPin);
    }

    public void attemptChangePhoneNo(View view) {
        boolean result = false;
        if(ChangePhone_EditText_PhoneNo.getText().toString().isEmpty())
        {
            MTIL_ChangePhone_PhoneNoLayout.setErrorEnabled(true);
            MTIL_ChangePhone_PhoneNoLayout.setError("Enter New Phone No");
            MTIL_ChangePhone_PhoneNoLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            result = false;
        }
        if(!ChangePhone_EditText_PhoneNo.getText().toString().isEmpty() && ChangePhone_EditText_PhoneNo.getText().toString().length() < 11)
        {
            MTIL_ChangePhone_PhoneNoLayout.setErrorEnabled(true);
            MTIL_ChangePhone_PhoneNoLayout.setError("Enter correct Phone No");
            MTIL_ChangePhone_PhoneNoLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            result = false;
        }
        if (!ChangePhone_EditText_PhoneNo.getText().toString().isEmpty()  && ChangePhone_EditText_PhoneNo.getText().toString().length() == 11) {
            MTIL_ChangePhone_PhoneNoLayout.setErrorEnabled(false);
            result = true;
        }

        if(ChangePhone_EditText_SecurityPin.getText().toString().isEmpty())
        {
            MTIL_ChangePhone_SecurityPinLayout.setErrorEnabled(true);
            MTIL_ChangePhone_SecurityPinLayout.setError("Enter Security Pin");
            MTIL_ChangePhone_SecurityPinLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            result = false;
        }
        if (!ChangePhone_EditText_SecurityPin.getText().toString().isEmpty() ) {
            MTIL_ChangePhone_SecurityPinLayout.setErrorEnabled(false);
            result = true;
        }
        if (result == true)
        {
            Toast.makeText(this, "Phone Number Changed", Toast.LENGTH_SHORT).show();
        }
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, MyProfileActivity.class);
        startActivity(in);
        finish();
    }

}