package com.androidproductions.timetracker;

import android.content.Context;
import android.database.Cursor;

import com.androidproductions.timetracker.data.Day;
import com.androidproductions.timetracker.data.TimesheetContract;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DayHelper {
    public static Day findToday(Context context)
    {
        final Calendar cal = Calendar.getInstance(Locale.getDefault());
        return findDay(context,new Date(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE)));
    }

    public static Day findDay(Context context, Date date)
    {
        Day day = null;
        Cursor c = context.getContentResolver().query(TimesheetContract.CONTENT_URI,null,
                TimesheetContract.Date + " = ?", new String[] {
                String.valueOf(date.getTime())
        },null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                day = new Day(c);
            }
            c.close();
        }
        return day;
    }
}
