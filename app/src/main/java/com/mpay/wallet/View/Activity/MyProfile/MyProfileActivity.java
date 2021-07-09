package com.mpay.wallet.View.Activity.MyProfile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.mpay.wallet.Utils.Constants;
import com.mpay.wallet.Utils.RotateImage;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.Utils.Utility;
import com.mpay.wallet.View.Activity.ChangeEmailID.ChangeEmailIDActivity;
import com.mpay.wallet.View.Activity.ChangePhoneNo.ChangePhoneNoActivity;
import com.mpay.wallet.View.Activity.EditProfile.EditProfileActivity;
import com.mpay.wallet.View.Activity.More.MoreActivity;
import com.mpay.wallet.View.Activity.Signup.SignupSecondActivity;
import com.mpay.wallet.View.Fragment.Transaction_History.Adapter.TransactionHistoryAdapter;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {

    static CircleImageView CircleImage_UserPic;
    ImageView Iv_Profile_IDPic1, Iv_Profile_IDPic2;
    TextView Tv_Profile_UserName, Tv_Profile_Mail,Tv_Profile_Phone, Tv_Profile_IDNO, Tv_Profile_IDPicHead, Tv_Profile_Address;
    private String imageIndex="userPic";
    private DialogInterface dialog;
    public File userImageFile;

    /**
     *  NEW ID
     */
    RelativeLayout Layout_btn_addNewPicID;
    TextInputLayout MTIL_BtmAddNewIDType, MTIL_BtmAddNewIDNo;
    TextInputEditText EditText_BtmAddNewIDType, EditText_BtmAddNewIDNo;
    LinearLayout Linear_BtmAddNewIDFront, Linear_BtmAddNewIDBack;
    ProgressBar ProgressImagesizeBtmAddNewID;
    TextView TV_BtmAddNewID_Sz_No, Tv_Click_BtmAddNewID;
    public File frontImageFile, backImageFile;
    boolean CaptureFront = false, CaptureBack = false;

    /**
     * NEW ADDRESS
     */
    TextInputLayout MTIL_BtmAddNewAddress1, MTIL_BtmAddNewAddress2, MTIL_BtmAddNewAddressPostal, MTIL_BtmAddNewAddressCountry, MTIL_BtmAddNewAddressCity;
    TextInputEditText EditText_BtmAddNewAddress1,EditText_BtmAddNewAddress2, EditText_BtmAddNewAddressPostal, EditText_BtmAddNewAddressCountry, EditText_BtmAddNewAddressCity;
    TextView Tv_Click_BtmAddNewAddress;
    ArrayList<String> arrayList_CountryList = null;
    ArrayList<String> arrayList_CityList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        CircleImage_UserPic = findViewById(R.id.CircleImage_UserPic);

        Iv_Profile_IDPic1 = findViewById(R.id.Iv_Profile_IDPic1);
        Iv_Profile_IDPic2 = findViewById(R.id.Iv_Profile_IDPic2);

        Tv_Profile_UserName = findViewById(R.id.Tv_Profile_UserName);
        Tv_Profile_Mail = findViewById(R.id.Tv_Profile_Mail);
        Tv_Profile_Phone = findViewById(R.id.Tv_Profile_Phone);
        Tv_Profile_IDNO = findViewById(R.id.Tv_Profile_IDNO);
        Tv_Profile_IDPicHead = findViewById(R.id.Tv_Profile_IDPicHead);
        Tv_Profile_Address = findViewById(R.id.Tv_Profile_Address);

        Tv_Profile_Mail.setText(SharedPreferenceAmount.getInstance(this).getString_Mail(Constants.EMAIL));

        if(SharedPreferenceAmount.getInstance(this).getString_Mail(Constants.EMAIL).equals("daviddorvik@gmail.com"))
        {
            Tv_Profile_UserName.setText("David Dorvik");
            Tv_Profile_Phone.setText("+33-700-595-4771");
            CircleImage_UserPic.setImageDrawable(getResources().getDrawable(R.drawable.man_1));
        }
        else if(SharedPreferenceAmount.getInstance(this).getString_Mail(Constants.EMAIL).equals("william@gmail.com"))
        {
            Tv_Profile_UserName.setText("William Rae");
            Tv_Profile_Phone.setText("+33-800-696-8771");
            CircleImage_UserPic.setImageDrawable(getResources().getDrawable(R.drawable.man_2));

        }
    }

    public void clickToChangeMailID(View view) {
        Intent in = new Intent(this, ChangeEmailIDActivity.class);
        startActivity(in);
        finish();
    }

    public void clickToChangePhoneNo(View view) {
        Intent in = new Intent(this, ChangePhoneNoActivity.class);
        startActivity(in);
        finish();
    }

    public void clickToAddNewID(View view) {
        showBottomSheetDialog_ToAddNewID();
    }

    public void clickToAddNewAdress(View view) {
        arrayList_CountryList = new ArrayList<>();
        arrayList_CountryList.add("Russia");
        arrayList_CountryList.add("Germany");
        arrayList_CountryList.add("United");
        arrayList_CountryList.add("France");
        arrayList_CountryList.add("Italy");
        arrayList_CountryList.add("Spain");
        arrayList_CountryList.add("Ukraine");
        arrayList_CountryList.add("Poland");
        arrayList_CountryList.add("Romania");
        arrayList_CountryList.add("Netherlands");
        arrayList_CountryList.add("Belgium");
        arrayList_CountryList.add("Czech Republic");
        arrayList_CountryList.add("Greece");
        arrayList_CountryList.add("Portugal");

        arrayList_CityList = new ArrayList<>();
        arrayList_CityList.add("MOSKVA");
        arrayList_CityList.add("LONDON");
        arrayList_CityList.add("St Petersburg");
        arrayList_CityList.add("BERLIN");
        arrayList_CityList.add("MADRID");
        arrayList_CityList.add("ROMA");
        arrayList_CityList.add("KIEV");
        arrayList_CityList.add("PARIS");
        arrayList_CityList.add("BUCURESTI");
        arrayList_CityList.add("BUDAPEST");
        arrayList_CityList.add("Hamburg");
        arrayList_CityList.add("MINSK");
        arrayList_CityList.add("WARSZAWA");
        arrayList_CityList.add("BEOGRAD");

        showBottomSheetDialog_ToAddNewAddress();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void clickToChangePic(View view) {
        checkRunTimePermission("userPic");
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    // check run time permission
    private void checkRunTimePermission(String imageIdx) {
        imageIndex = imageIdx;
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                selectImage(MyProfileActivity.this);
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
                        try {
                            if (imageIndex.equals("userPic")) {
                                userImageFile = Utility.getFileWithName(this, selectedImage, "UserPic.jpeg");
                                String imagePath = userImageFile.getAbsolutePath();             // photoFile is a File class.
                                Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);

                                Bitmap orientedBitmap = RotateImage.rotateBitmap(imagePath, selectedImage);
                                Glide.with(this).load(orientedBitmap).into(CircleImage_UserPic);

                                CircleImage_UserPic.setPadding(0, 0, 0, 0);
                                CircleImage_UserPic.setClickable(false);
                                imageIndex = "userPic";
                            }

                           else if (imageIndex.equals("frontImage")) {
                                Layout_btn_addNewPicID.setVisibility(View.VISIBLE);
                                ProgressImagesizeBtmAddNewID.setVisibility(View.VISIBLE);
                                frontImageFile = Utility.getFileWithName(this, selectedImage, "FrontImage.jpeg");

                                long fileSizeKB = frontImageFile.length() / 1024;    // KB
                                long fileSizeMB = fileSizeKB / 1024;    // MB
                                int intKB = (int) fileSizeKB;
                                int intMB = (int) fileSizeMB;

                                Iv_Profile_IDPic1.setImageBitmap(selectedImage);
                                if (intMB <= 0) {
                                    TV_BtmAddNewID_Sz_No.setText(String.valueOf(intKB) + " KB");
                                    ProgressImagesizeBtmAddNewID.setMax(1024);
                                    ProgressImagesizeBtmAddNewID.setProgress(intKB);
                                } else {
                                    TV_BtmAddNewID_Sz_No.setText(String.valueOf(intMB) + " MB");
                                    ProgressImagesizeBtmAddNewID.setMax(12);
                                    ProgressImagesizeBtmAddNewID.setProgress(intMB);
                                }

                                Iv_Profile_IDPic1.setVisibility(View.VISIBLE);
                                Iv_Profile_IDPic1.setPadding(0,0,0,0);
                                Iv_Profile_IDPic1.setClickable(false);
                                imageIndex = "frontImage";
                                CaptureFront = true;
                            }
                            else if (imageIndex.equals("backImage"))
                            {
                                backImageFile = Utility.getFileWithName(this, selectedImage,"BackImage.jpeg");
                                Iv_Profile_IDPic2.setVisibility(View.VISIBLE);
                                Iv_Profile_IDPic2.setPadding(0,0,0,0);
                                Iv_Profile_IDPic2.setClickable(false);
                                imageIndex = "backImage";
                                CaptureBack = true;
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    if (resultCode ==RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            if (imageIndex.equals("userPic"))
                            {
                                userImageFile = Utility.getFileWithName(this, bitmap,"UserPic.jpeg");
//                                normalizeImageForUri(this, frontImageFile, selectedImage);

                                String imagePath = userImageFile.getAbsolutePath();             // photoFile is a File class.
                                Bitmap myBitmap  = BitmapFactory.decodeFile(imagePath);

                                Bitmap orientedBitmap = RotateImage.rotateBitmap(imagePath, bitmap);
                                Glide.with(this).load(orientedBitmap).into(CircleImage_UserPic);
                                CircleImage_UserPic.setPadding(0,0,0,0);
                                CircleImage_UserPic.setClickable(false);
                                imageIndex = "userPic";
                            }

                            else if (imageIndex.equals("frontImage"))
                            {
                                Layout_btn_addNewPicID.setVisibility(View.VISIBLE);
                                ProgressImagesizeBtmAddNewID.setVisibility(View.VISIBLE);
                                frontImageFile = Utility.getFileWithName(this, bitmap,"FrontImage.jpeg");

                                long fileSizeKB = frontImageFile.length()/1024;    // KB
                                long fileSizeMB = fileSizeKB/1024;    // MB
                                int intKB = (int) fileSizeKB;
                                int intMB = (int) fileSizeMB;

                                Iv_Profile_IDPic1.setImageBitmap(bitmap);
                                if(intMB <= 0)
                                {
                                    TV_BtmAddNewID_Sz_No.setText(String.valueOf(intKB)+" KB");
                                    ProgressImagesizeBtmAddNewID.setMax(1024);
                                    ProgressImagesizeBtmAddNewID.setProgress(intKB);
                                }
                                else
                                {
                                    TV_BtmAddNewID_Sz_No.setText(String.valueOf(intMB)+" MB");
                                    ProgressImagesizeBtmAddNewID.setMax(12);
                                    ProgressImagesizeBtmAddNewID.setProgress(intMB);
                                }

                                Iv_Profile_IDPic1.setVisibility(View.VISIBLE);
                                Iv_Profile_IDPic1.setPadding(0,0,0,0);
                                Iv_Profile_IDPic1.setClickable(false);
                                imageIndex = "frontImage";
                                CaptureFront = true;
                            }
                            else if (imageIndex.equals("backImage"))
                            {
                                backImageFile = Utility.getFileWithName(this, bitmap,"BackImage.jpeg");
                                Iv_Profile_IDPic2.setVisibility(View.VISIBLE);
                                Iv_Profile_IDPic2.setImageBitmap(bitmap);
                                Iv_Profile_IDPic2.setPadding(0,0,0,0);
                                Iv_Profile_IDPic2.setClickable(false);
                                imageIndex = "backImage";
                                CaptureBack = true;
                            }
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
    public void backPressed(View view) {
        onBackPressed();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     *  NEW ID
     */
    private void showBottomSheetDialog_ToAddNewID() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_add_new_id_pic);

        MTIL_BtmAddNewIDType            = bottomSheetDialog.findViewById(R.id.MTIL_BtmAddNewIDType);
        MTIL_BtmAddNewIDNo              = bottomSheetDialog.findViewById(R.id.MTIL_BtmAddNewIDNo);

        EditText_BtmAddNewIDType        = bottomSheetDialog.findViewById(R.id.EditText_BtmAddNewIDType);
        EditText_BtmAddNewIDNo          = bottomSheetDialog.findViewById(R.id.EditText_BtmAddNewIDNo);

        Linear_BtmAddNewIDFront         = bottomSheetDialog.findViewById(R.id.Linear_BtmAddNewIDFront);
        Linear_BtmAddNewIDBack          = bottomSheetDialog.findViewById(R.id.Linear_BtmAddNewIDBack);

        Layout_btn_addNewPicID    = bottomSheetDialog.findViewById(R.id.Layout_btn_addNewPicID);
        ProgressImagesizeBtmAddNewID    = bottomSheetDialog.findViewById(R.id.ProgressImagesizeBtmAddNewID);

        TV_BtmAddNewID_Sz_No            = bottomSheetDialog.findViewById(R.id.TV_BtmAddNewID_Sz_No);
        Tv_Click_BtmAddNewID            = bottomSheetDialog.findViewById(R.id.Tv_Click_BtmAddNewID);

        bottomSheetDialog.show();

        MTIL_BtmAddNewIDType.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.MyPopupOtherStyle);

                //init the popup
                PopupMenu popup = new PopupMenu(wrapper, view);

                //inflate menu
                popup.getMenuInflater().inflate(R.menu.id_selection_menu, popup.getMenu());

                //implement click events
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        EditText_BtmAddNewIDType.setText(menuItem.getTitle());
                        MTIL_BtmAddNewIDNo.setVisibility(View.VISIBLE);
                        MTIL_BtmAddNewIDNo.setHint(menuItem.getTitle());
                        Tv_Profile_IDPicHead.setHint(menuItem.getTitle());
                        return true;
                    }
                });
                popup.show();
            }
        });

        Linear_BtmAddNewIDFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Front Image Capture is Clicked ", Toast.LENGTH_LONG).show();
                checkRunTimePermission("frontImage");
