package uk.co.xlabsystems.timetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import uk.co.xlabsystems.timetracker.data.Day;

public class TimeTrackerFragment extends Fragment {
    protected Day today;

    public TimeTrackerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    protected void findToday()
    {
        today = DayHelper.findToday(getActivity());
    }
}
