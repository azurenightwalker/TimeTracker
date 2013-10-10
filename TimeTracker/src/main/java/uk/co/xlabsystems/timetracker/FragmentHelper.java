package uk.co.xlabsystems.timetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public final class FragmentHelper {
    public static Fragment getFragmentByAction(ActionMethod actionMethod)
    {
        switch (actionMethod)
        {
            case ClockIn:
                return new ClockFragment(true);
            case ClockOut:
                return new ClockFragment(false);
            case SwitchProject:
                return new ProjectFragment();
            case ViewDay:
                return new DayFragment();
            default:
                Bundle arguments = new Bundle();
                arguments.putString(ProjectDetailFragment.ARG_ITEM_ID, actionMethod.toString());
                Fragment fragment = new ProjectDetailFragment();
                fragment.setArguments(arguments);
                return fragment;
        }
    }
}
