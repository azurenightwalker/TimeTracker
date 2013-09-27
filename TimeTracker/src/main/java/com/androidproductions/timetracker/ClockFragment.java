package com.androidproductions.timetracker;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidproductions.timetracker.com.androidproductions.timetracker.data.Day;
import com.androidproductions.timetracker.com.androidproductions.timetracker.data.TimesheetContract;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ClockFragment extends Fragment {
    private final Boolean mIsIn;

    public ClockFragment(Boolean isIn) {
        mIsIn = isIn;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(mIsIn ? R.layout.clock_in : R.layout.clock_out, container, false);
    }

    public void clock(final View v)
    {
        final TimePicker timePicker = (TimePicker) getActivity().findViewById(R.id.time);
        final Date date = TimeHelper.getDate(timePicker);
        Toast.makeText(getActivity(),TimeHelper.TimeFormat12Hr.format(date), Toast.LENGTH_SHORT).show();
        if (mIsIn)
        {
            Day day = new Day(date);
            getActivity().getContentResolver().insert(TimesheetContract.CONTENT_URI,day.asContentValues());
        }
    }
}
