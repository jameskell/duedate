package com.example.duedate.db;

import android.os.Build;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

public class Converters {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDateTime fromTimestamp(Long value) {
        return value == null ? null : LocalDateTime.ofEpochSecond(value, 0, ZoneId.of("America/New_York").getRules().getOffset(Instant.now()));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static Long localDateTimeToTimestamp(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.toEpochSecond(ZoneId.of("America/New_York").getRules().getOffset(Instant.now()));
    }

    @TypeConverter
    public static TaskPriority fromValue(String value) {
        if(value == null) {
            return null;
        }
        return Stream.of(TaskPriority.values())
                .filter(v -> v.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

    @TypeConverter
    public static String taskPriorityToString(TaskPriority p) {
        return p.getValue();
    }
}
