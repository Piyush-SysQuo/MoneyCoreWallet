package com.mpay.wallet.View.Activity.ChangeEmailID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.MyProfile.MyProfileActivity;

public class ChangeEmailIDActivity extends AppCompatActivity {
    TextInputLayout MTIL_ChangeMail_emailLayout, MTIL_ChangeMail_SecurityPinLayout;
    TextInputEditText ChangeMail_EditText_Email, ChangeMail_EditText_SecurityPin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email_id);

        MTIL_ChangeMail_emailLayout = findViewById(R.id.MTIL_ChangePhone_PhoneNoLayout);
        MTIL_ChangeMail_SecurityPinLayout = findViewById(R.id.MTIL_ChangeMail_SecurityPinLayout);

        ChangeMail_EditText_Email = findViewById(R.id.ChangeMail_EditText_Email);
        ChangeMail_EditText_SecurityPin = findViewById(R.id.ChangeMail_EditText_SecurityPin);
    }

    public void attemptChangeEmailID(View view) {
        boolean result = false;
        if(ChangeMail_EditText_Email.getText().toString().isEmpty())
        {
            MTIL_ChangeMail_emailLayout.setErrorEnabled(true);
            MTIL_ChangeMail_emailLayout.setError("Enter Email ID");
            MTIL_ChangeMail_emailLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            result = false;
        }
        if(!ChangeMail_EditText_Email.getText().toString().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(ChangeMail_EditText_Email.getText().toString()).matches())
        {
            MTIL_ChangeMail_emailLayout.setErrorEnabled(true);
            MTIL_ChangeMail_emailLayout.setError("Enter correct Email ID");
            MTIL_ChangeMail_emailLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            result = false;
        }
        if (!ChangeMail_EditText_Email.getText().toString().isEmpty()  && Patterns.EMAIL_ADDRESS.matcher(ChangeMail_EditText_Email.getText().toString()).matches()) {
            MTIL_ChangeMail_emailLayout.setErrorEnabled(false);
            result = true;
        }

        if(ChangeMail_EditText_SecurityPin.getText().toString().isEmpty())
        {
            MTIL_ChangeMail_SecurityPinLayout.setErrorEnabled(true);
            MTIL_ChangeMail_SecurityPinLayout.setError("Enter Security Pin");
            MTIL_ChangeMail_SecurityPinLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            result = false;
        }
        if (!ChangeMail_EditText_SecurityPin.getText().toString().isEmpty() ) {
            MTIL_ChangeMail_SecurityPinLayout.setErrorEnabled(false);
            result = true;
        }
        if (result == true)
        {
            Toast.makeText(this, "Email Changed", Toast.LENGTH_SHORT).show();
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