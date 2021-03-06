package com.mpay.wallet.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

public class SharedPreferenceAmount {

    private static SharedPreferenceAmount sharedPreferenceAmount = null;
    private SharedPreferences fSharedPreferences;
    private Context fContext;

    private SharedPreferenceAmount(@NotNull Context ctxt) {
        fContext = ctxt;
        fSharedPreferences = ctxt.getSharedPreferences(Constants.PrefrenceAmount, Context.MODE_PRIVATE);
    }

    /**
     * Get singleton instance of this class
     *
     * @param ctxt
     * @return
     */
    public static SharedPreferenceAmount getInstance(Context ctxt) {
        if (null == sharedPreferenceAmount) {

            sharedPreferenceAmount = new SharedPreferenceAmount(ctxt);
        }
        return sharedPreferenceAmount;
    }

    /**
     * Serialize the given object and stores in shared preference
     *
     * @return
     */


    /**
     * Set string values in preference
     *
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        SharedPreferences.Editor editor = fSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Set string Login type values in preference
     *
     * @param key
     * @param value
     */
    public void setString_TotAmount(String key, String value) {
        SharedPreferences.Editor editor = fSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void setString_SendAmount(String key, String value) {
        SharedPreferences.Editor editor = fSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void setString_ReceivedAmount(String key, String value) {
        SharedPreferences.Editor editor = fSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void setString_Mail(String key, String value) {
        SharedPreferences.Editor editor = fSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Get string value from preference
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return fSharedPreferences.getString(key, null);
    }

    /**
     * Get string Login Type value from preference
     *
     * @param key
     * @return
     */
    public String getString_TotAmount(String key) {
        return fSharedPreferences.getString(key, null);
    }
    public String getString_SendAmount(String key) {
        return fSharedPreferences.getString(key, null);
    }
    public String getString_ReceivedAmount(String key) {
        return fSharedPreferences.getString(key, null);
    }
    public String getString_Mail(String key) {
        return fSharedPreferences.getString(key, null);
    }

    /**
     * Removes the string value from preference
     *
     * @param key
     */
    public void removePrefs(String key) {
        SharedPreferences.Editor editor = fSharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * Deletes the shared preference data and closes the app
     *
     * @param context
     * @return
     */
    public static boolean logout(Context context) {
        SharedPreferenceAmount.getInstance(context).removePrefs(Constants.PREFS_AMOUNT);
        return true;
    }


}
