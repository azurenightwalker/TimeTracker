package uk.co.xlabsystems.timetracker.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class DayProvider extends ContentProvider
{
    private static final int DAY = 0;
    private static final int DAYS = 1;

    private static final String PROVIDER_NAME =
            "uk.co.xlabsystems.timetracker";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(PROVIDER_NAME, "days", DAYS);
        uriMatcher.addURI(PROVIDER_NAME, "days/#", DAY);
    }

    private TimeTrackerDB mTimeTrackerDB;

    public boolean onCreate() {
        mTimeTrackerDB = new TimeTrackerDB(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Default projection if none supplied
        if(projection == null) projection = getDefaultProjection(uri);

        // Defaults & ID's
        switch(uriMatcher.match(uri))
        {
            case DAYS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = "_ID ASC";
                break;
            case DAY:
                selection = (selection == null ? "" : (selection + " and ")) +
                        TimesheetContract._ID + " = " + uri.getLastPathSegment();
                break;
            default:
                return null;
        }
        final SQLiteDatabase db = mTimeTrackerDB.getReadableDatabase();
        if (db != null)
            return db.query(findTableName(uri), projection, selection, selectionArgs, null, null, sortOrder);
        return null;
    }

    public String getType(Uri uri) {
        switch(uriMatcher.match(uri))
        {
            case DAY:
                return "vnd.android.cursor.dir/vnd.uk.co.xlabsystems.timetracker.day";
            case DAYS:
                return "vnd.android.cursor.item/vnd.com.uk.co.xlabsystems.timetracker.day";
            default:
                return null;
        }
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mTimeTrackerDB.getWritableDatabase();
        if (db != null) {
            long id = db.insert(findTableName(uri),"",contentValues);
            db.close();
            return ContentUris.withAppendedId(uri, id);
        }
        return null;
    }
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mTimeTrackerDB.getWritableDatabase();
        if (db != null) {
            int affectedRows = db.delete(findTableName(uri),selection,selectionArgs);
            db.close();
            return affectedRows;
        }
        return 0;
    }

    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mTimeTrackerDB.getWritableDatabase();
        switch(uriMatcher.match(uri))
        {
            case DAY:
            selection = (selection == null ? "" : (selection + " and ")) +
                    TimesheetContract._ID + " = " + uri.getLastPathSegment();
            break;
            default:
                break;
        }
        if (db != null) {
            int affectedRows = db.update(findTableName(uri),contentValues,selection,selectionArgs);
            db.close();
            return affectedRows;
        }
        return 0;
    }

    private String findTableName(Uri uri)
    {
        switch(uriMatcher.match(uri))
        {
            case DAY:
                return TimeTrackerDB.TABLE_DAYS;
            case DAYS:
                return TimeTrackerDB.TABLE_DAYS;
            default:
                return null;
        }
    }

    private String[] getDefaultProjection(Uri uri)
    {
        switch(uriMatcher.match(uri))
        {
            case DAY:
                return TimesheetContract.PROJECTION;
            case DAYS:
                return TimesheetContract.PROJECTION;
            default:
                return null;
        }
    }
}