package com.androidproductions.timetracker.data;

import com.androidproductions.timetracker.WorkType;

public class ProjectWork {
    private final WorkType Type;
    private final Project Project;

    public ProjectWork(String name, WorkType workType)
    {
        Type = workType;
        Project = new Project(name);
    }

    public ProjectWork(Project project, WorkType workType)
    {
        Project = project;
        Type = workType;
    }

    @Override
    public String toString() {
        return Project.toString() + " - " + Type.toString();
    }

    public boolean isFor(Project selected, WorkType workType) {
        return selected.equals(Project) && Type == workType;
    }

    public String getProjectName() {
        return Project.toString();
    }

    public Project getProject() {
        return Project;
    }

    public WorkType getWorkType() {
        return Type;
    }
}
