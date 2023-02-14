package com.play.walk.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CalcTimeZone {

    private TimeZone time;
    private  Date date;
    private  DateFormat df;

    public CalcTimeZone(String timeZone) {

        this.time = TimeZone.getTimeZone(timeZone);
        this.df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = new Date();

    }

    public Timestamp getNowSeoultime(){

      /*  TimeZone time;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        time = TimeZone.getTimeZone("Asia/Seoul");
        df.setTimeZone(time);

        System.out.format("%s%n%s%n%n", time.getDisplayName(), df.format(date));
*/

        String sDate = this.df.format(this.date);
        Timestamp timestamp = Timestamp.valueOf(sDate);

        return timestamp;
        }

    }
