package com.mpay.wallet.View.Activity.Signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DatePickerDob;
import com.mpay.wallet.Utils.Progress;
import com.mpay.wallet.Utils.Utility;
import com.mpay.wallet.Utils.Validation;
import com.mpay.wallet.View.Activity.Login.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import maes.tech.intentanim.CustomIntent;

public class SignupActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{
    public TextInputLayout MTIL_FirstNameLayout,MTIL_MiddleNameLayout, MTIL_LastNameLayout, MTIL_mobileNumberLayout,
            MTIL_emailLayout_SignUp, MTIL_dobLayout;
    public TextInputEditText EditText_FirstName;
    public TextInputEditText EditText_MiddleName;
    public TextInputEditText EditText_LastName;
    public TextInputEditText EditText_MobileNumber;
    public TextInputEditText EditText_Email;
    public TextInputEditText EditText_DOB;
    ProgressBar ProgressImagesize;
    DatePickerDialog datePicker;
    DatePickerDialog.OnDateSetListener Date_DOB;
    Calendar Calendar_DOB;
    long minDate;
    private boolean isValid = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

                   Initialization();
                   if(savedInstanceState != null){
                       EditText_FirstName.setText(savedInstanceState.getString(Constants.FIRST_NAME));
                       EditText_MiddleName.setText(savedInstanceState.getString(Constants.MIDDLE_NAME));
                       EditText_LastName.setText(savedInstanceState.getString(Constants.LAST_NAME));
                       EditText_MobileNumber.setText(savedInstanceState.getString(Constants.MOBILE_NO));
                       EditText_DOB.setText(savedInstanceState.getString(Constants.DOB));
                       EditText_Email.setText(savedInstanceState.getString(Constants.EMAIL));
                   }
    }


    public void Initialization()
    {
        MTIL_FirstNameLayout     = (TextInputLayout) findViewById(R.id.MTIL_FirstNameLayout);
        MTIL_MiddleNameLayout     = (TextInputLayout) findViewById(R.id.MTIL_MiddleNameLayout);
        MTIL_LastNameLayout      = (TextInputLayout) findViewById(R.id.MTIL_LastNameLayout);
        MTIL_mobileNumberLayout  = (TextInputLayout) findViewById(R.id.MTIL_mobileNumberLayout);
        MTIL_emailLayout_SignUp         = (TextInputLayout) findViewById(R.id.MTIL_emailLayout_SignUp);
        MTIL_dobLayout           = (TextInputLayout) findViewById(R.id.MTIL_dobLayout);
        EditText_FirstName  = (TextInputEditText) findViewById(R.id.EditText_FirstName);
        EditText_MiddleName  = (TextInputEditText) findViewById(R.id.EditText_MiddleName);
        EditText_LastName   = (TextInputEditText)findViewById(R.id.EditText_LastName);
        EditText_MobileNumber      = (TextInputEditText)findViewById(R.id.EditText_MobileNumber);
        EditText_Email      = (TextInputEditText)findViewById(R.id.EditText_Email);
        EditText_DOB        = (TextInputEditText) findViewById(R.id.EditText_DOB);





        EditText_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerDob();
                datePicker.show(getSupportFragmentManager(), "date picker");

                /*Calendar_DOB    =   Calendar.getInstance();
                DateSet();
//                datePicker.show();*/
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.FIRST_NAME, EditText_FirstName.getText().toString());
        outState.putString(Constants.MIDDLE_NAME, EditText_MiddleName.getText().toString());
        outState.putString(Constants.LAST_NAME, EditText_LastName.getText().toString());
        outState.putString(Constants.MOBILE_NO, EditText_MobileNumber.getText().toString());
        outState.putString(Constants.EMAIL, EditText_Email.getText().toString());
        outState.putString(Constants.DOB, EditText_DOB.getText().toString());
    }

    public void nextClick(View view) {
        Validation validation = new Validation(this);
        if(validation.SignUpValidation(this) == true)
        {
            Intent i = null;
            i = new Intent(SignupActivity.this, SignupSecondActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
//            OTPDialog();
        }
    }

    public void signInClick(View view) {
        Intent i = null;
        i = new Intent(SignupActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
        CustomIntent.customType(SignupActivity.this, "left-to-right");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar userAge = new GregorianCalendar(year,month,dayOfMonth);
        Calendar minAdultAge = new GregorianCalendar();
        minAdultAge.add(Calendar.YEAR, -18);
        if (minAdultAge.before(userAge)) {
            isValid = false;
        }else{
            isValid = true;
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        EditText_DOB.setText(currentDateString);

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void DateSet(){
        /*Calendar c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH);
        int Day = c.get(Calendar.DAY_OF_MONTH);

        datePicker = new DatePickerDialog(this, R.style.MyDatePickerStyle, new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                EditText_DOB.setText(String.format("%d / %D / %d", i, i1 + 1, i2));
            }
        }, Year, Month, Day);*/


        DatePickerDialog mDate =  new DatePickerDialog(this, R.style.MyDatePickerStyle, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                Calendar_DOB = Calendar.getInstance();
                Calendar_DOB.set(Calendar.YEAR, year);
                Calendar_DOB.set(Calendar.MONTH, monthOfYear);
                Calendar_DOB.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //                minDate = Calendar_Pay.getTime().getTime();
                String myFormat = "dd-MM-YYYY"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String DOB_DATE               =   sdf.format(Calendar_DOB.getTime());
                String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(Calendar_DOB.getTime());
                EditText_DOB.setText(DOB_DATE);
            }
        }, Calendar_DOB.get(Calendar.YEAR), Calendar_DOB.get(Calendar.MONTH), Calendar_DOB.get(Calendar.DAY_OF_MONTH));
        mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDate.show();

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

        Intent i = null;
        i = new Intent(SignupActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
        CustomIntent.customType(SignupActivity.this, "right-to-left");
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}