package es.upm.miw.bantumi.utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestampDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestampDate(Date date) {
        return date == null ? null : date.getTime();
    }
}