package com.example.android.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new HabitDbHelper(this);
        displayDatabaseInfo(mDbHelper.readAllHabits());
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo(mDbHelper.readAllHabits());
    }

    private void displayDatabaseInfo(Cursor cursor) {
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // habits table in the database).
            TextView displayView = (TextView) findViewById(R.id.habit_list);
            displayView.setText("You have tracked " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitEntry._ID + " - " + HabitEntry.COLUMN_HABIT_NAME + " - ");
            displayView.append(HabitEntry.COLUMN_HABIT_FREQUENCY_AMOUNT + " " + HabitEntry.COLUMN_HABIT_FREQUENCY_INTERVAL + " - ");
            displayView.append(HabitEntry.COLUMN_HABIT_DATE);
            //Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int frequencyAmountColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_FREQUENCY_AMOUNT);
            int frequencyIntervalColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_FREQUENCY_INTERVAL);
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DATE);

            //Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumIndex);
                int currentFrequencyAmount = cursor.getInt(frequencyAmountColumnIndex);
                String currentFrequencyInterval = cursor.getString(frequencyIntervalColumnIndex);
                long currentTime = cursor.getLong(timeColumnIndex);

                //Convert long time into a String date
                String currentDate = getDate(currentTime);
                //Displays the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " + currentName + " - " + currentFrequencyAmount
                        + " " + currentFrequencyInterval + " - " + currentDate));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private String getDate(long time) {
        // Format time into a readable date
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormatter.format(time);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                mDbHelper.insertDummyHabits();
                displayDatabaseInfo(mDbHelper.readAllHabits());
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                mDbHelper.deleteAllHabits();
                displayDatabaseInfo(mDbHelper.readAllHabits());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
