package com.mpay.wallet.Utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Patterns;
import android.widget.Toast;

import com.mpay.wallet.View.Activity.Login.LoginActivity;
import com.mpay.wallet.View.Activity.Signup.SignupActivity;
import com.mpay.wallet.View.Activity.Signup.SignupSecondActivity;
import com.mpay.wallet.View.Fragment.Transfer.TransferFragment;

public class Validation {
    private Context applicationContext;
    final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    public Validation(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public boolean ValidateEmail(LoginActivity loginActivity)
    {
        if(loginActivity.Login_EditText_Email.getText().toString().isEmpty() == true)
        {
            Toast.makeText(applicationContext, "Please enter Email Id", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(loginActivity.Login_EditText_Email.getText().toString()).matches())
        {
            Toast.makeText(applicationContext, "Please enter Valid Email Id", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(loginActivity.Login_EditText_Password.getText().toString().isEmpty() == true)
        {
            Toast.makeText(applicationContext, "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        /*else if(!loginActivity.Login_EditText_Password.getText().toString().matches(PASSWORD_PATTERN))
        {

        }*/
        return true;
    }

    public boolean SignUpValidation(SignupActivity signupActivity)
    {
        boolean result = false;
        try {
            int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0, i = 0, j = 0, k = 0;
            if  (signupActivity.EditText_FirstName.getText().toString().isEmpty() == true) {
                signupActivity.MTIL_FirstNameLayout.setErrorEnabled(true);
                signupActivity.MTIL_FirstNameLayout.setError("Please Enter First Name");
                signupActivity.MTIL_FirstNameLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                a = 0;
            }
            if (signupActivity.EditText_FirstName.getText().toString().isEmpty() == false) {
                signupActivity.MTIL_FirstNameLayout.setErrorEnabled(false);
                a = 1;
            }

            /*if (signupActivity.EditText_MiddleName.getText().toString().isEmpty() == true) {
                signupActivity.MTIL_MiddleNameLayout.setErrorEnabled(true);
                signupActivity.MTIL_MiddleNameLayout.setError("Please Enter Middle Name");
                signupActivity.MTIL_MiddleNameLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                d = 0;
            }
            if (signupActivity.EditText_MiddleName.getText().toString().isEmpty() == false) {
                signupActivity.MTIL_MiddleNameLayout.setErrorEnabled(false);
                d = 1;
            }*/


            if (signupActivity.EditText_LastName.getText().toString().isEmpty() == true) {
                signupActivity.MTIL_LastNameLayout.setError("Please Enter Last Name");
                signupActivity.MTIL_LastNameLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                b = 0;
            }
            if (signupActivity.EditText_LastName.getText().toString().isEmpty() == false) {
                signupActivity.MTIL_LastNameLayout.setErrorEnabled(false);
                b = 1;
            }


            if (signupActivity.EditText_MobileNumber.getText().toString().isEmpty() == true) {
                signupActivity.MTIL_mobileNumberLayout.setError("Please Enter Mobile No.");
                signupActivity.MTIL_mobileNumberLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                c = 0;
            }
            if (signupActivity.EditText_MobileNumber.getText().toString().length() < 10) {
                signupActivity.MTIL_mobileNumberLayout.setError("Please Enter Correct Mobile No.");
                signupActivity.MTIL_mobileNumberLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                c = 0;
            }
            if (signupActivity.EditText_MobileNumber.getText().toString().isEmpty() == false && signupActivity.EditText_MobileNumber.getText().toString().length() == 10) {
                signupActivity.MTIL_mobileNumberLayout.setErrorEnabled(false);
                c = 1;
            }


            if (signupActivity.EditText_Email.getText().toString().isEmpty() == true) {
                signupActivity.MTIL_emailLayout_SignUp.setError("Please Enter Email Address");
                signupActivity.MTIL_emailLayout_SignUp.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                e = 0;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(signupActivity.EditText_Email.getText().toString()).matches()) {
                signupActivity.MTIL_emailLayout_SignUp.setError("Please Enter Correct Email Address");
                signupActivity.MTIL_emailLayout_SignUp.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                e = 0;
            }
            if (Patterns.EMAIL_ADDRESS.matcher(signupActivity.EditText_Email.getText().toString()).matches() == true && signupActivity.EditText_Email.getText().toString().isEmpty() == false) {
                signupActivity.MTIL_emailLayout_SignUp.setErrorEnabled(false);
                e = 1;
            }



            if (signupActivity.EditText_DOB.getText().toString().isEmpty() == true) {
                signupActivity.MTIL_dobLayout.setError("Please Enter DOB");
                signupActivity.MTIL_dobLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                f = 0;
            }
            if (signupActivity.EditText_DOB.getText().toString().isEmpty() == false) {
                signupActivity.MTIL_dobLayout.setErrorEnabled(false);
                f = 1;
            }


            if (a == 0 && b == 0 && c == 0 && /*d == 0 &&*/ e == 0 && f == 0 ) {
                result = false;
            } else if (a == 1 && b == 1 && c == 1 && /*d == 1 &&*/ e == 1 && f == 1  ) {
                result = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean SignUpSecondValidation(SignupSecondActivity signupSecondActivity) {
        boolean result = false;
        try {
            int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0, i = 0, j = 0, k = 0;
            if(signupSecondActivity.EditText_Password.getText().toString().isEmpty() == true)
            {
                signupSecondActivity.MTIL_passwordLayout_SignUp.setError("Please Enter Password");
                signupSecondActivity.MTIL_passwordLayout_SignUp.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                a = 0;
            }
            if(!(signupSecondActivity.EditText_Password.getText().toString().length() >= 6))
            {
                signupSecondActivity.MTIL_passwordLayout_SignUp.setError("Password Length should be greate than 6 digit");
                signupSecondActivity.MTIL_passwordLayout_SignUp.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                a = 0;
            }
            if(!signupSecondActivity.EditText_Password.getText().toString().matches(PASSWORD_PATTERN))
            {
                signupSecondActivity.MTIL_passwordLayout_SignUp.setError("Password must contain at least one digit, upper case letter and special characters");
                signupSecondActivity.MTIL_passwordLayout_SignUp.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                a = 0;
            }
            if(!signupSecondActivity.EditText_Password.getText().toString().isEmpty() && signupSecondActivity.EditText_Password.getText().toString().matches(PASSWORD_PATTERN))
            {
                signupSecondActivity.MTIL_passwordLayout_SignUp.setErrorEnabled(false);
                a = 1;
            }

            if(signupSecondActivity.EditText_RePassword.getText().toString().isEmpty() == true)
            {
                signupSecondActivity.MTIL_re_passwordLayout_SignUp.setError("Please Enter Confirm Password");
                signupSecondActivity.MTIL_re_passwordLayout_SignUp.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                b = 0;
            }
            if(!(signupSecondActivity.EditText_RePassword.getText().toString().length() >= 6))
            {
                signupSecondActivity.MTIL_re_passwordLayout_SignUp.setError("Password Length should be greate than 6 digit");
                signupSecondActivity.MTIL_re_passwordLayout_SignUp.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                b = 0;
            }
            if(!signupSecondActivity.EditText_RePassword.getText().toString().matches(PASSWORD_PATTERN))
            {
                signupSecondActivity.MTIL_re_passwordLayout_SignUp.setError("Password must contain at least one digit, upper case letter and special characters");
                signupSecondActivity.MTIL_re_passwordLayout_SignUp.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                b = 0;
            }
            if(!signupSecondActivity.EditText_RePassword.getText().toString().isEmpty() && signupSecondActivity.EditText_RePassword.getText().toString().matches(PASSWORD_PATTERN))
            {
                signupSecondActivity.MTIL_re_passwordLayout_SignUp.setErrorEnabled(false);
                b = 1;
            }


            if(!signupSecondActivity.EditText_RePassword.getText().toString().equals(signupSecondActivity.EditText_Password.getText().toString()))
            {
                signupSecondActivity.MTIL_re_passwordLayout_SignUp.setError("Password no Matched");
                signupSecondActivity.MTIL_re_passwordLayout_SignUp.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                c = 0;
            }

            if((!signupSecondActivity.EditText_RePassword.getText().toString().isEmpty() && !signupSecondActivity.EditText_Password.getText().toString().isEmpty())&& signupSecondActivity.EditText_RePassword.getText().toString().equals(signupSecondActivity.EditText_Password.getText().toString()))
            {
                signupSecondActivity.MTIL_re_passwordLayout_SignUp.setErrorEnabled(false);
                c = 1;
            }


            if (signupSecondActivity.EditText_ID_TYpe_Menu.getText().toString().isEmpty() == true) {
                signupSecondActivity.MTIL_idTypeLayout_Menu.setError("Please Select Id Type");
                signupSecondActivity.MTIL_idTypeLayout_Menu.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                d = 0;
            }
            if (signupSecondActivity.EditText_ID_TYpe_Menu.getText().toString().isEmpty() == false) {
                signupSecondActivity.MTIL_idTypeLayout_Menu.setErrorEnabled(false);
                d = 1;
            }


            if (signupSecondActivity.EditText_ID_Number.getText().toString().isEmpty() == true) {
                signupSecondActivity.MTIL_idNumberLayout.setError("Please Enter "+signupSecondActivity.EditText_ID_TYpe_Menu.getText().toString()+" Number");
                signupSecondActivity.MTIL_idNumberLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                e = 0;
            }
            if (signupSecondActivity.EditText_ID_Number.getText().toString().isEmpty() == false) {
                signupSecondActivity.MTIL_idNumberLayout.setErrorEnabled(false);
                e = 1;
            }


            if (signupSecondActivity.frontImageFile == null && signupSecondActivity.backImageFile == null) {
                Toast.makeText(signupSecondActivity, "Please Click ID front Images", Toast.LENGTH_SHORT).show();
                f = 0;
            }
            if (signupSecondActivity.frontImageFile != null && signupSecondActivity.backImageFile != null) {
                f = 1;
            }

            if(signupSecondActivity.ck_condition.isChecked() == false)
            {
                Toast.makeText(signupSecondActivity, "Please Check Terms and Condition", Toast.LENGTH_SHORT).show();
                g = 0;
            }
            if(signupSecondActivity.ck_condition.isChecked() == true)
            {
                g = 1;
            }


            if (a == 0 && b == 0 && c == 0 && d == 0 && e == 0 && f == 0 && g == 0 ) {
                result = false;
            } else if (a == 1 && b == 1 && c == 1 && d == 1 && e == 1 && f == 1 && g == 1 ) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean ConfirmFrag(TransferFragment transferFragment){
        boolean result = false;
        int a=0, b= 0;
        String Msg = null;
        if(transferFragment.EditText_Trnsfr_Mobile.getText().toString().isEmpty()){
            a = 0;
            Msg = "Please Enter Mobile No";
        }
        return result;
    }
}
