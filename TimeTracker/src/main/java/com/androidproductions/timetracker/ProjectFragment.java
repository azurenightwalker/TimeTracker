package com.androidproductions.timetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidproductions.timetracker.com.androidproductions.timetracker.data.Day;
import com.androidproductions.timetracker.com.androidproductions.timetracker.data.TimesheetContract;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProjectFragment extends TimeTrackerFragment {

    public ProjectFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        findToday();
        View v = inflater.inflate(R.layout.project, container, false);
        ((Spinner)v.findViewById(R.id.project)).setAdapter(new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                new String[] {
                        "Portal",
                        "NPEx",
                        "QTool"
                }
        ));
        v.findViewById(R.id.save).setEnabled(false);
        return v;
    }

    public void switchProject(View view)
    {

    }
}
