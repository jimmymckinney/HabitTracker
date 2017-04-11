package com.example.android.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.android.habittracker.data.HabitContract.HabitEntry;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "habittracker.db";
    private SQLiteDatabase mHabitDb;
    private ContentValues mValues;

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HabitEntry.SQL_CREATE_HABIT_TRACKER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public long insertHabit(EditText habitNameEditText, EditText habitFrequencyAmountEditText,
                            EditText habitFrequencyIntervalEditText, DatePicker datePicker) {
        mHabitDb = getWritableDatabase();

        //Store EditText and DatePicker objects in String, int, and long objects
        String habitName = habitNameEditText.getText().toString().trim();
        int habitFrequencyAmount = Integer.parseInt(habitFrequencyAmountEditText.getText().toString().trim());
        String habitFrequencyInterval = habitFrequencyIntervalEditText.getText().toString().trim();
        long time = habitTime(datePicker);

        //Store user input in Habit Table
        ContentValues mValues = new ContentValues();
        mValues.put(HabitEntry.COLUMN_HABIT_NAME, habitName);
        mValues.put(HabitEntry.COLUMN_HABIT_FREQUENCY_AMOUNT, habitFrequencyAmount);
        mValues.put(HabitEntry.COLUMN_HABIT_FREQUENCY_INTERVAL, habitFrequencyInterval);
        mValues.put(HabitEntry.COLUMN_HABIT_DATE, time);

        return mHabitDb.insert(HabitEntry.TABLE_NAME, null, mValues);
    }
    
    public void insertDummyHabits() {
        // Create a new map of mValues, where column names are the keys
        mValues = new ContentValues();
        mValues.put(HabitEntry.COLUMN_HABIT_NAME, "Drink Coffee");
        mValues.put(HabitEntry.COLUMN_HABIT_FREQUENCY_AMOUNT, 2);
        mValues.put(HabitEntry.COLUMN_HABIT_FREQUENCY_INTERVAL, "cups");
        mValues.put(HabitEntry.COLUMN_HABIT_DATE, 1491857185428L);

        // Insert the new row, returning the primary key value of the new row
        mHabitDb.insert(HabitEntry.TABLE_NAME, null, mValues);

        mValues.put(HabitEntry.COLUMN_HABIT_NAME, "Run");
        mValues.put(HabitEntry.COLUMN_HABIT_FREQUENCY_AMOUNT, 3);
        mValues.put(HabitEntry.COLUMN_HABIT_FREQUENCY_INTERVAL, "miles");
        mValues.put(HabitEntry.COLUMN_HABIT_DATE, 149185718000L);
        mHabitDb.insert(HabitEntry.TABLE_NAME, null, mValues);
    }

    private long habitTime(DatePicker datePicker) {
        // Pull year, month, and day from the DatePicker
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        // Enter year, month, and day into a calendar and then convert
        // to time in milliseconds
        Calendar calendar = new GregorianCalendar(year, month, day);
        return calendar.getTimeInMillis();
    }

    public Cursor readAllHabits() {
        mHabitDb = getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_FREQUENCY_AMOUNT,
                HabitEntry.COLUMN_HABIT_FREQUENCY_INTERVAL,
                HabitEntry.COLUMN_HABIT_DATE};

        return mHabitDb.query(HabitEntry.TABLE_NAME, projection,
                null, null, null, null, null);
    }

    public void deleteAllHabits() {
        mHabitDb = getWritableDatabase();
        mHabitDb.delete(HabitEntry.TABLE_NAME, null, null);
        mHabitDb.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + HabitEntry.TABLE_NAME + "'");
    }

}
