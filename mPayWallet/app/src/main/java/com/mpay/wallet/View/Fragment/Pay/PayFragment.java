package com.mpay.wallet.View.Fragment.Pay;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.AlertPopup;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DBHelper;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.Home.HomeActivity;
import com.mpay.wallet.View.Activity.Pay.PayActivity;
import com.mpay.wallet.View.Fragment.Confirm_Transfer.ConfirmTransferFragment;


public class PayFragment extends Fragment {
    private View view;
    TextView Tv_Pay_Tot_Amt, Tv_Pay_Name, Tv_Pay_Addredd, Tv_Pay_100, Tv_Pay_200,
            Tv_Pay_300, Tv_Pay_400, Tv_Pay_Fee, Tv_Pay_Mobile, Tv_Pay_Website;
    LinearLayout Layout_Pay_Topup;
    ImageView Iv_Pay_UserPic;
    TextInputLayout MTIL_Pay_SendAmount, MTIL_Pay_DescrpLayout;
    TextInputEditText EditText_Pay_SendAmount, EditText_Pay_Description;
    TextView Button_Pay_Payment;
    String TRANSACTION_TYPE = null, CLASS = null, MOBILE_NO = null, NAME = null, EMAIL = null, ADDRESS = null, WEBSITE = null;
    SQLiteDatabase mDb;
    DBHelper mHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_pay, container, false);
        Initilization();
        return view;
    }
    private void Initilization() {
        mHelper                     = new DBHelper(getActivity());
        Tv_Pay_Tot_Amt              = view.findViewById(R.id.Tv_Pay_Tot_Amt);
        Tv_Pay_Mobile              = view.findViewById(R.id.Tv_Pay_Mobile);
        Tv_Pay_Website              = view.findViewById(R.id.Tv_Pay_Website);
        Tv_Pay_Name                 = view.findViewById(R.id.Tv_Pay_Name);
        Tv_Pay_Addredd              = view.findViewById(R.id.Tv_Pay_Addredd);
        Tv_Pay_100                  = view.findViewById(R.id.Tv_Pay_100);
        Tv_Pay_200                  = view.findViewById(R.id.Tv_Pay_200);
        Tv_Pay_300                  = view.findViewById(R.id.Tv_Pay_300);
        Tv_Pay_400                  = view.findViewById(R.id.Tv_Pay_400);
        Tv_Pay_Fee                  = view.findViewById(R.id.Tv_Pay_Fee);
        Button_Pay_Payment          = view.findViewById(R.id.Button_Pay_Payment);


        Layout_Pay_Topup            = view.findViewById(R.id.Layout_Pay_Topup);

        Iv_Pay_UserPic              = view.findViewById(R.id.Iv_Pay_UserPic);

        MTIL_Pay_SendAmount         = view.findViewById(R.id.MTIL_Pay_SendAmount);
        EditText_Pay_SendAmount     = view.findViewById(R.id.EditText_Pay_SendAmount);
        MTIL_Pay_DescrpLayout       = view.findViewById(R.id.MTIL_Pay_DescrpLayout);
        EditText_Pay_Description    = view.findViewById(R.id.EditText_Pay_Description);
        Tv_Pay_Tot_Amt.setText("€"+Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.TOTAL_AMOUNT))+"");
        onClickEvent();
        GetArgument();
    }
    public void GetArgument(){
        if (getArguments() != null) {
            try {
                TRANSACTION_TYPE    = getArguments().getString("TRANSACTION_TYPE");
                CLASS               = getArguments().getString("CLASS");
                MOBILE_NO           = getArguments().getString("MOBILE_NO");
                NAME           = getArguments().getString("NAME");
                EMAIL           = getArguments().getString("EMAIL");
                ADDRESS           = getArguments().getString("ADDRESS");
                WEBSITE           = getArguments().getString("WEBSITE");
                Tv_Pay_Name.setText(NAME);
                Tv_Pay_Addredd.setText(ADDRESS);
                Tv_Pay_Mobile.setText(MOBILE_NO);
                Tv_Pay_Website.setText(WEBSITE);
                Iv_Pay_UserPic.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pizza_logo1));
                EditText_Pay_SendAmount.setText("75.00");
                EditText_Pay_SendAmount.setEnabled(false);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void backPressed(View view) {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void onClickEvent() {
        Tv_Pay_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Tv_Pay_100.setBackgroundResource(R.drawable.button_background);
                Tv_Pay_100.setTextColor(getResources().getColor(R.color.white));

                Tv_Pay_200.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_200.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                Tv_Pay_300.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_300.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                Tv_Pay_400.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_400.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                EditText_Pay_SendAmount.setText(Tv_Pay_100.getText().toString().replaceAll("€", ""));
            }
        });
        Tv_Pay_200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Tv_Pay_200.setBackgroundResource(R.drawable.button_background);
                Tv_Pay_200.setTextColor(getResources().getColor(R.color.white));

                Tv_Pay_100.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_100.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                Tv_Pay_300.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_300.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                Tv_Pay_400.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_400.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                EditText_Pay_SendAmount.setText(Tv_Pay_200.getText().toString().replaceAll("€", ""));
            }
        });
        Tv_Pay_300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Tv_Pay_300.setBackgroundResource(R.drawable.button_background);
                Tv_Pay_300.setTextColor(getResources().getColor(R.color.white));

                Tv_Pay_200.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_200.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                Tv_Pay_100.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_100.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                Tv_Pay_400.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_400.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                EditText_Pay_SendAmount.setText(Tv_Pay_300.getText().toString().replaceAll("€", ""));
            }
        });
        Tv_Pay_400.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Tv_Pay_400.setBackgroundResource(R.drawable.button_background);
                Tv_Pay_400.setTextColor(getResources().getColor(R.color.white));

                Tv_Pay_200.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_200.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                Tv_Pay_300.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_300.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                Tv_Pay_100.setBackgroundResource(R.drawable.background_light_boarder_dark_blue);
                Tv_Pay_100.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                EditText_Pay_SendAmount.setText(Tv_Pay_400.getText().toString().replaceAll("€", ""));
            }
        });
        /*Iv_Pay_UserPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });*/
        Button_Pay_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoPayment();
            }
        });
    }



    public void DoPayment() {
        double Tot_Amount = Double.parseDouble(Tv_Pay_Tot_Amt.getText().toString().replaceAll("€", ""));
        if (EditText_Pay_SendAmount.getText().toString().isEmpty()) {
            openDialog("Please enter Amount");
        } else if ( Double.parseDouble(EditText_Pay_SendAmount.getText().toString()) > Tot_Amount) {
            openDialog("Enter Amount should not greate then Total Wallet Amount");
        } else {
            Toast.makeText(getActivity(), "Money Transfered", Toast.LENGTH_SHORT).show();
            try {
                double Send_Amount = Double.parseDouble(EditText_Pay_SendAmount.getText().toString());
                ConfirmTransferFragment confirmTransferFragment = new ConfirmTransferFragment();
                Bundle bundle = new Bundle();
                bundle.putString("TRANSACTION_TYPE", "PAY");
                bundle.putString("MOBILE_NO", MOBILE_NO);
                bundle.putString("TRANSFER_AMOUNT", String.valueOf(Send_Amount));
                bundle.putString("NAME", NAME);
                bundle.putString("EMAIL", EMAIL);
                try {
                    DateFormat df = new SimpleDateFormat("dd MMM, yyyy, hh:mm a");
                    String date = df.format(Calendar.getInstance().getTime());
                    mDb = mHelper.getWritableDatabase();
                    String q = "INSERT INTO TRANSACTION_MST (PERSON, AMOUNT, INOUT, TRANSACTION_TYPE, TRANSACTION_STATUS, TRANSACTION_DATE) VALUES ('"+NAME.replaceAll("'", "")+"', '"+String.valueOf(Send_Amount)+"', 'OUT', 'Withdrawal', 'Confirmed', '" + date + "')";
                    mDb.execSQL(q);
                }catch (Exception e){
                    e.printStackTrace();
                }
                confirmTransferFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, confirmTransferFragment).addToBackStack("Transfer").commit();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // open dialog
    private void openDialog(String msg){

        AlertPopup mAlert = new AlertPopup(getActivity());
        mAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlert.setMessage(msg);
        mAlert.setOkButton(view -> {
            mAlert.dismiss();
            //Do want you want
        });
        mAlert.show();
    }
}