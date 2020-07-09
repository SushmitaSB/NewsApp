package com.example.newsapp.model;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeManager {
    Context context;
    public DateTimeManager(Context context){
        this.context = context;
    }

    public String getPrevMonthDate(){
        String prevDate;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate=df.format(c);

        Date date = null;
        try {
            date = df.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        prevDate = df.format(calendar.getTime());

        return prevDate;
    }

    public String getPrevdatDate(){

        String prevDate;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate=df.format(c);

        Date date = null;
        try {
            date = df.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        prevDate = df.format(calendar.getTime());

        return prevDate;
    }
}
