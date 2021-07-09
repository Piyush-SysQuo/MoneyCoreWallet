package com.mpay.wallet.Utils;

import android.app.Activity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.mpay.wallet.R;

public class CalendarViewUtil {
    String SelectedDate = null ;
    public void CalendarBottomDialog(Activity activity, TextInputEditText editText) {

        try {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
            bottomSheetDialog.setContentView(R.layout.utils_calendarview);


            //        CALANDER

            CalendarView CalendarViewUtilDate = bottomSheetDialog.findViewById(R.id.CalendarViewUtilDate);
            TextView Tv_AcceptUtilDate = bottomSheetDialog.findViewById(R.id.Tv_AcceptUtilDate);
            bottomSheetDialog.show();

            Tv_AcceptUtilDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText(SelectedDate);
                    bottomSheetDialog.dismiss();
                }
            });


            CalendarViewUtilDate.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull android.widget.CalendarView calendarView, int i, int i1, int i2) {

                    String msg = "Selected date Day: " + i2 + " Month : " + (i1 + 1) + " Year " + i;
                    String Day = null;
                    String Month = null;
                    String Year = null;
                    if (i2 < 10) {
                        Day = "0" + String.valueOf(i2);
                    } else {
                        Day = String.valueOf(i2);
                    }
                    if ((i1 + 1) < 10) {
                        Month = "0" + String.valueOf(i1 + 1);
                    } else {
                        Month = String.valueOf(i1 + 1);
                    }
                    Year = String.valueOf(i);
                    SelectedDate = Day + "-" + Month + "-" + Year;

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
