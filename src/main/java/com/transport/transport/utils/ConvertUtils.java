package com.transport.transport.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvertUtils {

    public static Date getDate(long timestampInString) {
        Date date = new Date(timestampInString);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format.format(date);
        return date;
    }

    public static Timestamp convertToTime(long milliseconds) {
        Timestamp time = new Timestamp(milliseconds);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String formatted = format.format(time);
        return Timestamp.valueOf(formatted);
    }
}
