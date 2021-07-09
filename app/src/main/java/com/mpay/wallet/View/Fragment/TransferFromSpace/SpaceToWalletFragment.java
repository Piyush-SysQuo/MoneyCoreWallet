package com.mpay.wallet.View.Fragment.TransferFromSpace;

import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DBHelper;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Fragment.Space.View.SpaceCreateFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SpaceToWalletFragment extends Fragment {
    private View view;
    TextInputLayout MTIL_SpaceToWalletAmount;
    TextInputEditText EditText_SpaceToWalletAmount;
    CheckBox Check_CreateSpaceCondition;
    TextView Tv_SpaceToWallet_Tot_Amt, Tv_BtnSpaceToWallet;
    ImageView Iv_SpaceToWallet_Back;
    NumberFormat formatter;

    DBHelper mHelper;
    SQLiteDatabase mDb;
    boolean SaveResult = false;
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public SpaceToWalletFragment() {
        // Required empty public constructor
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_space_to_wallet, container, false);
        Initilization();
        return view;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void Initilization() {
        formatter = new DecimalFormat("#0.00");
        mHelper = new DBHelper(getActivity());

        Iv_SpaceToWallet_Back = view.findViewById(R.id.Iv_SpaceToWallet_Back);

        Tv_SpaceToWallet_Tot_Amt = view.findViewById(R.id.Tv_SpaceToWallet_Tot_Amt);
        Tv_BtnSpaceToWallet = view.findViewById(R.id.Tv_BtnSpaceToWallet);

        EditText_SpaceToWalletAmount = view.findViewById(R.id.EditText_SpaceToWalletAmount);

        MTIL_SpaceToWalletAmount = view.findViewById(R.id.MTIL_SpaceToWalletAmount);

        double TotAmt = Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
        Tv_SpaceToWallet_Tot_Amt.setText("€"+formatter.format(TotAmt));

        Iv_SpaceToWallet_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Tv_BtnSpaceToWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EditText_SpaceToWalletAmount.getText().toString().isEmpty() == true) {
                    MTIL_SpaceToWalletAmount.setErrorEnabled(true);
                    MTIL_SpaceToWalletAmount.setError("Enter amount");
                    MTIL_SpaceToWalletAmount.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                }
                else if (EditText_SpaceToWalletAmount.getText().toString().isEmpty() == false) {
                    MTIL_SpaceToWalletAmount.setErrorEnabled(false);
                    Toast.makeText(getActivity(), "Amount Transfer to Wallet Successfully", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}