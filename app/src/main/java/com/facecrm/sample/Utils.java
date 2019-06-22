package com.facecrm.sample;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {
    public static String appId = "146e1fe7-3c17-460d-81a3-b5441eb5dc70";

    public static String covertTime(String timestampUTC) {
        String FORMAT_UTC = "yyyy-MM-dd'T'hh:mm:ss.SSSSSS'Z'";
        String FORMAT_LOCAL = "yyyy-MM-dd hh:mm:ss";
        String formattedDate = timestampUTC;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_UTC);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = formatter.parse(timestampUTC);

            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter2 = new SimpleDateFormat(FORMAT_LOCAL);
            formatter2.setTimeZone(TimeZone.getTimeZone(getCurrentTimeZone()));

            formattedDate = formatter2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    private static String getCurrentTimeZone() {
        TimeZone tz = Calendar.getInstance().getTimeZone();
        return tz.getID();
    }
}