//                bottomSheetDialog.dismiss();
            }
        });

        Linear_BtmAddNewIDBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Back Image Capture is Clicked ", Toast.LENGTH_LONG).show();
                checkRunTimePermission("backImage");
//                bottomSheetDialog.dismiss();
            }
        });

        Tv_Click_BtmAddNewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = false;
                if(EditText_BtmAddNewIDType.getText().toString().isEmpty())
                {
                    MTIL_BtmAddNewIDType.setErrorEnabled(true);
                    MTIL_BtmAddNewIDType.setError("Select ID Type");
                    MTIL_BtmAddNewIDType.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    result = false;
                }
                if (!EditText_BtmAddNewIDType.getText().toString().isEmpty() ) {
                    MTIL_BtmAddNewIDType.setErrorEnabled(false);
                    result = true;
                }

                if(EditText_BtmAddNewIDNo.getText().toString().isEmpty())
                {
                    MTIL_BtmAddNewIDNo.setErrorEnabled(true);
                    MTIL_BtmAddNewIDNo.setError("Enter Id Number");
                    MTIL_BtmAddNewIDNo.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    result = false;
                }
                if (!EditText_BtmAddNewIDNo.getText().toString().isEmpty() ) {
                    MTIL_BtmAddNewIDNo.setErrorEnabled(false);
                    result = true;
                }
                if(CaptureFront == false){
                    Toast.makeText(MyProfileActivity.this, "ID Front Side Image", Toast.LENGTH_SHORT).show();
                    result = false;
                }
                if(CaptureBack == false){
                    Toast.makeText(MyProfileActivity.this, "ID Back Side Image", Toast.LENGTH_SHORT).show();
                    result = false;
                }
                if(CaptureFront == true && CaptureBack == true)
                {
                    result = true;
                }
                if (result == true)
                {
                    Tv_Profile_IDNO.setText(EditText_BtmAddNewIDNo.getText().toString());
                    Toast.makeText(getApplicationContext(), "Id Saved", Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                }
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     *  NEW ADDRESS
     */
    private void showBottomSheetDialog_ToAddNewAddress() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_add_address);

        MTIL_BtmAddNewAddress1            = bottomSheetDialog.findViewById(R.id.MTIL_BtmAddNewAddress1);
        MTIL_BtmAddNewAddress2              = bottomSheetDialog.findViewById(R.id.MTIL_BtmAddNewAddress2);
        MTIL_BtmAddNewAddressPostal              = bottomSheetDialog.findViewById(R.id.MTIL_BtmAddNewAddressPostal);
        MTIL_BtmAddNewAddressCountry              = bottomSheetDialog.findViewById(R.id.MTIL_BtmAddNewAddressCountry);
        MTIL_BtmAddNewAddressCity              = bottomSheetDialog.findViewById(R.id.MTIL_BtmAddNewAddressCity);

        EditText_BtmAddNewAddress1        = bottomSheetDialog.findViewById(R.id.EditText_BtmAddNewAddress1);
        EditText_BtmAddNewAddress2          = bottomSheetDialog.findViewById(R.id.EditText_BtmAddNewAddress2);
        EditText_BtmAddNewAddressPostal          = bottomSheetDialog.findViewById(R.id.EditText_BtmAddNewAddressPostal);
        EditText_BtmAddNewAddressCountry          = bottomSheetDialog.findViewById(R.id.EditText_BtmAddNewAddressCountry);
        EditText_BtmAddNewAddressCity          = bottomSheetDialog.findViewById(R.id.EditText_BtmAddNewAddressCity);

        Tv_Click_BtmAddNewAddress    = bottomSheetDialog.findViewById(R.id.Tv_Click_BtmAddNewAddress);

        bottomSheetDialog.show();

        MTIL_BtmAddNewAddressCountry.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.MyPopupOtherStyle);
                    Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.MyPopupOtherStyle);

                    //init the popup
                    PopupMenu popupMenu = new PopupMenu(wrapper, v);
