package uk.co.xlabsystems.timetracker;


import android.content.Context;

import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class ProjectCache {

    private static ProjectCache _projectCache;
    private final Context mContext;

    private ProjectCache(Context con)
    {
        mContext = con;
    }

    public static ProjectCache getInstance()
    {
        if (_projectCache == null)
            throw new IllegalAccessError("This has not already been instantiated");
        return _projectCache;
    }

    public static void initialize(Context con)
    {
        if (_projectCache != null)
            throw new IllegalAccessError("This has already been instantiated");
        _projectCache = new ProjectCache(con);

    }

    public String get()
    {
        FileInputStream fis;
        String content = "";
        try {
            fis = mContext.openFileInput("TT_Cache");
            byte[] input = new byte[fis.available()];
            while (fis.read(input) != -1) {
                content += new String(input);
            }
            fis.close();
            return content;
        } catch (FileNotFoundException ie){
            ie.printStackTrace();
            return "";
        } catch (IOException e){
            e.printStackTrace();
            return "";
        }

    }

    public Calendar getTimestamp()
    {
       final Calendar cal = Calendar.getInstance(Locale.getDefault());
       cal.setTimeInMillis(mContext.getFileStreamPath("TT_Cache").lastModified());
       return cal;
    }

    public void save(JSONArray json) {
        String filename = "TT_Cache";
        FileOutputStream fos;
        try {
            fos = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(json.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
