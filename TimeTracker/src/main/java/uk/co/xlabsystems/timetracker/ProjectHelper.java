package uk.co.xlabsystems.timetracker;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.xlabsystems.timetracker.data.Day;
import uk.co.xlabsystems.timetracker.data.Project;
import uk.co.xlabsystems.timetracker.data.ProjectWork;
import uk.co.xlabsystems.timetracker.network.NetworkHelper;

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

    public static List<Project> getProjectList() {
        // TODO: Fetch these using NetworkHelper
        //
        JSONArray project = NetworkHelper.getInstance().GetArray("Projects");
        if (project != null)
        {
            try {
                ProjectList.clear();

                for(int i=0; i<project.length(); i++)
                {
                    JSONObject temp = project.getJSONObject(i);

                    ProjectList.add(new Project(
                            temp.getString("Name"),
                            temp.getBoolean("HasDev"),
                            temp.getBoolean("HasSupport"),
                            temp.getBoolean("HasResearch"),
                            temp.getBoolean("HasSales")
                    ));
                }
            }
            catch(JSONException e){
                //ProjectList.add(new Project("No Projects"));
            }
        }
        else if (ProjectList.size() == 0)
        {
            ProjectList.add(new Project("No Projects", false, false, false,false));
        }

        return ProjectList;
    }

    public static ProjectWork getCurrentProject(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("TimeTrackerPreferences", Context.MODE_PRIVATE);
        return new ProjectWork(
                mPrefs.getString("Project",""),
                WorkType.parse(mPrefs.getInt("WorkType", 0))
        );
    }

    public static void setCurrentProject(Context context, ProjectWork projectWork,Calendar time) {
        ProjectWork old = getCurrentProject(context);
        SharedPreferences mPrefs = context.getSharedPreferences("TimeTrackerPreferences", Context.MODE_PRIVATE);
        Day today = DayHelper.findToday(context);
        today.addProjectHours(old, workOutHours(context,time));
        SharedPreferences.Editor edi = mPrefs.edit();
        edi.putString("Project",projectWork.getProjectName());
        edi.putInt("WorkType", projectWork.getWorkType().Value);
        edi.putLong("SwitchTime", time.getTime().getTime());
        edi.commit();
        context.getContentResolver().update(today.getUri(),today.asContentValues(),null,null);
    }

    public static void stopProject(Context context, Calendar time) {
        ProjectWork old = getCurrentProject(context);
        SharedPreferences mPrefs = context.getSharedPreferences("TimeTrackerPreferences", Context.MODE_PRIVATE);
        Day today = DayHelper.findToday(context);
        today.addProjectHours(old, workOutHours(context,time));
        SharedPreferences.Editor edi = mPrefs.edit();
        edi.putLong("SwitchTime", time.getTime().getTime());
        edi.commit();
        context.getContentResolver().update(today.getUri(),today.asContentValues(),null,null);
    }

    private static double workOutHours(Context context,Calendar time)
    {
        SharedPreferences mPrefs = context.getSharedPreferences("TimeTrackerPreferences", Context.MODE_PRIVATE);
        if (time == null)
            time = Calendar.getInstance(Locale.getDefault());
        Long millis = time.getTime().getTime() - mPrefs.getLong("SwitchTime",0);
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
