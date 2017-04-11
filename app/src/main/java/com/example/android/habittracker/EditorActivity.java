package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditorActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;
    private EditText mHabitNameEditText;
    private EditText mHabitFrequencyAmountEditText;
    private EditText mHabitFrequencyIntervalEditText;
    private DatePicker mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //Find all the relevant views that we will need to read user input data from
        mHabitNameEditText = (EditText) findViewById(R.id.edit_habit_name);
        mHabitFrequencyAmountEditText = (EditText) findViewById(R.id.edit_habit_frequency_amount);
        mHabitFrequencyIntervalEditText = (EditText) findViewById(R.id.edit_habit_frequency_interval);
        mDatePicker = (DatePicker) findViewById(R.id.date_picker);

        mDbHelper = new HabitDbHelper(this);
    }

    private long datePicker() {
        // Pull year, month, and day from the DatePicker
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();

        // Enter year, month, and day into a calendar and then convert
        // to time in milliseconds
        Calendar calendar = new GregorianCalendar(year, month, day);
        return calendar.getTimeInMillis();
    }

    private void insertHabit() {
        String habitName = mHabitNameEditText.getText().toString().trim();
        int habitFrequencyAmount = Integer.parseInt(mHabitFrequencyAmountEditText.getText().toString().trim());
        String habitFrequencyInterval = mHabitFrequencyIntervalEditText.getText().toString().trim();
        long time = datePicker();

        // Format time into a readable date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        String date = dateFormatter.format(time);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, habitName);
        values.put(HabitEntry.COLUMN_HABIT_FREQUENCY_AMOUNT, habitFrequencyAmount);
        values.put(HabitEntry.COLUMN_HABIT_FREQUENCY_INTERVAL, habitFrequencyInterval);
        values.put(HabitEntry.COLUMN_HABIT_DATE, time);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        //Toast nessage displaying id.
        if (newRowId < 0) {
            Toast.makeText(this, "Error with saving habit.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Habit _ID#" + newRowId + " saved.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertHabit();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
