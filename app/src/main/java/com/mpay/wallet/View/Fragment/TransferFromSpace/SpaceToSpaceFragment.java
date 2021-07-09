package com.mpay.wallet.View.Fragment.TransferFromSpace;

import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DBHelper;
import com.mpay.wallet.Utils.SharedPreferenceAmount;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SpaceToSpaceFragment extends Fragment {
    TextInputLayout MTIL_SpaceToSpaceAmount;
    TextInputEditText EditText_SpaceToSpaceAmount;
    Spinner Spinner_SpaceToSpaceName;
    TextView Tv_SpaceToSpace_Tot_Amt, Tv_CashInRefillDay, Tv_SpaceToSpaceRefillAmt, Tv_SpaceToSpaceTransfer;
    ImageView Iv_SpaceToSpace_Back, Iv_CashInRefillDay,Iv_CashInRefillAmt;
    Switch SwitchBtn_SpaceToSpaceRefill;
    LinearLayout LL_SpaceToSpaceRefillDay, LL_CashInRefillAmt;
    NumberFormat formatter;

    DBHelper mHelper;
    SQLiteDatabase mDb;
    boolean SaveResult = false;

    private View view;
    public SpaceToSpaceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_space_to_space, container, false);
        Initilization();
        return view;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void Initilization() {
        formatter = new DecimalFormat("#0.00");
        mHelper = new DBHelper(getActivity());

        Iv_SpaceToSpace_Back            = view.findViewById(R.id.Iv_SpaceToSpace_Back);
        Iv_CashInRefillDay              = view.findViewById(R.id.Iv_CashInRefillDay);
        Iv_CashInRefillAmt              = view.findViewById(R.id.Iv_CashInRefillAmt);

        Tv_SpaceToSpace_Tot_Amt         = view.findViewById(R.id.Tv_SpaceToSpace_Tot_Amt);
        Tv_SpaceToSpaceTransfer         = view.findViewById(R.id.Tv_SpaceToSpaceTransfer);
        Tv_CashInRefillDay              = view.findViewById(R.id.Tv_CashInRefillDay);
        Tv_SpaceToSpaceRefillAmt        = view.findViewById(R.id.Tv_SpaceToSpaceRefillAmt);

        EditText_SpaceToSpaceAmount     = view.findViewById(R.id.EditText_SpaceToSpaceAmount);

        MTIL_SpaceToSpaceAmount         = view.findViewById(R.id.MTIL_SpaceToSpaceAmount);

        Spinner_SpaceToSpaceName        = view.findViewById(R.id.Spinner_SpaceToSpaceName);

        SwitchBtn_SpaceToSpaceRefill    = view.findViewById(R.id.SwitchBtn_SpaceToSpaceRefill);

        LL_SpaceToSpaceRefillDay        = view.findViewById(R.id.LL_SpaceToSpaceRefillDay);
        LL_CashInRefillAmt              = view.findViewById(R.id.LL_CashInRefillAmt);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),R.layout.textview2,getResources().getStringArray(R.array.space_name));
        adapter1.setDropDownViewResource(R.layout.id_type_textview);
        Spinner_SpaceToSpaceName.setAdapter(adapter1);
        Spinner_SpaceToSpaceName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        double TotAmt = Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
        Tv_SpaceToSpace_Tot_Amt.setText("€"+formatter.format(TotAmt));

        Iv_SpaceToSpace_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Tv_SpaceToSpaceTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EditText_SpaceToSpaceAmount.getText().toString().isEmpty() == true) {
                    MTIL_SpaceToSpaceAmount.setErrorEnabled(true);
                    MTIL_SpaceToSpaceAmount.setError("Enter amount");
                    MTIL_SpaceToSpaceAmount.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                }
                else if (EditText_SpaceToSpaceAmount.getText().toString().isEmpty() == false) {
                    MTIL_SpaceToSpaceAmount.setErrorEnabled(false);
                    Toast.makeText(getActivity(), "Amount Transfer to Space Successfully", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}