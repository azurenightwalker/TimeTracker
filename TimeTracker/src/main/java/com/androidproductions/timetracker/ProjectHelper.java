package com.androidproductions.timetracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.androidproductions.timetracker.data.Day;
import com.androidproductions.timetracker.data.Project;
import com.androidproductions.timetracker.data.ProjectWork;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class ProjectHelper {
    private static final List<Project> ProjectList = new ArrayList<Project>();
    private static final double Hour = 1000 * 60 * 60; // Milliseconds * Seconds * Minutes
    static {
        ProjectList.add(new Project("Portal"));
        ProjectList.add(new Project("NPEx"));
        ProjectList.add(new Project("QTool"));
    }

    public static List<Project> getProjectList() {
        // TODO: Fetch these using NetworkHelper
        return ProjectList;
    }

    public static ProjectWork getCurrentProject(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("TimeTrackerPreferences", Context.MODE_PRIVATE);
        return new ProjectWork(
                mPrefs.getString("Project",""),
                WorkType.parse(mPrefs.getInt("WorkType", 0))
        );
    }

    public static void setCurrentProject(Context context, ProjectWork projectWork) {
        ProjectWork old = getCurrentProject(context);
        SharedPreferences mPrefs = context.getSharedPreferences("TimeTrackerPreferences", Context.MODE_PRIVATE);
        Day today = DayHelper.findToday(context);
        today.addProjectHours(old, workOutHours(context));
        SharedPreferences.Editor edi = mPrefs.edit();
        edi.putString("Project",projectWork.getProjectName());
        edi.putInt("WorkType", projectWork.getWorkType().Value);
        edi.putLong("SwitchTime", Calendar.getInstance(Locale.getDefault()).getTime().getTime());
        edi.commit();
        context.getContentResolver().update(today.getUri(),today.asContentValues(),null,null);
    }

    public static void stopProject(Context context) {
        ProjectWork old = getCurrentProject(context);
        SharedPreferences mPrefs = context.getSharedPreferences("TimeTrackerPreferences", Context.MODE_PRIVATE);
        Day today = DayHelper.findToday(context);
        today.addProjectHours(old, workOutHours(context));
        SharedPreferences.Editor edi = mPrefs.edit();
        edi.putLong("SwitchTime", Calendar.getInstance(Locale.getDefault()).getTime().getTime());
        edi.commit();
        context.getContentResolver().update(today.getUri(),today.asContentValues(),null,null);
    }

    private static double workOutHours(Context context)
    {
        SharedPreferences mPrefs = context.getSharedPreferences("TimeTrackerPreferences", Context.MODE_PRIVATE);
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        Long millis = cal.getTime().getTime() - mPrefs.getLong("SwitchTime",0);
        BigDecimal bd = new BigDecimal(Double.toString(millis / Hour));
        bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static Map<ProjectWork,Double> getAllProjectWorks(Day day)
    {
        Map<ProjectWork,Double> res = new LinkedHashMap<ProjectWork, Double>();
        for(Project pro : getProjectList())
        {
            for(WorkType wt : pro.getWorkTypes())
            {
                ProjectWork pw = new ProjectWork(pro,wt);
                double hours = day.getProjectHours(pw.toString());
                if (hours > 0)
                    res.put(pw,hours);
            }
        }

        return res;
    }
}
