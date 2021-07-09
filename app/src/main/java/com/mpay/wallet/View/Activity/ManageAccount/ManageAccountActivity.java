package com.mpay.wallet.View.Activity.ManageAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import android.widget.TextView;

import com.bumptech.glide.load.model.ModelLoaderRegistry;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Activity.ManageAccount.Adapter.ManageAccountAdapter;
import com.mpay.wallet.View.Activity.ManageAccount.InterFace.ManageAccountInterFace;
import com.mpay.wallet.View.Activity.ManageAccount.Model.ManageAccountItemList;
import com.mpay.wallet.View.Activity.More.MoreActivity;
import com.mpay.wallet.View.Fragment.Space.Adapter.SpaceAdapter;
import com.mpay.wallet.View.Fragment.Space.Model.SpaceItemAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ManageAccountActivity extends AppCompatActivity implements ManageAccountInterFace {
    RecyclerView RecylerView_ManageAcc;

    private List<ManageAccountItemList> mList_Bank = new ArrayList<>();
    private ManageAccountAdapter mAdapter_Bank;


    /**
     * ADD NEW BANK
     */
    TextInputLayout MTIL_CashIn_Btm_Add_BankName, MTIL_CashIn_Btm_Add_UserIDBank, MTIL_CashIn_Btm_Add_PassPinBank;
    AutoCompleteTextView AutoComplete_CashIn_Btm_Add_BankName;
    TextInputEditText EditText_CashIn_Btm_Add_BankName, EditText_CashIn_Btm_Add_UserIDBank, EditText_CashIn_Btm_Add_PassPinBank;
    TextView Tv_BTN_CashIn_AddBank;
    ArrayList<String> arrayList_BankList = null;
    ArrayAdapter<String> arrayAdapter_BankList;
    JSONArray jsonArray_AddBank;
    boolean resultBank = false;
    String NextActivity = null, BANK_NAME = null, BANK_NO = null;
    NumberFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        Initilization();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void Initilization() {
        formatter = new DecimalFormat("#0.00");

        jsonArray_AddBank = new JSONArray();


        RecylerView_ManageAcc = findViewById(R.id.RecylerView_ManageAcc);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));
        RecylerView_ManageAcc.addItemDecoration(itemDecorator);

        addList();
//        adapter();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void addList(){
        mList_Bank.clear();
        ManageAccountItemList itemAdapter = new ManageAccountItemList();
        itemAdapter.setBankname("HSBC");
        itemAdapter.setBankno("**** 1234");
        mList_Bank.add(itemAdapter);


        itemAdapter = new ManageAccountItemList();
        itemAdapter.setBankname("BNP Paribas");
        itemAdapter.setBankno("**** 5678");
        mList_Bank.add(itemAdapter);


        itemAdapter = new ManageAccountItemList();
        itemAdapter.setBankname("Crédit Agricole");
        itemAdapter.setBankno("**** 9632");
        mList_Bank.add(itemAdapter);


        itemAdapter = new ManageAccountItemList();
        itemAdapter.setBankname("Deutsche Bank");
        itemAdapter.setBankno("**** 5874");
        mList_Bank.add(itemAdapter);

        try {
            JSONObject jsonObject_AddBank = new JSONObject();
            jsonObject_AddBank.put("BANK_NAME", "HSBC");
            jsonObject_AddBank.put("AC_NO", "****1234");

            jsonArray_AddBank.put(jsonObject_AddBank);

            jsonObject_AddBank = new JSONObject();
            jsonObject_AddBank.put("BANK_NAME", "BNP Paribas");
            jsonObject_AddBank.put("AC_NO", "****5678");

            jsonArray_AddBank.put(jsonObject_AddBank);

            jsonObject_AddBank = new JSONObject();
            jsonObject_AddBank.put("BANK_NAME", "Crédit Agricole");
            jsonObject_AddBank.put("AC_NO", "****9632");

            jsonArray_AddBank.put(jsonObject_AddBank);

            jsonObject_AddBank = new JSONObject();
            jsonObject_AddBank.put("BANK_NAME", "Deutsche Bank");
            jsonObject_AddBank.put("AC_NO", "****5874");

            jsonArray_AddBank.put(jsonObject_AddBank);


            addList_Bank();
            adapter_Bank();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void adapter(){
        try {
            mAdapter_Bank = new ManageAccountAdapter(mList_Bank, getApplicationContext(), this);
            RecylerView_ManageAcc.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
            RecylerView_ManageAcc.setAdapter(mAdapter_Bank);
            mAdapter_Bank.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void getBankDetails(String bankName, String bankNumber) {

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void addNewAccount(View view) {
        showBottomSheetDialog_ToAddBank();
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
    public void backPressed(View view) {
        onBackPressed();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, MoreActivity.class);
        startActivity(in);
        finish();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//















//    ADD BANK
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
            arrayAdapter_BankList = new ArrayAdapter<>(getApplicationContext(), R.layout.textview_singlechoice, arrayList_BankList);
            AutoComplete_CashIn_Btm_Add_BankName.setAdapter(arrayAdapter_BankList);
            AutoComplete_CashIn_Btm_Add_BankName.setThreshold(1);
        }catch ( Exception e){
            e.printStackTrace();
        }
        bottomSheetDialog.show();
        MTIL_CashIn_Btm_Add_BankName.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                ManageAccountItemList itemAdapter = new ManageAccountItemList();
                JSONObject object = jsonArray_AddBank.getJSONObject(i);

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
            mAdapter_Bank = new ManageAccountAdapter(mList_Bank, getApplicationContext(), this);
            RecylerView_ManageAcc.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
            RecylerView_ManageAcc.setAdapter(mAdapter_Bank);
            mAdapter_Bank.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}