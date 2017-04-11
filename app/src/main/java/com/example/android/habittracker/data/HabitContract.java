package com.example.android.habittracker.data;

import android.provider.BaseColumns;

public class HabitContract {

    private HabitContract() {

    }

    public static final class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "habit";
        public static final String COLUMN_HABIT_FREQUENCY_AMOUNT = "frequency_amount";
        public static final String COLUMN_HABIT_FREQUENCY_INTERVAL = "frequency_interval";
        public static final String COLUMN_HABIT_DATE = "date";

    }

}
