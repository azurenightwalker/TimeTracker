package uk.co.xlabsystems.timetracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimeTrackerDB extends SQLiteOpenHelper {
    public static final String TABLE_DAYS = "days";
    private static final String DATABASE_NAME = "TimeTracker";

    private static final String DATABASE_CREATE_DAYS =
            "create table "+ TABLE_DAYS +" (" +
                    TimesheetContract._ID + " integer primary key autoincrement, " +
                    TimesheetContract.Date + " int not null, " +
                    TimesheetContract.TimeIn + " int not null, " +
                    TimesheetContract.TimeOut + " int null, " +
                    TimesheetContract.Projects + " text not null)";

    public TimeTrackerDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_DAYS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}