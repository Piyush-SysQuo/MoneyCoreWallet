package com.mpay.wallet.View.Activity.Statement.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CalendarView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.AlertPopup;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DBHelper;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.Home.HomeActivity;
import com.mpay.wallet.View.Fragment.Transaction_History.Adapter.TransactionHistoryAdapter;
import com.mpay.wallet.View.Fragment.Transaction_History.Model.HistoryItemAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class StatementActivity extends AppCompatActivity {
    TextView Tv_Statement_Tot_Amt;
    ImageView Iv_Statement_Back;
    RecyclerView Recycler_Statement_View;
    private List<HistoryItemAdapter> mList = new ArrayList<>();
    private TransactionHistoryAdapter mAdapter;
    String query = null;
    String Message = null;

    SQLiteDatabase mDb;
    DBHelper mHelper;

    NumberFormat formatter;
    CalendarView CalendarViewDTSelectionStart, CalendarViewDTSelectionEnd;
    Calendar calendar;
    TextView TvStmtStartDT, TvStmtEndDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        Initilization();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void Initilization() {
        formatter               = new DecimalFormat("#0.00");
        mHelper                 = new DBHelper(this);

        Tv_Statement_Tot_Amt    = findViewById(R.id.Tv_Statement_Tot_Amt);
        Iv_Statement_Back       = findViewById(R.id.Iv_Statement_Back);
        Recycler_Statement_View = findViewById(R.id.Recycler_Statement_View);

        double TotAmt           = Double.parseDouble(SharedPreferenceAmount.getInstance(this).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
        Tv_Statement_Tot_Amt.setText("€"+formatter.format(TotAmt)+"");

        query = "SELECT * FROM TRANSACTION_MST  ORDER BY _ID ASC";
        Message = "No Statement";
        recentTransactionList();

        calendar = Calendar.getInstance();


        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2012);


        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void recentTransactionList(){
        mList.clear();
        mDb = mHelper.getReadableDatabase();
        Cursor c = mDb.rawQuery(query, null);
        if(c.getCount()>0){
            mList = new ArrayList<HistoryItemAdapter>();
            int i = 1;
            while ((c.moveToNext())){
                HistoryItemAdapter ListItems = new HistoryItemAdapter();
                String PERSON = c.getString(c.getColumnIndex("PERSON"));
                String Amount = c.getString(c.getColumnIndex("AMOUNT"));
                String InOut = c.getString(c.getColumnIndex("INOUT"));
                String TransactionType = c.getString(c.getColumnIndex("TRANSACTION_TYPE"));
                String TransactionStatus = c.getString(c.getColumnIndex("TRANSACTION_STATUS"));
                String TransactionDate = c.getString(c.getColumnIndex("TRANSACTION_DATE"));

                /*ListItems.setPayto(PERSON);
                ListItems.setAmount("€"+Double.parseDouble(Amount)+"");
//                ListItems.setDate("11 May  10:30 AM");
                ListItems.setDate(TransactionDate);
                ListItems.setTransaction_type(TransactionType);*/

                ListItems.setProfilePic(R.drawable.man_1);
                ListItems.setPayto(PERSON);
                ListItems.setAmount("€"+Double.parseDouble(Amount)+"");
                ListItems.setDate(TransactionDate);
                ListItems.setTransaction_status(TransactionStatus);
                ListItems.setTransaction_type(TransactionType);

                if(PERSON.equals("William Rae")) {
                    ListItems.setProfilePic(R.drawable.man_2);
                }
                else if(PERSON.equals("David Dorvik")) {
                    ListItems.setProfilePic(R.drawable.man_1);
                }
                else if(PERSON.equals("DOMINOS PIZZA PARIS 16 NORTH")) {
                    ListItems.setProfilePic(R.drawable.pizza_logo1);
                }
                else
                {
                    ListItems.setProfilePic(R.drawable.avatar);
                }

                mList.add(ListItems);
            }
            mAdapter = new TransactionHistoryAdapter(mList, this);
            Recycler_Statement_View.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
            Recycler_Statement_View.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            openDialog(Message);
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//    BOTTOM DIALOG EMAIL
    public void statementRequestBottomDialog(View v){

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_send_statement);

        TextInputLayout MTIL_StmtReqMail   = bottomSheetDialog.findViewById(R.id.MTIL_StmtReqMail);
        TextInputEditText EditText_StmtReqMail   = bottomSheetDialog.findViewById(R.id.EditText_StmtReqMail);
        TextView BTN_StmtReqMail   = bottomSheetDialog.findViewById(R.id.BTN_StmtReqMail);

        bottomSheetDialog.show();

        BTN_StmtReqMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditText_StmtReqMail.getText().toString().isEmpty())
                {
                    MTIL_StmtReqMail.setErrorEnabled(true);
                    MTIL_StmtReqMail.setError("Enter Mail");
                    MTIL_StmtReqMail.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    return;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(EditText_StmtReqMail.getText().toString()).matches())
                {
                    MTIL_StmtReqMail.setErrorEnabled(true);
                    MTIL_StmtReqMail.setError("Email Address not valid");
                    MTIL_StmtReqMail.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    return ;
                }
                else {
                    MTIL_StmtReqMail.setErrorEnabled(false);
                    bottomSheetDialog.dismiss();
                    Intent in = new Intent(StatementActivity.this, StatementSendViaMailActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void filterBottomDialog(View view) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_statement_filter);

        String[] names ;

        ListView ListViewFilterStatement   = bottomSheetDialog.findViewById(R.id.ListViewFilterStatement);
        TextView Tv_StatementFilterApply   = bottomSheetDialog.findViewById(R.id.Tv_StatementFilterApply);


        LinearLayout LayoutBtmStmtList      = bottomSheetDialog.findViewById(R.id.LayoutBtmStmtList);


        ListViewFilterStatement.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // create adapter using array from resources file
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.filters,
                R.layout.textview_singlechoice);
        ListViewFilterStatement.setAdapter(adapter);
        names = getResources().getStringArray(R.array.filters);
        bottomSheetDialog.show();

        Tv_StatementFilterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ClickedItem =  names[ListViewFilterStatement.getCheckedItemPosition()];
                if(ClickedItem.equals("Custom Date")) {
                    bottomSheetDialog.dismiss();
                    CalendarBottomDialog();
                }
                Toast.makeText(StatementActivity.this, ClickedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void CalendarBottomDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_statement_calendar);

        String[] names ;

    //        CALANDER
        LinearLayout LayoutBtmCalendarView  = bottomSheetDialog.findViewById(R.id.LayoutBtmCalendarView);
        TextView Tv_StatementFilterApplyDate  = bottomSheetDialog.findViewById(R.id.Tv_StatementFilterApplyDate);



        RelativeLayout RelativeStmtStartDT  = bottomSheetDialog.findViewById(R.id.RelativeStmtStartDT);
        RelativeLayout RelativeStmtEndDT  = bottomSheetDialog.findViewById(R.id.RelativeStmtEndDT);

        TvStmtStartDT  = bottomSheetDialog.findViewById(R.id.TvStmtStartDT);
        TvStmtEndDT  = bottomSheetDialog.findViewById(R.id.TvStmtEndDT);

        CalendarViewDTSelectionStart  = bottomSheetDialog.findViewById(R.id.CalendarViewDTSelectionStart);
        CalendarViewDTSelectionEnd  = bottomSheetDialog.findViewById(R.id.CalendarViewDTSelectionEnd);

        CalendarViewDTSelectionStart.setVisibility(View.GONE);
        CalendarViewDTSelectionEnd.setVisibility(View.GONE);

        bottomSheetDialog.show();

        Tv_StatementFilterApplyDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
                long endOfMonth = calendar.getTimeInMillis();
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                long startOfMonth = calendar.getTimeInMillis();
                CalendarViewDTSelection.setMaxDate(endOfMonth);
                CalendarViewDTSelection.setMinDate(startOfMonth);


                String minDateString = new SimpleDateFormat("dd-MM-yyyy").format(new Date(CalendarViewDTSelection.getMinDate()));
                String maxDateString = new SimpleDateFormat("dd-MM-yyyy").format(new Date(CalendarViewDTSelection.getMaxDate()));

                TvStmtStartDT.setText(minDateString);
                TvStmtEndDT .setText(maxDateString);

                Toast.makeText(getApplicationContext(), "MMDDYYYY Min date - " + minDateString + " Max Date is " + maxDateString, Toast.LENGTH_LONG).show();*/
            }
        });
        RelativeStmtStartDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeStmtStartDT.setBackground(getResources().getDrawable(R.drawable.background_square_blue_light));
                RelativeStmtEndDT.setBackground(getResources().getDrawable(R.drawable.background_square_blue_white));
                CalendarViewDTSelectionStart.setVisibility(View.VISIBLE);
                CalendarViewDTSelectionEnd.setVisibility(View.GONE);
            }
        });
        RelativeStmtEndDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeStmtEndDT.setBackground(getResources().getDrawable(R.drawable.background_square_blue_light));
                RelativeStmtStartDT.setBackground(getResources().getDrawable(R.drawable.background_square_blue_white));
                CalendarViewDTSelectionStart.setVisibility(View.GONE);
                CalendarViewDTSelectionEnd.setVisibility(View.VISIBLE);
            }
        });

        CalendarViewDTSelectionStart.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String msg = "Selected date Day: " + i2 + " Month : " + (i1 + 1) + " Year " + i;
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                String Day = null;
                String Month = null;
                String Year = null;
                if(i2 < 10)
                {
                    Day = "0"+String.valueOf(i2);
                }
                else
                {
                    Day = String.valueOf(i2);
                }
                if((i1+1) < 10)
                {
                    Month = "0"+String.valueOf(i1+1);
                }
                else
                {
                    Month = String.valueOf(i1+1);
                }
                Year = String.valueOf(i);
                TvStmtStartDT.setText(Day+"-"+Month+"-"+Year);
                RelativeStmtEndDT.setBackground(getResources().getDrawable(R.drawable.background_square_blue_light));
                RelativeStmtStartDT.setBackground(getResources().getDrawable(R.drawable.background_square_blue_white));
                CalendarViewDTSelectionStart.setVisibility(View.GONE);
                CalendarViewDTSelectionEnd.setVisibility(View.VISIBLE);
            }
        });

        CalendarViewDTSelectionEnd.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String msg = "Selected date Day: " + i2 + " Month : " + (i1 + 1) + " Year " + i;
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                String Day = null;
                String Month = null;
                String Year = null;
                if(i2 < 10)
                {
                    Day = "0"+String.valueOf(i2);
                }
                else
                {
                    Day = String.valueOf(i2);
                }
                if((i1+1) < 10)
                {
                    Month = "0"+String.valueOf(i1+1);
                }
                else
                {
                    Month = String.valueOf(i1+1);
                }
                Year = String.valueOf(i);
                TvStmtEndDT.setText(Day+"-"+Month+"-"+Year);
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void viewStatement(View view) {
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    // open dialog
    private void openDialog(String msg){

        AlertPopup mAlert = new AlertPopup(this);
        mAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlert.setMessage(msg);
        mAlert.setOkButton(view -> {
            mAlert.dismiss();
            //Do want you want
        });
        mAlert.show();
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
        Intent in = new Intent(this, HomeActivity.class);
        startActivity(in);
        finish();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}