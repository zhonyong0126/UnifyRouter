package com.feture.learnfilter.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String STANDARD_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String STANDARD_WITHOUT_SYMBOL_DATE_PATTERN = "yyyyMMddHHmmss";

    public static String parseDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_DATE_PATTERN);
        return sdf.format(date);
    }

    public static String parseDateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date parseStringToDate(String dateStr, String pattern) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(dateStr);
    }

    public static long getSecondDistance(Date bigDate, Date smallDate) {
        long diff = bigDate.getTime() - smallDate.getTime();

        return diff / 1000;
    }
}
