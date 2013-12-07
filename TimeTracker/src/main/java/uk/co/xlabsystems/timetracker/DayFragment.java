package uk.co.xlabsystems.timetracker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.co.xlabsystems.timetracker.data.ProjectWork;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class DayFragment extends TimeTrackerFragment implements View.OnClickListener {

    private LinearLayout projects;
    private TextView clockIn;
    private TextView clockOut;
    private DatePicker datePicker;
    private Button updateDayButton;
    private Button pushDayButton;

    public DayFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.day, container, false);
        if (v != null)
        {
            clockIn = (TextView)v.findViewById(R.id.clockInText);

            clockOut = (TextView)v.findViewById(R.id.clockOutText);

            projects = (LinearLayout) v.findViewById(R.id.projectHolder);

            datePicker = (DatePicker)v.findViewById(R.id.datePicker);

            datePicker.setMaxDate(
                    Calendar.getInstance(Locale.getDefault())
                            .getTime().getTime()
            );
            updateDayButton = (Button)v.findViewById(R.id.updateDay);
            pushDayButton = (Button)v.findViewById(R.id.pushDay);
            updateDayButton.setOnClickListener(this);
            pushDayButton.setOnClickListener(this);
            onClick(updateDayButton);
        }
        return v;
    }

    public void onClick(final View view)
    {
        if (view == updateDayButton)
        {
            today = DayHelper.findDay(getActivity(),getDateFromDatePicker());
            clockIn.setText(
                    today == null ? "Not clocked in" : TimeHelper.TimeFormat12Hr.format(today.getTimeIn())
            );
            clockOut.setText(
                    today == null || today.getTimeOut().getTime() == 0 ? "Not clocked out" : TimeHelper.TimeFormat12Hr.format(today.getTimeOut())
            );
            projects.removeAllViews();
            if (today != null)
            {
                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                Map<ProjectWork, Double> works = ProjectHelper.getAllProjectWorks(today);
                for(ProjectWork key : works.keySet())
                {
                    View row = inflater.inflate(R.layout.project_row, null, false);
                    if (row != null)
                    {
                        ((TextView)row.findViewById(R.id.projectLabel)).setText(key.toString());
                        ((TextView)row.findViewById(R.id.projectHours)).setText(String.valueOf(works.get(key).doubleValue()));
                        projects.addView(row);
                    }
                }
            }
        }
        else if (view == pushDayButton)
        {
            today.pushData();
        }
    }

    public Date getDateFromDatePicker(){
        return new Date(
                datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth());
    }
}
