package com.androidproductions.timetracker.com.androidproductions.timetracker.data;

import com.androidproductions.timetracker.WorkType;

import java.util.ArrayList;
import java.util.List;

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

    public Boolean hasDev()
    {
        return HasDev;
    }

    public Boolean hasSupport()
    {
        return HasSupport;
    }

    public Boolean hasResearch()
    {
        return HasResearch;
    }

    public List<WorkType> getWorkTypes()
    {
        List<WorkType> res = new ArrayList<WorkType>();
        if (hasDev())
            res.add(WorkType.Dev);
        if (hasResearch())
            res.add(WorkType.Research);
        if (hasSupport())
            res.add(WorkType.Support);
        return res;
    }
}
