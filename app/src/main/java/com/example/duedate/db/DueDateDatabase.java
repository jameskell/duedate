package com.example.duedate.db;

import android.content.Context;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TaskItem.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DueDateDatabase extends RoomDatabase {

    public abstract TaskItemDao taskItemDao();

    private static volatile DueDateDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DueDateDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (DueDateDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DueDateDatabase.class, "duedate_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
