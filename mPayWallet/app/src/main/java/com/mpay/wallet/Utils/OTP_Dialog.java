package com.mpay.wallet.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.Signup.SignupSecondActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class OTP_Dialog {

    EditText etfirst, etsecand, etthird, etfourth;

    public void showDialogOTP(Activity activity, String msg){
        final Dialog dialogOTP = new Dialog(activity);
        dialogOTP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOTP.setCancelable(false);
        dialogOTP.setContentView(R.layout.dialog_otp);
        dialogOTP.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        try{
            TextView TvOtpMobileNo = dialogOTP.findViewById(R.id.TvOtpMobileNo);
            TextView TvOtpChaneMobileNo = dialogOTP.findViewById(R.id.TvOtpChaneMobileNo);
            TextView txt_resend = dialogOTP.findViewById(R.id.txt_resend);
            TextView TvBtnVerify = dialogOTP.findViewById(R.id.TvBtnVerify);


            etfirst = dialogOTP.findViewById(R.id.etfirst);
            etsecand = dialogOTP.findViewById(R.id.etsecand);
            etthird = dialogOTP.findViewById(R.id.etthird);
            etfourth = dialogOTP.findViewById(R.id.etfourth);

            //            textWatcherForOtp();

            TvOtpChaneMobileNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Request to Change Mobile no for Verification", Toast.LENGTH_SHORT).show();
                }
            });

            txt_resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "OTP Send again", Toast.LENGTH_SHORT).show();
                }
            });

            TvBtnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etfirst.getText().toString().isEmpty()) {
                        Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (etsecand.getText().toString().isEmpty()) {
                        Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (etthird.getText().toString().isEmpty()) {
                        Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (etfourth.getText().toString().isEmpty()) {
                        Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String firstBox = etfirst.getText().toString().trim();
                        String secBox = etsecand.getText().toString().trim();
                        String thirdBox = etthird.getText().toString().trim();
                        String fourthBox = etthird.getText().toString().trim();
                        String fOtp = firstBox + secBox + thirdBox + fourthBox;
                        if (fOtp.equals("1234")) {
                            Toast.makeText(activity, "OTP Validate Successfull", Toast.LENGTH_SHORT).show();
                            dialogOTP.dismiss();
                        } else {
                            Toast.makeText(activity, "Invalid OTP", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            });

//            TEXT CHANGE START
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
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                        }
                    } else {
                        etthird.requestFocus();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
//            TEXT CHANGE END
        }catch (Exception e){
            e.printStackTrace();
        }

        dialogOTP.show();
    }

    //PopupWindow display method

    public void showPopupWindow(final View view) {


        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.dialog_otp, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setWindowLayoutMode(10, 30);

        //Initialize the elements of our window, install the handler

        try{
            TextView TvOtpMobileNo = popupView.findViewById(R.id.TvOtpMobileNo);
            TextView TvOtpChaneMobileNo = popupView.findViewById(R.id.TvOtpChaneMobileNo);
            TextView txt_resend = popupView.findViewById(R.id.txt_resend);
            TextView TvBtnVerify = popupView.findViewById(R.id.TvBtnVerify);


            etfirst = popupView.findViewById(R.id.etfirst);
            etsecand = popupView.findViewById(R.id.etsecand);
            etthird = popupView.findViewById(R.id.etthird);
            etfourth = popupView.findViewById(R.id.etfourth);

            //            textWatcherForOtp();

            TvOtpChaneMobileNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Request to Change Mobile no for Verification", Toast.LENGTH_SHORT).show();
                }
            });

            txt_resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "OTP Send again", Toast.LENGTH_SHORT).show();
                }
            });

            TvBtnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etfirst.getText().toString().isEmpty()) {
                        Toast.makeText(view.getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (etsecand.getText().toString().isEmpty()) {
                        Toast.makeText(view.getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (etthird.getText().toString().isEmpty()) {
                        Toast.makeText(view.getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (etfourth.getText().toString().isEmpty()) {
                        Toast.makeText(view.getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String firstBox = etfirst.getText().toString().trim();
                        String secBox = etsecand.getText().toString().trim();
                        String thirdBox = etthird.getText().toString().trim();
                        String fourthBox = etthird.getText().toString().trim();
                        String fOtp = firstBox + secBox + thirdBox + fourthBox;
                        if (fOtp.equals("1234")) {
                            Toast.makeText(view.getContext(), "OTP Validate Successfull", Toast.LENGTH_SHORT).show();
                            popupWindow.dismiss();
                        } else {
                            Toast.makeText(view.getContext(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            });

//            TEXT CHANGE START
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
                            /*InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getContext().getCurrentFocus().getWindowToken(), 0);*/
                        } catch (Exception e) {
                        }
                    } else {
                        etthird.requestFocus();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
//            TEXT CHANGE END
        }catch (Exception e){
            e.printStackTrace();
        }



        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }

}
