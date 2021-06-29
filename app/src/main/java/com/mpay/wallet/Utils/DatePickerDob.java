package com.mpay.wallet.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.widget.RelativeLayout.LayoutParams;

import java.util.Calendar;


public class DatePickerDob extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Light_Dialog,(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);

        // Create a TextView programmatically.
        TextView tv = new TextView(getActivity());

        // Create a TextView programmatically
        LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setPadding(10, 10, 10, 10);

        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        tv.setText("Your Date of Birth");
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setBackgroundColor(Color.parseColor("#003b7d"));

        // Set the newly created TextView as a custom tile of DatePickerDialog
        datePickerDialog.setCustomTitle(tv);

        // Or you can simply set a tile for DatePickerDialog
            /*
                setTitle(CharSequence title)
                    Set the title text for this dialog's window.
            */
//        datePickerDialog.setTitle("Select your Date of Birth."); // Uncomment this line to activate it

        datePickerDialog.getDatePicker().setMaxDate(now-568080000000l);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return  datePickerDialog;
    }
}
