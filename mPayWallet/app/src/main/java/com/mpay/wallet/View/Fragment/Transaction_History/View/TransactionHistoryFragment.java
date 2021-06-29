package com.mpay.wallet.View.Fragment.Transaction_History.View;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpay.wallet.R;
import com.mpay.wallet.Utils.AlertPopup;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DBHelper;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Fragment.Notification.adapter.NotificationTransactionAdapter;
import com.mpay.wallet.View.Fragment.Notification.model.NotificationItemAdapter;
import com.mpay.wallet.View.Fragment.Transaction_History.Adapter.TransactionHistoryAdapter;
import com.mpay.wallet.View.Fragment.Transaction_History.Model.HistoryItemAdapter;
import com.mpay.wallet.View.Fragment.home.adapter.RecentTransactionAdapter;
import com.mpay.wallet.View.Fragment.home.model.ItemAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionHistoryFragment() {
        // Required empty public constructor
    }

    private View view;
    TextView Tv_TransHstry_Tot_Amt, Tv_TransHstry_CashIn;
    LinearLayout Layout_TransHstry_CashIn;
    ImageView Iv_TransHstry_CashIn, Iv_TransHstry_Back;
    RecyclerView Recycler_TransHstry_View;
    private List<HistoryItemAdapter> mList = new ArrayList<>();
    private TransactionHistoryAdapter mAdapter;
    String query = null;
    String Message = null;

    SQLiteDatabase mDb;
    DBHelper mHelper;

    public static TransactionHistoryFragment newInstance(String param1, String param2) {
        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_transaction_history, container, false);
        Initilization();
        return view;
    }
    private void Initilization() {
        mHelper                     = new DBHelper(getActivity());
        Tv_TransHstry_Tot_Amt       = view.findViewById(R.id.Tv_TransHstry_Tot_Amt);
        Layout_TransHstry_CashIn    = view.findViewById(R.id.Layout_TransHstry_CashIn);
        Iv_TransHstry_Back        = view.findViewById(R.id.Iv_TransHstry_Back);
        Iv_TransHstry_CashIn        = view.findViewById(R.id.Iv_TransHstry_CashIn);
        Tv_TransHstry_CashIn        = view.findViewById(R.id.Tv_TransHstry_CashIn);
        Recycler_TransHstry_View    = view.findViewById(R.id.Recycler_TransHstry_View);

        Back();
        /*addList();
        adapter();*/
        double TotAmt = Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
        Tv_TransHstry_Tot_Amt.setText("€"+TotAmt+"");
        query = "SELECT * FROM TRANSACTION_MST WHERE TRANSACTION_TYPE = 'Cash in'  ORDER BY _ID ASC";
        Message = "No history of Cash In transaction";
        recentTransactionList();

        Iv_TransHstry_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Layout_TransHstry_CashIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, false, R.style.MyPopupOtherStyle);
            }
        });
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
            mAdapter = new TransactionHistoryAdapter(mList, getActivity());
            Recycler_TransHstry_View.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
            Recycler_TransHstry_View.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            openDialog(Message);
        }
    }

    private void addList(){
        try {
            HistoryItemAdapter itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a5);
            itemAdapter.setPayto("frecharge");
            itemAdapter.setAmount("€100");
            itemAdapter.setDate("17 May, 10:30 AM");
            itemAdapter.setTransaction_status("Confirmed");
            itemAdapter.setTransaction_type("Withdraw");
            mList.add(itemAdapter);

            itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a4);
            itemAdapter.setPayto("Zeux");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("17 May, 10:30 AM");
            itemAdapter.setTransaction_status("Pending");
            itemAdapter.setTransaction_type("Withdraw");
            mList.add(itemAdapter);

            itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a3);
            itemAdapter.setPayto("From : 780901****5678");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("16 May, 10:30 AM");
            itemAdapter.setTransaction_status("Decline");
            itemAdapter.setTransaction_type("Cash in");
            mList.add(itemAdapter);

            itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a2);
            itemAdapter.setPayto("David Ellefson");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("15 May, 10:30 AM");
            itemAdapter.setTransaction_status("Confirmed");
            itemAdapter.setTransaction_type("Withdraw");
            mList.add(itemAdapter);

            itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a1);
            itemAdapter.setPayto("From : 780901****5678");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("14 May, 10:30 AMM");
            itemAdapter.setTransaction_status("Confirmed");
            itemAdapter.setTransaction_type("Cash in");
            mList.add(itemAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void addListSuccess(){
        try {
            HistoryItemAdapter itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a5);
            itemAdapter.setPayto("frecharge");
            itemAdapter.setAmount("€100");
            itemAdapter.setDate("17 May, 10:30 AM");
            itemAdapter.setTransaction_status("Confirmed");
            itemAdapter.setTransaction_type("Withdraw");
            mList.add(itemAdapter);

            itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a2);
            itemAdapter.setPayto("David Ellefson");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("15 May, 10:30 AM");
            itemAdapter.setTransaction_status("Confirmed");
            itemAdapter.setTransaction_type("Withdraw");
            mList.add(itemAdapter);

            itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a1);
            itemAdapter.setPayto("From : 780901****5678");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("14 May, 10:30 AMM");
            itemAdapter.setTransaction_status("Confirmed");
            itemAdapter.setTransaction_type("Cash in");
            mList.add(itemAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void addListFailed(){
        try {
            HistoryItemAdapter itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a3);
            itemAdapter.setPayto("From : 780901****5678");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("16 May, 10:30 AM");
            itemAdapter.setTransaction_status("Decline");
            itemAdapter.setTransaction_type("Cash in");
            mList.add(itemAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void addListWithdraw(){
        try {
            HistoryItemAdapter itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a5);
            itemAdapter.setPayto("frecharge");
            itemAdapter.setAmount("€100");
            itemAdapter.setDate("17 May, 10:30 AM");
            itemAdapter.setTransaction_status("Confirmed");
            itemAdapter.setTransaction_type("Withdraw");
            mList.add(itemAdapter);

            itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a4);
            itemAdapter.setPayto("Zeux");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("17 May, 10:30 AM");
            itemAdapter.setTransaction_status("Pending");
            itemAdapter.setTransaction_type("Withdraw");
            mList.add(itemAdapter);

            itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a1);
            itemAdapter.setPayto("From : 780901****5678");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("14 May, 10:30 AMM");
            itemAdapter.setTransaction_status("Confirmed");
            itemAdapter.setTransaction_type("Cash in");
            mList.add(itemAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void addList_CashIn(){
        try {
            HistoryItemAdapter itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a3);
            itemAdapter.setPayto("From : 780901****5678");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("16 May, 10:30 AM");
            itemAdapter.setTransaction_status("Decline");
            itemAdapter.setTransaction_type("Cash in");
            mList.add(itemAdapter);

            itemAdapter = new HistoryItemAdapter();
            itemAdapter.setProfilePic(R.drawable.a1);
            itemAdapter.setPayto("From : 780901****5678");
            itemAdapter.setAmount("€200");
            itemAdapter.setDate("14 May, 10:30 AMM");
            itemAdapter.setTransaction_status("Confirmed");
            itemAdapter.setTransaction_type("Cash in");
            mList.add(itemAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void adapter(){
        try {
            mAdapter = new TransactionHistoryAdapter(mList, getActivity());
            Recycler_TransHstry_View.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
            Recycler_TransHstry_View.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Back()
    {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }
    /**
     * method responsible to show popup menu
     *
     * @param anchor      is a view where the popup will be shown
     * @param isWithIcons flag to check if icons to be shown or not
     * @param style       styling for popup menu
     */
    private void showPopupMenu(View anchor, boolean isWithIcons, int style) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(getActivity(), style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);

        /*  The below code in try catch is responsible to display icons*/
        if (isWithIcons) {
            try {
                Field[] fields = popup.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popup);
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //inflate menu
        popup.getMenuInflater().inflate(R.menu.transaction_selection_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Tv_TransHstry_CashIn.setText(menuItem.getTitle());
                if(menuItem.getTitle().equals("Cash In"))
                {
                    Message = null;
                    mList.clear();
                    query = "SELECT * FROM TRANSACTION_MST WHERE TRANSACTION_TYPE = 'Cash in' ORDER BY _ID ASC";
                    Message = "No history of Cash In transaction";
                    recentTransactionList();
                    /*addList_CashIn();
                    adapter();*/
                }
                else if(menuItem.getTitle().equals("Withdrawal"))
                {
                    Message = null;
                    mList.clear();
                    query = "SELECT * FROM TRANSACTION_MST WHERE TRANSACTION_TYPE = 'Withdrawal' ORDER BY _ID ASC";
                    Message = "No history of Withdrawl transaction";
                    recentTransactionList();
                    /*addListWithdraw();
                    adapter();*/
                }
                else if(menuItem.getTitle().equals("Successful"))
                {
                    Message = null;
                    mList.clear();
                    query = "SELECT * FROM TRANSACTION_MST WHERE TRANSACTION_TYPE = 'Successful' ORDER BY _ID ASC";
                    Message = "No history fo Successful transaction";
                    recentTransactionList();
                    /*addListSuccess();
                    adapter();*/
                }
                else if(menuItem.getTitle().equals("Failed"))
                {
                    Message = null;
                    mList.clear();
                    query = "SELECT * FROM TRANSACTION_MST WHERE TRANSACTION_TYPE = 'Failed' ORDER BY _ID ASC";
                    Message = "No history of Failed transaction";
                    recentTransactionList();
                    /*addListFailed();
                    adapter();*/
                }
                return true;
            }
        });
        popup.show();

    }

    // open dialog
    private void openDialog(String msg){

        AlertPopup mAlert = new AlertPopup(getContext());
        mAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlert.setMessage(msg);
        mAlert.setOkButton(view -> {
            mAlert.dismiss();
            //Do want you want
        });
        mAlert.show();
    }
}