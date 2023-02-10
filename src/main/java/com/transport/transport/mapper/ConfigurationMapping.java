package com.transport.transport.mapper;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


public class ConfigurationMapping {
    static Long mapDateToLong(Date date) {
        if (date != null) {
            return date.getTime();
        } else {
            return null;
        }

    }

    static Long mapDateTimeToLong(Timestamp date) {
        if (date != null) {
            return date.getTime();
        } else {
            return null;
        }

    }

    static Timestamp mapLongToDateTime(Long time) {
        return new Timestamp(time);
    }


    static Long mapTimeToLong(Time time) {
        if (time != null) {
            return time.getTime();
        } else {
            return null;
        }
    }
}
