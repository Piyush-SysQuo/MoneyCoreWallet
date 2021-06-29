package com.mpay.wallet.View.Activity.BioMetric;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mpay.wallet.R;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class BiometricActivity extends AppCompatActivity {

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

    private boolean KeyGurdSecurityAuth = false;
    private boolean FingerPrintPermissionAuth = false;
    private boolean FingerPrintEnrolledAuth = false;
    private static final String KEY_NAME = "mPayBio_Key";

    public static ImageView Img_Biometric;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);

        Img_Biometric = findViewById(R.id.Img_Biometric);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
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
        }
    }

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
}