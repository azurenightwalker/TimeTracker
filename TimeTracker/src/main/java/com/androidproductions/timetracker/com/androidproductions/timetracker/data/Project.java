package com.androidproductions.timetracker.com.androidproductions.timetracker.data;

public class Project {
    private final String Name;
    private final Boolean HasDev;
    private final Boolean HasSupport;
    private final Boolean HasResearch;

    public Project(String name)
    {
        Name = name;
        HasDev = true;
        HasSupport = true;
        HasResearch = true;
    }

    public Project(String name, Boolean hasDev, Boolean hasSupport, Boolean hasResearch)
    {
        Name = name;
        HasDev = hasDev;
        HasSupport = hasSupport;
        HasResearch = hasResearch;
    }

    @Override
    public String toString() {
        return Name;
    }
}
