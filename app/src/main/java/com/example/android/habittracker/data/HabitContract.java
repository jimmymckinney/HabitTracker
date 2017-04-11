package com.example.android.habittracker.data;

import android.provider.BaseColumns;

public class HabitContract {

    private HabitContract() {

    }

    public static final class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";

        public static final String COLUMN_HABIT_NAME = "habit";
        public static final String COLUMN_HABIT_FREQUENCY_AMOUNT = "frequency_amount";
        public static final String COLUMN_HABIT_FREQUENCY_INTERVAL = "frequency_interval";
        public static final String COLUMN_HABIT_DATE = "date";

        public static final String SQL_CREATE_HABIT_TRACKER_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_HABIT_FREQUENCY_AMOUNT + " INTEGER NOT NULL, "
                + HabitEntry.COLUMN_HABIT_FREQUENCY_INTERVAL + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_HABIT_DATE + " INTEGER NOT NULL);";

    }

}
