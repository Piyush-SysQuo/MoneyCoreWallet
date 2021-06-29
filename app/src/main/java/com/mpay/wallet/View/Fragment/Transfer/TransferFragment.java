package com.mpay.wallet.View.Fragment.Transfer;

import android.Manifest;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.AlertPopup;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DBHelper;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.Utils.Validation;
import com.mpay.wallet.View.Activity.Signup.SignupSecondActivity;
import com.mpay.wallet.View.Fragment.Confirm_Transfer.ConfirmTransferFragment;
import com.mpay.wallet.View.Fragment.ContactPhone.PhoneContactFragment;
import com.mpay.wallet.View.Fragment.Scan_QR.ScanQRFragment;

public class TransferFragment extends Fragment {
    private View view;
    TextInputLayout MTIL_Trnsfr_MobileLayout, MTIL_Trnsfr_AmtLayout;
    public TextInputEditText EditText_Trnsfr_Mobile, EditText_Trnsfr_Amount;
    TextView Button_TransferAmount, Tv_transfr_Add_descrip, Tv_transfr_Or, Tv_transfr_Tot_Amt, Tv_Trnsfr_Contact_Name, Tv_Trnsfr_Contact_Email;
    ImageView Iv_Transfr_Back, Img_UserContact;
    LinearLayout Layout_Scan_QRCode, Layout_Tranfer_QrDtl;
    String NAME = null, EMAIL = null, MOBILE_NO = null;

    SQLiteDatabase mDb;
    DBHelper mHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_transfer, container, false);
        Initilization();
        return view;
    }
    private void Initilization(){
        mHelper = new DBHelper(getActivity());
        MTIL_Trnsfr_MobileLayout    = view.findViewById(R.id.MTIL_Trnsfr_MobileLayout);
        MTIL_Trnsfr_AmtLayout       = view.findViewById(R.id.MTIL_Trnsfr_AmtLayout);

        EditText_Trnsfr_Mobile      = view.findViewById(R.id.EditText_Trnsfr_Mobile);
        EditText_Trnsfr_Amount      = view.findViewById(R.id.EditText_Trnsfr_Amount);

        Tv_transfr_Tot_Amt          = view.findViewById(R.id.Tv_transfr_Tot_Amt);
        String TAMT = SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.TOTAL_AMOUNT);
        Tv_transfr_Tot_Amt.setText("€" +TAMT);
        Tv_transfr_Or               = view.findViewById(R.id.Tv_transfr_Or);
        Tv_transfr_Add_descrip      = view.findViewById(R.id.Tv_transfr_Add_descrip);
        Button_TransferAmount       = view.findViewById(R.id.Button_TransferAmount);

        Iv_Transfr_Back             = view.findViewById(R.id.Iv_Transfr_Back);

        Layout_Scan_QRCode          = view.findViewById(R.id.Layout_Scan_QRCode);
        Layout_Tranfer_QrDtl        = view.findViewById(R.id.Layout_Tranfer_QrDtl);
        Img_UserContact             = view.findViewById(R.id.Img_UserContact);
        Tv_Trnsfr_Contact_Name      = view.findViewById(R.id.Tv_Trnsfr_Contact_Name);
        Tv_Trnsfr_Contact_Email    = view.findViewById(R.id.Tv_Trnsfr_Contact_Email);

        MTIL_Trnsfr_MobileLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getActivity()).withPermission(Manifest.permission.READ_PHONE_NUMBERS).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new PhoneContactFragment()).addToBackStack("Transfer").commit();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
            }
        });

        Iv_Transfr_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Layout_Scan_QRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanQRFragment scanQRFragment = new ScanQRFragment();
                Bundle bundle = new Bundle();
                bundle.putString("CLASS", "TRANSFER");
                bundle.putString("TRANSACTION_TYPE", "TRANSFER");
                scanQRFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, scanQRFragment).addToBackStack("Transfer").commit();
            }
        });

        Button_TransferAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double Tot_Amount = Double.parseDouble(Tv_transfr_Tot_Amt.getText().toString().replaceAll("€", ""));

                    if (EditText_Trnsfr_Mobile.getText().toString().isEmpty()) {
                        openDialog("Please enter Mobile No.");
                    } else if (EditText_Trnsfr_Mobile.getText().toString().length() < 12) {
                        openDialog("Please enter correct Mobile No.");
                    } else if (EditText_Trnsfr_Amount.getText().toString().isEmpty()) {
                        openDialog("Please enter Amount");
                    } else if ( Double.parseDouble(EditText_Trnsfr_Amount.getText().toString()) > Tot_Amount) {
                        openDialog("Enter Amount should not greate then Total Wallet Amount");
                    } else {
//                        Toast.makeText(getActivity(), "Money Transfered", Toast.LENGTH_SHORT).show();
                        double Send_Amount = Double.parseDouble(EditText_Trnsfr_Amount.getText().toString());
                        ConfirmTransferFragment confirmTransferFragment = new ConfirmTransferFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("MOBILE_NO", EditText_Trnsfr_Mobile.getText().toString());
                        bundle.putString("TRANSFER_AMOUNT", String.valueOf(Send_Amount));
                        bundle.putString("NAME", NAME);
                        bundle.putString("EMAIL", EMAIL);
                        bundle.putString("TRANSACTION_TYPE", "TRANSFER");
                        try {
                            DateFormat df = new SimpleDateFormat("dd MMM, yyyy, hh:mm a");
                            String date = df.format(Calendar.getInstance().getTime());
                            mDb = mHelper.getWritableDatabase();
                            String q = "INSERT INTO TRANSACTION_MST (PERSON, AMOUNT, INOUT, TRANSACTION_TYPE, TRANSACTION_STATUS, TRANSACTION_DATE) VALUES ('"+NAME+"', '"+String.valueOf(Send_Amount)+"', 'OUT', 'Withdrawal', 'Confirmed', '" + date + "')";
                            mDb.execSQL(q);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        confirmTransferFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, confirmTransferFragment).addToBackStack("Transfer").commit();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Tv_transfr_Add_descrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), R.string.add_description, Toast.LENGTH_SHORT).show();
            }
        });
        Qr_Scan_Result();
        Back();
    }

    public void Qr_Scan_Result(){
        if (getArguments() != null) {
            try {
                if (getArguments().getString("EMAIL") == null) {
                    MOBILE_NO = getArguments().getString("MOBILE_NO");
                    NAME = getArguments().getString("NAME");
                    EditText_Trnsfr_Mobile.setText(MOBILE_NO);
//                    EditText_Trnsfr_Mobile.setEnabled(false);
                }
                else if (getArguments().getString("EMAIL") != null){

                    EMAIL = getArguments().getString("EMAIL");
                    NAME = getArguments().getString("NAME");
                    MOBILE_NO = getArguments().getString("MOBILE_NO");

                    Tv_transfr_Or.setVisibility(View.GONE);
                    Layout_Tranfer_QrDtl.setVisibility(View.VISIBLE);
                    EditText_Trnsfr_Mobile.setEnabled(false);

                    EditText_Trnsfr_Mobile.setText(MOBILE_NO);
                    Tv_Trnsfr_Contact_Name.setText(NAME);
                    Tv_Trnsfr_Contact_Email.setText(EMAIL);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
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