package uk.co.xlabsystems.timetracker;

import android.app.Application;

import uk.co.xlabsystems.timetracker.network.NetworkHelper;
import uk.co.xlabsystems.timetracker.security.CredentialStore;

public class TimeTrackerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ProjectCache.initialize(getApplicationContext());
        NetworkHelper.initialize(getApplicationContext());
        CredentialStore.initialize(getApplicationContext());
    }
}
