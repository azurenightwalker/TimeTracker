package uk.co.xlabsystems.timetracker.data;

import uk.co.xlabsystems.timetracker.WorkType;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Project other = (Project) obj;

        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.toString().hashCode();
        return hash;
    }
}