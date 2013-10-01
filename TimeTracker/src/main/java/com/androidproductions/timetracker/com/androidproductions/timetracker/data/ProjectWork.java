package com.androidproductions.timetracker.com.androidproductions.timetracker.data;

import com.androidproductions.timetracker.WorkType;

public class ProjectWork {
    private final String Name;
    private final WorkType Type;

    public ProjectWork(String name, WorkType workType)
    {
        Name = name;
        Type = workType;
    }

    public ProjectWork(Project project, WorkType workType)
    {
        Name = project.toString();
        Type = workType;
    }

    @Override
    public String toString() {
        return Name + " - " + Type.toString();
    }

    public boolean isFor(Project selected, WorkType workType) {
        return selected.toString().equals(Name) && Type == workType;
    }

    public String getProject() {
        return Name;
    }

    public WorkType getWorkType() {
        return Type;
    }
}
