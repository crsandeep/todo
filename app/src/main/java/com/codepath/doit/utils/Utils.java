package com.codepath.doit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utils {
    public static Date getDateFromString(String date) {
        SimpleDateFormat sf = new SimpleDateFormat("M/dd/yyyy");
        sf.setLenient(true);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateAndTimeFromString(String date) {
        SimpleDateFormat sf = new SimpleDateFormat("M/dd/yyyy hh:mm");
        sf.setLenient(true);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromDate(Date date) {
        String strDate = "";
        if(date != null) {
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH) + 1;
            strDate += month;
            strDate += "/";
            strDate += calendar.get(Calendar.DATE);
            strDate += "/";
            strDate += calendar.get(Calendar.YEAR);
        }
        return strDate;
    }

    public static String getStringFromDateAndTime(Date date) {
        String strDate = "";
        if(date != null) {
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH) + 1;
            strDate += month;
            strDate += "/";
            strDate += calendar.get(Calendar.DATE);
            strDate += "/";
            strDate += calendar.get(Calendar.YEAR);
            strDate += " ";
            int hrs = calendar.get(Calendar.HOUR_OF_DAY);
            int mnts = calendar.get(Calendar.MINUTE);

            String curTime = String.format("%02d:%02d", hrs, mnts);

            strDate += curTime;
        }
        return strDate;
    }
}
