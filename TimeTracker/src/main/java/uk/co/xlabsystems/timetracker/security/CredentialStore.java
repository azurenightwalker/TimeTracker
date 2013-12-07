package uk.co.xlabsystems.timetracker.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

public class CredentialStore {
    public static final String TIME_TRACKER_USERNAME = "TimeTrackerUsername";
    public static final String TIME_TRACKER_PASSWORD = "TimeTrackerPassword";
    private static CredentialStore ourInstance;
    private Context mContext;

    public static void initialize(Context context) {
        ourInstance = new CredentialStore(context);
    }

    public static CredentialStore getInstance() {
        return ourInstance;
    }

    private CredentialStore(Context context) {
        mContext = context;
    }

    public String getUsername()
    {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(TIME_TRACKER_USERNAME, "");
    }

    public String getPassword()
    {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(TIME_TRACKER_PASSWORD, "");
    }

    public void setUsername(String username)
    {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        edit.putString(TIME_TRACKER_USERNAME,username);
        edit.commit();
    }

    public void setPassword(String password)
    {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        edit.putString(TIME_TRACKER_PASSWORD,password);
        edit.commit();
    }

    public CredentialsProvider getCredentials()
    {
        CredentialsProvider credProvider = new BasicCredentialsProvider();
        credProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials("x-labsystems.co.uk\\"+getUsername(), getPassword()));
        return credProvider;
    }
}
