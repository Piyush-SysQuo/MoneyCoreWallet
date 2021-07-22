package com.mpay.wallet.View.Activity.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.AlertPopup;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.Progress;
import com.mpay.wallet.View.Activity.ForgotPassword.viewmodel.ForgotPasswordViewModel;
import com.mpay.wallet.View.Activity.Home.HomeActivity;
import com.mpay.wallet.View.Activity.Login.LoginActivity;
import com.mpay.wallet.View.Activity.Login.model.LoginModel;
import com.mpay.wallet.View.Activity.Login.viewmodel.LoginViewModel;

import java.util.HashMap;
import java.util.Objects;

public class ForgotPasswordActivity extends AppCompatActivity implements CountryCodePicker.OnCountryChangeListener {
    public TextInputEditText EditText_MobileNumber;
    CountryCodePicker ccp;
    public TextInputLayout MTIL_mobileNumberLayout;

    private String countryCode;


    private FirebaseAuth mAuth;
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks;
    private String verificationCode;

    private Progress progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EditText_MobileNumber= (TextInputEditText)findViewById(R.id.EditText_MobileNumber);
        MTIL_mobileNumberLayout  = (TextInputLayout) findViewById(R.id.MTIL_mobileNumberLayout);
        ccp=    (CountryCodePicker )findViewById(R.id.ccp);
        // Country Picker Listener
        ccp.setOnCountryChangeListener(this);
        countryCode = ccp.getDefaultCountryCodeWithPlus();
        // initialize progress dialog instance
        progress = new Progress(this);
        (Objects.requireNonNull(progress.getWindow())).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
    }

    public void attemptResetPassword(View view) {

        if (EditText_MobileNumber.getText().toString().length() < 10) {
            MTIL_mobileNumberLayout.setError("Please Enter Correct Mobile No.");
           MTIL_mobileNumberLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));

        }else{
            progress.show();
            // call sign up api
            ForgotPasswordViewModel viewModel = ViewModelProviders.of(ForgotPasswordActivity.this).get(ForgotPasswordViewModel.class);
            viewModel.init();
            HashMap<String,String> SendData =new HashMap<>();
            SendData.put(Constants.PR_MOBLINE,countryCode+EditText_MobileNumber.getText().toString());

            viewModel.checkNumber(SendData);
            viewModel.getVolumesResponseLiveData().observe(ForgotPasswordActivity.this, response -> {

                progress.hide();

                if(response!=null){
                    boolean status = response.isStatus();
                    if(status){
                        Intent i=new Intent(this,VerificationActivity.class);
                        i.putExtra(Constants.MOBILE_NO,countryCode+EditText_MobileNumber.getText().toString());
                        i.putExtra(Constants.EMAIL,response.getResult());
                        startActivity(i);

//                            CustomIntent.customType(SignupSecondActivity.this, "left-to-right");
                    }else{
                        openDialog(response.getMessage());
                    }
                }
                else{
                    openDialog("Something went wrong");
//                    openDialog(signUpResponse.getMessage());
                }
            });

        }

    }


    private void openDialog(String msg){

        AlertPopup mAlert = new AlertPopup(this);
        mAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlert.setMessage(msg);
        mAlert.setOkButton( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlert.dismiss();
                //Do want you want
            }
        });
        mAlert.show();
    }
    @Override
    public void onCountrySelected() {
        countryCode = ccp.getSelectedCountryCodeWithPlus();
    }
}