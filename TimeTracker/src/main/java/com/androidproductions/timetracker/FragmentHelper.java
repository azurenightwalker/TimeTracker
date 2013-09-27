package com.androidproductions.timetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public final class FragmentHelper {
    public static final Fragment getFragmentByAction(ActionMethod actionMethod)
    {
        switch (actionMethod)
        {
            case Configure:
                return new ConfigureFragment();
            case ClockIn:
                return new ClockFragment(true);
            case ClockOut:
                return new ClockFragment(false);
            default:
                Bundle arguments = new Bundle();
                arguments.putString(ProjectDetailFragment.ARG_ITEM_ID, actionMethod.toString());
                Fragment fragment = new ProjectDetailFragment();
                fragment.setArguments(arguments);
                return fragment;
        }
    }
}
