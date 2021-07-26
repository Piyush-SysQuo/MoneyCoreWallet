package com.mpay.wallet.View.Activity.BioMetric;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.os.CancellationSignal;
import android.os.Handler;
import android.widget.Toast;

import com.mpay.wallet.R;
import com.mpay.wallet.Utils.Progress;
import com.mpay.wallet.View.Activity.Login.VIEW.LoginActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private CancellationSignal cancellationSignal;
    private Context appContext;
    Progress progress;
    public FingerprintHandler(Context context) {
        appContext = context;
    }

    /**
     * a method needs to be added which can be called to initiate the fingerprint authentication.
     * When called, this method will need to be passed the FingerprintManager and CryptoObject instances.
     * Name this method startAuth and implement it in the FingerprintHandler.java class file as follows
     * (note that code has also been added to once again check that fingerprint permission has been granted):
     * @param manager
     * @param cryptoObject
     */
    public void startAuth(FingerprintManager manager,
                          FingerprintManager.CryptoObject cryptoObject) {

        progress = new Progress(appContext);
        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    /**
     * add the callback handler methods, each of which is implemented to display a toast message
     * indicating the result of the fingerprint authentication:
     * @param errMsgId
     * @param errString
     */
    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        Toast.makeText(appContext,
                "Authentication error\n" + errString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(appContext,
                "Authentication help\n" + helpString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        progress.show();
        new Handler().postDelayed(() -> {
            progress.dismiss();
            LoginActivity.IV_login_Bio.setImageResource(R.drawable.biometric_failed);
        }, 2000);
        Toast.makeText(appContext,
                "Authentication failed.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {
        progress.show();
        new Handler().postDelayed(() -> {
            progress.dismiss();
            LoginActivity.IV_login_Bio.setImageResource(R.drawable.biometric_success);
        }, 2000);
        Toast.makeText(appContext,
                "Authentication succeeded.",
                Toast.LENGTH_LONG).show();
    }
}
