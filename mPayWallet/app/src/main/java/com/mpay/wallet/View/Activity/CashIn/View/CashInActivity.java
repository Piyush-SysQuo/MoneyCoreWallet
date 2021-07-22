package com.mpay.wallet.View.Activity.CashIn.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.MonthYear.MonthFormat;
import com.mpay.wallet.MonthYear.MonthYearPickerDialog;
import com.mpay.wallet.MonthYear.MonthYearPickerDialogFragment;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.FourDigitCardFormatWatcher;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.CashIn.Adapter.AddBankAdapter;
import com.mpay.wallet.View.Activity.CashIn.Adapter.AddCardAdapter;
import com.mpay.wallet.View.Activity.CashIn.InterFace.BankInterFace;
import com.mpay.wallet.View.Activity.CashIn.Model.BankItemList;
import com.mpay.wallet.View.Activity.CashIn.Model.CardItemList;
import com.mpay.wallet.View.Activity.Home.HomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CashInActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, BankInterFace {
    public TextView Tv_CashIn_Tot_Amt, Tv_CashInAdd, Tv_CashInRefillDay, Tv_CashInRefillAmt, Tv_CashInTransFee, 
            Tv_CashIn_Contiue,Tv_CashIn_Card, Tv_CashIn_Bank, EditText_CashIn_Btm_Add_NameOnCard;
    public TextInputEditText EditText_CashIn_Amount;
    public TextInputLayout MTIL_CashIn_Amount;
    public LinearLayout Layout_CashIn_Card, Layout_CashIn_Bank, Layout_Add_Bank_Crd, LL_CashInRefillDay, LL_CashInRefillAmt;
    public Switch SwitchBtn_CashInRefill;
    public ImageView Iv_CashIn_Back;


//    Add Card
    CheckBox CheckB_CashIn_SaveCardCVV;
    TextInputLayout MTIL_CashIn_Btm_Add_NameOnCard, MTIL_CashIn_Btm_Add_NumberOnCard, MTIL_CashIn_Btm_Add_CvvOnCard, MTIL_CashIn_Btm_Add_ExpDTOnCard;
    TextInputEditText EditText_CashIn_Btm_Add_ExpDTOnCard, EditText_CashIn_Btm_Add_NumberOnCard, EditText_CashIn_Btm_Add_CvvOnCard;
    TextView Tv_BTN_CashIn_AddCard;
    RecyclerView RecyclerView_CashInCard;
    String language = "en";
    boolean isValid = false;
    private int currentYear;
    private int yearSelected;
    private int monthSelected;
    JSONArray jsonArray_AddCard;
    private List<CardItemList> mList_Card = new ArrayList<>();
    private AddCardAdapter mAdapter_Card;


//    Add Bank
    RecyclerView RecyclerView_CashInBank;
    TextInputLayout MTIL_CashIn_Btm_Add_BankName, MTIL_CashIn_Btm_Add_UserIDBank, MTIL_CashIn_Btm_Add_PassPinBank;
    AutoCompleteTextView AutoComplete_CashIn_Btm_Add_BankName;
    TextInputEditText EditText_CashIn_Btm_Add_BankName, EditText_CashIn_Btm_Add_UserIDBank, EditText_CashIn_Btm_Add_PassPinBank;
    TextView Tv_BTN_CashIn_AddBank;
    ArrayList<String> arrayList_BankList = null;
    ArrayAdapter<String> arrayAdapter_BankList;
    JSONArray jsonArray_AddBank;
    private List<BankItemList> mList_Bank = new ArrayList<>();
    private AddBankAdapter mAdapter_Bank;
    boolean resultBank = false;
    String NextActivity = "CARD", BANK_NAME = null, BANK_NO = null;
    NumberFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_in);
        Initilization();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void Initilization() {
        formatter = new DecimalFormat("#0.00");
//        Json Array
        jsonArray_AddCard = new JSONArray();
        jsonArray_AddBank = new JSONArray();
//        TextView
        Tv_CashIn_Tot_Amt = findViewById(R.id.Tv_CashIn_Tot_Amt);
        Tv_CashIn_Card = findViewById(R.id.Tv_CashIn_Card);
        Tv_CashIn_Bank = findViewById(R.id.Tv_CashIn_Bank);
        Tv_CashInAdd = findViewById(R.id.Tv_CashInAdd);
        Tv_CashInRefillDay = findViewById(R.id.Tv_CashInRefillDay);
        Tv_CashInRefillAmt = findViewById(R.id.Tv_CashInRefillAmt);
        Tv_CashInTransFee = findViewById(R.id.Tv_CashInTransFee);
        Tv_CashIn_Contiue = findViewById(R.id.Tv_CashIn_Contiue);

//        EditText
        EditText_CashIn_Amount = findViewById(R.id.EditText_CashIn_Amount);
        
//        Layout
        MTIL_CashIn_Amount = findViewById(R.id.MTIL_CashIn_Amount);

        Layout_CashIn_Card = findViewById(R.id.Layout_CashIn_Card);
        Layout_CashIn_Bank = findViewById(R.id.Layout_CashIn_Bank);
        Layout_Add_Bank_Crd = findViewById(R.id.Layout_Add_Bank_Crd);
        LL_CashInRefillDay = findViewById(R.id.LL_CashInRefillDay);
        LL_CashInRefillAmt = findViewById(R.id.LL_CashInRefillAmt);

//        Switch
        SwitchBtn_CashInRefill = findViewById(R.id.SwitchBtn_CashInRefill);

//        ImageView
        Iv_CashIn_Back = findViewById(R.id.Iv_CashIn_Back);

//        RecycleView
        RecyclerView_CashInCard = findViewById(R.id.RecyclerView_CashInCard);
        RecyclerView_CashInCard.setVisibility(View.VISIBLE);
        RecyclerView_CashInBank = findViewById(R.id.RecyclerView_CashInBank);

        Layout_Add_Bank_Crd.setOnClickListener(this);
        double TotAmt = Double.parseDouble(SharedPreferenceAmount.getInstance(CashInActivity.this).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
        Tv_CashIn_Tot_Amt.setText("€"+formatter.format(TotAmt)+"");
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.Layout_Add_Bank_Crd){
            if(Tv_CashInAdd.getText().toString().equals(getString(R.string.add_new_card))){
                NextActivity = "CARD";
                showBottomSheetDialog_ToAddCard();
            }
            else if(Tv_CashInAdd.getText().toString().equals(getString(R.string.add_new_bank))){
                NextActivity = "BANK";
                showBottomSheetDialog_ToAddBank();
            }
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra(Constants.KEY_LANGUAGE,language);
        startActivity(intent);
        finish();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void continueAction(View view) {
        if(EditText_CashIn_Amount.getText().toString().isEmpty()){
            MTIL_CashIn_Amount.setErrorEnabled(true);
            MTIL_CashIn_Amount.setError("Please enter Amount");
            MTIL_CashIn_Amount.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return;
        }
        if(!EditText_CashIn_Amount.getText().toString().isEmpty()){
            MTIL_CashIn_Amount.setErrorEnabled(false);
            if(NextActivity.equals("BANK")){
                Intent in = new Intent(this, ConfirmCashinActivity.class);
                in.putExtra("BANK_NAME", BANK_NAME);
                in.putExtra("BANK_NO", BANK_NO);
                in.putExtra("AMOUNT", EditText_CashIn_Amount.getText().toString().replace("€", ""));
                startActivity(in);
                finish();
            }
            else
            {
                Toast.makeText(this, "For now Please select Bank", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void selectCard(View view) {
        RecyclerView_CashInCard.setVisibility(View.VISIBLE);
        RecyclerView_CashInBank.setVisibility(View.GONE);
        Layout_CashIn_Card.setBackgroundColor(0);
        Tv_CashIn_Card.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        Layout_CashIn_Bank.setBackgroundColor(getResources().getColor(R.color.white));
        Tv_CashIn_Bank.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.ensan_soft_gray));
        Tv_CashInAdd.setText(R.string.add_new_card);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void selecBank(View view) {
        RecyclerView_CashInCard.setVisibility(View.GONE);
        RecyclerView_CashInBank.setVisibility(View.VISIBLE);
        Layout_CashIn_Bank.setBackgroundColor(0);
        Tv_CashIn_Bank.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        Layout_CashIn_Card.setBackgroundColor(getResources().getColor(R.color.white));
        Tv_CashIn_Card.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.ensan_soft_gray));
        Tv_CashInAdd.setText(R.string.add_new_bank);

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
        /*arrayList_BankList.add("Cassa depositi e prestiti");
        arrayList_BankList.add("PAO Sberbank of Russia");
        arrayList_BankList.add("ABN AMRO");
        arrayList_BankList.add("CaixaBank");
        arrayList_BankList.add("KBC Group");
        arrayList_BankList.add("Svenska Handelsbanken");
        arrayList_BankList.add("DNA ASA");
        arrayList_BankList.add("Nationwide Building Society");
        arrayList_BankList.add("Skandinaviska Enskilda Banken");
        arrayList_BankList.add("Landesbank Baden-Württemberg");
        arrayList_BankList.add("La Banque Postale");
        arrayList_BankList.add("Swedbank");
        arrayList_BankList.add("Banco de Sabadell");
        arrayList_BankList.add("BFA Sociedad Tenedora de Acciones");
        arrayList_BankList.add("Erste Group Bank");
        arrayList_BankList.add("Bayerische Landesbank");
        arrayList_BankList.add("Raiffessen Gruppe Switzerland");
        arrayList_BankList.add("Nykredit");
        arrayList_BankList.add("JSCVTB Bank");
        arrayList_BankList.add("Dexia");
        arrayList_BankList.add("Belfius Banque");
        arrayList_BankList.add("Norddeutsche Landesbank Girozentrale");
        arrayList_BankList.add("Banco BPM");
        arrayList_BankList.add("Landesbank Hessen-Thüringen Girozentrale");
        arrayList_BankList.add("Zürcher Kantonalbank");
        arrayList_BankList.add("Banca Monte dei Paschi di Siena");
        arrayList_BankList.add("OP Financial Group");*/
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     *  Bottom Sheet Dialog for Add Card
     */
    private void showBottomSheetDialog_ToAddCard() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_add_cand);

        MTIL_CashIn_Btm_Add_NameOnCard      =   bottomSheetDialog.findViewById(R.id.MTIL_CashIn_Btm_Add_NameOnCard);
        MTIL_CashIn_Btm_Add_NumberOnCard    =   bottomSheetDialog.findViewById(R.id.MTIL_CashIn_Btm_Add_NumberOnCard);
        MTIL_CashIn_Btm_Add_CvvOnCard       =   bottomSheetDialog.findViewById(R.id.MTIL_CashIn_Btm_Add_CvvOnCard);
        MTIL_CashIn_Btm_Add_ExpDTOnCard     =   bottomSheetDialog.findViewById(R.id.MTIL_CashIn_Btm_Add_ExpDTOnCard);
        
        
        EditText_CashIn_Btm_Add_NameOnCard    =   bottomSheetDialog.findViewById(R.id.EditText_CashIn_Btm_Add_NameOnCard);
        EditText_CashIn_Btm_Add_NumberOnCard  =   bottomSheetDialog.findViewById(R.id.EditText_CashIn_Btm_Add_NumberOnCard);
        EditText_CashIn_Btm_Add_CvvOnCard     =   bottomSheetDialog.findViewById(R.id.EditText_CashIn_Btm_Add_CvvOnCard);
        EditText_CashIn_Btm_Add_ExpDTOnCard   =   bottomSheetDialog.findViewById(R.id.EditText_CashIn_Btm_Add_ExpDTOnCard);
        CheckB_CashIn_SaveCardCVV                      =   bottomSheetDialog.findViewById(R.id.CheckB_CashIn_SaveCardCVV);
        Tv_BTN_CashIn_AddCard                          =   bottomSheetDialog.findViewById(R.id.Tv_BTN_CashIn_AddCard);

        bottomSheetDialog.show();

        EditText_CashIn_Btm_Add_ExpDTOnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*DialogFragment datePicker = new DatePickerDob();
                datePicker.show(getSupportFragmentManager(), "date picker");*/

                displayMonthYearPickerDialogFragment( true,
                        true
                );
            }
        });

        Tv_BTN_CashIn_AddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        FourDigitCardFormatWatcher tv = new FourDigitCardFormatWatcher(EditText_CashIn_Btm_Add_NumberOnCard);
        EditText_CashIn_Btm_Add_NumberOnCard.addTextChangedListener(tv);

        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        yearSelected = currentYear;
        monthSelected = calendar.get(Calendar.MONTH);

        Tv_BTN_CashIn_AddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCardFun();
                bottomSheetDialog.dismiss();
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void addNewCardFun(){
        if(EditText_CashIn_Btm_Add_NameOnCard.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_NameOnCard.setError("Enter Name");
            MTIL_CashIn_Btm_Add_NameOnCard.setErrorEnabled(true);
            MTIL_CashIn_Btm_Add_NameOnCard.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return;
        }
        if(!EditText_CashIn_Btm_Add_NameOnCard.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_NameOnCard.setErrorEnabled(false);
        }

        if(EditText_CashIn_Btm_Add_NumberOnCard.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_NumberOnCard.setError("Enter Number");
            MTIL_CashIn_Btm_Add_NumberOnCard.setErrorEnabled(true);
            MTIL_CashIn_Btm_Add_NumberOnCard.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return;
        }
        if(!EditText_CashIn_Btm_Add_NumberOnCard.getText().toString().isEmpty() && EditText_CashIn_Btm_Add_NumberOnCard.getText().toString().length() < 16)
        {
            MTIL_CashIn_Btm_Add_NumberOnCard.setError("Enter Correct Number");
            MTIL_CashIn_Btm_Add_NumberOnCard.setErrorEnabled(true);
            MTIL_CashIn_Btm_Add_NumberOnCard.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return;
        }
        if(!EditText_CashIn_Btm_Add_NumberOnCard.getText().toString().isEmpty() && EditText_CashIn_Btm_Add_NumberOnCard.getText().toString().length() == 16)
        {
            MTIL_CashIn_Btm_Add_NumberOnCard.setErrorEnabled(false);
        }

        if(EditText_CashIn_Btm_Add_CvvOnCard.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_CvvOnCard.setError("Enter CVV Number");
            MTIL_CashIn_Btm_Add_CvvOnCard.setErrorEnabled(true);
            MTIL_CashIn_Btm_Add_CvvOnCard.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return;
        }
        if(!EditText_CashIn_Btm_Add_CvvOnCard.getText().toString().isEmpty() && EditText_CashIn_Btm_Add_CvvOnCard.getText().toString().length() < 3)
        {
            MTIL_CashIn_Btm_Add_CvvOnCard.setError("Enter Correct CVV Number");
            MTIL_CashIn_Btm_Add_CvvOnCard.setErrorEnabled(true);
            MTIL_CashIn_Btm_Add_CvvOnCard.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return;
        }
        if(!EditText_CashIn_Btm_Add_CvvOnCard.getText().toString().isEmpty() && EditText_CashIn_Btm_Add_CvvOnCard.getText().toString().length() == 3)
        {
            MTIL_CashIn_Btm_Add_CvvOnCard.setErrorEnabled(false);
        }
        if(EditText_CashIn_Btm_Add_ExpDTOnCard.getText().toString().isEmpty())
        {
            MTIL_CashIn_Btm_Add_ExpDTOnCard.setError("Expiry Date");
            MTIL_CashIn_Btm_Add_ExpDTOnCard.setErrorEnabled(true);
            MTIL_CashIn_Btm_Add_ExpDTOnCard.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return;
        }
        /*if(!EditText_CashIn_Btm_Add_ExpDTOnCard.getText().toString().isEmpty())
        {

            Calendar calendar = Calendar.getInstance();

            int crntYear = calendar.get(Calendar.YEAR);
            int cntMonth = calendar.get(Calendar.MONTH);

            MTIL_CashIn_Btm_Add_ExpDTOnCard.setError("Expiry Date");
            MTIL_CashIn_Btm_Add_ExpDTOnCard.setErrorEnabled(true);
            MTIL_CashIn_Btm_Add_ExpDTOnCard.setErrorTextColor(ColorStateList.valueOf(Color.RED));
            return;
        }*/
        else
        {
            try {
                JSONObject jsonObject_AddCard = new JSONObject();
                jsonObject_AddCard.put("CARD_NAME", EditText_CashIn_Btm_Add_NameOnCard.getText().toString());
                jsonObject_AddCard.put("CARD_NO", EditText_CashIn_Btm_Add_NumberOnCard.getText().toString());
                jsonObject_AddCard.put("CVV_NO", EditText_CashIn_Btm_Add_CvvOnCard.getText().toString());
                jsonObject_AddCard.put("EXP_DT", EditText_CashIn_Btm_Add_ExpDTOnCard.getText().toString());

                jsonArray_AddCard.put(jsonObject_AddCard);
                addList_Card();
                adapter_Card();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void addList_Card(){
        try {
            mList_Card.clear();
            for (int i = 0; i < jsonArray_AddCard.length(); i++) {
                CardItemList itemAdapter = new CardItemList();
                JSONObject object = jsonArray_AddCard.getJSONObject(i);

                itemAdapter.setProfilePic(R.drawable.man_2);
                itemAdapter.setCardno(object.getString("CARD_NO"));
                mList_Card.add(itemAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void adapter_Card(){
        try {
            mAdapter_Card = new AddCardAdapter(mList_Card, this);
            RecyclerView_CashInCard.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
            RecyclerView_CashInCard.setAdapter(mAdapter_Card);
            mAdapter_Card.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        try {
            Calendar userAge = new GregorianCalendar(year, month, dayOfMonth);
            Calendar minAdultAge = new GregorianCalendar();
            minAdultAge.add(Calendar.YEAR, 0);
            if (minAdultAge.before(userAge)) {
                isValid = false;
            } else {
                isValid = true;
            }
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);

            String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
            EditText_CashIn_Btm_Add_ExpDTOnCard.setText(currentDateString);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private MonthYearPickerDialogFragment createDialog(boolean customTitle) {
        return MonthYearPickerDialogFragment
                .getInstance(monthSelected,
                        yearSelected,
                        customTitle ? getString(R.string.Month_Year) : null,
                        MonthFormat.SHORT);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private MonthYearPickerDialogFragment createDialogWithRanges(boolean customTitle) {
        final int minYear = currentYear;
        final int maxYear = currentYear+20;
        final int maxMoth = 11;
        final int minMoth = monthSelected;
        final int minDay = 1;
        final int maxDay = 31;
        long minDate;
        long maxDate;

        Calendar calendar = Calendar.getInstance();

        calendar.clear();
        calendar.set(minYear, minMoth, minDay);
        minDate = calendar.getTimeInMillis();

        calendar.clear();
        calendar.set(maxYear, maxMoth, maxDay);
        maxDate = calendar.getTimeInMillis();

        return MonthYearPickerDialogFragment
                .getInstance(monthSelected,
                        yearSelected,
                        minDate,
                        maxDate,
                        customTitle ? getString(R.string.Month_Year) : null,
                        MonthFormat.SHORT);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void displayMonthYearPickerDialogFragment(boolean withRanges,
                                                      boolean customTitle) {
        MonthYearPickerDialogFragment dialogFragment = withRanges ?
                createDialogWithRanges(customTitle) :
                createDialog(customTitle);

        dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int year, int monthOfYear) {
                monthSelected = monthOfYear;
                yearSelected = year;
                updateViews();
            }
        });

        dialogFragment.show(getSupportFragmentManager(), null);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void updateViews() {
        String month = new DateFormatSymbols().getMonths()[monthSelected];
//        if(monthSelected.)
        EditText_CashIn_Btm_Add_ExpDTOnCard.setText(String.format("%s / %s", monthSelected, yearSelected));
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//

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
            RecyclerView_CashInBank.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
            RecyclerView_CashInBank.setAdapter(mAdapter_Bank);
            mAdapter_Bank.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getBanName(String bankName, String bankNumber) {
        BANK_NAME = bankName;
        BANK_NO = bankNumber;
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
}