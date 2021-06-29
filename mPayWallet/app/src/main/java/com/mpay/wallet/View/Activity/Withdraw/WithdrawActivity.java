package com.mpay.wallet.View.Activity.Withdraw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.CashIn.Adapter.AddBankAdapter;
import com.mpay.wallet.View.Activity.CashIn.InterFace.BankInterFace;
import com.mpay.wallet.View.Activity.CashIn.Model.BankItemList;
import com.mpay.wallet.View.Activity.CashIn.View.CashInActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class WithdrawActivity extends AppCompatActivity implements BankInterFace {
    NumberFormat formatter;
    JSONArray jsonArray_AddBank;
    TextView Tv_Withdraw_Tot_Amt, Tv_Withdraw_Contiue;
    TextInputLayout MTIL_Withdraw_Amount, MTIL_Withdraw_Disbursement;
    TextInputEditText EditText_Withdraw_Amount, EditText_Withdraw_Disbursement;
    RecyclerView RecyclerView_WithdrawBank;
    LinearLayout Layout_Add_New_Bank;
    ArrayList<String> arrayList_DisbursementList = null;

    TextInputLayout MTIL_CashIn_Btm_Add_BankName, MTIL_CashIn_Btm_Add_UserIDBank, MTIL_CashIn_Btm_Add_PassPinBank;
    AutoCompleteTextView AutoComplete_CashIn_Btm_Add_BankName;
    TextInputEditText EditText_CashIn_Btm_Add_BankName, EditText_CashIn_Btm_Add_UserIDBank, EditText_CashIn_Btm_Add_PassPinBank;
    TextView Tv_BTN_CashIn_AddBank;
    ArrayList<String> arrayList_BankList = null;
    ArrayAdapter<String> arrayAdapter_BankList;
    private List<BankItemList> mList_Bank = new ArrayList<>();
    private AddBankAdapter mAdapter_Bank;
    boolean resultBank = false;
    String BANK_NAME = null, BANK_NO = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        Initilization();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void Initilization() {
        formatter = new DecimalFormat("#0.00");
//        Json Array
        jsonArray_AddBank = new JSONArray();
//        TextView
        Tv_Withdraw_Tot_Amt = findViewById(R.id.Tv_Withdraw_Tot_Amt);
        Tv_Withdraw_Contiue = findViewById(R.id.Tv_Withdraw_Contiue);

        MTIL_Withdraw_Amount = findViewById(R.id.MTIL_Withdraw_Amount);
        MTIL_Withdraw_Disbursement = findViewById(R.id.MTIL_Withdraw_Disbursement);

        EditText_Withdraw_Amount = findViewById(R.id.EditText_Withdraw_Amount);
        EditText_Withdraw_Disbursement = findViewById(R.id.EditText_Withdraw_Disbursement);

        RecyclerView_WithdrawBank = findViewById(R.id.RecyclerView_WithdrawBank);

        Layout_Add_New_Bank = findViewById(R.id.Layout_Add_New_Bank);
        double TotAmt = Double.parseDouble(SharedPreferenceAmount.getInstance(WithdrawActivity.this).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
        Tv_Withdraw_Tot_Amt.setText("€"+formatter.format(TotAmt)+"");


        arrayList_DisbursementList = new ArrayList<>();
        arrayList_DisbursementList.add("Account");
        arrayList_DisbursementList.add("Cash");

        arrayList_BankList = new ArrayList<>();
        arrayList_BankList.add("HSBC");
        arrayList_BankList.add("BNP Paribas");
        arrayList_BankList.add("Crédit Agricole");
        arrayList_BankList.add("Deutsche Bank");
        arrayList_BankList.add("Banco Santander");
        arrayList_BankList.add("Barclays");
        arrayList_BankList.add("Société Générale");
        arrayList_BankList.add("Groupe BPCE");
        arrayList_BankList.add("Lloyds Banking Group");
        arrayList_BankList.add("ING Groep");
        arrayList_BankList.add("UniCredit");
        arrayList_BankList.add("Royal Bank of Scotland");
        arrayList_BankList.add("Intesa Sanpaolo");
        arrayList_BankList.add("Crédit Mutuel Group");
        arrayList_BankList.add("UBS Group");
        arrayList_BankList.add("Credit Suisse");
        arrayList_BankList.add("Banco Bilbao");
        arrayList_BankList.add("Rabobank");
        arrayList_BankList.add("Nordea Bank");
        arrayList_BankList.add("Standard Chartered");
        arrayList_BankList.add("DZ Bank");
        arrayList_BankList.add("Danske Bank");
        arrayList_BankList.add("Commerzbank AG");

        MTIL_Withdraw_Disbursement.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDisbursementList(view, false, R.style.MyPopupOtherStyle);
            }
        });


    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void popUpDisbursementList(View anchor, boolean isWithIcons, int style){
        try {
            Context wrapper = new ContextThemeWrapper(this, style);

            //init the popup
            PopupMenu popupMenu = new PopupMenu(wrapper, anchor);
    //            PopupMenu popupMenu = new PopupMenu(CashInActivity.this, v);

            for (int i = 0; arrayList_DisbursementList.size() > i; i++) {
                popupMenu.getMenu().add(1, i, i, arrayList_DisbursementList.get(i));
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
    //                    Toast.makeText(CashInActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                    if(item.getTitle().toString().equals("Account"))
                    {
                        RecyclerView_WithdrawBank.setVisibility(View.VISIBLE);
                        Layout_Add_New_Bank.setVisibility(View.VISIBLE);
                    }
                    else{
                        RecyclerView_WithdrawBank.setVisibility(View.GONE);
                        Layout_Add_New_Bank.setVisibility(View.GONE);
                    }
                    EditText_Withdraw_Disbursement.setText(item.getTitle());
                    return false;
                }
            });
            popupMenu.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     *  Bottom Sheet Dialog for Add Bank
     */
    private void showBottomSheetDialog_ToAddBank() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_add_bank);

        MTIL_CashIn_Btm_Add_BankName            =   bottomSheetDialog.findViewById(R.id.MTIL_CashIn_Btm_Add_BankName);
        MTIL_CashIn_Btm_Add_UserIDBank          =   bottomSheetDialog.findViewById(R.id.MTIL_CashIn_Btm_Add_UserIDBank);
        MTIL_CashIn_Btm_Add_PassPinBank         =   bottomSheetDialog.findViewById(R.id.MTIL_CashIn_Btm_Add_PassPinBank);


        EditText_CashIn_Btm_Add_BankName      =   bottomSheetDialog.findViewById(R.id.EditText_CashIn_Btm_Add_BankName);
        EditText_CashIn_Btm_Add_UserIDBank      =   bottomSheetDialog.findViewById(R.id.EditText_CashIn_Btm_Add_UserIDBank);
        EditText_CashIn_Btm_Add_PassPinBank     =   bottomSheetDialog.findViewById(R.id.EditText_CashIn_Btm_Add_PassPinBank);

        AutoComplete_CashIn_Btm_Add_BankName    =   bottomSheetDialog.findViewById(R.id.AutoComplete_CashIn_Btm_Add_BankName);

        Tv_BTN_CashIn_AddBank                   =   bottomSheetDialog.findViewById(R.id.Tv_BTN_CashIn_AddBank);

        try {
            arrayAdapter_BankList = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_bank, arrayList_BankList);
            AutoComplete_CashIn_Btm_Add_BankName.setAdapter(arrayAdapter_BankList);
            AutoComplete_CashIn_Btm_Add_BankName.setThreshold(1);
        }catch ( Exception e){
            e.printStackTrace();
        }
        bottomSheetDialog.show();
        MTIL_CashIn_Btm_Add_BankName.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpBankList(view, false, R.style.MyPopupOtherStyle);
            }
        });
        Tv_BTN_CashIn_AddBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBankFun();
                if(resultBank == true) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void popUpBankList(View anchor, boolean isWithIcons, int style){
        try {
            Context wrapper = new ContextThemeWrapper(this, style);

            //init the popup
            PopupMenu popupMenu = new PopupMenu(wrapper, anchor);
//            PopupMenu popupMenu = new PopupMenu(CashInActivity.this, v);

            for (int i = 0; arrayList_BankList.size() > i; i++) {
                popupMenu.getMenu().add(1, i, i, arrayList_BankList.get(i));
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
//                    Toast.makeText(CashInActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                    EditText_CashIn_Btm_Add_BankName.setText(item.getTitle());
                    return false;
                }
            });
            popupMenu.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public boolean addNewBankFun(){
        resultBank = false;
        if(EditText_CashIn_Btm_Add_BankName.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_BankName.setError("Select Bank");
            MTIL_CashIn_Btm_Add_BankName.setErrorEnabled(true);
            MTIL_CashIn_Btm_Add_BankName.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            resultBank = false;
        }
        if(!EditText_CashIn_Btm_Add_BankName.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_BankName.setErrorEnabled(false);
            resultBank = true;
        }

        if(EditText_CashIn_Btm_Add_UserIDBank.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_UserIDBank.setError("Enter User ID");
            MTIL_CashIn_Btm_Add_UserIDBank.setErrorEnabled(true);
            MTIL_CashIn_Btm_Add_UserIDBank.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            resultBank = false;
        }
        if(!EditText_CashIn_Btm_Add_UserIDBank.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_UserIDBank.setErrorEnabled(false);
            resultBank = true;
        }

        if(EditText_CashIn_Btm_Add_PassPinBank.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_PassPinBank.setError("Enter Password or PIN");
            MTIL_CashIn_Btm_Add_PassPinBank.setErrorEnabled(true);
            MTIL_CashIn_Btm_Add_PassPinBank.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            resultBank = false;
        }
        if(!EditText_CashIn_Btm_Add_PassPinBank.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_PassPinBank.setErrorEnabled(false);
            resultBank = true;
        }


        if(resultBank == true)
        {
            try {
                JSONObject jsonObject_AddBank = new JSONObject();
                jsonObject_AddBank.put("BANK_NAME", EditText_CashIn_Btm_Add_BankName.getText().toString());
                jsonObject_AddBank.put("AC_NO", "****1234");
                jsonObject_AddBank.put("USER_ID", EditText_CashIn_Btm_Add_UserIDBank.getText().toString());
                jsonObject_AddBank.put("PASSWORD", EditText_CashIn_Btm_Add_PassPinBank.getText().toString());

                jsonArray_AddBank.put(jsonObject_AddBank);
                addList_Bank();
                adapter_Bank();

            }catch (Exception e){
                e.printStackTrace();
            }
            resultBank = true;
        }
        return resultBank;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void addList_Bank(){
        try {
            mList_Bank.clear();
            for (int i = 0; i < jsonArray_AddBank.length(); i++) {
                BankItemList itemAdapter = new BankItemList();
                JSONObject object = jsonArray_AddBank.getJSONObject(i);

                itemAdapter.setProfilePic(R.drawable.man_2);
                itemAdapter.setBankname(object.getString("BANK_NAME"));
                itemAdapter.setBankno(object.getString("AC_NO"));
                mList_Bank.add(itemAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void adapter_Bank(){
        try {
            mAdapter_Bank = new AddBankAdapter(mList_Bank, getApplicationContext(), this);
            RecyclerView_WithdrawBank.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
            RecyclerView_WithdrawBank.setAdapter(mAdapter_Bank);
            mAdapter_Bank.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void getBanName(String bankName, String bankNumber) {
        BANK_NAME = bankName;
        BANK_NO = bankNumber;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void continueAction(View view) {
        if(EditText_Withdraw_Amount.getText().toString().isEmpty()){
            MTIL_Withdraw_Amount.setErrorEnabled(true);
            MTIL_Withdraw_Amount.setError("Enter Amount");
            MTIL_Withdraw_Amount.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return;
        }
        if(!EditText_Withdraw_Amount.getText().toString().isEmpty()){
            MTIL_Withdraw_Amount.setErrorEnabled(false);
        }
        if(EditText_Withdraw_Disbursement.getText().toString().isEmpty())
        {
            MTIL_Withdraw_Disbursement.setErrorEnabled(true);
            MTIL_Withdraw_Disbursement.setError("select Disbursement");
            MTIL_Withdraw_Disbursement.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return;
        }
        if(!EditText_Withdraw_Disbursement.getText().toString().isEmpty())
        {
            MTIL_Withdraw_Disbursement.setErrorEnabled(false);
            String AMOUNT = EditText_Withdraw_Amount.getText().toString().replace("€", "");
            Intent in = new Intent(this, ConfirmWithdrawActivity.class);
            in.putExtra("BANK_NAME",BANK_NAME);
            in.putExtra("BANK_NO",BANK_NO);
            in.putExtra("AMOUNT",AMOUNT);
            startActivity(in);
            finish();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void addNewBank(View view) {
        showBottomSheetDialog_ToAddBank();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    protected void onResume() {
        super.onResume();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}