package uk.co.xlabsystems.timetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import uk.co.xlabsystems.timetracker.data.Day;
import uk.co.xlabsystems.timetracker.data.Project;
import uk.co.xlabsystems.timetracker.data.ProjectWork;
import uk.co.xlabsystems.timetracker.data.TimesheetContract;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClockFragment extends TimeTrackerFragment  implements AdapterView.OnItemSelectedListener {
    private final Boolean mIsIn;

    private TimePicker timePicker;
    private Spinner projectSpinner;
    private Spinner workTypeSpinner;
    private ProjectWork CurrentProject;
    private List<WorkType> workTypes;

    public ClockFragment(Boolean isIn) {
        super();
        mIsIn = isIn;
        //projectList = ProjectHelper.getProjectList();
        new ProjectDataTask().execute(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        findToday();
        View v = inflater.inflate(mIsIn ? R.layout.clock_in : R.layout.clock_out, container, false);
        Boolean canSave = mIsIn && today == null || !mIsIn && today != null && today.getTimeOut().getTime() == 0;
        v.findViewById(R.id.save).setEnabled(canSave);
        timePicker = (TimePicker) v.findViewById(R.id.time);
        if (mIsIn)
            InitView(v);
        return v;
    }

    public void clock(final View v)
    {

        final Date date = TimeHelper.getDate(timePicker);
        if (date.after(new Date()))
            Toast.makeText(getActivity(),
                    mIsIn ? R.string.noClockIn : R.string.noClockOut,
                    Toast.LENGTH_SHORT).show();
        else
        {
            if (mIsIn)
            {
                Day day = new Day(date);
                getActivity().getContentResolver().insert(TimesheetContract.CONTENT_URI,day.asContentValues());
                SharedPreferences mPrefs =  getActivity().getSharedPreferences("TimeTrackerPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor edi = mPrefs.edit();
                edi.putLong("SwitchTime", Calendar.getInstance(Locale.getDefault()).getTime().getTime());
                edi.commit();
                Project project = (Project)projectSpinner.getSelectedItem();
                WorkType workType = (WorkType)workTypeSpinner.getSelectedItem();
                CurrentProject = new ProjectWork(project,workType);
                ProjectHelper.setCurrentProject(getActivity(),CurrentProject);
            }
            else
            {
                ProjectHelper.stopProject(getActivity());
                findToday();
                today.setTimeOut(Calendar.getInstance(Locale.getDefault()).getTime());
                getActivity().getContentResolver().update(today.getUri(), today.asContentValues(), null, null);
            }
            v.setEnabled(false);
        }
    }

    private void InitView(View v)
    {
        projectSpinner = (Spinner)v.findViewById(R.id.project);
        workTypeSpinner = (Spinner)v.findViewById(R.id.workType);
    }

    private void UpdateView()
    {
        projectSpinner.setAdapter(new ArrayAdapter<Project>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                ProjectList
        ));
        projectSpinner.setOnItemSelectedListener(this);

        CurrentProject = ProjectHelper.getCurrentProject(getActivity());
        projectSpinner.setSelection(Math.max(ProjectList.indexOf(CurrentProject.getProject()),0));
        workTypeSpinner.setOnItemSelectedListener(this);

        UpdateWorkTypes();
        workTypeSpinner.setSelection(Math.max(workTypes.indexOf(CurrentProject.getWorkType()),0));
    }

    private void UpdateWorkTypes()
    {
        workTypes = ((Project) projectSpinner.getSelectedItem()).getWorkTypes();
        if (workTypes.size() == 0)
        {
            workTypeSpinner.setVisibility(View.GONE);
            workTypeSpinner.setAdapter(new ArrayAdapter<WorkType>(
                    getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    workTypes));
        }else{
            workTypeSpinner.setVisibility(View.VISIBLE);
            workTypeSpinner.setAdapter(new ArrayAdapter<WorkType>(
                    getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    workTypes));
        }



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == projectSpinner)
            UpdateWorkTypes();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void updateProjectView()
    {
        UpdateView();
    }
}
