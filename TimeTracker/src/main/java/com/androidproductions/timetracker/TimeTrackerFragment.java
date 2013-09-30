package com.androidproductions.timetracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidproductions.timetracker.com.androidproductions.timetracker.data.Day;
import com.androidproductions.timetracker.com.androidproductions.timetracker.data.TimesheetContract;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeTrackerFragment extends Fragment {
    protected Day today;

    public TimeTrackerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    protected void findToday()
    {
        today = null;
        final Calendar cal = Calendar.getInstance(Locale.getDefault());
        Cursor c = getActivity().getContentResolver().query(TimesheetContract.CONTENT_URI,null,
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
    }
}
