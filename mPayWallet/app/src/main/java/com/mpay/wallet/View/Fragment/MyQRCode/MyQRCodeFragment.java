package com.mpay.wallet.View.Fragment.MyQRCode;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.DBHelper;
import com.mpay.wallet.Utils.SharedPreference;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.View.Fragment.home.view.HomeFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MyQRCodeFragment extends Fragment {
    private View view;
    private LinearLayout Layout_QRcode_Share, Layout_QRcode_Download;
    private ImageView Iv_QR_Back, Iv_QR_Code, UserIMG;
    TextView Tv_myqr_Name, Tv_myqr_Mail;
    SQLiteDatabase mDb;
    DBHelper mHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_my_qr_code, container, false);
        Initilization();
        Back();
        return view;
    }
    private void Initilization(){
        mHelper = new DBHelper(getActivity());

        Tv_myqr_Name = view.findViewById(R.id.Tv_myqr_Name);
        Tv_myqr_Mail = view.findViewById(R.id.Tv_myqr_Mail);
        Iv_QR_Back = view.findViewById(R.id.Iv_QR_Back);
        Iv_QR_Code = view.findViewById(R.id.Iv_QR_Code);
        UserIMG = view.findViewById(R.id.UserIMG);
        Layout_QRcode_Share = view.findViewById(R.id.Layout_QRcode_Share);
        Layout_QRcode_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRunTimePermission_Write();
            }
        });
        Layout_QRcode_Download = view.findViewById(R.id.Layout_QRcode_Download);
        Layout_QRcode_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to get the image from the ImageView (say iv)
                try {
                    BitmapDrawable draw = (BitmapDrawable) Iv_QR_Code.getDrawable();
                    Bitmap bitmap = draw.getBitmap();

                    FileOutputStream outStream = null;
                    File sdCard = Environment.getExternalStorageDirectory();
                    File dir = new File(sdCard.getAbsolutePath() + "/Download");
                    dir.mkdirs();
                    String fileName = String.format("%d.jpg", System.currentTimeMillis());
                    File outFile = new File(dir, fileName);

                    outStream = new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Iv_QR_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, new HomeFragment()).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        /*if(SharedPreferenceAmount.getInstance(getActivity()).getString_Mail(Constants.EMAIL).equals("daviddorvik@gmail.com"))
        {
            Tv_Home_Name.setText("Hey Good Morning\nDavid");
        }
        else*/
        if(SharedPreferenceAmount.getInstance(getActivity()).getString_Mail(Constants.EMAIL).equals("william@gmail.com"))
        {
            double Past_Tot_Amt = Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_TotAmount(Constants.TOTAL_AMOUNT));
            double Past_Rec_Amt = Double.parseDouble(SharedPreferenceAmount.getInstance(getActivity()).getString_ReceivedAmount(Constants.RECIVED_AMOUNT));

            double New_Tot_Am = Past_Tot_Amt + 100.00;
            double New_Rec_Amt = Past_Rec_Amt + 100.00;

            SharedPreferenceAmount.getInstance(getActivity()).setString_TotAmount(Constants.TOTAL_AMOUNT, New_Tot_Am+"");
            SharedPreferenceAmount.getInstance(getActivity()).setString_ReceivedAmount(Constants.RECIVED_AMOUNT, New_Rec_Amt+"");
            UserIMG.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.man_2));
            Iv_QR_Code.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.qr_my_william));
            Tv_myqr_Name.setText("William Rae");
            Tv_myqr_Mail.setText(SharedPreferenceAmount.getInstance(getActivity()).getString_Mail(Constants.EMAIL));
            try {
                DateFormat df = new SimpleDateFormat("dd MMM, yyyy, hh:mm a");
                String date = df.format(Calendar.getInstance().getTime());
                mDb = mHelper.getWritableDatabase();
                String q = "INSERT INTO TRANSACTION_MST (PERSON, AMOUNT, INOUT, TRANSACTION_TYPE, TRANSACTION_STATUS, TRANSACTION_DATE) VALUES ('David Dorvik', '100.00', 'IN', 'Cash in', 'Confirmed', '" + date + "')";
                mDb.execSQL(q);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        else if(SharedPreferenceAmount.getInstance(getActivity()).getString_Mail(Constants.EMAIL).equals("daviddorvik@gmail.com"))
        {
            UserIMG.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.man_1));
            Iv_QR_Code.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.qr_my_david));
            Tv_myqr_Name.setText("David Dorvik");
            Tv_myqr_Mail.setText(SharedPreferenceAmount.getInstance(getActivity()).getString_Mail(Constants.EMAIL));
        }
    }

    // check run time permission
    private void checkRunTimePermission_Write() {

        Dexter.withContext(getContext()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                checkRunTimePermission_Read();
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
    private void checkRunTimePermission_Read() {

        Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                try {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) Iv_QR_Code.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    shareImageandText(bitmap);

                    /*Bitmap bitmap;
                    Drawable drawable = ((ImageView)view).getDrawable();
                    bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

                    Canvas canvas = new Canvas(bitmap);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);
                    shareImageandText(bitmap);*/
                }catch (Exception e){
                    e.printStackTrace();
                }
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
    private void shareImageandText(Bitmap bitmap) {
        try {
            Uri uri = getmageToShare(bitmap);
            Intent intent = new Intent(Intent.ACTION_SEND);

            // putting uri of image to be shared
            intent.putExtra(Intent.EXTRA_STREAM, uri);

            // adding text to share
            intent.putExtra(Intent.EXTRA_TEXT, "David Dorvik");

            // Add subject Here
            intent.putExtra(Intent.EXTRA_SUBJECT, "QR Code");

            // setting type to image
            intent.setType("image/png");

            // calling startactivity() to share
            startActivity(Intent.createChooser(intent, "Share Via"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Retrieving the url to share
    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(getActivity().getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "qr_code.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            file.setReadable(true, false);
            uri = FileProvider.getUriForFile(getActivity(), "com.mpay.wallet", file);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
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
}