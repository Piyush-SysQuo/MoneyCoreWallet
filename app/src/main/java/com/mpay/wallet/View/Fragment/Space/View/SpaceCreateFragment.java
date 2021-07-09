package com.mpay.wallet.View.Fragment.Space.View;

import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.CalendarViewUtil;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DBHelper;
import com.mpay.wallet.Utils.SharedPreferenceAmount;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class SpaceCreateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    TextInputLayout MTIL_CreateSpaceName, MTIL_CreateSpaceAmount, MTIL_CreateSpaceDate;
    TextInputEditText EditText_CreateSpaceName, EditText_CreateSpaceAmount, EditText_CreateSpaceDate;
    CheckBox Check_CreateSpaceCondition;
    TextView Tv_CreateSpace_Tot_Amt, Tv_BtnCreateSpace;
    ImageView Iv_CreateSpace_Back;
    NumberFormat formatter;

    DBHelper mHelper;
    SQLiteDatabase mDb;
    View view;
    boolean SaveResult = false;
    public SpaceCreateFragment() {
        // Required empty public constructor
    }

    public static SpaceCreateFragment newInstance(String param1, String param2) {
        SpaceCreateFragment fragment = new SpaceCreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_space_create, container, false);
        Initilization();
        return  view;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void Initilization() {
        formatter               = new DecimalFormat("#0.00");
        mHelper                 = new DBHelper(getActivity());

        Iv_CreateSpace_Back         = view.findViewById(R.id.Iv_CreateSpace_Back);

        MTIL_CreateSpaceName        = view.findViewById(R.id.MTIL_CreateSpaceName);
        MTIL_CreateSpaceAmount      = view.findViewById(R.id.MTIL_CreateSpaceAmount);
        MTIL_CreateSpaceDate        = view.findViewById(R.id.MTIL_CreateSpaceDate);

        EditText_CreateSpaceName    = view.findViewById(R.id.EditText_CreateSpaceName);
        EditText_CreateSpaceAmount  = view.findViewById(R.id.EditText_CreateSpaceAmount);
        EditText_CreateSpaceDate    = view.findViewById(R.id.EditText_CreateSpaceDate);

        Tv_CreateSpace_Tot_Amt      = view.findViewById(R.id.Tv_CreateSpace_Tot_Amt);
        Tv_BtnCreateSpace           = view.findViewById(R.id.Tv_BtnCreateSpace);

        Check_CreateSpaceCondition  = view.findViewById(R.id.Check_CreateSpaceCondition);

        double TotAmt           = Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
        Tv_CreateSpace_Tot_Amt.setText("€"+formatter.format(TotAmt)+"");

        MTIL_CreateSpaceDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CalendarViewUtil calendarViewUtil = new CalendarViewUtil();

                    calendarViewUtil.CalendarBottomDialog(getActivity(), EditText_CreateSpaceDate);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Iv_CreateSpace_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Tv_BtnCreateSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewSpace();
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public boolean createNewSpace()
    {
        SaveResult = true;
        if (EditText_CreateSpaceName.getText().toString().isEmpty() == true) {
            MTIL_CreateSpaceName.setErrorEnabled(true);
            MTIL_CreateSpaceName.setError("Enter space name");
            MTIL_CreateSpaceName.setErrorTextColor(ColorStateList.valueOf(Color.RED));

            SaveResult = false;
        }
        if (EditText_CreateSpaceName.getText().toString().isEmpty() == false) {
            MTIL_CreateSpaceName.setErrorEnabled(false);
            SaveResult = true;
        }

        if (EditText_CreateSpaceAmount.getText().toString().isEmpty() == true) {
            MTIL_CreateSpaceAmount.setErrorEnabled(true);
            MTIL_CreateSpaceAmount.setError("Enter traget amount");
            MTIL_CreateSpaceAmount.setErrorTextColor(ColorStateList.valueOf(Color.RED));

            SaveResult = false;
        }
        if (EditText_CreateSpaceAmount.getText().toString().isEmpty() == false) {
            MTIL_CreateSpaceAmount.setErrorEnabled(false);
            SaveResult = true;
        }

        if (EditText_CreateSpaceDate.getText().toString().isEmpty() == true) {
            MTIL_CreateSpaceDate.setErrorEnabled(true);
            MTIL_CreateSpaceDate.setError("select date");
            MTIL_CreateSpaceDate.setErrorTextColor(ColorStateList.valueOf(Color.RED));

            SaveResult = false;
        }
        if (EditText_CreateSpaceDate.getText().toString().isEmpty() == false) {
            MTIL_CreateSpaceDate.setErrorEnabled(false);
            SaveResult = true;
        }

        if(Check_CreateSpaceCondition.isChecked() == false)
        {
            Toast.makeText(getActivity(), "Please Check terms and condition", Toast.LENGTH_SHORT).show();
            SaveResult = false;
        }

        if(Check_CreateSpaceCondition.isChecked() == true)
        {
            SaveResult =  true;
        }


        if(SaveResult == true)
        {
            Toast.makeText(getActivity(), "Space created successfull", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().popBackStack();
        }
        return SaveResult;
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
}