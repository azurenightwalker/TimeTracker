package com.androidproductions.timetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TodayFragment extends TimeTrackerFragment {

    public TodayFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        findToday();
        View v = inflater.inflate(R.layout.today, container, false);
        ((TextView)v.findViewById(R.id.clockInText)).setText(
                today == null ? "Not clocked in" : TimeHelper.TimeFormat12Hr.format(today.getTimeIn())
        );
        ((TextView)v.findViewById(R.id.clockOutText)).setText(
                today == null || today.getTimeOut().getTime() == 0 ? "Not clocked out" : TimeHelper.TimeFormat12Hr.format(today.getTimeOut())
        );
        return v;
    }
}
