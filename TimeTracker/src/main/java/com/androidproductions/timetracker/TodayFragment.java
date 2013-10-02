package com.androidproductions.timetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidproductions.timetracker.data.ProjectWork;

import java.util.Map;

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
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.projectHolder);
        Map<ProjectWork, Double> works = ProjectHelper.getAllProjectWorks(today);
        for(ProjectWork key : works.keySet())
        {
            View row = inflater.inflate(R.layout.project_row,null,false);
            ((TextView)row.findViewById(R.id.projectLabel)).setText(key.toString());
            ((TextView)row.findViewById(R.id.projectHours)).setText(String.valueOf(works.get(key).doubleValue()));
            ll.addView(row);
        }
        return v;
    }
}
