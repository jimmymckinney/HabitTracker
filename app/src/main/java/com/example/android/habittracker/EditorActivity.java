package com.example.android.habittracker;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.habittracker.data.HabitDbHelper;

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
        mDbHelper = new HabitDbHelper(this);

        //Find all the relevant views that we will need to read user input data from
        mHabitNameEditText = (EditText) findViewById(R.id.edit_habit_name);
        mHabitFrequencyAmountEditText = (EditText) findViewById(R.id.edit_habit_frequency_amount);
        mHabitFrequencyIntervalEditText = (EditText) findViewById(R.id.edit_habit_frequency_interval);
        mDatePicker = (DatePicker) findViewById(R.id.date_picker);
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
                long newRowId = mDbHelper.insertHabit(mHabitNameEditText, mHabitFrequencyAmountEditText, mHabitFrequencyIntervalEditText, mDatePicker);
                //Toast nessage displaying id.
                if (newRowId < 0) {
                    Toast.makeText(this, "Error with saving habit.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Habit _ID#" + newRowId + " saved.", Toast.LENGTH_SHORT).show();
                }
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