//            PopupMenu popupMenu = new PopupMenu(CashInActivity.this, v);

                    for (int i = 0; arrayList_CountryList.size() > i; i++) {
                        popupMenu.getMenu().add(1, i, i, arrayList_CountryList.get(i));
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
//                    Toast.makeText(CashInActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                            EditText_BtmAddNewAddressCountry.setText(item.getTitle());
                            return false;
                        }
                    });
                    popupMenu.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        MTIL_BtmAddNewAddressCity.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
//                    Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.MyPopupOtherStyle);
                    Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.MyPopupOtherStyle);

                    //init the popup
                    PopupMenu popupMenu = new PopupMenu(wrapper, view);
//            PopupMenu popupMenu = new PopupMenu(CashInActivity.this, v);

                    for (int i = 0; arrayList_CityList.size() > i; i++) {
                        popupMenu.getMenu().add(1, i, i, arrayList_CityList.get(i));
                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
//                    Toast.makeText(CashInActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                            EditText_BtmAddNewAddressCity.setText(item.getTitle());
                            return false;
                        }
                    });
                    popupMenu.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        Tv_Click_BtmAddNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = false;
                if(EditText_BtmAddNewAddress1.getText().toString().isEmpty())
                {
                    MTIL_BtmAddNewAddress1.setErrorEnabled(true);
                    MTIL_BtmAddNewAddress1.setError("Select Address 1");
                    MTIL_BtmAddNewAddress1.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    result = false;
                }
                if (!EditText_BtmAddNewAddress1.getText().toString().isEmpty() ) {
                    MTIL_BtmAddNewAddress1.setErrorEnabled(false);
                    result = true;
                }

                if(EditText_BtmAddNewAddress2.getText().toString().isEmpty())
                {
                    MTIL_BtmAddNewAddress2.setErrorEnabled(true);
                    MTIL_BtmAddNewAddress2.setError("Enter Address 2");
                    MTIL_BtmAddNewAddress2.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    result = false;
                }
                if (!EditText_BtmAddNewAddress2.getText().toString().isEmpty() ) {
                    MTIL_BtmAddNewAddress2.setErrorEnabled(false);
                    result = true;
                }

                if(EditText_BtmAddNewAddressPostal.getText().toString().isEmpty())
                {
                    MTIL_BtmAddNewAddressPostal.setErrorEnabled(true);
                    MTIL_BtmAddNewAddressPostal.setError("Enter Postal Code");
                    MTIL_BtmAddNewAddressPostal.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    result = false;
                }
                if (!EditText_BtmAddNewAddressPostal.getText().toString().isEmpty() ) {
                    MTIL_BtmAddNewAddressPostal.setErrorEnabled(false);
                    result = true;
                }

                if(EditText_BtmAddNewAddressCountry.getText().toString().isEmpty())
                {
                    MTIL_BtmAddNewAddressCountry.setErrorEnabled(true);
                    MTIL_BtmAddNewAddressCountry.setError("Select Country");
                    MTIL_BtmAddNewAddressCountry.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    result = false;
                }
                if (!EditText_BtmAddNewAddressCountry.getText().toString().isEmpty() ) {
                    MTIL_BtmAddNewAddressCountry.setErrorEnabled(false);
                    result = true;
                }

                if(EditText_BtmAddNewAddressCity.getText().toString().isEmpty())
                {
                    MTIL_BtmAddNewAddressCity.setErrorEnabled(true);
                    MTIL_BtmAddNewAddressCity.setError("Select City/Locality");
                    MTIL_BtmAddNewAddressCity.setErrorTextColor(ColorStateList.valueOf(Color.RED));
                    result = false;
                }
                if (!EditText_BtmAddNewAddressCity.getText().toString().isEmpty() ) {
                    MTIL_BtmAddNewAddressCity.setErrorEnabled(false);
                    result = true;
                }


                if(CaptureFront == true && CaptureBack == true)
                {
                    result = true;
                }
                if (result == true)
                {
                    Tv_Profile_Address.setText(EditText_BtmAddNewAddress1.getText().toString()+"\n"+EditText_BtmAddNewAddress2.getText().toString()+"\n"+EditText_BtmAddNewAddressPostal.getText().toString()+", "+EditText_BtmAddNewAddressCity.getText().toString()+",\n"+EditText_BtmAddNewAddressCountry.getText().toString());
                    Toast.makeText(getApplicationContext(), "Address Saved", Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                }
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, MoreActivity.class);
        startActivity(in);
        finish();
    }

    public void clickEditProfile(View view) {
        Intent in = new Intent(this, EditProfileActivity.class);
        startActivity(in);
        finish();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//






    public static void normalizeImageForUri(Context context, File file, Uri uri) {
        try {
            Log.d("TAG", "URI value = " + file.getAbsolutePath());
            ExifInterface exif = new ExifInterface(file.getAbsolutePath());
            Log.d("TAG", "Exif value = " + exif);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            Bitmap rotatedBitmap = rotateBitmap(bitmap, orientation);
            if (!bitmap.equals(rotatedBitmap)) {
                saveBitmapToFile(context, rotatedBitmap, uri);
            }

            Glide.with(context).load(rotatedBitmap).into(CircleImage_UserPic);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            case 0:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();

            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void saveBitmapToFile(Context context, Bitmap croppedImage, Uri saveUri) {
        if (saveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = context.getContentResolver().openOutputStream(saveUri);
                if (outputStream != null) {
                    croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException e) {

            } finally {
                closeSilently(outputStream);
                croppedImage.recycle();
            }
        }
    }

    private static void closeSilently(@Nullable Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }












}