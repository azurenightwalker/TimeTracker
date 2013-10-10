package uk.co.xlabsystems.timetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Project detail screen.
 * This fragment is either contained in a {@link uk.co.xlabsystems.timetracker.ActionListActivity}
 * in two-pane mode (on tablets) or a {@link uk.co.xlabsystems.timetracker.FragmentHolderActivity}
 * on handsets.
 */
public class ConfigureFragment extends Fragment {


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ConfigureFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project_detail, container, false);
        ((TextView) rootView.findViewById(R.id.project_detail)).setText("Configure");

        return rootView;
    }
}
