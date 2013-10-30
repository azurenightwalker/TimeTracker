package uk.co.xlabsystems.timetracker;

import android.app.Application;

import uk.co.xlabsystems.timetracker.network.NetworkHelper;

public class TimeTrackerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ProjectCache.initialize(getApplicationContext());
        NetworkHelper.initialize(getApplicationContext());
    }
}
