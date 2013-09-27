package com.androidproductions.timetracker;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;


/**
 * An activity representing a list of Projects. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ProjectDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ActionListFragment} and the item details
 * (if present) is a {@link ProjectDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ActionListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ActionListActivity extends FragmentActivity
        implements ActionListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ActionMethod mSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeUI(null);
    }

    private void InitializeUI(ActionMethod method) {
        setContentView(R.layout.activity_project_list);

        if (findViewById(R.id.project_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ActionListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.project_list))
                    .setActivateOnItemClick(true);
        }
        if (method != null)
        {
            onItemSelected(method);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ActionListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(ActionMethod method) {
        mSelected = method;
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.project_detail_container, FragmentHelper.getFragmentByAction(mSelected))
                    .commit();
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, FragmentHolderActivity.class);
            detailIntent.putExtra(FragmentHolderActivity.FRAGMENT_ID, mSelected.Value);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        /*InitializeUI(mSelected);*/
    }
}
