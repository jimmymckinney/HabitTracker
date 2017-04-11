package com.example.android.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittracker.data.HabitContract.HabitEntry;

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "habittracker.db";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABIT_TRACKER_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_HABIT_FREQUENCY_AMOUNT + " INTEGER NOT NULL, "
                + HabitEntry.COLUMN_HABIT_FREQUENCY_INTERVAL + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_HABIT_DATE + " INTEGER NOT NULL);";
        db.execSQL(SQL_CREATE_HABIT_TRACKER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }
}
