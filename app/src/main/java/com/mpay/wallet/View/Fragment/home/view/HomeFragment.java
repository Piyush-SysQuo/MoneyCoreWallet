package com.mpay.wallet.View.Fragment.home.view;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DBHelper;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Activity.CashIn.View.CashInActivity;
import com.mpay.wallet.View.Activity.More.MoreActivity;
import com.mpay.wallet.View.Activity.MyQRCodeActivity;
import com.mpay.wallet.View.Activity.Statement.View.StatementActivity;
import com.mpay.wallet.View.Activity.Withdraw.WithdrawActivity;
import com.mpay.wallet.View.Fragment.MyQRCode.MyQRCodeFragment;
import com.mpay.wallet.View.Fragment.Notification.View.NotificationFragment;
import com.mpay.wallet.View.Fragment.Scan_QR.ScanQRFragment;
import com.mpay.wallet.View.Fragment.Transaction_History.View.TransactionHistoryFragment;
import com.mpay.wallet.View.Fragment.Transfer.TransferFragment;
import com.mpay.wallet.View.Fragment.TransferFromSpace.SpaceToSpaceFragment;
import com.mpay.wallet.View.Fragment.home.adapter.RecentTransactionAdapter;
import com.mpay.wallet.View.Fragment.home.model.ItemAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    private View view;
    private PieChart pieChart;
    private LinearLayout LinearGrid_Transfer, LinearGrid_Pay, LinearGrid_History, LinearGrid_CashIn, LinearGrid_Withdraw, LinearGrid_Statement;
    TextView TV1, Tv_wallet_SwitchText, Tv_wallet_Available_Bln, Tv_wallet_Received_Bln, Tv_wallet_Spent_Bln, Tv_Home_Name;
    ImageView IV_Home_QRCode, IV_Home_Notification;
    LinearLayout lnn;
    private RecyclerView RecyclerView;
    private List<ItemAdapter> mList = new ArrayList<>();
    private RecentTransactionAdapter mAdapter;
    Switch switchButton;
    double TotAmt ;
    double RecvAmt;
    double SpntAmt;
    SQLiteDatabase mDb;
    DBHelper mHelper;
    NumberFormat formatter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        pieChart = view.findViewById(R.id.pieChart);
        Initilization();
        return view;
    }

    private void Initilization()
    {
        formatter = new DecimalFormat("#0.00");
        mHelper                     = new DBHelper(getActivity());
        TV1     = view.findViewById(R.id.TV1);
        Tv_wallet_Available_Bln     = view.findViewById(R.id.Tv_wallet_Available_Bln);
        Tv_wallet_Received_Bln     = view.findViewById(R.id.Tv_wallet_Received_Bln);
        Tv_wallet_Spent_Bln     = view.findViewById(R.id.Tv_wallet_Spent_Bln);

        RecyclerView     = view.findViewById(R.id.RecyclerView);
        Tv_wallet_SwitchText     = view.findViewById(R.id.Tv_wallet_SwitchText);
        LinearGrid_Transfer     = view.findViewById(R.id.LinearGrid_Transfer);
        LinearGrid_Pay          = view.findViewById(R.id.LinearGrid_Pay);
        LinearGrid_History      = view.findViewById(R.id.LinearGrid_History);
        LinearGrid_CashIn       = view.findViewById(R.id.LinearGrid_CashIn);
        LinearGrid_Withdraw     = view.findViewById(R.id.LinearGrid_Withdraw);
        LinearGrid_Statement    = view.findViewById(R.id.LinearGrid_Statement);
        IV_Home_QRCode    = view.findViewById(R.id.IV_Home_QRCode);
        IV_Home_Notification    = view.findViewById(R.id.IV_Home_Notification);
        Tv_Home_Name    = view.findViewById(R.id.Tv_Home_Name);

        if(SharedPreferenceAmount.getInstance(getActivity()).getString_Mail(Constants.EMAIL).equals("daviddorvik@gmail.com"))
        {
            Tv_Home_Name.setText("Hey Good Morning\nDavid");
        }
        else if(SharedPreferenceAmount.getInstance(getActivity()).getString_Mail(Constants.EMAIL).equals("william@gmail.com"))
        {
            Tv_Home_Name.setText("Hey Good Morning\nWilliam");

        }

        lnn = view.findViewById(R.id.lnn);

        View layout= view.findViewById(R.id.layout);

        LinearGrid_Transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transfer_Bottom_Dialog();
            }
        });
        LinearGrid_Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanQRFragment scanQRFragment = new ScanQRFragment();
                Bundle bundle = new Bundle();
                bundle.putString("CLASS", "PAY");
                bundle.putString("TRANSACTION_TYPE", "TRANSFER");
                scanQRFragment.setArguments(bundle);
                Dexter.withContext(getActivity()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, scanQRFragment).addToBackStack("Home").commit();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new PayFragment()).addToBackStack("Home").commit();
            }
        });
        LinearGrid_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new TransactionHistoryFragment()).addToBackStack("Home").commit();
            }
        });
        LinearGrid_CashIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar snackbar = Snackbar
                        .make(lnn, "Cash in", Snackbar.LENGTH_LONG);
                snackbar.show();*/
                Intent intent = new Intent(getActivity(), CashInActivity.class);
                intent.putExtra(Constants.KEY_LANGUAGE,"en");
                startActivity(intent);
                getActivity().finish();
            }
        });
        LinearGrid_Withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar snackbar = Snackbar
                        .make(lnn, "Withdraws", Snackbar.LENGTH_LONG);
                snackbar.show();*/
                Intent intent = new Intent(getActivity(), WithdrawActivity.class);
                intent.putExtra(Constants.KEY_LANGUAGE,"en");
                startActivity(intent);
                getActivity().finish();
            }
        });
        LinearGrid_Statement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar snackbar = Snackbar
                        .make(lnn, "Statement", Snackbar.LENGTH_LONG);
                snackbar.show();*/
                Intent intent = new Intent(getActivity(), StatementActivity.class);
                intent.putExtra(Constants.KEY_LANGUAGE,"en");
                startActivity(intent);
                getActivity().finish();

            }
        });


        switchButton = view.findViewById(R.id.switch_button);
        switchButton.setChecked(false);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switchButton.isChecked() == true)
                {
                    switchButton.setText("UnLock");
                }
                if(switchButton.isChecked() == false)
                {
                    switchButton.setText("Lock");
                }
            }
        });

        /*addList();
        adapter();*/
        recentTransactionList();

        IV_Home_QRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new MyQRCodeFragment()).addToBackStack("Home").commit();
                Intent in = new Intent(getActivity(), MyQRCodeActivity.class);
                in.putExtra("ACTIVITY_CLASS", "HOME");
                startActivity(in);
                getActivity().finish();
            }
        });

        IV_Home_Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new NotificationFragment()).addToBackStack("Home").commit();
            }
        });


        try {
            TotAmt = Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.TOTAL_AMOUNT).toString());
            RecvAmt = Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.RECIVED_AMOUNT).toString());
            SpntAmt = Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.SPENT_AMOUNT).toString());

            Tv_wallet_Available_Bln.setText("€"+formatter.format(TotAmt)+"");
            Tv_wallet_Received_Bln.setText("€"+formatter.format(RecvAmt)+"");
            Tv_wallet_Spent_Bln.setText("€"+formatter.format(SpntAmt)+"");
        }catch (Exception e){
            e.printStackTrace();
        }


        PieDataSet pieDataSet = new PieDataSet(pieChartDataSet(),"");
        pieDataSet.setColors(getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.button_color), getResources().getColor(R.color.home_chart_expen));
        pieDataSet.setValueLineColor(R.color.home_drak_back);
        pieDataSet.setValueTextSize(0f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setDrawSliceText(false);
        pieChart.setData(pieData);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawEntryLabels(true);
        pieChart.setDrawMarkers(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setClickable(false);
        pieChart.setHoleColor(getResources().getColor(R.color.home_drak_back));

        pieChart.getLegend().setEnabled(false);
        pieChart.setCenterText("");
    }


    public void recentTransactionList(){
        mList.clear();
        mDb = mHelper.getReadableDatabase();
        String q = "SELECT * FROM TRANSACTION_MST ORDER BY _ID ASC";
        Cursor c = mDb.rawQuery(q, null);
        if(c.getCount()>0){
            mList = new ArrayList<ItemAdapter>();
            int i = 1;
            while ((c.moveToNext())){
                ItemAdapter ListItems = new ItemAdapter();
                String PERSON = c.getString(c.getColumnIndex("PERSON"));
                String Amount = c.getString(c.getColumnIndex("AMOUNT"));
                String InOut = c.getString(c.getColumnIndex("INOUT"));
                String TransactionType = c.getString(c.getColumnIndex("TRANSACTION_TYPE"));
                String TransactionStatus = c.getString(c.getColumnIndex("TRANSACTION_STATUS"));
                String TransactionDate = c.getString(c.getColumnIndex("TRANSACTION_DATE"));

                ListItems.setName(PERSON);
                ListItems.setAmount("€"+formatter.format(Double.parseDouble(Amount))+"");
//                ListItems.setDate("11 May  10:30 AM");
                ListItems.setDate(TransactionDate);
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
            mAdapter = new RecentTransactionAdapter(mList, getActivity());
            RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
            RecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            TV1.setText("No Transaction");
        }
    }
    private ArrayList<PieEntry> pieChartDataSet(){
        ArrayList<PieEntry> dataSet = new ArrayList<PieEntry>();

        dataSet.add(new PieEntry((float) TotAmt)); // Total
        dataSet.add(new PieEntry((float) RecvAmt)); // Received
        dataSet.add(new PieEntry((float) SpntAmt)); //Spent
        return  dataSet;
    }

    private void addList(){
        mList.clear();
        ItemAdapter itemAdapter = new ItemAdapter();
        itemAdapter.setProfilePic(R.drawable.man_1);
        itemAdapter.setName("William daniels");
        itemAdapter.setAmount("€50.00");
        itemAdapter.setDate("11 May  10:30 AM");
        itemAdapter.setTransaction_type("Withdraw");
        mList.add(itemAdapter);


        itemAdapter = new ItemAdapter();
        itemAdapter.setProfilePic(R.drawable.man_2);
        itemAdapter.setName("Michael");
        itemAdapter.setAmount("€150.00");
        itemAdapter.setDate("10 May  10:30 AM");
        itemAdapter.setTransaction_type("Cash in");
        mList.add(itemAdapter);


        itemAdapter = new ItemAdapter();
        itemAdapter.setProfilePic(R.drawable.man_3);
        itemAdapter.setName("Carly Rae");
        itemAdapter.setAmount("€50.00");
        itemAdapter.setDate("09 May  10:30 AM");
        itemAdapter.setTransaction_type("Withdraw");
        mList.add(itemAdapter);
    }
    private void adapter(){
        try {
            mAdapter = new RecentTransactionAdapter(mList, getActivity());
            RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
            RecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // enable run time permission
    private void enableRunTimePermission(String phone){
        Dexter.withContext(getActivity()).withPermissions(Manifest.permission.CALL_PHONE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone));//change the number
                        getActivity().startActivity(callIntent);

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*addList();
        adapter();*/
        recentTransactionList();
    }

//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//    TRANSFER
    public void Transfer_Bottom_Dialog(){

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_transfer);

        RelativeLayout Layout_trnsfr_Btm_Dlg1   = bottomSheetDialog.findViewById(R.id.Layout_trnsfr_Btm_Dlg1);
        RelativeLayout Layout_trnsfr_Btm_Dlg2   = bottomSheetDialog.findViewById(R.id.Layout_trnsfr_Btm_Dlg2);

        bottomSheetDialog.show();

        Layout_trnsfr_Btm_Dlg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Transfer to Wallet", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new TransferFragment()).addToBackStack("Home").commit();
                bottomSheetDialog.dismiss();
            }
        });

        Layout_trnsfr_Btm_Dlg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpaceToSpaceFragment fragment = new SpaceToSpaceFragment();
                Bundle args = new Bundle();
                args.putString("AMOUNT", "100");
                args.putString("SPACE_NAME", "Space-1");
                args.putString("POSITION", "1");
                fragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, fragment).addToBackStack("Space").commit();
            }
        });
    }
}