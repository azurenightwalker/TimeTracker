package com.androidproductions.timetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidproductions.timetracker.com.androidproductions.timetracker.data.Project;
import com.androidproductions.timetracker.com.androidproductions.timetracker.data.ProjectWork;

public class ProjectFragment extends TimeTrackerFragment implements AdapterView.OnItemSelectedListener{

    private Spinner projectSpinner;
    private Spinner workTypeSpinner;
    private View save;

    public ProjectFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        findToday();
        View v = inflater.inflate(R.layout.project, container, false);
        projectSpinner = (Spinner)v.findViewById(R.id.project);
        projectSpinner.setAdapter(new ArrayAdapter<Project>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                ProjectHelper.getProjectList()
        ));
        projectSpinner.setOnItemSelectedListener(this);

        workTypeSpinner = (Spinner)v.findViewById(R.id.workType);
        workTypeSpinner.setAdapter(new ArrayAdapter<WorkType>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                WorkType.values()
        ));
        workTypeSpinner.setOnItemSelectedListener(this);

        save = v.findViewById(R.id.save);
        updateView();
        return v;
    }

    public void switchProject(View view)
    {
        Project project = (Project)projectSpinner.getSelectedItem();
        WorkType workType = (WorkType)workTypeSpinner.getSelectedItem();
        ProjectWork projectWork = new ProjectWork(project,workType);
        ProjectHelper.setCurrentProject(getActivity(),projectWork);
        updateView();
    }

    public void updateView()
    {
        Project selected = (Project)projectSpinner.getSelectedItem();
        WorkType workType = (WorkType)workTypeSpinner.getSelectedItem();
        ProjectWork projectWork = ProjectHelper.getCurrentProject(getActivity());
        save.setEnabled(!projectWork.isFor(selected,workType));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updateView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
