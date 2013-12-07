package uk.co.xlabsystems.timetracker;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import java.util.List;

import uk.co.xlabsystems.timetracker.data.Project;

public class ProjectDataTask extends AsyncTask<Fragment, Void, List<Project>> {
    private Fragment frag;
    /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
         protected List<Project> doInBackground(Fragment... frags) {
            frag = frags[0];
            return ProjectHelper.getProjectList();
        }

        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(List<Project> result) {
            ((TimeTrackerFragment)frag).updateProjects(result);
        }
    }


