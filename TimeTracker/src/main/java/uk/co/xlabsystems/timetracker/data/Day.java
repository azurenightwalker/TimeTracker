package uk.co.xlabsystems.timetracker.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import uk.co.xlabsystems.timetracker.network.NetworkHelper;

public class Day {
    private final long Id;
    private final Date Day;
    private final Date TimeIn;
    private Date TimeOut;
    private final JSONObject Projects;
    private double holidayHours;
    private double lunchHours;
    private double trainingHours;

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
        Id = cursor.getLong(cursor.getColumnIndex(TimesheetContract._ID));
        Day = new Date(cursor.getLong(cursor.getColumnIndex(TimesheetContract.Date)));
        TimeIn = new Date(cursor.getLong(cursor.getColumnIndex(TimesheetContract.TimeIn)));
        TimeOut = new Date(cursor.getLong(cursor.getColumnIndex(TimesheetContract.TimeOut)));
        try {
            tempProj = new JSONObject(cursor.getString(cursor.getColumnIndex(TimesheetContract.Projects)));
        } catch (JSONException e) {
            e.printStackTrace();
            tempProj = new JSONObject();
        }
        Projects = tempProj;
    }

    public Day(Date timeIn)
    {
        final Calendar cal = Calendar.getInstance(Locale.getDefault());
        Day = new Date(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
        TimeIn = timeIn;
        Projects = new JSONObject();
        Id = -1L;
    }

    public void addProjectHours(ProjectWork project, double hours)
    {
        try {
            Projects.put(project.toString(),getProjectHours(project.toString())+hours);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public double getProjectHours(String project)
    {
        try {
            return Projects.getDouble(project);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Date getTimeOut()
    {
        return TimeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.TimeOut = timeOut;
    }

    public Uri getUri()
    {
        return ContentUris.withAppendedId(TimesheetContract.CONTENT_URI,Id);
    }

    public Date getTimeIn() {
        return TimeIn;
    }

    public void pushData() {
        JSONObject data = new JSONObject();
        try {
            data.put("TimeIn", getTimeIn());
            data.put("TimeOut", getTimeOut());
            data.put("Holiday", getHolidayHours());
            data.put("Lunch", getLunchHours());
            data.put("Training", getTrainingHours());
            data.put("Work", Projects);
            NetworkHelper.getInstance().Post(data,"Work");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public double getHolidayHours() {
        return holidayHours;
    }

    public double getLunchHours() {
        return lunchHours;
    }

    public double getTrainingHours() {
        return trainingHours;
    }
}
