package com.mpay.wallet.View.Activity.Login;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

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
import com.mpay.wallet.Utils.Progress;
import com.mpay.wallet.Utils.SharedPreferenceAmount;
import com.mpay.wallet.Utils.Utility;
import com.mpay.wallet.Utils.Validation;
import com.mpay.wallet.View.Activity.BioMetric.FingerprintHandler;
import com.mpay.wallet.View.Activity.ForgotPassword.ForgotPasswordActivity;
import com.mpay.wallet.View.Activity.Home.HomeActivity;
import com.mpay.wallet.View.Activity.Login.model.LoginModel;
import com.mpay.wallet.View.Activity.Login.viewmodel.LoginViewModel;
import com.mpay.wallet.View.Activity.Signup.SignupActivity;
import com.mpay.wallet.View.Activity.Signup.SignupSecondActivity;
import com.mpay.wallet.View.Activity.Signup.viewmodel.SignUpViewModel;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class LoginActivity extends AppCompatActivity {

    public
    TextInputEditText Login_EditText_Email;
    public
    TextInputEditText Login_EditText_Password;
    TextInputLayout MTIL_Login_emailLayout;
    TextInputLayout MTIL_Login_passwordLayout;

    /*@BindView(R.id.img_lg)
    ImageView imageView;*/

    private Progress progress;
    public static  ImageView IV_login_Bio;
    private String language;
    private SharedPreferences sharedPreferences;
    /**
     * Accessing KeyGurd and FingerPrint manager
     */
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private Cipher cipher;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintHandler helper;
    TextView TV_Login_select_Lng;

    private boolean KeyGurdSecurityAuth = false;
    private boolean FingerPrintPermissionAuth = false;
    private boolean FingerPrintEnrolledAuth = false;
    private static final String KEY_NAME = "mPayBio_Key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get data from shared preferences
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_LANGUAGE,MODE_PRIVATE);
        language = sharedPreferences.getString(Constants.KEY_LANGUAGE,"");
        Utility.setLocale(this,language);

        setContentView(R.layout.activity_login);

        TV_Login_select_Lng         = findViewById(R.id.TV_Login_select_Lng);
        IV_login_Bio                = findViewById(R.id.IV_login_Bio);
        Login_EditText_Email        = findViewById(R.id.Login_EditText_Email);
        Login_EditText_Password     = findViewById(R.id.Login_EditText_Password);
        MTIL_Login_emailLayout      = findViewById(R.id.MTIL_Login_emailLayout);
        MTIL_Login_passwordLayout   = findViewById(R.id.MTIL_Login_passwordLayout);
        // initialize progress dialog instance
        progress = new Progress(this);
        (Objects.requireNonNull(progress.getWindow())).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);

        language = "en";

