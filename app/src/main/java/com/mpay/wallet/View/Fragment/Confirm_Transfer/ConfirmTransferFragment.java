package com.mpay.wallet.View.Fragment.Confirm_Transfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.SuccessfulTransfer.SuccessfulTransferActivity;

import maes.tech.intentanim.CustomIntent;

public class ConfirmTransferFragment extends Fragment {
    private View view;
    public TextView Tv_Receipt_Head, Tv_Confirm_msg, Tv_Confirm_Amt, Tv_Confirm_Name, Button_ConfirmCancel, Button_ConfirmTransfer;
        public EditText EditTextConfrimFirst, EditTextConfrimSecond, EditTextConfrimThird, EditTextConfrimFourth;
        ImageView Iv_Confirm_Back;
        String TRANSFER_AMOUNT, MOBILE_NO, TRANSACTION_TYPE, NAME, EMAIL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            view = inflater.inflate(R.layout.fragment_confirm_transfer, container, false);
            Initilization();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
    private void Initilization() {
    Iv_Confirm_Back = view.findViewById(R.id.Iv_Confirm_Back);

    Tv_Receipt_Head = view.findViewById(R.id.Tv_Receipt_Head);
    Tv_Confirm_msg = view.findViewById(R.id.Tv_Confirm_msg);
    Tv_Confirm_Amt = view.findViewById(R.id.Tv_Confirm_Amt);
    Tv_Confirm_Name = view.findViewById(R.id.Tv_Confirm_Name);

    Button_ConfirmCancel = view.findViewById(R.id.Button_ConfirmCancel);
    Button_ConfirmTransfer = view.findViewById(R.id.Button_ConfirmTransfer);

    EditTextConfrimFirst = view.findViewById(R.id.EditTextConfrimFirst);
    EditTextConfrimSecond = view.findViewById(R.id.EditTextConfrimSecond);
    EditTextConfrimThird = view.findViewById(R.id.EditTextConfrimThird);
    EditTextConfrimFourth = view.findViewById(R.id.EditTextConfrimFourth);
    /*EditTextConfrimFirst.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    EditTextConfrimSecond.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    EditTextConfrimThird.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    EditTextConfrimFourth.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    EditTextConfrimFirst.setSelection(EditTextConfrimFirst.getText().length());
    EditTextConfrimSecond.setSelection(EditTextConfrimSecond.getText().length());
    EditTextConfrimThird.setSelection(EditTextConfrimThird.getText().length());
    EditTextConfrimFourth.setSelection(EditTextConfrimFourth.getText().length());*/

    TextChangeListener();

    MOBILE_NO = null;
    TRANSFER_AMOUNT = null;
    if (getArguments() != null) {
        MOBILE_NO    = getArguments().getString("MOBILE_NO");
        TRANSFER_AMOUNT      = getArguments().getString("TRANSFER_AMOUNT");
        TRANSACTION_TYPE      = getArguments().getString("TRANSACTION_TYPE");
        NAME      = getArguments().getString("NAME");
        EMAIL      = getArguments().getString("EMAIL");
        Tv_Confirm_Amt.setText("€"+TRANSFER_AMOUNT);
        Tv_Confirm_Name.setText(NAME);
    }
    Tv_Confirm_Amt.setText("€"+TRANSFER_AMOUNT);
    if(TRANSACTION_TYPE.equals("PAY"))
    {
        Tv_Receipt_Head.setText("Confirm");
        Tv_Confirm_msg.setText(R.string.you_are_about_to_pay);
    }
    else if(TRANSACTION_TYPE.equals("TRANSFER"))
    {
        Tv_Receipt_Head.setText("Confirm Transfer");
        Tv_Confirm_msg.setText(R.string.you_are_about_to_transfer);
    }

    Button_ConfirmCancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            getActivity().getSupportFragmentManager().popBackStack();
        }
    });

    Iv_Confirm_Back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            getActivity().getSupportFragmentManager().popBackStack();
        }
    });

    Button_ConfirmTransfer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (EditTextConfrimFirst.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                return;
            } else if (EditTextConfrimSecond.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                return;
            } else if (EditTextConfrimThird.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                return;
            } else if (EditTextConfrimFourth.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String firstBox = EditTextConfrimFirst.getText().toString().trim();
                String secBox = EditTextConfrimSecond.getText().toString().trim();
                String thirdBox = EditTextConfrimThird.getText().toString().trim();
                String fourthBox = EditTextConfrimFourth.getText().toString().trim();
                String fOtp = firstBox + secBox + thirdBox + fourthBox;
                if (fOtp.equals("1234")) {
                    Intent i = null;
                    i = new Intent(getActivity(), SuccessfulTransferActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("TRANSFER_AMOUNT", TRANSFER_AMOUNT);
                    i.putExtra("MOBILE_NO", MOBILE_NO);
                    i.putExtra("EMAIL", EMAIL);
                    i.putExtra("NAME", NAME);
                    i.putExtra("TRANSACTION_TYPE", TRANSACTION_TYPE);
                    startActivity(i);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Invalid OTP", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    });
}
    
    public void TextChangeListener()
    {
        EditTextConfrimFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (EditTextConfrimFirst.getText().toString().length() == 1) {
                    EditTextConfrimSecond.requestFocus();
                } else {
                    EditTextConfrimFirst.requestFocus();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (EditTextConfrimFirst.getText().toString().length() == 1) {
                    EditTextConfrimSecond.requestFocus();
                } else {
                    EditTextConfrimFirst.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        EditTextConfrimSecond.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (EditTextConfrimSecond.getText().toString().length() == 1) {
                    EditTextConfrimThird.requestFocus();
                } else {
                    EditTextConfrimFirst.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        EditTextConfrimThird.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (EditTextConfrimThird.getText().toString().length() == 1) {
                    EditTextConfrimFourth.requestFocus();
                } else {
                    EditTextConfrimSecond.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        EditTextConfrimFourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (EditTextConfrimFourth.getText().toString().length() == 1) {
                    //                    fou.continueTv.requestFocus();
                    try {
//                        hideSoftKeyboard(requireActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    EditTextConfrimThird.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View currentFocus = activity.getCurrentFocus();

        if (inputMethodManager != null) {
            IBinder windowToken = activity.getWindow().getDecorView().getRootView().getWindowToken();
            inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);

            if (currentFocus != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }

    }
}