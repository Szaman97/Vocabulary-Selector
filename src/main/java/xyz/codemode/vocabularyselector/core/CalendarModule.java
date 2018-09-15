package xyz.codemode.vocabularyselector.core;

import xyz.codemode.util.FileUtility;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class CalendarModule {
    public static boolean canDrawVocabulary() {
        currentDate = Calendar.getInstance();
        deserializeNextDrawDate();
        if(currentDate.compareTo(nextDrawDate) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void increaseNextDrawDate(int days) {
        nextDrawDate.add(Calendar.DATE, days);
        serializeNextDrawDate();
    }

    public static String nextDrawDate() {
        deserializeNextDrawDate();  //just to make sure everything is up to date
        return nextDrawDateString;
    }

    /**
     * Build String containing date
     * @param year
     * @param month value has to be in the range 0-11
     * @param day
     * @return Date in format 'yyyy-mm-dd'
     */
    public static String buildStringDate(int year, int month, int day) {
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append('-');
        String monthString = Integer.toString(++month);
        if(monthString.length()==1)
            sb.append('0');
        sb.append(monthString);
        sb.append('-');
        String dayString = Integer.toString(day);
        if(dayString.length()==1)
            sb.append('0');
        sb.append(dayString);
        return sb.toString();
    }

    private static void deserializeNextDrawDate() {
        try {
            String content = FileUtility.loadAsString(file);
            JSONObject json = new JSONObject(content);
            int year = (int) json.get("YEAR");
            int month = (int) json.get("MONTH");
            int day = (int) json.get("DAY");
            nextDrawDateString = buildStringDate(year,month,day);
            nextDrawDate = Calendar.getInstance();
            nextDrawDate.set(year,month,day);
            nullTime(nextDrawDate);

        } catch (IOException e) {
            //When file not found
            nextDrawDate = Calendar.getInstance();
            nullTime(nextDrawDate);
        }
    }

    private static void nullTime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    private static void serializeNextDrawDate() {
        JSONStringer stringer = new JSONStringer();
        stringer.object();
        stringer.key("YEAR");
        stringer.value(nextDrawDate.get(Calendar.YEAR));
        stringer.key("MONTH");
        stringer.value(nextDrawDate.get(Calendar.MONTH));
        stringer.key("DAY");
        stringer.value(nextDrawDate.get(Calendar.DAY_OF_MONTH));
        stringer.endObject();
        try {
            FileUtility.saveTextFile(file,stringer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static File file = new File("./cfg/nextDrawDate.json");
    private static Calendar currentDate;
    private static Calendar nextDrawDate;
    private static String nextDrawDateString;
}
