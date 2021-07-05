package com.mpay.wallet.View.Activity.Signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.OTP_Dialog;
import com.mpay.wallet.Utils.Utility;
import com.mpay.wallet.Utils.Validation;
import com.mpay.wallet.View.Activity.Login.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import maes.tech.intentanim.CustomIntent;

public class SignupSecondActivity extends AppCompatActivity {
    public TextInputLayout MTIL_passwordLayout_SignUp, MTIL_re_passwordLayout_SignUp, MTIL_idNumberLayout, MTIL_idTypeLayout, MTIL_idTypeLayout_Menu;
    public AutoCompleteTextView ACTV_idTypeAutoCmplt;
    public Spinner Spinner_ID_Selection;
    public TextInputEditText EditText_Password;
    public TextInputEditText EditText_RePassword;
    public TextInputEditText EditText_ID_Number;
    public TextInputEditText EditText_ID_TYpe_Menu;
    public LinearLayout      Layout_Id_Img, Layout_Id_ImgCapture;
    public ImageView IV_frontImg, IV_backImg, IV_frontImg_Cross, IV_backImg_Cross;
    public CheckBox ck_condition;
    private TextView TV_btm_dlg_Front_Sz_No;
    ImageView IV_btm_dlg_Front_Sz_Nm;
    EditText etfirst, etsecand, etthird, etfourth;
    RelativeLayout Layout_btn_addPicID;
    AlertDialog alertDialog;
    private DialogInterface dialog;
    private String imageIndex="frontImage";
    public File frontImageFile, backImageFile;
    ProgressBar ProgressImagesize;
    Dialog dialogOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_second);
        Initialization();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void Initialization(){
        ACTV_idTypeAutoCmplt    = (AutoCompleteTextView) findViewById(R.id.ACTV_idTypeAutoCmplt);
        Spinner_ID_Selection    = (Spinner) findViewById(R.id.Spinner_ID_Selection);
        MTIL_idTypeLayout       = findViewById(R.id.MTIL_idTypeLayout);
        MTIL_idNumberLayout     = findViewById(R.id.MTIL_idNumberLayout);


        MTIL_passwordLayout_SignUp      = (TextInputLayout) findViewById(R.id.MTIL_passwordLayout_SignUp);
        MTIL_re_passwordLayout_SignUp   = (TextInputLayout) findViewById(R.id.MTIL_re_passwordLayout_SignUp);
        MTIL_idTypeLayout_Menu   = (TextInputLayout) findViewById(R.id.MTIL_idTypeLayout_Menu);


        EditText_Password       = (TextInputEditText)findViewById(R.id.EditText_Password);
        EditText_RePassword     = (TextInputEditText)findViewById(R.id.EditText_RePassword);
        EditText_ID_TYpe_Menu     = (TextInputEditText)findViewById(R.id.EditText_ID_TYpe_Menu);
        ACTV_idTypeAutoCmplt    = (AutoCompleteTextView)findViewById(R.id.ACTV_idTypeAutoCmplt);
        Spinner_ID_Selection    = (Spinner) findViewById(R.id.Spinner_ID_Selection);
        EditText_ID_Number      = (TextInputEditText)findViewById(R.id.EditText_ID_Number);
        ck_condition            = (CheckBox) findViewById(R.id.ck_condition);
        Layout_Id_Img           = (LinearLayout) findViewById(R.id.Layout_Id_Img);
        Layout_Id_ImgCapture    = (LinearLayout) findViewById(R.id.Layout_Id_ImgCapture);
        IV_frontImg             = (ImageView) findViewById(R.id.IV_frontImg);
        IV_frontImg_Cross       = (ImageView) findViewById(R.id.IV_frontImg_Cross);
        IV_backImg              = (ImageView) findViewById(R.id.IV_backImg);
        IV_backImg_Cross        = (ImageView) findViewById(R.id.IV_backImg_Cross);

        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.id_type_textview,getResources().getStringArray(R.array.id_proof));
        ACTV_idTypeAutoCmplt.setAdapter(adapter);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.textview2,getResources().getStringArray(R.array.id_proof));
        adapter1.setDropDownViewResource(R.layout.id_type_textview);
        Spinner_ID_Selection.setAdapter(adapter1);
        Spinner_ID_Selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MTIL_idNumberLayout.setHint(Spinner_ID_Selection.getSelectedItem().toString()+" No.");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        MTIL_idTypeLayout_Menu.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, false, R.style.MyPopupOtherStyle);
            }
        });
        IV_frontImg_Cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frontImageFile = null;
                IV_frontImg.setImageBitmap(null);
                IV_frontImg.setVisibility(View.GONE);
                IV_frontImg_Cross.setVisibility(View.GONE);
                if(imageIndex.equals("frontImage"))
                {
                    imageIndex = "backImage";
                }
                else if(imageIndex.equals("backImage"))
                {
                    imageIndex = "frontImage";
                }
                if(frontImageFile == null && backImageFile == null)
                {
                    Layout_Id_ImgCapture.setVisibility(View.VISIBLE);
                }
            }
        });
        IV_backImg_Cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backImageFile = null;
                IV_backImg.setImageBitmap(null);
                IV_backImg.setVisibility(View.GONE);
                IV_backImg_Cross.setVisibility(View.GONE);
                if(imageIndex.equals("frontImage"))
                {
                    imageIndex = "backImage";
                }
                else if(imageIndex.equals("backImage"))
                {
                    imageIndex = "frontImage";
                }


                if(frontImageFile == null && backImageFile == null)
                {
                    Layout_Id_ImgCapture.setVisibility(View.VISIBLE);
                }
            }
        });

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId())
        {
            case R.id.EditText_Password:

                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(this,"show",Toast.LENGTH_SHORT).show();
                        EditText_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                    case MotionEvent.ACTION_UP:
                        EditText_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        Toast.makeText(this,"hide",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;

            case R.id.EditText_RePassword:

                switch ( motionEvent.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(this,"show",Toast.LENGTH_SHORT).show();
                        EditText_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        break;
                    case MotionEvent.ACTION_UP:
                        EditText_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        Toast.makeText(this,"hide",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
        return true;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     * method responsible to show popup menu
     *
     * @param anchor      is a view where the popup will be shown
     * @param isWithIcons flag to check if icons to be shown or not
     * @param style       styling for popup menu
     */
    private void showPopupMenu(View anchor, boolean isWithIcons, int style) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(this, style);

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
        popup.getMenuInflater().inflate(R.menu.id_selection_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                EditText_ID_TYpe_Menu.setText(menuItem.getTitle());
                MTIL_idNumberLayout.setVisibility(View.VISIBLE);
                MTIL_idNumberLayout.setHint(menuItem.getTitle());
                return true;
            }
        });
        popup.show();

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    protected void onResume() {
        super.onResume();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void signInClick(View view) {
        Intent i = null;
        i = new Intent(SignupSecondActivity.this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
        CustomIntent.customType(SignupSecondActivity.this, "left-to-right");
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void SignUP(View view) {
        Validation validation = new Validation(this);
        if(validation.SignUpSecondValidation(this) == true)
        {
//            dialogOTP = new Dialog(this);
            OTPDialog();
        }
//        OTP_Dialog alert = new OTP_Dialog();
//        alert.showPopupWindow(view);
//        OTPDialog();
//        alert.showDialogOTP(SignupSecondActivity.this, "OTP has been sent to your Mail ");
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void backPressed(View view) {
        onBackPressed();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void AddIDImages(View view) {
        showBottomSheetDialog();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     * All Image Functon goes here
     */
    /**
     *  Bottom Sheet Dialog for Add Id Images
     */
    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_id_images);

        LinearLayout Linear_btm_dlg_Front   = bottomSheetDialog.findViewById(R.id.Linear_btm_dlg_Front);
        LinearLayout Linear_btm_dlg_Back    = bottomSheetDialog.findViewById(R.id.Linear_btm_dlg_Back);
        TextView TV_btm_dlg_Front_Sz_NM     = bottomSheetDialog.findViewById(R.id.TV_btm_dlg_Front_Sz_NM);
        TextView Tv_Click_Add_ID            = bottomSheetDialog.findViewById(R.id.Tv_Click_Add_ID);
        TV_btm_dlg_Front_Sz_No              = bottomSheetDialog.findViewById(R.id.TV_btm_dlg_Front_Sz_No);
        IV_btm_dlg_Front_Sz_Nm              = bottomSheetDialog.findViewById(R.id.IV_btm_dlg_Front_Sz_Nm);
        Layout_btn_addPicID                   = bottomSheetDialog.findViewById(R.id.Layout_btn_addPicID);
        ProgressImagesize                   = bottomSheetDialog.findViewById(R.id.ProgressImagesize);

        bottomSheetDialog.show();

        Linear_btm_dlg_Front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Front Image Capture is Clicked ", Toast.LENGTH_LONG).show();
                checkRunTimePermission("frontImage");
//                bottomSheetDialog.dismiss();
            }
        });

        Linear_btm_dlg_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Back Image Capture is Clicked ", Toast.LENGTH_LONG).show();
                checkRunTimePermission("backImage");
//                bottomSheetDialog.dismiss();
            }
        });

        Tv_Click_Add_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Upload is Clicked", Toast.LENGTH_LONG).show();
                Layout_Id_ImgCapture.setVisibility(View.GONE);
                bottomSheetDialog.dismiss();
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    // check run time permission
    private void checkRunTimePermission(String imageIdx) {
        imageIndex = imageIdx;
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                selectImage(SignupSecondActivity.this);
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
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void selectImage(SignupActivity signupActivity) {
        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == 0) {
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap clickedImage = (Bitmap) data.getExtras().get("data");
                    if (imageIndex.equals("frontImage"))
                    {
                        frontImageFile = Utility.getFileWithName(this, clickedImage,"FrontImage.jpeg");
                    }
                    if (imageIndex.equals("backImage"))
                    {
                        backImageFile = Utility.getFileWithName(this, clickedImage,"BackImage.jpeg");
                    }

                    openImageDialog(clickedImage);
                }
            }
        }
    }*/
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    // Method to show clicked/selected image
    private void openImageDialog(Bitmap bitmap){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (imageIndex.equals("frontImage")){
                    IV_frontImg.setImageBitmap(bitmap);
                    IV_frontImg.setPadding(0,0,0,0);
                    IV_frontImg.setClickable(false);
                    imageIndex = "frontImage";
                }if (imageIndex.equals("backImage")){
                    IV_backImg.setImageBitmap(bitmap);
                    IV_backImg.setPadding(0,0,0,0);
                    IV_backImg.setClickable(false);
                    imageIndex = "backImage";
                }

                if(frontImageFile != null)
                    if(backImageFile != null) {
                        Layout_Id_Img.setVisibility(View.VISIBLE);
                        Layout_Id_ImgCapture.setVisibility(View.GONE);
                    }
                    else {
                        Layout_Id_Img.setVisibility(View.GONE);
                        Layout_Id_ImgCapture.setVisibility(View.VISIBLE);
                    }
                else {
                    Layout_Id_Img.setVisibility(View.GONE);
                    Layout_Id_ImgCapture.setVisibility(View.VISIBLE);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (imageIndex.equals("frontImage"))
                {
                    frontImageFile = null;
                }
                if (imageIndex.equals("backImage"))
                {
                    backImageFile = null;
                }
            }
        });
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.custom_image_dialog, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.imageView);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)bitmap.getHeight() / (float)bitmap.getWidth()));
                image.setLayoutParams(layoutParams);
                image.setImageBitmap(bitmap);
            }
        });

        dialog.show();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void selectImage(Context context) {
        final CharSequence[] options = getResources().getStringArray(R.array.image_dialog_profile);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.selectImage));
        builder.setItems(options, (dialog, item) -> {

            this.dialog = dialog;
            if (options[item].equals(getString(R.string.take_photo))) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
                dialog.dismiss();

            } else if (options[item].equals(getString(R.string.choose_gallery))){
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
                dialog.dismiss();

            } else if (options[item].equals(getString(R.string.cancel))) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // dismiss dialog
        if(dialog != null){
            dialog.dismiss();
        }
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
//                        File file = getFile(selectedImage);
                        if (imageIndex.equals("frontImage"))
                        {
                            Layout_btn_addPicID.setVisibility(View.VISIBLE);
                            ProgressImagesize.setVisibility(View.VISIBLE);
                            frontImageFile = Utility.getFileWithName(this, selectedImage,"FrontImage.jpeg");

                            long fileSizeKB = frontImageFile.length()/1024;    // KB
                            long fileSizeMB = fileSizeKB/1024;    // MB
                            int intKB = (int) fileSizeKB;
                            int intMB = (int) fileSizeMB;

                            IV_btm_dlg_Front_Sz_Nm.setImageBitmap(selectedImage);
                            if(intMB <= 0)
                            {
                                TV_btm_dlg_Front_Sz_No.setText(String.valueOf(intKB) + " KB");
                                ProgressImagesize.setMax(1024);
                                ProgressImagesize.setProgress(intKB);
                            }
                            else {
                                TV_btm_dlg_Front_Sz_No.setText(String.valueOf(intMB) + " MB");
                                ProgressImagesize.setMax(12);
                                ProgressImagesize.setProgress(intMB);
                            }
                        }
                        if (imageIndex.equals("backImage"))
                        {
                            backImageFile = Utility.getFileWithName(this, selectedImage,"BackImage.jpeg");
                        }

                        if (imageIndex.equals("frontImage")){
                            IV_frontImg.setVisibility(View.VISIBLE);
                            IV_frontImg_Cross.setVisibility(View.VISIBLE);
                            IV_frontImg.setImageBitmap(selectedImage);
                            IV_frontImg.setPadding(0,0,0,0);
                            IV_frontImg.setClickable(false);
                            imageIndex = "frontImage";
                        }if (imageIndex.equals("backImage")){
                            IV_backImg.setVisibility(View.VISIBLE);
                            IV_backImg_Cross.setVisibility(View.VISIBLE);
                            IV_backImg.setImageBitmap(selectedImage);
                            IV_backImg.setPadding(0,0,0,0);
                            IV_backImg.setClickable(false);
                            imageIndex = "backImage";
                        }

                        if(frontImageFile != null)
                            if(backImageFile != null)
                                Layout_Id_Img.setVisibility(View.VISIBLE);
                            else
                                Layout_Id_Img.setVisibility(View.GONE);
                        else
                            Layout_Id_Img.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    if (resultCode ==RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                            File file = getFile(bitmap);
                            if (imageIndex.equals("frontImage"))
                            {
                                Layout_btn_addPicID.setVisibility(View.VISIBLE);
                                ProgressImagesize.setVisibility(View.VISIBLE);

                                frontImageFile = Utility.getFileWithName(this, bitmap,"FrontImage.jpeg");

                                long fileSizeKB = frontImageFile.length()/1024;    // KB
                                long fileSizeMB = fileSizeKB/1024;    // MB
                                int intKB = (int) fileSizeKB;
                                int intMB = (int) fileSizeMB;

                                IV_btm_dlg_Front_Sz_Nm.setImageBitmap(bitmap);
                                if(intMB <= 0)
                                {
                                    TV_btm_dlg_Front_Sz_No.setText(String.valueOf(intKB)+" KB");
                                    ProgressImagesize.setMax(1024);
                                    ProgressImagesize.setProgress(intKB);
                                }
                                else
                                {
                                    TV_btm_dlg_Front_Sz_No.setText(String.valueOf(intMB)+" MB");
                                    ProgressImagesize.setMax(12);
                                    ProgressImagesize.setProgress(intMB);
                                }
                            }
                            if (imageIndex.equals("backImage"))
                            {
                                backImageFile = Utility.getFileWithName(this, bitmap,"BackImage.jpeg");
                            }

                            if (imageIndex.equals("frontImage")){
                                IV_frontImg.setVisibility(View.VISIBLE);
                                IV_frontImg_Cross.setVisibility(View.VISIBLE);
                                IV_frontImg.setImageBitmap(bitmap);
                                IV_frontImg.setPadding(0,0,0,0);
                                IV_frontImg.setClickable(false);
                                imageIndex = "frontImage";
                            }if (imageIndex.equals("backImage")){
                                IV_backImg.setVisibility(View.VISIBLE);
                                IV_backImg_Cross.setVisibility(View.VISIBLE);
                                IV_backImg.setImageBitmap(bitmap);
                                IV_backImg.setPadding(0,0,0,0);
                                IV_backImg.setClickable(false);
                                imageIndex = "backImage";
                            }

                            if(frontImageFile != null)
                                if(backImageFile != null)
                                    Layout_Id_Img.setVisibility(View.VISIBLE);
                                else
                                    Layout_Id_Img.setVisibility(View.GONE);
                            else
                                Layout_Id_Img.setVisibility(View.GONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    // convert image into file
    private File getFile(Bitmap selectedImage){

        File file = new File(this.getCacheDir(),"FileName.png");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void OTPDialog() {
        try {
            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_otp, viewGroup, false);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            alertDialog = builder.create();
            /*dialogOTP.setContentView(R.layout.dialog_otp);
            dialogOTP.setCancelable(false);*/

            TextView TvOtpMobileNo = dialogView.findViewById(R.id.TvOtpMobileNo);
            TextView TvOtpChaneMobileNo = dialogView.findViewById(R.id.TvOtpChaneMobileNo);
            TextView txt_resend = dialogView.findViewById(R.id.txt_resend);
            TextView TvBtnVerify = dialogView.findViewById(R.id.TvBtnVerify);


            etfirst = dialogView.findViewById(R.id.etfirst);
            etsecand = dialogView.findViewById(R.id.etsecand);
            etthird = dialogView.findViewById(R.id.etthird);
            etfourth = dialogView.findViewById(R.id.etfourth);

    //            textWatcherForOtp();

            TvOtpChaneMobileNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SignupSecondActivity.this, "Request to Change Mobile no for Verification", Toast.LENGTH_SHORT).show();
                }
            });

            txt_resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SignupSecondActivity.this, "OTP Send again", Toast.LENGTH_SHORT).show();
                }
            });

            TvBtnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etfirst.getText().toString().isEmpty()) {
                        Toast.makeText(SignupSecondActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (etsecand.getText().toString().isEmpty()) {
                        Toast.makeText(SignupSecondActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (etthird.getText().toString().isEmpty()) {
                        Toast.makeText(SignupSecondActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (etfourth.getText().toString().isEmpty()) {
                        Toast.makeText(SignupSecondActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String firstBox = etfirst.getText().toString().trim();
                        String secBox = etsecand.getText().toString().trim();
                        String thirdBox = etthird.getText().toString().trim();
                        String fourthBox = etfourth.getText().toString().trim();
                        String fOtp = firstBox + secBox + thirdBox + fourthBox;
                        if (fOtp.equals("1234")) {
                            alertDialog.dismiss();
                            Intent i = null;
                            i = new Intent(SignupSecondActivity.this, LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                            CustomIntent.customType(SignupSecondActivity.this, "left-to-right");
                        } else {
                            Toast.makeText(SignupSecondActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            });

//            TEXT CHANGE START
            etfirst.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (etfirst.getText().toString().length() == 1) {
                        etsecand.requestFocus();
                    } else {
                        etfirst.requestFocus();
                    }
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (etfirst.getText().toString().length() == 1) {
                        etsecand.requestFocus();
                    } else {
                        etfirst.requestFocus();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            etsecand.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (etsecand.getText().toString().length() == 1) {
                        etthird.requestFocus();
                    } else {
                        etfirst.requestFocus();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            etthird.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (etthird.getText().toString().length() == 1) {
                        etfourth.requestFocus();
                    } else {
                        etsecand.requestFocus();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            etfourth.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (etfourth.getText().toString().length() == 1) {
                        //                    fou.continueTv.requestFocus();
                        try {
                            etfourth.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        etthird.requestFocus();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
//            TEXT CHANGE END

        }catch (Exception e){
            e.printStackTrace();
        }
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
//        dialogOTP.show();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void textWatcherForOtp() {
        etfirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (etfirst.getText().toString().length() == 1) {
                    etsecand.requestFocus();
                } else {
                    etfirst.requestFocus();
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etfirst.getText().toString().length() == 1) {
                    etsecand.requestFocus();
                } else {
                    etfirst.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etsecand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etsecand.getText().toString().length() == 1) {
                    etthird.requestFocus();
                } else {
                    etfirst.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etthird.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etthird.getText().toString().length() == 1) {
                    etfourth.requestFocus();
                } else {
                    etsecand.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etfourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etfourth.getText().toString().length() == 1) {
                    //                    fou.continueTv.requestFocus();
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                    }
                } else {
                    etthird.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onBackPressed() {

        Intent i = null;
        i = new Intent(SignupSecondActivity.this, SignupActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
        CustomIntent.customType(SignupSecondActivity.this, "right-to-left");
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//

}