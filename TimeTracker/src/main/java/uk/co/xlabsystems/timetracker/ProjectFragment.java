package uk.co.xlabsystems.timetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import uk.co.xlabsystems.timetracker.data.Project;
import uk.co.xlabsystems.timetracker.data.ProjectWork;

import java.util.List;

public class ProjectFragment extends TimeTrackerFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private Spinner projectSpinner;
    private Spinner workTypeSpinner;
    private View save;
    private List<Project> projectList;
    private List<WorkType> workTypes;
    private ProjectWork CurrentProject;
    private TimePicker timePicker;

    public ProjectFragment() {
        super();
        projectList = ProjectHelper.getProjectList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        findToday();
        View v = inflater.inflate(R.layout.project, container, false);
        InitView(v);
        updateView();
        return v;
    }

    private void InitView(View v)
    {
        projectSpinner = (Spinner)v.findViewById(R.id.project);
        projectSpinner.setAdapter(new ArrayAdapter<Project>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                projectList
        ));
        projectSpinner.setOnItemSelectedListener(this);

        workTypeSpinner = (Spinner)v.findViewById(R.id.workType);
        workTypeSpinner.setOnItemSelectedListener(this);

        CurrentProject = ProjectHelper.getCurrentProject(getActivity());
        projectSpinner.setSelection(Math.max(projectList.indexOf(CurrentProject.getProject()),0));
        UpdateWorkTypes();
        workTypeSpinner.setSelection(Math.max(workTypes.indexOf(CurrentProject.getWorkType()), 0));
        timePicker = (TimePicker)v.findViewById(R.id.time);
        save = v.findViewById(R.id.save);
        save.setOnClickListener(this);
    }

    private void UpdateWorkTypes()
    {
        Project project = ((Project) projectSpinner.getSelectedItem());
        if (project != null)
        {
            workTypes = project.getWorkTypes();
            workTypeSpinner.setAdapter(new ArrayAdapter<WorkType>(
                    getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    workTypes
            ));
        }
    }

    public void updateView()
    {
        Project selected = (Project)projectSpinner.getSelectedItem();
        WorkType workType = (WorkType)workTypeSpinner.getSelectedItem();
        save.setEnabled(!CurrentProject.isFor(selected,workType));
    }

    public void onClick(View view)
    {
        if (view == save)
        {
            Project project = (Project)projectSpinner.getSelectedItem();
            WorkType workType = (WorkType)workTypeSpinner.getSelectedItem();
            CurrentProject = new ProjectWork(project,workType);
            ProjectHelper.setCurrentProject(getActivity(),CurrentProject,
                    TimeHelper.getCalendar(timePicker));
            updateView();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == projectSpinner)
            UpdateWorkTypes();
        updateView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
