package com.androidproductions.timetracker;

import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by scott.jones on 27/09/13.
 */
public final class TimeHelper {
    public static final SimpleDateFormat DateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());
    public static final SimpleDateFormat DateTimeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm",Locale.getDefault());
    public static final SimpleDateFormat TimeFormat24Hr = new SimpleDateFormat("HH:mm",Locale.getDefault());
    public static final SimpleDateFormat TimeFormat12Hr = new SimpleDateFormat("hh:mm a",Locale.getDefault());

    public static Date getDate(final TimePicker timePicker)
    {
        final Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        return cal.getTime();
    }
}