//        FingerPrint Hardwae Checking
        FingerPrint_Hardwae_Checking();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void FingerPrint_Hardwae_Checking()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Fingerprint API only available on from Android 6.0 (M)
            fingerprintManager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
            if (!fingerprintManager.isHardwareDetected())
            {
                // Device doesn't support fingerprint authentication
                IV_login_Bio.setVisibility(View.GONE);
            }
            else if (!fingerprintManager.hasEnrolledFingerprints())
            {
                // User hasn't enrolled any fingerprints to authenticate with
                /**
                 * TODO
                 * Register user Finger Print here
                 */
                IV_login_Bio.setVisibility(View.GONE);
            }
            else
            {
                // Everything is ready for fingerprint authentication
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    keyguardManager     =   (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                    fingerprintManager  =   (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
                    KeyGuardSecurity(KeyGurdSecurityAuth);
                    FingerPrintPermission(FingerPrintPermissionAuth);
                    FingerPrintEnrolled(FingerPrintEnrolledAuth);

                    generateKey();

                    if (cipherInit()) {
                        cryptoObject = new FingerprintManager.CryptoObject(cipher);
                        helper = new FingerprintHandler(this);
                        helper.startAuth(fingerprintManager, cryptoObject);
                    }
                }*/
            }
        }
        else
        {
            IV_login_Bio.setVisibility(View.GONE);
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void attemptLogin(View view) {
        Validation validation = new Validation(this);
        if(validation.ValidateEmail(this))
        {
            try {
                File f = new File(
                        "/data/data/com.mpay.wallet/shared_prefs/mpaywallet.app.amt.xml");
                if (f.exists())
                {

                }
                else {
                    SharedPreferenceAmount.getInstance(getApplicationContext()).setString(Constants.LOGIN, "true");
                    SharedPreferenceAmount.getInstance(getApplicationContext()).setString_TotAmount(Constants.TOTAL_AMOUNT, "2500.00");
                    SharedPreferenceAmount.getInstance(getApplicationContext()).setString_SendAmount(Constants.SPENT_AMOUNT, "00.00");
                    SharedPreferenceAmount.getInstance(getApplicationContext()).setString_ReceivedAmount(Constants.RECIVED_AMOUNT, "00.00");
                    SharedPreferenceAmount.getInstance(getApplicationContext()).setString_Mail(Constants.EMAIL, Login_EditText_Email.getText().toString());
                }
                /*if(SharedPreferenceAmount.getInstance(getApplicationContext()).getString(Constants.LOGIN).equals("true")) {

                }
                else if (SharedPreferenceAmount.getInstance(getApplicationContext()))
                else{

                }*/
            }catch (Exception e){
                e.printStackTrace();
            }


            progress.show();
            // call sign up api
            LoginViewModel viewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);
            viewModel.init();
            LoginModel loginModel=new LoginModel(Login_EditText_Email.getText().toString(),Login_EditText_Password.getText().toString());

            viewModel.Login(loginModel);
            viewModel.getVolumesResponseLiveData().observe(LoginActivity.this, response -> {

                progress.hide();

                if(response!=null){
                    boolean status = response.isStatus();
                    if(status){

                        SharedPreferenceAmount.getInstance(getApplicationContext()).setString_Mail(Constants.EMAIL, Login_EditText_Email.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra(Constants.KEY_LANGUAGE,language);
                        startActivity(intent);
                        finish();
                    }else{
                        openDialog(response.getMessage());
                    }
                }
                else{
                    openDialog("Something went wrong");
                }
            });
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void forgotPassword(View view) {
        Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        intent.putExtra(Constants.KEY_LANGUAGE,language);
        startActivity(intent);
        finish();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void signUpClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        intent.putExtra(Constants.KEY_LANGUAGE,language);
        startActivity(intent);
        finish();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void backPressed(View view) {
        finish();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    private void openDialog(String msg){
        AlertPopup mAlert = new AlertPopup(this);
        mAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlert.setMessage(msg);
        mAlert.setOkButton( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlert.dismiss();
                //Do want you want
            }
        });
        mAlert.show();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void BioMateric(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Fingerprint API only available on from Android 6.0 (M)
            fingerprintManager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
            if (fingerprintManager.hasEnrolledFingerprints()) {
//                startActivity(new Intent(getApplicationContext(), BiometricActivity.class));
                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Verification")
                        .setSubtitle("Login Authentication!!!")
                        .setDescription("Lift and rest your finger anywhere on the screen repeatedly.")
                        .setNegativeButtonText("CANCEL")
                        .build();
                getPrompt().authenticate(promptInfo);
            }
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    @Override
    public void onBackPressed() {
        finish();
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public void changeLanguage(View view) {
        showPopupMenu(view, true, R.style.MyPopupOtherStyle);
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     * Finger Print
     * @return
     */
    private BiometricPrompt getPrompt(){
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        };
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, callback);
        return biometricPrompt;
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     * Checking the Security Settings
     */
    private boolean KeyGuardSecurity(boolean KeyGurdAuth)
    {
        if (!keyguardManager.isKeyguardSecure()) {
            Toast.makeText(this, "Lock screen security not enabled in Settings", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            KeyGurdSecurityAuth = true;
            return true;
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     *  FingerPrint Permission Check
     */
    private boolean FingerPrintPermission( boolean FingerPrintAuth)
    {
        Dexter.withContext(this).withPermission(Manifest.permission.USE_FINGERPRINT).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
        Dexter.withContext(this).withPermission(Manifest.permission.USE_BIOMETRIC).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Fingerprint authentication permission not enabled", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            FingerPrintPermissionAuth = true;
            return true;
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     *  FingerPrint Permission Check
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean FingerPrintEnrolled(boolean EnrolledAuth)
    {
        if (!fingerprintManager.hasEnrolledFingerprints()) {

            // This happens when no fingerprints are registered.
            Toast.makeText(this, "Register at least one fingerprint in Settings", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            FingerPrintEnrolledAuth = true;
            return true;
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     * Key Store
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * An instance of the KeyGenerator
         */
        try {
            keyGenerator = KeyGenerator.getInstance( KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            throw new RuntimeException(
                    "Failed to get KeyGenerator instance", e);
        }
        /**
         *  Key generator
         */
        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    /**
     * Initializing the Cipher
     * Now that the key has been generated the next step is to initialize the cipher that will be
     * used to create the encrypted FingerprintManager.CryptoObject instance.
     * This CryptoObject will, in turn, be used during the fingerprint authentication process.
     * Cipher configuration involves obtaining a Cipher instance and initializing it with the key stored in the Keystore container.
     * Add a new method named cipherInit to the FingerprintDemoActivity.java file to perform these tasks:
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
    /**
     * Creating the CryptoObject Instance
     */
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
        popup.getMenuInflater().inflate(R.menu.lamguage_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if(menuItem.getTitle().equals("English")) {
                    TV_Login_select_Lng.setText("EN");
                    setLocale("er");
                    SharedPreferenceAmount.getInstance(getApplicationContext()).setString_Language(Constants.KEY_LANGUAGE, "er");
                }
                if(menuItem.getTitle().equals("French")) {
                    setLocale("fr");
                    SharedPreferenceAmount.getInstance(getApplicationContext()).setString_Language(Constants.KEY_LANGUAGE, "fr");
                    TV_Login_select_Lng.setText("FR");
                }
                return true;
            }
        });
        popup.show();

    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
    public  void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
//------------------------------------------------------------------------------------------------\\
//------------------------------------------------------------------------------------------------//
}