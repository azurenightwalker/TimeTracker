package uk.co.xlabsystems.timetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import uk.co.xlabsystems.timetracker.data.Day;
import uk.co.xlabsystems.timetracker.data.Project;

public abstract class TimeTrackerFragment extends Fragment {
    protected Day today;
    protected List<Project> ProjectList;

    public TimeTrackerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    protected final void findToday()
    {
        today = DayHelper.findToday(getActivity());
    }

    protected final void updateProjects(List<Project> projects)
    {
        ProjectList = projects;
        updateProjectView();
    }

    protected void updateProjectView()
    {

    }
}
