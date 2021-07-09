package com.mpay.wallet.View.Activity.EditProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mpay.wallet.R;
import com.mpay.wallet.Utils.DatePickerDob;
import com.mpay.wallet.Utils.RotateImage;
import com.mpay.wallet.Utils.Utility;
import com.mpay.wallet.View.Activity.ChangeEmailID.ChangeEmailIDActivity;
import com.mpay.wallet.View.Activity.MyProfile.MyProfileActivity;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    TextInputLayout MTIL_EditProfile_FirstNameLayout, MTIL_EditProfile_MiddleNameLayout,
                    MTIL_EditProfile_LastNameLayout, MTIL_EditProfile_mobileNumberLayout,
                    MTIL_EditProfile_GenderLayout, MTIL_EditProfile_DOBLayout, MTIL_EditProfile_idTypeLayout_Menu,
                    MTIL_EditProfile_idNumberLayout;

    TextInputEditText EditText_EditProfile_FirstName, EditText_EditProfile_MiddleName,
                        EditText_EditProfile_LastName, EditText_EditProfile_MobileNumber, EditText_EditProfile_Gender,
                        EditText_EditProfile_DOB, EditText_EditProfile_ID_TYpe_Menu, EditText_EditProfile_ID_Number;

    CountryCodePicker ccp;

    ImageView IV_EditProfile_frontImg, IV_EditProfile_frontImg_Cross, IV_EditProfile_backImg, IV_EditProfile_backImg_Cross;

    LinearLayout Layout_EditProfile_AddNewID, Layout_EditProfile_AddNewAddress;

    Calendar Calendar_DOB;
    TextView Tv_EditProfile_Address;
    boolean isValid = true;

    /**
     *  NEW ID
     */
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

    TextView Tv_Profile_IDNO, Tv_Profile_IDPicHead, Tv_Profile_Address;
    private String imageIndex="userPic";
    private DialogInterface dialog;
    public File userImageFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Initialization();
    }
    public void Initialization()
    {
        MTIL_EditProfile_FirstNameLayout    = findViewById(R.id.MTIL_EditProfile_FirstNameLayout);
        MTIL_EditProfile_MiddleNameLayout   = findViewById(R.id.MTIL_EditProfile_MiddleNameLayout);
        MTIL_EditProfile_LastNameLayout     = findViewById(R.id.MTIL_EditProfile_LastNameLayout);
        MTIL_EditProfile_mobileNumberLayout = findViewById(R.id.MTIL_EditProfile_mobileNumberLayout);
        MTIL_EditProfile_GenderLayout       = findViewById(R.id.MTIL_EditProfile_GenderLayout);
        MTIL_EditProfile_DOBLayout          = findViewById(R.id.MTIL_EditProfile_DOBLayout);
        MTIL_EditProfile_DOBLayout.setOnClickListener(this);
        MTIL_EditProfile_idTypeLayout_Menu  = findViewById(R.id.MTIL_EditProfile_idTypeLayout_Menu);
        MTIL_EditProfile_idNumberLayout     = findViewById(R.id.MTIL_EditProfile_idNumberLayout);


        EditText_EditProfile_FirstName      = findViewById(R.id.EditText_EditProfile_FirstName);
        EditText_EditProfile_MiddleName     = findViewById(R.id.EditText_EditProfile_MiddleName);
        EditText_EditProfile_LastName       = findViewById(R.id.EditText_EditProfile_LastName);
        EditText_EditProfile_MobileNumber   = findViewById(R.id.EditText_EditProfile_MobileNumber);
        EditText_EditProfile_Gender         = findViewById(R.id.EditText_EditProfile_Gender);
        EditText_EditProfile_DOB            = findViewById(R.id.EditText_EditProfile_DOB);
        EditText_EditProfile_DOB.setOnClickListener(this);
        EditText_EditProfile_ID_TYpe_Menu   = findViewById(R.id.EditText_EditProfile_ID_TYpe_Menu);
        EditText_EditProfile_ID_Number      = findViewById(R.id.EditText_EditProfile_ID_Number);


        ccp      = findViewById(R.id.ccp);

        Tv_EditProfile_Address      = findViewById(R.id.Tv_EditProfile_Address);

        IV_EditProfile_frontImg      = findViewById(R.id.IV_EditProfile_frontImg);
        IV_EditProfile_frontImg_Cross      = findViewById(R.id.IV_EditProfile_frontImg_Cross);
        IV_EditProfile_backImg      = findViewById(R.id.IV_EditProfile_backImg);
        IV_EditProfile_backImg_Cross      = findViewById(R.id.IV_EditProfile_backImg_Cross);

        Layout_EditProfile_AddNewID      = findViewById(R.id.Layout_EditProfile_AddNewID);
        Layout_EditProfile_AddNewID.setOnClickListener(this);
        Layout_EditProfile_AddNewAddress      = findViewById(R.id.Layout_EditProfile_AddNewAddress);
        Layout_EditProfile_AddNewAddress.setOnClickListener(this);
    }
    public void backPressed(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, MyProfileActivity.class);
        startActivity(in);
        finish();
    }

    public void saveClick(View view) {
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.EditText_EditProfile_DOB)
        {
            DialogFragment datePicker = new DatePickerDob();
            datePicker.show(getSupportFragmentManager(), "date picker");
        }
        if(v.getId() == R.id.Layout_EditProfile_AddNewID)
        {
            showBottomSheetDialog_ToAddNewID();
        }
        if(v.getId() == R.id.Layout_EditProfile_AddNewAddress)
        {
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
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar userAge = new GregorianCalendar(year,month,dayOfMonth);
        Calendar minAdultAge = new GregorianCalendar();
        minAdultAge.add(Calendar.YEAR, -18);
        if (minAdultAge.before(userAge)) {
            isValid = false;
        }else{
            isValid = true;
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        EditText_EditProfile_DOB.setText(currentDateString);

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
                selectImage(EditProfileActivity.this);
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
                            if (imageIndex.equals("frontImage")) {
                                frontImageFile = Utility.getFileWithName(this, selectedImage, "FrontImage.jpeg");

                                long fileSizeKB = frontImageFile.length() / 1024;    // KB
                                long fileSizeMB = fileSizeKB / 1024;    // MB
                                int intKB = (int) fileSizeKB;
                                int intMB = (int) fileSizeMB;

                                IV_EditProfile_frontImg.setImageBitmap(selectedImage);
                                if (intMB <= 0) {
                                    TV_BtmAddNewID_Sz_No.setText(String.valueOf(intKB) + " KB");
                                    ProgressImagesizeBtmAddNewID.setMax(1024);
                                    ProgressImagesizeBtmAddNewID.setProgress(intKB);
                                } else {
                                    TV_BtmAddNewID_Sz_No.setText(String.valueOf(intMB) + " MB");
                                    ProgressImagesizeBtmAddNewID.setMax(12);
                                    ProgressImagesizeBtmAddNewID.setProgress(intMB);
                                }

                                IV_EditProfile_frontImg.setVisibility(View.VISIBLE);
                                IV_EditProfile_frontImg.setPadding(0,0,0,0);
                                IV_EditProfile_frontImg.setClickable(false);
                                imageIndex = "frontImage";
                                CaptureFront = true;
                            }
                            else if (imageIndex.equals("backImage"))
                            {
                                backImageFile = Utility.getFileWithName(this, selectedImage,"BackImage.jpeg");
                                IV_EditProfile_backImg.setVisibility(View.VISIBLE);
                                IV_EditProfile_backImg.setPadding(0,0,0,0);
                                IV_EditProfile_backImg.setClickable(false);
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
                            if (imageIndex.equals("frontImage"))
                            {
                                frontImageFile = Utility.getFileWithName(this, bitmap,"FrontImage.jpeg");

                                long fileSizeKB = frontImageFile.length()/1024;    // KB
                                long fileSizeMB = fileSizeKB/1024;    // MB
                                int intKB = (int) fileSizeKB;
                                int intMB = (int) fileSizeMB;

                                IV_EditProfile_frontImg.setImageBitmap(bitmap);
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

                                IV_EditProfile_frontImg.setVisibility(View.VISIBLE);
                                IV_EditProfile_frontImg.setPadding(0,0,0,0);
                                IV_EditProfile_frontImg.setClickable(false);
                                imageIndex = "frontImage";
                                CaptureFront = true;
                            }
                            else if (imageIndex.equals("backImage"))
                            {
                                backImageFile = Utility.getFileWithName(this, bitmap,"BackImage.jpeg");
                                IV_EditProfile_backImg.setVisibility(View.VISIBLE);
                                IV_EditProfile_backImg.setImageBitmap(bitmap);
                                IV_EditProfile_backImg.setPadding(0,0,0,0);
                                IV_EditProfile_backImg.setClickable(false);
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
                    Toast.makeText(EditProfileActivity.this, "ID Front Side Image", Toast.LENGTH_SHORT).show();
                    result = false;
                }
                if(CaptureBack == false){
                    Toast.makeText(EditProfileActivity.this, "ID Back Side Image", Toast.LENGTH_SHORT).show();
                    result = false;
                }
                if(CaptureFront == true && CaptureBack == true)
                {
                    result = true;
                }
                if (result == true)
                {
                    EditText_EditProfile_ID_Number.setText(EditText_BtmAddNewIDNo.getText().toString());
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
                    Tv_EditProfile_Address.setText(EditText_BtmAddNewAddress1.getText().toString()+"\n"+EditText_BtmAddNewAddress2.getText().toString()+"\n"+EditText_BtmAddNewAddressPostal.getText().toString()+", "+EditText_BtmAddNewAddressCity.getText().toString()+",\n"+EditText_BtmAddNewAddressCountry.getText().toString());
                    Toast.makeText(getApplicationContext(), "Address Saved", Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                }
            }
        });
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//

}