package com.mpay.wallet.View.Activity.Statement.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpay.wallet.R;
import com.mpay.wallet.Utils.AlertPopup;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DBHelper;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Fragment.Transaction_History.Adapter.TransactionHistoryAdapter;
import com.mpay.wallet.View.Fragment.Transaction_History.Model.HistoryItemAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class StatementActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Tv_Statement_Tot_Amt;
    LinearLayout Layout_Statement_CashIn;
    ImageView Iv_Statement_Back;
    RecyclerView Recycler_Statement_View;
    private List<HistoryItemAdapter> mList = new ArrayList<>();
    private TransactionHistoryAdapter mAdapter;
    String query = null;
    String Message = null;

    SQLiteDatabase mDb;
    DBHelper mHelper;

    NumberFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        Initilization();
    }

    private void Initilization() {
        formatter               = new DecimalFormat("#0.00");
        mHelper                 = new DBHelper(this);

        Tv_Statement_Tot_Amt    = findViewById(R.id.Tv_Statement_Tot_Amt);
        Iv_Statement_Back       = findViewById(R.id.Iv_Statement_Back);
        Recycler_Statement_View = findViewById(R.id.Recycler_Statement_View);

        double TotAmt           = Double.parseDouble(SharedPreferenceAmount.getInstance(this).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
        Tv_Statement_Tot_Amt.setText("€"+formatter.format(TotAmt)+"");

        query = "SELECT * FROM TRANSACTION_MST WHERE TRANSACTION_TYPE = 'Cash in'  ORDER BY _ID ASC";
        Message = "No history of Cash In transaction";
        recentTransactionList();

        Iv_Statement_Back.setOnClickListener(this);

        Layout_Statement_CashIn.setOnClickListener(this);
    }
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
    public void backPressed(View view) {
    }

    @Override
    public void onClick(View v) {
        
    }
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
}