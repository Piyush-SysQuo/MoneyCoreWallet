package com.mpay.wallet.View.Fragment.Scan_QR;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mpay.wallet.R;
import com.mpay.wallet.View.Fragment.ContactPhone.PhoneContactFragment;
import com.mpay.wallet.View.Fragment.Pay.PayFragment;
import com.mpay.wallet.View.Fragment.Transfer.TransferFragment;

public class ScanQRFragment extends Fragment {
    private View view;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    RelativeLayout Layout_scan_QR;
    ImageView Iv_Scan_Back;
    TextView Tv_ScanQR_Cancel;
    String TRANSACTION_TYPE = null, CLASS = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scan_qr, container, false);
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Initilization();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
        return view;
    }
    private void Initilization() {
        Tv_ScanQR_Cancel = view.findViewById(R.id.Tv_ScanQR_Cancel);
        Iv_Scan_Back = view.findViewById(R.id.Iv_Scan_Back);
        Layout_scan_QR = view.findViewById(R.id.Layout_scan_QR);
        Layout_scan_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), QrCodeActivity.class);
                startActivityForResult(i, REQUEST_CODE_QR_SCAN);
            }
        });
        Iv_Scan_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        Tv_ScanQR_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        Qr_Scan_Result();
    }
    public void Qr_Scan_Result(){
        if (getArguments() != null) {
            try {
                TRANSACTION_TYPE = getArguments().getString("TRANSACTION_TYPE");
                CLASS = getArguments().getString("CLASS");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK)
        {
            Log.d("LOGTAG","COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");

            if(CLASS.equals("TRANSFER"))
            {
                String[] result_split = result.split(",");
                String Name = result_split[0];
                String Email = result_split[1];
                String MOBILE_NO = result_split[2];

                Bundle bundle = new Bundle();
                bundle.putString("MOBILE_NO", MOBILE_NO);
                bundle.putString("NAME", Name);
                bundle.putString("EMAIL", Email);
                bundle.putString("TRANSACTION_TYPE", TRANSACTION_TYPE);
                bundle.putString("CLASS", CLASS);

                TransferFragment transferFragment = new TransferFragment();
                transferFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, transferFragment).commit();
            }
            if(CLASS.equals("PAY"))
            {
                String[] result_split = result.split(",");
                String Name = result_split[0];
                String Address = result_split[1];
                String MOBILE_NO = result_split[2];
                String Website = result_split[3];
                String Email = result_split[4];

                Bundle bundle = new Bundle();
                bundle.putString("MOBILE_NO", MOBILE_NO);
                bundle.putString("ADDRESS", Address);
                bundle.putString("WEBSITE", Website);
                bundle.putString("NAME", Name);
                bundle.putString("EMAIL", Email);
                bundle.putString("TRANSACTION_TYPE", TRANSACTION_TYPE);
                bundle.putString("CLASS", CLASS);


                PayFragment payFragment = new PayFragment();
                payFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, payFragment).commit();
            }

            /*Log.d("LOGTAG","Have scan result in your app activity :"+ result);
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Scan result");
            alertDialog.setMessage(result);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            TransferFragment transferFragment = new TransferFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("MOBILE_NO", "987654321852");
                            transferFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, transferFragment).commit();
//                            getActivity().getSupportFragmentManager().popBackStack();
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();*/

        }
    }
}