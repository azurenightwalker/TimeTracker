package com.androidproductions.timetracker;

import android.content.Context;
import android.database.Cursor;

import com.androidproductions.timetracker.com.androidproductions.timetracker.data.Day;
import com.androidproductions.timetracker.com.androidproductions.timetracker.data.TimesheetContract;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DayHelper {
    public static final Day findToday(Context context)
    {
        Day today = null;
        final Calendar cal = Calendar.getInstance(Locale.getDefault());
        Cursor c = context.getContentResolver().query(TimesheetContract.CONTENT_URI,null,
                TimesheetContract.Date + " = ?", new String[] {
                String.valueOf(new Date(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE))
                        .getTime())
        },null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                today = new Day(c);
            }
            c.close();
        }
        return today;
    }
}
