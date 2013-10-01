package com.androidproductions.timetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.androidproductions.timetracker.com.androidproductions.timetracker.data.Project;
import com.androidproductions.timetracker.com.androidproductions.timetracker.data.ProjectWork;

import java.util.ArrayList;
import java.util.List;

public final class ProjectHelper {
    private static final List<Project> ProjectList = new ArrayList<Project>();

    static {
        ProjectList.add(new Project("Portal"));
        ProjectList.add(new Project("NPEx"));
        ProjectList.add(new Project("QTool"));
    }

    public static final List<Project> getProjectList() {
        // TODO: Fetch these, no need to be hard-coded
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
        SharedPreferences mPrefs = context.getSharedPreferences("TimeTrackerPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor edi = mPrefs.edit();
        edi.putString("Project",projectWork.getProject());
        edi.putInt("WorkType",projectWork.getWorkType().Value);
        edi.commit();
        // TODO: Persist to database
    }
}
