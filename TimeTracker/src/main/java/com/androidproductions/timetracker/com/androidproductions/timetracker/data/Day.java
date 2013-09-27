package com.androidproductions.timetracker.com.androidproductions.timetracker.data;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Day {
    private final Date Day;
    private final Date TimeIn;
    private Date TimeOut;
    private final JSONObject Projects;

    public ContentValues asContentValues()
    {
        ContentValues cv = new ContentValues();
        cv.put(TimesheetContract.Date,Day.getTime());
        cv.put(TimesheetContract.TimeIn,TimeIn.getTime());
        if (TimeOut != null)
            cv.put(TimesheetContract.TimeOut,TimeOut.getTime());
        cv.put(TimesheetContract.Projects,Projects.toString());
        return cv;
    }

    public Day(Cursor cursor)
    {
        JSONObject tempProj;
        Day = new Date(cursor.getLong(cursor.getColumnIndex(TimesheetContract.Date)));
        TimeIn = new Date(cursor.getLong(cursor.getColumnIndex(TimesheetContract.TimeIn)));
        TimeOut = new Date(cursor.getLong(cursor.getColumnIndex(TimesheetContract.TimeOut)));
        try {
            tempProj = new JSONObject(cursor.getString(cursor.getColumnIndex(TimesheetContract.TimeOut)));
        } catch (JSONException e) {
            e.printStackTrace();
            tempProj = new JSONObject();
        }
        Projects = tempProj;
    }

    public Day(Date timeIn)
    {
        final Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        Day = cal.getTime();
        TimeIn = timeIn;
        Projects = new JSONObject();
    }

    public void setProjectHours(String project, int hours)
    {
        try {
            Projects.put(project,hours);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getProjectHours(String project)
    {
        try {
            return Projects.getInt(project);
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
