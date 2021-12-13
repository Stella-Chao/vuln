package com.tf.backend.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class dateUtil {

    public static String getDate(String when) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        if (when.equals("Week")) {
            c.add(Calendar.DATE, - 7);
        }
        if (when.equals("Month")) {
            c.add(Calendar.MONTH, - 1);
        }
        if (when.equals("Year")) {
            c.add(Calendar.YEAR, - 1);
        }
        Date d = c.getTime();
        String day = format.format(d);
        return day;
    }
}
