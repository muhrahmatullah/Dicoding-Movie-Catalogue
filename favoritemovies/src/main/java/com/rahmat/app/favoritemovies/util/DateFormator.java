package com.rahmat.app.favoritemovies.util;

import com.rahmat.app.favoritemovies.rest.UtilsConstant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by muhrahmatullah on 09/08/18.
 */
public class DateFormator {
    private static String format(String date, String format) {
        String result = "";

        DateFormat old = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = old.parse(date);
            DateFormat newFormat = new SimpleDateFormat(format);
            result = newFormat.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getDate(String date) {
        return format(date, UtilsConstant.DATE_FORMAT);
    }

    public static String getDateDay(String date) {
        return format(date, UtilsConstant.DATE_FORMAT_DAY);
    }
}
