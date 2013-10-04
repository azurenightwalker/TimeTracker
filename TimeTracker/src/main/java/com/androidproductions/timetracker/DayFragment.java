package com.androidproductions.timetracker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidproductions.timetracker.data.ProjectWork;

import java.util.Map;

public class DayFragment extends TimeTrackerFragment {

    private LinearLayout projects;
    private TextView clockIn;
    private TextView clockOut;

    public DayFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        findToday();
        View v = inflater.inflate(R.layout.day, container, false);
        clockIn = (TextView)v.findViewById(R.id.clockInText);

        clockOut = (TextView)v.findViewById(R.id.clockOutText);

        projects = (LinearLayout) v.findViewById(R.id.projectHolder);
        if (today != null)
        {
            Map<ProjectWork, Double> works = ProjectHelper.getAllProjectWorks(today);
            for(ProjectWork key : works.keySet())
            {
                View row = inflater.inflate(R.layout.project_row,null,false);
                ((TextView)row.findViewById(R.id.projectLabel)).setText(key.toString());
                ((TextView)row.findViewById(R.id.projectHours)).setText(String.valueOf(works.get(key).doubleValue()));
                projects.addView(row);
            }
        }
        return v;
    }

    public void updateDay()
    {
        clockIn.setText(
                today == null ? "Not clocked in" : TimeHelper.TimeFormat12Hr.format(today.getTimeIn())
        );
        clockOut.setText(
                today == null || today.getTimeOut().getTime() == 0 ? "Not clocked out" : TimeHelper.TimeFormat12Hr.format(today.getTimeOut())
        );
        projects.removeAllViews();
        if (today != null)
        {
            LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            Map<ProjectWork, Double> works = ProjectHelper.getAllProjectWorks(today);
            for(ProjectWork key : works.keySet())
            {
                View row = inflater.inflate(R.layout.project_row,null,false);
                ((TextView)row.findViewById(R.id.projectLabel)).setText(key.toString());
                ((TextView)row.findViewById(R.id.projectHours)).setText(String.valueOf(works.get(key).doubleValue()));
                projects.addView(row);
            }
        }
    }
}
