package com.example.messenger.utils;
import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.util.Log;

public class TimeUtils {
    @TargetApi(Build.VERSION_CODES.N)
    public static String getRelativeTimeSpanString(double date) {
        double currentTime = Calendar.getInstance().getTimeInMillis();
        double different = currentTime - date;

        double secondsInMilli = 1000;
        double minutesInMilli = secondsInMilli * 60;
        double hoursInMilli = minutesInMilli * 60;
        double daysInMilli = hoursInMilli * 24;

        double elapsedDays = different / daysInMilli;

        SimpleDateFormat df = new SimpleDateFormat("d/MM/yy HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        if (elapsedDays < 1) {
            SimpleDateFormat  sameDayDateFormat = new SimpleDateFormat("HH:mm");
            sameDayDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            return sameDayDateFormat.format(date);
        }
        if (elapsedDays <= 7 && elapsedDays >= 1) {
            SimpleDateFormat sameWeekDateFormat = new SimpleDateFormat("EEEE HH:mm");
            sameWeekDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            return sameWeekDateFormat.format(date);
        }

        if (elapsedDays > 7) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
            if (dateFormat.format(currentTime).equals(dateFormat.format(date))) {
                SimpleDateFormat sameYearDateFormat = new SimpleDateFormat("d/MM HH:mm");
                sameYearDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
                return sameYearDateFormat.format(date);
            }
            else {
                SimpleDateFormat diffYearDateFormat = new SimpleDateFormat("d/MM/yy HH:mm");
                diffYearDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
                return diffYearDateFormat.format(date);
            }
        }
        return df.format(date);
    }

    public static boolean CompareDate(double data1, double data2, int mins){
        Log.d("data1", String.valueOf(data1));
        Log.d("data2", String.valueOf(data2));
        Log.d("data2 - data1", String.valueOf(data2-data1));
        double millis = mins * 60 * 1000;
        if (data2 - data1 > millis){
            return true;
        }
        return false;
    }
}
